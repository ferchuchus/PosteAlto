package ar2018.TPFinal.posteAlto.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ar2018.TPFinal.posteAlto.R;

public class TablaHolder {
    public ImageView ivImagen;
    public TextView tvEquipo;
    public TextView tvPG;
    public TextView tvPP;
    public TextView tvTF;
    public TextView tvTC;
    public TextView tvD;



    public TablaHolder(View base) {
        ivImagen = (ImageView) base.findViewById(R.id.imImagen);
        tvEquipo = (TextView) base.findViewById(R.id.tvEquipo);
        tvPG = (TextView) base.findViewById(R.id.tvPG);
        tvPP = (TextView) base.findViewById(R.id.tvPP);
        tvTF = (TextView) base.findViewById(R.id.tvTF);
        tvTC = (TextView) base.findViewById(R.id.tvTC);
        tvD = (TextView) base.findViewById(R.id.tvD);


    }
}
