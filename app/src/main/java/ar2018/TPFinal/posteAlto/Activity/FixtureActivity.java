package ar2018.TPFinal.posteAlto.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import ar2018.TPFinal.posteAlto.Fragment.FixtureFragment;
import ar2018.TPFinal.posteAlto.Fragment.MapaFragment;

import ar2018.TPFinal.posteAlto.R;

public class FixtureActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{
    String lat;
    String lon;
    String dir;
    String nom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture);
    //    shouldDisplayHomeUp();
        Intent i = getIntent();
        if(i.getExtras()!=null) {
            lat = String.valueOf(i.getExtras().get("Latitud"));
            lon = String.valueOf( i.getExtras().get("Longitud"));
            dir = String.valueOf( i.getExtras().get("Direccion"));
            nom = String.valueOf( i.getExtras().get("NombreClub"));
            mostrarGimnasio(lat,lon,dir,nom);
        }else {
            String tag = "fixture";
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment == null) {
                fragment = new FixtureFragment();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenido, fragment, tag)
                    //.addToBackStack(null)
                    .show(fragment)
                    .commit();
        }
    }

    public void mostrarGimnasio(final String lat, final String lon, String dir,String nom) {
        String tag = "gimnasio";
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new MapaFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("mapas", 2);
            bundle.putString("lat",lat);
            bundle.putString("long",lon);
            bundle.putString("dir",dir);
            bundle.putString("nom",nom);
            fragment.setArguments(bundle);
        }
        getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenido, fragment, tag)
                    //.addToBackStack(null)
                    .show(fragment)
                    .commit();

    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp() {
        boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
        // getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
        ActionBar actionBar= getSupportActionBar();
        Log.d("ACTION BAR", "ab="+actionBar);
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
