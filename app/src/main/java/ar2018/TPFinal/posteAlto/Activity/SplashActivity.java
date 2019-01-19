package ar2018.TPFinal.posteAlto.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import ar2018.TPFinal.posteAlto.Dao.PartidoDao;
import ar2018.TPFinal.posteAlto.Modelo.Partido;
import ar2018.TPFinal.posteAlto.R;
import ar2018.TPFinal.posteAlto.Receiver.EstadoPartidoReceiver;
import ar2018.TPFinal.posteAlto.RetrofitClient.RestClient;
import retrofit2.Call;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 3000; // 3 segundos
    static final int ESTADO=1;
    static final int AVISO_PARTIDO=2;
    Partido partido;
    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(SplashActivity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        }, DURACION_SPLASH);
        Bundle argumentos= getIntent().getExtras();
        if(argumentos!=null){
            if(Boolean.parseBoolean(argumentos.getString("avisoEstado")))
                enviarBrodcastEstado(argumentos.getInt("idPartido"));
            else enviarBrodcastAviso(argumentos.getInt("idPartido"));
        }
    }

    private void enviarBrodcastAviso(final int idPartido) {
        Runnable r= new Runnable() {
            @Override
            public void run() {
                PartidoDao partidoDao= RestClient.getInstance().getRetrofit().create(PartidoDao.class);
                Call<Partido> partidoCall= partidoDao.buscarPartidoPorId(idPartido);
                try {
                    Response<Partido> response= partidoCall.execute();
                    partido=response.body();
                    Message message= handler.obtainMessage(AVISO_PARTIDO);
                    message.sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t= new Thread(r);
        t.start();

    }

    private void enviarBrodcastEstado(final int idPartido){
        Runnable r= new Runnable() {
            @Override
            public void run() {
                PartidoDao partidoDao= RestClient.getInstance().getRetrofit().create(PartidoDao.class);
                Call<Partido> partidoCall= partidoDao.buscarPartidoPorId(idPartido);
                try {
                    Response<Partido> response= partidoCall.execute();
                    partido=response.body();
                    Message message= handler.obtainMessage(ESTADO);
                    message.sendToTarget();
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
            if(preferences.getBoolean(partido.getLocal().getNombre(), false) ||
                    preferences.getBoolean(partido.getVisitante().getNombre(), false)){
                Intent i= new Intent(getApplicationContext(), EstadoPartidoReceiver.class);
                i.putExtra("local", partido.getLocal().getNombre());
                i.putExtra("visitante", partido.getVisitante().getNombre());
                switch(msg.what){
                    case ESTADO:
                        switch (partido.getEstado()){
                            case 1:
                                i.setAction("Partido.Estado.Iniciado");
                                break;
                            case 2:
                                i.setAction("Partido.Estado.Fin2doCuarto");
                                i.putExtra("tantosL", partido.getResultado().getTantosL());
                                i.putExtra("tantosV", partido.getResultado().getTantosV());
                                break;
                            case 3:
                                i.setAction("Partido.Estado.Finalizado");
                                i.putExtra("tantosL", partido.getResultado().getTantosL());
                                i.putExtra("tantosV", partido.getResultado().getTantosV());
                                break;
                        }

                        break;
                    case AVISO_PARTIDO:
                        i.setAction("aviso partido proximo");
                        i.putExtra("timeStamp", partido.getFecha());
                        break;
                }
                sendBroadcast(i);
            }

        }
    };
}
