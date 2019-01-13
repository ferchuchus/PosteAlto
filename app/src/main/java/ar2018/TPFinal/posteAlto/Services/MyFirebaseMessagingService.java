package ar2018.TPFinal.posteAlto.Services;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ar2018.TPFinal.posteAlto.Receiver.EstadoPartidoReceiver;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData()!= null){
            enviarBrodcast();
        }
    }

    private void enviarBrodcast(){
        Intent i= new Intent(MyFirebaseMessagingService.this, EstadoPartidoReceiver.class);
        i.setAction("Partido.Estado.Iniciado");
        sendBroadcast(i);
    }
}
