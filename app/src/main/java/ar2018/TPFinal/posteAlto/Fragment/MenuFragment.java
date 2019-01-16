package ar2018.TPFinal.posteAlto.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ar2018.TPFinal.posteAlto.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    private Button btnCatA;
    private Button btnCatB;
    private Button btnCatC;
    private OnVerCategoriaListener listener;

    public MenuFragment() {
        // Required empty public constructor
    }

    public interface OnVerCategoriaListener {
        void mostrarCategoria(String cat);
    }

    public void setListener(OnVerCategoriaListener listener) {
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        btnCatA = (Button) v.findViewById(R.id.btnCatA);
        btnCatB = (Button) v.findViewById(R.id.btnCatB);
        btnCatC = (Button) v.findViewById(R.id.btnCatC);
        btnCatA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cat="A";
                listener.mostrarCategoria(cat);
            }
        });
        btnCatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.mostrarCategoria("B");
            }
        });
        btnCatC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.mostrarCategoria("C");
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (OnVerCategoriaListener) context;
        }
        catch (Exception e){
            Log.v("test", "Error, no se ha podido llevar a cabo la comunicación");
        }
    }

}
