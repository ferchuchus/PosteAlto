package ar2018.TPFinal.posteAlto.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ar2018.TPFinal.posteAlto.Adapter.FechaAdapter;
import ar2018.TPFinal.posteAlto.Adapter.PasarFechasAdapter;
import ar2018.TPFinal.posteAlto.Dao.FechaDao;
import ar2018.TPFinal.posteAlto.Dao.PartidoDao;
import ar2018.TPFinal.posteAlto.Modelo.Fecha;
import ar2018.TPFinal.posteAlto.Modelo.Partido;
import ar2018.TPFinal.posteAlto.R;
import ar2018.TPFinal.posteAlto.RetrofitClient.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FixtureFragment extends Fragment {
    private ViewPager viewPager;
    private List<Fragment> fechas;
    private List<Fecha> fechasBD;

    private OnFixtureInteractionListener mListener;

    public FixtureFragment() {
        // Required empty public constructor
    }

    public interface OnFixtureInteractionListener {
       Fragment crearFechaFragment(Fecha fecha);
    }
 lalala
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_fixture, container, false);
        txtFecha = v.findViewById(R.id.txtFecha);
        txtEquipoLibre = v.findViewById(R.id.txtEquipoLibre);
        rvFecha = v.findViewById(R.id.rvFecha);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvFecha.setLayoutManager(mLayoutManager);

        Bundle argumentos = getArguments();
        if (argumentos != null) {
            String fe = argumentos.getString("fechaNro", "");
            txtFecha.setText(fe.toUpperCase());
            buscarPartidosFecha(fe);
        }
        return v;
        buscarFechas();
        return v;
    }

    private void buscarPartidosFecha(String fecha) {
        PartidoDao partidoDao = RestClient.getInstance().getRetrofit().create(PartidoDao.class);
        Call<List<Partido>> callPartidos = partidoDao.listarParidosEnFecha(fecha);
        Log.d("VER","NOMBRE: "+callPartidos);
        callPartidos.enqueue(new Callback<List<Partido>>() {
            @Override
            public void onResponse(Call<List<Partido>> call, Response<List<Partido>> response) {
                switch (response.code()) {
                    case 200:
                        fechaAdapter = new FechaAdapter(response.body());
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
            public void onFailure(Call<List<Partido>> call, Throwable t) {

            }
        });
        txtEquipoLibre.setText(partidos.get(partidos.size()).getLocal().getNombre());
    }

    private void buscarFechas() {
        Log.d("VER","SI ENTRO");
        //buscar fechas en la Base de datos y ordenarlas, luego crear el list de Fragments
        FechaDao equipoDao = RestClient.getInstance().getRetrofit().create(FechaDao.class);
        Call<List<Fecha>> callEquipos = equipoDao.listarFechas();
        Log.d("VER","NOMBRE: "+callEquipos);
        callEquipos.enqueue(new Callback<List<Fecha>>() {
            @Override
            public void onResponse(Call<List<Fecha>> call, Response<List<Fecha>> response) {
                switch (response.code()) {
                    case 200:
                        fechasBD = response.body();
                        for (Fecha f : fechasBD) {
                            Log.d("TRAJO FECHAS","NOMBRE: "+f.getNombre());
                            Fragment fag=mListener.crearFechaFragment(f);
                            fechas.add(fag);
                        }
                        viewPager.setAdapter(new PasarFechasAdapter(getContext(), fechas));
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFixtureInteractionListener) {
            mListener = (OnFixtureInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
