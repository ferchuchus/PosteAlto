package ar2018.TPFinal.posteAlto.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ar2018.TPFinal.posteAlto.Dao.EquipoDao;
import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import ar2018.TPFinal.posteAlto.RetrofitClient.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    static final int TODOS_LOS_EQUIPOS = 1;
    GoogleMap mapa;
    CameraUpdate cu;
    int llamadoDe;
    List<Equipo> equipos;
    Double latitud;
    Double longitud;
    String direccion;
    String nombre;


    public MapaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        llamadoDe = 0;
        Bundle argumentos = getArguments();
        if (argumentos != null) {
            llamadoDe = argumentos.getInt("mapas");
            if (llamadoDe == 2) {
                latitud = Double.parseDouble(argumentos.getString("lat"));
                longitud = Double.parseDouble(argumentos.getString("long"));
                direccion = String.valueOf(argumentos.getString("dir"));
                nombre=String.valueOf(argumentos.getString("nom"));
            }
        }
        getMapAsync(this);


        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        switch (llamadoDe) {
            case 1:
                obtenerTodosLosEquipos();
                break;
            case 2:
                mostrarGimnasio();
                break;
        }

    }

    private void obtenerTodosLosEquipos() {
        EquipoDao equipoDao = RestClient.getInstance().getRetrofit().create(EquipoDao.class);
        Call<List<Equipo>> callEquipos = equipoDao.listarEquipos();
        callEquipos.enqueue(new Callback<List<Equipo>>() {
            @Override
            public void onResponse(Call<List<Equipo>> call, Response<List<Equipo>> response) {
                switch (response.code()) {
                    case 200:
                        ArrayList<LatLng> latLangList = new ArrayList<>();
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        equipos = response.body();
                        for (Equipo e : equipos) {
                            LatLng latLng = new LatLng(Double.parseDouble(e.getLatitud()), Double.parseDouble(e.getLongitud()));
                            mapa.addMarker(new MarkerOptions().position(latLng).title(e.getNombre()).snippet(e.getDireccion()));
                            latLangList.add(latLng);
                            builder.include(latLng);
                        }
                        LatLngBounds latLngBounds = builder.build();
                        cu = CameraUpdateFactory.newLatLngBounds(latLngBounds, 1); //ERROR EN MI CELU CON 0
                        mapa.moveCamera(cu);
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

    private void mostrarGimnasio() {
        LatLng latLng = new LatLng(latitud, longitud);
        mapa.addMarker(new MarkerOptions().position(latLng).title(nombre).snippet(direccion));
        cu = CameraUpdateFactory.newLatLngZoom(latLng, 13.2f);
        mapa.moveCamera(cu);
        }
}
