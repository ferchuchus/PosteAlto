package ar2018.TPFinal.posteAlto.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ar2018.TPFinal.posteAlto.Holder.SeguiEquipoHolder;
import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import ar2018.TPFinal.posteAlto.R;

public class SeguiEquipoAdapter extends ArrayAdapter<Equipo> {
    private Context contexto;
    private List<Equipo> listaEquipos;

    public SeguiEquipoAdapter(Context context, List<Equipo> listaEquipos) {
        super(context, R.layout.activity_segui_equipo,listaEquipos);
        this.contexto = context;
        this.listaEquipos= listaEquipos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View fila = convertView;
        if (fila == null) {
            fila = inflater.inflate(R.layout.fila_segui_equipo, null);
        }
        SeguiEquipoHolder holder = (SeguiEquipoHolder) fila.getTag();

        if (holder == null) {
            holder = new SeguiEquipoHolder(fila);
            fila.setTag(holder);
        }

        Equipo filaEquipo = super.getItem(position);
        holder.ivImagen.setImageResource(R.mipmap.recreativo_a);
        //CONVERTIR LA IMAGEN COMO VOS SABES ---> holder.ivImagen.setImageBitmap(equipo.getImagen().getImagen());
        holder.tvEquipo.setText(filaEquipo.getNombre());
        holder.cbSigue.setChecked(false);

        return fila;
    }
}
