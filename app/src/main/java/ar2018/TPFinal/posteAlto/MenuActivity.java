package ar2018.TPFinal.posteAlto;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Button btnCatA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        btnCatA = (Button) findViewById(R.id.btnCatA);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.navview);
        navView.setItemIconTintList(null);
        modificarAspecto();
        shouldDisplayHomeUp();


        btnCatA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "categoriaA";
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new VerCategoriaFragment();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenido, fragment, tag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        boolean fragmentTransaction = false;
                        Fragment fragment = null;
                        String tag = "";
                        switch (menuItem.getItemId()) {
                            case R.id.optInicio:
                                Intent i = new Intent(getBaseContext(), MenuActivity.class);
                                startActivity(i);
                                break;
                            case R.id.opCatA:
                                tag = "categoriaA";
                                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                                if (fragment == null) {
                                    fragment = new VerCategoriaFragment();
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
                                            ((MapaFragment) fragment).setListener(MenuActivity.this);
                                        }
                                        fragmentTransaction = true;
                                        break;*/
                        }
                        if (fragmentTransaction) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.contenido, fragment, tag)
                                    .addToBackStack(null)
                                    .commit();

                            menuItem.setChecked(true);

                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });


    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.homeAsUp:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }*/
        return super.onOptionsItemSelected(item);
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
}
