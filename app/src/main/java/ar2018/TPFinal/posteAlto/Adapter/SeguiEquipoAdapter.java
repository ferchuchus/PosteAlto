package ar2018.TPFinal.posteAlto.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import java.util.List;

import ar2018.TPFinal.posteAlto.Holder.SeguiEquipoHolder;
import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import ar2018.TPFinal.posteAlto.R;

public class SeguiEquipoAdapter extends ArrayAdapter<Equipo> {
    private Context contexto;
    private List<Equipo> listaEquipos;
    SharedPreferences preferences;

    public SeguiEquipoAdapter(Context context, List<Equipo> listaEquipos) {
        super(context, R.layout.activity_segui_equipo,listaEquipos);
        this.contexto = context;
        this.listaEquipos= listaEquipos;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        preferences= PreferenceManager.getDefaultSharedPreferences(contexto);
        View fila = convertView;
        if (fila == null) {
            fila = inflater.inflate(R.layout.fila_segui_equipo, null);
        }
        SeguiEquipoHolder holder = (SeguiEquipoHolder) fila.getTag();

        if (holder == null) {
            holder = new SeguiEquipoHolder(fila);
            fila.setTag(holder);
        }

        final Equipo filaEquipo = super.getItem(position);
        String encodeImage= filaEquipo.getImagen().getImagen();
        String pureCodeBase64= encodeImage.substring(encodeImage.indexOf(",")+1);
        byte[] decodeString= Base64.decode(pureCodeBase64, Base64.DEFAULT);
        holder.ivImagen.setImageBitmap(BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length));
        holder.tvEquipo.setText(filaEquipo.getNombre());
        holder.cbSigue.setChecked(preferences.getBoolean(filaEquipo.getNombre(), false));

        holder.cbSigue.setTag(position);
        final SeguiEquipoHolder finalHolder = holder;
        holder.cbSigue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= preferences.edit();
                String nombre=filaEquipo.getNombre();
                Boolean isChecked= finalHolder.cbSigue.isChecked();
                editor.putBoolean(nombre, isChecked);
                editor.apply();
            }
        });

        return fila;
    }
}
