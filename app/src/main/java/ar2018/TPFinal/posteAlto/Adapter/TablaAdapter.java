package ar2018.TPFinal.posteAlto.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ar2018.TPFinal.posteAlto.Holder.TablaHolder;
import ar2018.TPFinal.posteAlto.Modelo.FilaTabla;
import ar2018.TPFinal.posteAlto.R;

public class TablaAdapter extends ArrayAdapter<FilaTabla> {
    private Context contexto;
    private List<FilaTabla> listaEquiposOrdenada;

    public TablaAdapter(Context context, List<FilaTabla> listaEquipos) {
        super(context, R.layout.activity_tabla_posiciones,listaEquipos);
        this.contexto = context;
        this.listaEquiposOrdenada = listaEquipos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View fila = convertView;
        if (fila == null) {
            fila = inflater.inflate(R.layout.fila_tabla, null);
        }
        TablaHolder holder = (TablaHolder) fila.getTag();

        if (holder == null) {
            holder = new TablaHolder(fila);
            fila.setTag(holder);
        }

        FilaTabla filaEquipo = super.getItem(position);
        holder.ivImagen.setImageBitmap(filaEquipo.getImagenEquipo());
        //CONVERTIR LA IMAGEN COMO VOS SABES ---> holder.ivImagen.setImageBitmap(equipo.getImagen().getImagen());
        holder.tvEquipo.setText(filaEquipo.getNombreEquipo());
        holder.tvPG.setText(String.valueOf(filaEquipo.getPg()));
        holder.tvPP.setText(String.valueOf(filaEquipo.getPp()));
        holder.tvTF.setText(String.valueOf(filaEquipo.getTf()));
        holder.tvTC.setText(String.valueOf(filaEquipo.getTc()));
        holder.tvD.setText(String.valueOf(filaEquipo.getD()));
        if ((position+1)/2==0) {
            holder.tvEquipo.setTextColor(Color.parseColor("#000000"));
            holder.tvPG.setTextColor(Color.parseColor("#000000"));
            holder.tvPP.setTextColor(Color.parseColor("#000000"));
            holder.tvTF.setTextColor(Color.parseColor("#000000"));
            holder.tvTC.setTextColor(Color.parseColor("#000000"));
            holder.tvD.setTextColor(Color.parseColor("#000000"));
        }
        return fila;
    }
}
