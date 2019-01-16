package ar2018.TPFinal.posteAlto.Receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import ar2018.TPFinal.posteAlto.R;

public class EstadoPartidoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent pendingIntent=null;
        NotificationCompat.Builder notification=null;
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        switch(intent.getAction()){
            case "Partido.Estado.Iniciado":
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pendingIntent= PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    notification= new NotificationCompat.Builder(context, "CANAL01")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("PARTIDO INCIADO")
                            .setContentText("Equipo A vs Equipo B ha comenzado")
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
                    break;

        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification.build());
    }

}
