package ar2018.TPFinal.posteAlto.Services;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import ar2018.TPFinal.posteAlto.Dao.PartidoDao;
import ar2018.TPFinal.posteAlto.Modelo.Partido;
import ar2018.TPFinal.posteAlto.Receiver.EstadoPartidoReceiver;
import ar2018.TPFinal.posteAlto.RetrofitClient.RestClient;
import retrofit2.Call;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    static final int ESTADO=1;
    static final int AVISO_PARTIDO=2;
    Partido partido;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData()!= null){
            if(Boolean.parseBoolean(remoteMessage.getData().get("avisoEstado")))
                enviarBrodcastEstado(Integer.parseInt(remoteMessage.getData().get("idPartido")));
            else enviarBrodcastAviso(Integer.parseInt(remoteMessage.getData().get("idPartido")));
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
            Intent i= new Intent(MyFirebaseMessagingService.this, EstadoPartidoReceiver.class);
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
    };
}
