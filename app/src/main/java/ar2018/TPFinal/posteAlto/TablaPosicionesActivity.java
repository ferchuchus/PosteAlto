package ar2018.TPFinal.posteAlto;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import ar2018.TPFinal.posteAlto.Modelo.FilaTabla;
import ar2018.TPFinal.posteAlto.Modelo.Imagen;

public class TablaPosicionesActivity extends AppCompatActivity {
    ListView lvTablaPosiciones;
    List<Equipo> listaEquipos= new ArrayList<Equipo>();
    List<FilaTabla> listaOrdenada= new ArrayList<FilaTabla>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_posiciones);

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

        listaOrdenada=calcularTabla(listaEquipos);
        lvTablaPosiciones = (ListView) findViewById(R.id.lvTabla);
        lvTablaPosiciones.setAdapter(new TablaAdapter(getApplicationContext(), listaOrdenada));

    }

    private List<FilaTabla> calcularTabla(List<Equipo> listaEquipos) {
        ArrayList<FilaTabla> listaOrd = new ArrayList<FilaTabla>();
        FilaTabla fT;
        //buscar en base de datos  según equipo y hacer calculos

        //BORRAR ES SOLO PARA PROBAR QUE LA INTERFAZ ANDA!
        int i=0;
        for(Equipo e :listaEquipos){
            //buscar  calcular
            fT= new FilaTabla();
            fT.setNombreEquipo(e.getNombre());
            fT.setImagenEquipo(e.getImagen().getImagen());
            fT.setD(-555);
            if(i==0) {
                fT.setPg(55);
            }else {
                fT.setPg(5);
            }
            fT.setPp(55);
            fT.setTc(5555);
            fT.setTf(5555);
            Log.d("Tiene", "agrego: " + fT.getNombreEquipo());
            listaOrd.add(fT);
            Log.d("Tiene", "agregó: " + listaOrd.get(i).getNombreEquipo());
            i++;
        }

        //Ordena según PartidosGanados
        Collections.sort(listaOrd);
        return listaOrd;
    }
}
