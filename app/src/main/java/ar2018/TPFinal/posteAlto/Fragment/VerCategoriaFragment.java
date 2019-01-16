package ar2018.TPFinal.posteAlto.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ar2018.TPFinal.posteAlto.Activity.TablaPosicionesActivity;
import ar2018.TPFinal.posteAlto.R;


public class VerCategoriaFragment extends Fragment {
    private TextView titulo;
    private Button btnFixture;
    private Button btnTablaPosiciones;
    private Button btnSeguiTuEquipo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ver_categoria, container, false);
        titulo = v.findViewById(R.id.txtTitulo);
        btnFixture = v.findViewById(R.id.btnFix);
        btnTablaPosiciones = v.findViewById(R.id.btnPosiciones);
        btnSeguiTuEquipo = v.findViewById(R.id.btnEqui);
        Bundle argumentos = getArguments();
        if (argumentos != null) {
            String cat = argumentos.getString("MostarCategoria", "");
            switch (cat) {
                case "A":
                    titulo.setText("LIGA PARANAENSE A");
                    break;
                case "B":
                    titulo.setText("LIGA PARANAENSE B");
                    break;
                case "C":
                    titulo.setText("LIGA PARANAENSE C");
                    break;
            }
        }
        btnFixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getContext(), FixtureActivity.class);
                startActivity(intent);*/
            }
        });
        btnTablaPosiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TablaPosicionesActivity.class);
                startActivity(intent);
            }
        });
        btnSeguiTuEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* Intent intent = new Intent(getContext(), SeleccionEquiposActivity.class);
                startActivity(intent);*/
            }
        });
        return v;
    }


}