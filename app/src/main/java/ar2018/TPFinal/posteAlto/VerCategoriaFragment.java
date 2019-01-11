package ar2018.TPFinal.posteAlto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class VerCategoriaFragment extends Fragment {
    private TextView titulo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_ver_categoria, container, false);
        titulo = (TextView) v.findViewById(R.id.txtTitulo);
        Bundle argumentos= getArguments();
        if(argumentos!=null) {
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
        return v;
    }


}