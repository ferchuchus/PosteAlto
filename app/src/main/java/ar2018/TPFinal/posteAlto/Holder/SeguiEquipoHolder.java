package ar2018.TPFinal.posteAlto.Holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import ar2018.TPFinal.posteAlto.R;

public class SeguiEquipoHolder {
    public ImageView ivImagen;
    public TextView tvEquipo;
    public CheckBox cbSigue;



    public SeguiEquipoHolder(View base) {
        ivImagen = (ImageView) base.findViewById(R.id.imImagen);
        tvEquipo = (TextView) base.findViewById(R.id.tvEquipo);
        cbSigue = (CheckBox) base.findViewById(R.id.cbSigue);



    }
}

