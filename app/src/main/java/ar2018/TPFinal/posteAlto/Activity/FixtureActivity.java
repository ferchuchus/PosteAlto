package ar2018.TPFinal.posteAlto.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ar2018.TPFinal.posteAlto.Fragment.FechaFragment;
import ar2018.TPFinal.posteAlto.Fragment.FixtureFragment;
import ar2018.TPFinal.posteAlto.Fragment.MapaFragment;
import ar2018.TPFinal.posteAlto.Fragment.VerCategoriaFragment;
import ar2018.TPFinal.posteAlto.Modelo.Fecha;
import ar2018.TPFinal.posteAlto.R;

public class FixtureActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener,
        FixtureFragment.OnFixtureInteractionListener {
    String lat;
    String lon;
    String dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture);
        Intent i = getIntent();
        if(i.getExtras()!=null) {
            lat = String.valueOf(i.getExtras().get("Latitud"));
            lon = String.valueOf( i.getExtras().get("Longitud"));
            dir = String.valueOf( i.getExtras().get("Direccion"));
            mostrarGimnasio(lat,lon,dir);
        }
        String tag = "fixture";
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new FixtureFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenido, fragment, tag)
                .addToBackStack(null)
                .show(fragment)
                .commit();
    }

    public void mostrarGimnasio(final String lat, final String lon, String dir) {
        String tag = "gimnasio";
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new MapaFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("mapas", 0);
            bundle.putString("lat",lat);
            bundle.putString("long",lon);
            bundle.putString("dir",dir);
            fragment.setArguments(bundle);
        }
        getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenido, fragment, tag)
                    .addToBackStack(null)
                    .show(fragment)
                    .commit();

    }

    @Override
    public Fragment crearFechaFragment(Fecha fecha) {
        String tag = fecha.getNombre();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new FechaFragment();
            //Buscar PARTIDOS DE LA FECHA
        }
        Bundle bundle = new Bundle();
        bundle.putString("fechaNro",tag);
        fragment.setArguments(bundle);
         getSupportFragmentManager()
                .beginTransaction();// --->  fragmentManager.executePendingTransactions() ??
        return fragment;
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
