package ar2018.TPFinal.posteAlto.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ar2018.TPFinal.posteAlto.Adapter.PasarFechasAdapter;
import ar2018.TPFinal.posteAlto.R;

public class FixtureFragment extends Fragment {
    private ViewPager viewPager;
    private List<Fragment> fechas;

    private OnFixtureInteractionListener mListener;

    public FixtureFragment() {
        // Required empty public constructor
    }

    public interface OnFixtureInteractionListener {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_fixture, container, false);
        buscarFechas();
        viewPager = (ViewPager) v.findViewById(R.id.vpFechas);
        viewPager.setAdapter(new PasarFechasAdapter(this.getContext(), fechas));


        return v;
    }

    private void buscarFechas() {
        //buscar fechas en la Base de datos y ordenarlas, luego crear el list de Fragments
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFixtureInteractionListener) {
            mListener = (OnFixtureInteractionListener) context;
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
