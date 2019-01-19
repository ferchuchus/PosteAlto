package ar2018.TPFinal.posteAlto.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar2018.TPFinal.posteAlto.Adapter.SeguiEquipoAdapter;
import ar2018.TPFinal.posteAlto.Adapter.TablaAdapter;
import ar2018.TPFinal.posteAlto.Dao.EquipoDao;
import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import ar2018.TPFinal.posteAlto.Modelo.Imagen;
import ar2018.TPFinal.posteAlto.R;
import ar2018.TPFinal.posteAlto.RetrofitClient.RestClient;
import retrofit2.Call;
import retrofit2.Response;

public class SeguiEquipoActivity extends AppCompatActivity {
    static final int EQUIPOS_CARGADOS=1;
    ListView lvSeguiEquipo;
    List<Equipo> listaEquipos= new ArrayList<Equipo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segui_equipo);

        buscarEquipos();

        lvSeguiEquipo = (ListView) findViewById(R.id.lvSegui);

    }

    private void buscarEquipos(){
        Runnable r= new Runnable() {
            @Override
            public void run() {
                EquipoDao equipoDao= RestClient.getInstance().getRetrofit().create(EquipoDao.class);
                Call<List<Equipo>> callEquipos= equipoDao.listarEquipos();
                try {
                    Response<List<Equipo>> response=callEquipos.execute();
                    listaEquipos=response.body();
                    Message mensaje= handler.obtainMessage(EQUIPOS_CARGADOS);
                    mensaje.sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t= new Thread(r);
        t.start();
    }

    Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==EQUIPOS_CARGADOS)
                lvSeguiEquipo.setAdapter(new SeguiEquipoAdapter(getApplicationContext(),listaEquipos));
        }
    };
}
