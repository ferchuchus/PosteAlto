package ar2018.TPFinal.posteAlto.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ar2018.TPFinal.posteAlto.Adapter.FechaAdapter;
import ar2018.TPFinal.posteAlto.Modelo.Partido;
import ar2018.TPFinal.posteAlto.R;


public class FechaFragment extends Fragment {
    private TextView txtFecha;
    private TextView txtEquipoLibre;
    private RecyclerView rvFecha;
    private List<Partido> partidos;
    private FechaAdapter fechaAdapter;
    private OnFechaFragmentListener mListener;

    public FechaFragment() {
        // Required empty public constructor
    }

    public interface OnFechaFragmentListener {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fecha, container, false);
        txtFecha = v.findViewById(R.id.txtFecha);
        txtEquipoLibre = v.findViewById(R.id.txtEquipoLibre);
        rvFecha = v.findViewById(R.id.rvFecha);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvFecha.setLayoutManager(mLayoutManager);

        fechaAdapter = new FechaAdapter(partidos);
        rvFecha.setAdapter(fechaAdapter);

        Bundle argumentos = getArguments();
        if (argumentos != null) {
            String fe = argumentos.getString("fechaNro", "");
            txtFecha.setText("FECHA "+fe);
            buscarPartidosFecha();
            }
        return v;
    }

    private void buscarPartidosFecha() {
        //Entrar a base de datos y buscar Partidos!
        txtEquipoLibre.setText(partidos.get(partidos.size()).getLocal().getNombre());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFechaFragmentListener) {
            mListener = (OnFechaFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
