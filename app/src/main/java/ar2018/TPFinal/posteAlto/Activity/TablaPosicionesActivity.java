package ar2018.TPFinal.posteAlto.Activity;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ListView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import ar2018.TPFinal.posteAlto.Dao.EquipoDao;
import ar2018.TPFinal.posteAlto.Dao.PartidoDao;
import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import ar2018.TPFinal.posteAlto.Modelo.FilaTabla;
import ar2018.TPFinal.posteAlto.Modelo.Partido;
import ar2018.TPFinal.posteAlto.R;
import ar2018.TPFinal.posteAlto.Adapter.TablaAdapter;
import ar2018.TPFinal.posteAlto.RetrofitClient.RestClient;
import retrofit2.Call;
import retrofit2.Response;

public class TablaPosicionesActivity extends AppCompatActivity {
    static final int LISTAS_CARGADAS=1;
    static final int ERROR=2;
    ListView lvTablaPosiciones;
    List<Equipo> listaEquipos= new ArrayList<Equipo>();
    List<FilaTabla> listaOrdenada= new ArrayList<FilaTabla>();
    List<Partido> listaPartidos= new ArrayList<>();
    List<Partido> listaPartidosTRegular= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_posiciones);

        buscarEquiposYPartidos();
        lvTablaPosiciones = (ListView) findViewById(R.id.lvTabla);

    }

    private List<FilaTabla> calcularTabla() {
        ArrayList<FilaTabla> listaOrd = new ArrayList<FilaTabla>();
        FilaTabla fT;
        int pg,pp,tf,tc;
        for(Equipo e:listaEquipos){
            pg=0;pp=0;tf=0;tc=0;
            fT= new FilaTabla();
            for(Partido p:listaPartidosTRegular){
                if(e.getId()==p.getLocal().getId()){
                    if(p.getResultado().getTantosL()>p.getResultado().getTantosV()) pg=pg+1;
                    else pp=pp+1;
                    tf=tf+p.getResultado().getTantosL();
                    tc=tc+p.getResultado().getTantosV();
                }
                else if(e.getId()==p.getVisitante().getId()){
                    if(p.getResultado().getTantosL()>p.getResultado().getTantosV()) pp=pp+1;
                    else pg=pg+1;
                    tf=tf+p.getResultado().getTantosV();
                    tc=tc+p.getResultado().getTantosL();
                }

            }
            String encode= e.getImagen().getImagen();
            String pureCodeBase64=encode.substring(encode.indexOf(",")+1);
            byte[] decodeString= Base64.decode(pureCodeBase64, Base64.DEFAULT);
            fT.setImagenEquipo(BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length));//falata proceso para convertir imagen
            fT.setNombreEquipo(e.getNombre());
            fT.setPg(pg);
            fT.setPp(pp);
            fT.setTf(tf);
            fT.setTc(tc);
            fT.setD(tf-tc);
            listaOrd.add(fT);
        }
        Collections.sort(listaOrd);
        return listaOrd;
    }

    private void buscarEquiposYPartidos(){
        Runnable r= new Runnable() {
            @Override
            public void run() {
                EquipoDao equipoDao= RestClient.getInstance().getRetrofit().create(EquipoDao.class);
                PartidoDao partidoDao=RestClient.getInstance().getRetrofit().create(PartidoDao.class);
                Calendar c= Calendar.getInstance();
                SimpleDateFormat sdfMes= new SimpleDateFormat("MM");
                SimpleDateFormat sdfAnio= new SimpleDateFormat("yyyy");
                int mes= Integer.parseInt(sdfMes.format(c.getTime()));
                int anio;
                if(mes<6) anio=Integer.parseInt(sdfAnio.format(c.getTime()))-1;
                else anio=Integer.parseInt(sdfAnio.format(c.getTime()));
                Call<List<Equipo>> callEquipos= equipoDao.listarEquipos();
                Call<List<Partido>> callPartidos= partidoDao.buscarPartidosPorAnio(anio);
                try {
                    Response<List<Equipo>> responseEquipos= callEquipos.execute();
                    Response<List<Partido>> responsePartidos= callPartidos.execute();
                    listaEquipos=responseEquipos.body();
                    listaPartidos=responsePartidos.body();
                    Message mensaje= handler.obtainMessage(LISTAS_CARGADAS);
                    mensaje.sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message mensaje= handler.obtainMessage(ERROR);
                    mensaje.sendToTarget();
                }
            }
        };
        Thread t= new Thread(r);
        t.start();
    }

    private void buscarPartidosTRegular(){
        String palabraABuscar="Fecha";
        for(Partido p:listaPartidos){
            String nombre=p.getFechaCompetencia().getNombre();
            String[] palabras=nombre.split("\\s+");
            for(String palabra:palabras){
                if(palabraABuscar.contains(palabra)) listaPartidosTRegular.add(p);
            }
        }
    }

    Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    buscarPartidosTRegular();
                    listaOrdenada=calcularTabla();
                    lvTablaPosiciones.setAdapter(new TablaAdapter(getApplicationContext(), listaOrdenada));
                    break;
                case 2:
                    AlertDialog alertDialog= new AlertDialog.Builder(TablaPosicionesActivity.this).create();
                    alertDialog.setTitle("Error de Conexión");
                    alertDialog.setMessage("Revise su conexión de internet y vuelva a ejecutar");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
            }
        }
    };
}
