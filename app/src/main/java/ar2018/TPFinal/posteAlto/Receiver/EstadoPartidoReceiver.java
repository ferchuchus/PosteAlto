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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ar2018.TPFinal.posteAlto.R;

public class EstadoPartidoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent pendingIntent=null;
        NotificationCompat.Builder notification=null;
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String mensaje= null;
        String titulo="Poste Alto";
        switch(intent.getAction()){
            case "Partido.Estado.Iniciado":
               // titulo=;
                mensaje="PARTIDO INICIADO\n"+intent.getExtras().getString("local")+" vs "+
                        intent.getExtras().getString("visitante")+" ha comenzado";
                break;
            case "Partido.Estado.Fin2doCuarto":
               // titulo=;
                mensaje="HA FINALIZADO EL 2DO CUARTO\n"+intent.getExtras().getString("local")+" "+intent.getExtras().getInt("tantosL")+
                        " - "+intent.getExtras().getInt("tantosV")+intent.getExtras().getString("visitante");
                break;
            case "Partido.Estado.Finalizado":
                //titulo
                mensaje="PARTIDO FINALIZADO\n"+intent.getExtras().getString("local")+" "+intent.getExtras().getInt("tantosL")+
                        " - "+intent.getExtras().getInt("tantosV")+intent.getExtras().getString("visitante");
                break;
            case "aviso partido proximo":
                long timeStamp= Long.parseLong(intent.getExtras().getString("timeStamp"));
                SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                String hora= sdf.format(new Date(timeStamp*1000));
                //titulo="Poste Alto";
                mensaje="AVISO DE PARTIDO\n"+ intent.getExtras().get("local")+" vs "+intent.getExtras().getString("visitante")+
                        " comenzara a las "+hora;

        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent= PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        notification= new NotificationCompat.Builder(context, "CANAL01")
                .setSmallIcon(R.mipmap.icon_ball)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification.build());
    }

}
