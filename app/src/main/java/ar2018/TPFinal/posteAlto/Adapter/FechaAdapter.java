package ar2018.TPFinal.posteAlto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ar2018.TPFinal.posteAlto.Activity.FixtureActivity;
import ar2018.TPFinal.posteAlto.Modelo.Partido;
import ar2018.TPFinal.posteAlto.R;

public class FechaAdapter extends RecyclerView.Adapter<FechaAdapter.FechaHolder> {
    private List<Partido> partidos;
    private ViewGroup context;

    public static class FechaHolder extends RecyclerView.ViewHolder{
        public TextView txtFechaHora;
        public ImageView ivEquipo1;
        public ImageView ivEquipo2;
        public TextView txtEquipo1;
        public TextView txtEquipo2;
        public TextView txtRdo1;
        public TextView txtRdo2;
        public TextView txtClub;


        public FechaHolder(View base){
            super(base);
            this.txtFechaHora = (TextView) base.findViewById(R.id.txtFechaHora);
            this.ivEquipo1 = (ImageView) base.findViewById(R.id.ivEquipo1);
            this.ivEquipo2 = (ImageView) base.findViewById(R.id.ivEquipo2);
            this.txtEquipo1 = (TextView) base.findViewById(R.id.txtEquipo1);
            this.txtEquipo2 = (TextView) base.findViewById(R.id.txtEquipo2);
            this.txtRdo1 = (TextView) base.findViewById(R.id.txtRdo1);
            this.txtRdo2 = (TextView) base.findViewById(R.id.txtRdo2);
            this.txtClub = (TextView) base.findViewById(R.id.txtClub);
        }

    }

    public FechaAdapter(List<Partido> datos) {
        partidos = datos;
    }

    @NonNull
    @Override
    public FechaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fila_fecha, viewGroup, false);
        context=viewGroup;
        FechaHolder vh = new FechaHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FechaHolder fechaHolder, int i) {
        final Partido partido = partidos.get(i);

        //Fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        fechaHolder.txtFechaHora.setText(sdf.format(Long.valueOf(partido.getFecha())*1000));

        //IMAGENES!
        String encodeImageL= partido.getLocal().getImagen().getImagen();
        String pureCodeBase64L= encodeImageL.substring(encodeImageL.indexOf(",")+1);
        byte[] decodeStringL= Base64.decode(pureCodeBase64L, Base64.DEFAULT);
        fechaHolder.ivEquipo1.setImageBitmap(BitmapFactory.decodeByteArray(decodeStringL, 0, decodeStringL.length));
        String encodeImage= partido.getVisitante().getImagen().getImagen();
        String pureCodeBase64= encodeImage.substring(encodeImage.indexOf(",")+1);
        byte[] decodeString= Base64.decode(pureCodeBase64, Base64.DEFAULT);
        fechaHolder.ivEquipo2.setImageBitmap(BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length));

        fechaHolder.txtEquipo1.setText(partido.getLocal().getNombre());
        fechaHolder.txtEquipo2.setText(partido.getVisitante().getNombre());
        fechaHolder.txtRdo1.setText(String.valueOf(partido.getResultado().getTantosL()));
        fechaHolder.txtRdo2.setText(String.valueOf(partido.getResultado().getTantosV()));
        fechaHolder.txtClub.setText(partido.getLocal().getDireccion());
        fechaHolder.txtClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getContext(), FixtureActivity.class);
                i.putExtra("Latitud", partido.getLocal().getLatitud());
                i.putExtra("Longitud", partido.getLocal().getLongitud());
                i.putExtra("Direccion",partido.getLocal().getDireccion());
                context.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return partidos.size();
    }




}
