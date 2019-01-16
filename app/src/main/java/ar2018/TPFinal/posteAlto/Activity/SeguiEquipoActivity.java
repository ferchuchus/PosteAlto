package ar2018.TPFinal.posteAlto.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar2018.TPFinal.posteAlto.Adapter.SeguiEquipoAdapter;
import ar2018.TPFinal.posteAlto.Adapter.TablaAdapter;
import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import ar2018.TPFinal.posteAlto.Modelo.Imagen;
import ar2018.TPFinal.posteAlto.R;

public class SeguiEquipoActivity extends AppCompatActivity {
    ListView lvSeguiEquipo;
    List<Equipo> listaEquipos= new ArrayList<Equipo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segui_equipo);

        //Buscar equipos en BD.

        //BORRAR CARGO DATOS PARA PROBAR QUE LA INTERFAZ ANDA
        Equipo uno=new Equipo();
        uno.setNombre("Recreativo A");
        Imagen iUno=new Imagen();
        iUno.setImagen("R.mipmap.recreativo_a");
        uno.setImagen(iUno);
        Log.d("UNO","Tiene: "+uno);
        listaEquipos.add(uno);
        Equipo dos=new Equipo();
        dos.setNombre("Recreativo B");
        Imagen iDos=new Imagen();
        iDos.setImagen("R.mipmap.recreativo_a");
        dos.setImagen(iDos);
        listaEquipos.add(dos);

        lvSeguiEquipo = (ListView) findViewById(R.id.lvSegui);
        lvSeguiEquipo.setAdapter(new SeguiEquipoAdapter(getApplicationContext(),listaEquipos));
    }
}
