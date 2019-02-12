package ar2018.TPFinal.posteAlto.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar2018.TPFinal.posteAlto.Activity.TablaPosicionesActivity;
import ar2018.TPFinal.posteAlto.Adapter.FechaAdapter;
import ar2018.TPFinal.posteAlto.Dao.EquipoDao;
import ar2018.TPFinal.posteAlto.Dao.FechaDao;
import ar2018.TPFinal.posteAlto.Dao.PartidoDao;
import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import ar2018.TPFinal.posteAlto.Modelo.Fecha;
import ar2018.TPFinal.posteAlto.Modelo.Partido;
import ar2018.TPFinal.posteAlto.R;
import ar2018.TPFinal.posteAlto.RetrofitClient.RestClient;
import retrofit2.Call;
import retrofit2.Response;

public class FixtureFragment extends Fragment {
    static final int _EQUIPOS = 1;
    static final int _PARTIDOS = 2;
    static final int _FECHAS = 3;
    static final int ERROR = 4;
    private TextView txtFecha;
    private TextView txtEquipoLibre;
    private RecyclerView rvFecha;
    private ImageView imPrevious;
    private ImageView imNext;
    private FechaAdapter fechaAdapter;
    private List<Fecha> fechas = new ArrayList<>();
    private List<Partido> partidos = new ArrayList<>();
    private List<Partido> partidos2 = new ArrayList<>();
    private List<Equipo> equipos = new ArrayList<>();
    private List<String> nombresEquipos = new ArrayList<>();
    private Integer fechaEnPantalla = 1;
    private Integer totalFechas;

    public FixtureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fixture, container, false);
        txtFecha = v.findViewById(R.id.txtFecha);
        txtEquipoLibre = v.findViewById(R.id.txtEquipoLibre);
        rvFecha = v.findViewById(R.id.rvFecha);
        imPrevious = v.findViewById(R.id.ivPrevious);
        imNext = v.findViewById(R.id.ivNext);

        buscarFechas();

        imPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fechaEnPantalla == 1) {
                    fechaEnPantalla = totalFechas;
                    txtFecha.setText(fechas.get(totalFechas - 1).getNombre().toUpperCase());
                    buscarPartidosFecha(fechas.get(totalFechas - 1).getId());
                } else {
                    fechaEnPantalla--;
                    txtFecha.setText(fechas.get(fechaEnPantalla - 1).getNombre().toUpperCase());
                    buscarPartidosFecha(fechas.get(fechaEnPantalla - 1).getId());

                }
            }
        });
        imNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fechaEnPantalla > fechas.size()) {
                    fechaEnPantalla = 1;
                    txtFecha.setText(fechas.get(fechaEnPantalla - 1).getNombre().toUpperCase());
                    buscarPartidosFecha(fechas.get(fechaEnPantalla - 1).getId());
                } else {
                    fechaEnPantalla++;
                    txtFecha.setText(fechas.get(fechaEnPantalla - 2).getNombre().toUpperCase());
                    buscarPartidosFecha(fechas.get(fechaEnPantalla - 2).getId());


                }
            }
        });
        return v;
    }

    private void buscarFechas() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                FechaDao fechaDAO = RestClient.getInstance().getRetrofit().create(FechaDao.class);
                Call<List<Fecha>> callFechas = fechaDAO.listarFechas();
                try {
                    Response<List<Fecha>> response = callFechas.execute();
                    fechas = response.body();
                    totalFechas = fechas.size();
                    Message mensaje = handler.obtainMessage(_FECHAS);
                    mensaje.sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message mensaje= handler.obtainMessage(ERROR);
                    mensaje.sendToTarget();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    private void buscarPartidosFechaBD(final Integer idFecha) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                PartidoDao partidoDao = RestClient.getInstance().getRetrofit().create(PartidoDao.class);
                Call<List<Partido>> callPartidos = partidoDao.listarPartidos();//EnFecha(idFecha);
                try {
                    Response<List<Partido>> response = callPartidos.execute();
                    partidos2 = response.body();
                    for (int i = 0; i < partidos2.size(); i++) {
                        if (partidos2.get(i).getFechaCompetencia().getId() == idFecha)
                            partidos.add(partidos2.get(i));
                    }
                    Message mensaje = handler.obtainMessage(_PARTIDOS);
                    mensaje.sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message mensaje= handler.obtainMessage(ERROR);
                    mensaje.sendToTarget();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    private void buscarPartidosFecha(final Integer idFecha) {
        partidos.clear();
        for (int i = 0; i < partidos2.size(); i++) {
            if (partidos2.get(i).getFechaCompetencia().getId() == idFecha)
                partidos.add(partidos2.get(i));
        }
        determinarEquipoLibre();
    }

    private void determinarEquipoLibreBD() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                EquipoDao equipoDao = RestClient.getInstance().getRetrofit().create(EquipoDao.class);
                Call<List<Equipo>> callEquipos = equipoDao.listarEquipos();
                try {
                    Response<List<Equipo>> response = callEquipos.execute();
                    equipos = response.body();
                    for (int i = 0; i < equipos.size(); i++) {
                        nombresEquipos.add(equipos.get(i).getNombre());
                    }
                    for (int i = 0; i < partidos.size(); i++) {
                        nombresEquipos.remove(partidos.get(i).getLocal().getNombre());
                        nombresEquipos.remove(partidos.get(i).getVisitante().getNombre());
                    }

                    Message mensaje = handler.obtainMessage(_EQUIPOS);
                    mensaje.sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message mensaje = handler.obtainMessage(ERROR);
                    mensaje.sendToTarget();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    private void determinarEquipoLibre() {
        nombresEquipos.clear();
        for (int i = 0; i < equipos.size(); i++) {
            nombresEquipos.add(equipos.get(i).getNombre());
        }
        for (int i = 0; i < partidos.size(); i++) {
            nombresEquipos.remove(partidos.get(i).getLocal().getNombre());
            nombresEquipos.remove(partidos.get(i).getVisitante().getNombre());
        }
        terminarCrearComponentes();
    }

    private void terminarCrearComponentes() {
        if (nombresEquipos.size() == 1) {
            txtEquipoLibre.setText("LIBRE: " + nombresEquipos.get(0));
        } else {
            txtEquipoLibre.setText(" ");
        }
        fechaAdapter = new FechaAdapter(partidos);
        rvFecha.setAdapter(fechaAdapter);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case _FECHAS:
                    txtFecha.setText(fechas.get(fechaEnPantalla - 1).getNombre().toUpperCase());
                    buscarPartidosFechaBD(fechas.get(fechaEnPantalla - 1).getId());
                    break;
                case _PARTIDOS:
                    determinarEquipoLibreBD();
                    break;
                case _EQUIPOS:
                    txtEquipoLibre.setText("LIBRE: " + nombresEquipos.get(0));
                    fechaEnPantalla++;
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    rvFecha.setLayoutManager(llm);
                    fechaAdapter = new FechaAdapter(partidos);
                    rvFecha.setAdapter(fechaAdapter);
                    break;
                case ERROR:
                    AlertDialog alertDialog= new AlertDialog.Builder(getContext()).create();
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
                    break;
            }
        }

    };

}
