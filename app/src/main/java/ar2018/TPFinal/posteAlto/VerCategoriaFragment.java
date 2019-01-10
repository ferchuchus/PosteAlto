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

import java.util.List;


public class VerCategoriaFragment extends Fragment {
   // private Button btnBuscar;

 /*   private OnVerCategoriaListener  listener;

   public interface OnVerCategoriaListener{
        void buscarReclamosTipo(String tipo);
    }

    public void setListener(OnVerCategoriaListener listener) {
        this.listener = listener;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_ver_categoria, container, false);
       /* btnBuscar= (Button) v.findViewById(R.id.btnBuscar);
        List<String> tiposReclamos= getNames();
        adapterTipoReclamo= new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, tiposReclamos);
        spinnerTipoReclamo.setAdapter(adapterTipoReclamo);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buscarReclamosTipo(spinnerTipoReclamo.getSelectedItem().toString());
            }
        });
*/
        return v;
    }


}