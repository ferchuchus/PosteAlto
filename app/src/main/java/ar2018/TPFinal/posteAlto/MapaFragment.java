package ar2018.TPFinal.posteAlto;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar2018.TPFinal.posteAlto.Dao.EquipoDao;
import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import ar2018.TPFinal.posteAlto.RetrofitClient.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    static final int TODOS_LOS_EQUIPOS=1;
    GoogleMap mapa;
    int llamdoDe;
    List<Equipo> equipos;
    public MapaFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= super.onCreateView(inflater, container, savedInstanceState);
        llamdoDe=0;
        Bundle argumentos= getArguments();
        if(argumentos !=null) llamdoDe=argumentos.getInt("mapas");
        getMapAsync(this);


        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa=googleMap;
        switch (llamdoDe){
            case 1:
                obtenerTodosLosEquipos();
                break;
        }

    }

    private void obtenerTodosLosEquipos() {
        EquipoDao equipoDao = RestClient.getInstance().getRetrofit().create(EquipoDao.class);
        Call<List<Equipo>> callEquipos= equipoDao.listarEquipos();
        callEquipos.enqueue(new Callback<List<Equipo>>() {
            @Override
            public void onResponse(Call<List<Equipo>> call, Response<List<Equipo>> response) {
                ArrayList<LatLng> latLangList= new ArrayList<>();
                LatLngBounds.Builder builder= new LatLngBounds.Builder();
                equipos= response.body();
                for(Equipo e:equipos){
                    LatLng latLng= new LatLng(Long.parseLong(e.getLatitud()),Long.parseLong(e.getLongitud()));
                    mapa.addMarker(new MarkerOptions().position(latLng));
                    latLangList.add(latLng);
                    builder.include(latLng);
                }
                LatLngBounds latLngBounds= builder.build();
                CameraUpdate cu= CameraUpdateFactory.newLatLngBounds(latLngBounds, 0);
                mapa.moveCamera(cu);
            }

            @Override
            public void onFailure(Call<List<Equipo>> call, Throwable t) {

            }
        });
    }
}
