package ar2018.TPFinal.posteAlto;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;

public class PrincipalActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener,
        MenuFragment.OnVerCategoriaListener {
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    //private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        shouldDisplayHomeUp();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.navview);
        navView.setItemIconTintList(null);
        modificarAspecto();
        contenidoPpalMenu();
        createNotificationChannel();


        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        boolean fragmentTransaction = false;
                        Fragment fragment = null;
                        String tag = "";
                        switch (menuItem.getItemId()) {
                            case R.id.optInicio:
                                tag = "menu";
                                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                                if (fragment == null) {
                                    fragment = new MenuFragment();
                                }
                                fragmentTransaction = true;

                                break;
                            case R.id.opCatA:
                                tag = "categoria";
                                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                                if (fragment == null) {
                                    fragment = new VerCategoriaFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("MostarCategoria", "C");
                                    fragment.setArguments(bundle);
                                }
                                fragmentTransaction = true;
                                break;
                            case R.id.opCatB:
                                tag = "categoria";
                                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                                if (fragment == null) {
                                    fragment = new VerCategoriaFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("MostarCategoria", "B");
                                    fragment.setArguments(bundle);
                                }
                                fragmentTransaction = true;
                                break;
                            case R.id.opCatC:
                                tag = "categoria";
                                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                                if (fragment == null) {
                                    fragment = new VerCategoriaFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("MostarCategoria", "C");
                                    fragment.setArguments(bundle);
                                }
                                fragmentTransaction = true;
                                break;
                                 /*   case R.id.optGym:
                                        tag = "gimnasios";
                                        fragment = getSupportFragmentManager().findFragmentByTag(tag);
                                        if (fragment == null) {
                                            fragment = new MapaFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("mapas", 1);
                                            fragment.setArguments(bundle);
                                            ((MapaFragment) fragment).setListener(PrincipalActivity.this);
                                        }
                                        fragmentTransaction = true;
                                        break;*/
                        }
                        if (fragmentTransaction) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.contenido, fragment, tag)
                                    .addToBackStack(null)
                                    .show(fragment)
                                    .commit();

                            menuItem.setChecked(true);
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });


    }

    private void contenidoPpalMenu() {
        String tag = "menu";
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new MenuFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenido, fragment, tag)
                .addToBackStack(null)
                .show(fragment)
                .commit();
    }

    private void modificarAspecto() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MenuItem tools1 = navView.getMenu().findItem(R.id.opCat);
        SpannableString spanString1 = new SpannableString(tools1.getTitle());
        spanString1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.letras_y_titulos)), 0, spanString1.length(), 0);
        tools1.setTitle(spanString1);

        MenuItem tools2 = navView.getMenu().findItem(R.id.optRedesSociales);
        SpannableString spanString2 = new SpannableString(tools2.getTitle());
        spanString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.letras_y_titulos)), 0, spanString2.length(), 0);
        tools2.setTitle(spanString2);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void mostrarCategoria(String cat) {
        String tag = "categoria";
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new VerCategoriaFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("MostarCategoria", cat);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenido, fragment)
                .commit();
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name= getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel= new NotificationChannel("CANAL01", name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
