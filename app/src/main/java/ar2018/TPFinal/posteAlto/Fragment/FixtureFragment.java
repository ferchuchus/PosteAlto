package ar2018.TPFinal.posteAlto.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar2018.TPFinal.posteAlto.Adapter.FechaAdapter;
import ar2018.TPFinal.posteAlto.Dao.EquipoDao;
import ar2018.TPFinal.posteAlto.Dao.FechaDao;
import ar2018.TPFinal.posteAlto.Dao.PartidoDao;
import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import ar2018.TPFinal.posteAlto.Modelo.Fecha;
import ar2018.TPFinal.posteAlto.Modelo.Partido;
import ar2018.TPFinal.posteAlto.R;
import ar2018.TPFinal.posteAlto.RetrofitClient.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FixtureFragment extends Fragment {
    static final String _URL =/*"http://192.168.0.13:5000/"*/"http://192.168.1.5:5000/";
    private TextView txtFecha;
    private TextView txtEquipoLibre;
    private RecyclerView rvFecha;
    private ImageView imPrevious;
    private ImageView imNext;
    private FechaAdapter fechaAdapter;
    private List<Fecha> fechas;
    private List<Partido> partidos;
    private List<Equipo> equipos;
    private List<String> nombresEquipos;
    private Integer fechaEnPantalla=1;
    private Integer totalFechas;

    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit retrofit=new Retrofit.Builder().baseUrl(_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

    public FixtureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_fixture, container, false);
        txtFecha = v.findViewById(R.id.txtFecha);
        txtEquipoLibre = v.findViewById(R.id.txtEquipoLibre);
        rvFecha = v.findViewById(R.id.rvFecha);
        imPrevious = v.findViewById(R.id.ivPrevious);
        imNext = v.findViewById(R.id.ivNext);

        fechas=buscarFechas();
        totalFechas=fechas.size();
        for (Fecha f : fechas) {
            Log.d("TRAJO FECHAS","NOMBRE: "+f.getNombre());
        }
        txtFecha.setText(fechas.get(fechaEnPantalla-1).getNombre().toUpperCase());
        partido=buscarPartidosFecha(fechas.get(fechaEnPantalla-1).getId());

        imPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   if(fechaEnPantalla==totalFechas){
                txtFecha.setText(fechas.get(fechaEnPantalla-1).getNombre().toUpperCase());
                buscarPartidosFecha(fechas.get(fechaEnPantalla-1).getId());
                }
                else if(fechaEnPantalla==1){

            }else{
                    txtFecha.setText(fechas.get(fechaEnPantalla-1).getNombre().toUpperCase());
                    buscarPartidosFecha(fechas.get(fechaEnPantalla-1).getId());
                }*/
            }
        });
        imNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFecha.setText(fechas.get(fechaEnPantalla-1).getNombre().toUpperCase());
                buscarPartidosFecha(fechas.get(fechaEnPantalla-1).getId());
            }
        });
        return v;
    }

    private List<Fecha> buscarFechas() {
       Log.d("VER","SI ENTRO");
        FechaDao fechaDAO = RestClient.getInstance().getRetrofit().create(FechaDao.class);
        Call<List<Fecha>> callEquipos = fechaDAO.listarFechas();
        Log.d("VER","NOMBRE: "+callEquipos);
        Response<List<Fecha>> response=null;
        try{
            response = invocacionSyn.execute();
        }catch (IOException e){
            e.printStackTrace();
        }
        return response.body();

        callEquipos.enqueue(new Callback<List<Fecha>>() {
            @Override
            public void onResponse(Call<List<Fecha>> call, Response<List<Fecha>> response) {
                switch (response.code()) {
                    case 200:
                        fechas = response.body();
                        totalFechas=fechas.size();
                        for (Fecha f : fechas) {
                            Log.d("TRAJO FECHAS","NOMBRE: "+f.getNombre());
                        }
                        txtFecha.setText(fechas.get(fechaEnPantalla-1).getNombre().toUpperCase());
                        buscarPartidosFecha(fechas.get(fechaEnPantalla-1).getId());
                        break;
                    case 400:
                        Toast.makeText(getContext(), "Bad Request",
                                Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(getContext(), "Internal Server Error",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "NO TA",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Fecha>> call, Throwable t) {

            }
        });

    }

    private void buscarPartidosFecha(Integer idFecha) {
        PartidoDao partidoDao = RestClient.getInstance().getRetrofit().create(PartidoDao.class);
        Call<List<Partido>> callPartidos = partidoDao.listarParidosEnFecha(idFecha);
        Log.d("VER","NOMBRE: "+callPartidos);
        callPartidos.enqueue(new Callback<List<Partido>>() {
            @Override
            public void onResponse(Call<List<Partido>> call, Response<List<Partido>> response) {
                switch (response.code()) {
                    case 200:
                        partidos = response.body();
                        for (Partido p : partidos) {
                            Log.d("TRAJO PARTIDOS","ID: "+p.getId());
                        }
                        determinarEquipoLibre();
                        break;
                    case 400:
                        Toast.makeText(getContext(), "Bad Request",
                                Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(getContext(), "Internal Server Error",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "NO TA",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Partido>> call, Throwable t) {

            }
        });
    }

    private void determinarEquipoLibre(){
        EquipoDao equipoDao= RestClient.getInstance().getRetrofit().create(EquipoDao.class);
        Call<List<Equipo>> callEquipos= equipoDao.listarEquipos();
        callEquipos.enqueue(new Callback<List<Equipo>>() {
            @Override
            public void onResponse(Call<List<Equipo>> call, Response<List<Equipo>> response) {
                switch (response.code()) {
                    case 200:
                        equipos = response.body();
                        for (Equipo e : equipos) {
                            Log.d("TRAJO Eqipos","NOMBRE: "+e.getNombre());
                            nombresEquipos.add(e.getNombre());
                        }
                        for(Partido p: partidos){
                            nombresEquipos.remove(p.getLocal().getNombre());
                            nombresEquipos.remove(p.getVisitante().getNombre());
                        }
                        txtEquipoLibre.setText(nombresEquipos.get(0));
                        fechaAdapter = new FechaAdapter(partidos);
                        rvFecha.setAdapter(fechaAdapter);
                        break;
                    case 400:
                        Toast.makeText(getContext(), "Bad Request",
                                Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(getContext(), "Internal Server Error",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "NO TA",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Equipo>> call, Throwable t) {

            }
        });
    }

}
