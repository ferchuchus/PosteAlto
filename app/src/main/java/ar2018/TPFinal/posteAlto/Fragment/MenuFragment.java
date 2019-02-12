package ar2018.TPFinal.posteAlto.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar2018.TPFinal.posteAlto.Dao.AuspicianteDao;
import ar2018.TPFinal.posteAlto.Dao.NoticiaDao;
import ar2018.TPFinal.posteAlto.Modelo.Auspiciante;
import ar2018.TPFinal.posteAlto.Modelo.Noticia;
import ar2018.TPFinal.posteAlto.R;
import ar2018.TPFinal.posteAlto.RetrofitClient.RestClient;
import retrofit2.Call;
import retrofit2.Response;

import static java.lang.Thread.sleep;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    static final int MOSTRAR_NOTICIA=1;
    static final int ERROR=2;
    static final int MOSTRAR_AUSPICIANTE=3;
    private Button btnCatA;
    private Button btnCatB;
    private Button btnCatC;
    List<Noticia> listarNoticias= new ArrayList<>();
    List<Auspiciante> listarAuspiciantes= new ArrayList<>();
    private Noticia noticia;
    private Auspiciante auspiciante;
    private ImageView imagenNoticia;
    private ImageView imgAuspiciante;
    private TextView textAuspiciante;
    private OnVerCategoriaListener listener;

    public MenuFragment() {
        // Required empty public constructor
    }

    public interface OnVerCategoriaListener {
        void mostrarCategoria(String cat);
    }

    public void setListener(OnVerCategoriaListener listener) {
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        btnCatA = (Button) v.findViewById(R.id.btnCatA);
        btnCatB = (Button) v.findViewById(R.id.btnCatB);
        btnCatC = (Button) v.findViewById(R.id.btnCatC);
        imagenNoticia= (ImageView) v.findViewById(R.id.imageNoticia);
        imgAuspiciante=(ImageView) v.findViewById(R.id.imagenPublicidad);
        textAuspiciante=(TextView) v.findViewById(R.id.txtPublicidad);
        noticias();
        auspiciantes();
        btnCatA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cat="A";
                listener.mostrarCategoria(cat);
            }
        });
        btnCatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.mostrarCategoria("B");
            }
        });
        btnCatC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.mostrarCategoria("C");
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (OnVerCategoriaListener) context;
        }
        catch (Exception e){
            Log.v("test", "Error, no se ha podido llevar a cabo la comunicación");
        }
    }

    void noticias(){
        Runnable r= new Runnable() {
            @Override
            public void run() {

                NoticiaDao noticiaDao= RestClient.getInstance().getRetrofit().create(NoticiaDao.class);
                Call<List<Noticia>> callNoticias= noticiaDao.listarNoticias();
                try {
                    Response<List<Noticia>> responseEquipos= callNoticias.execute();
                    listarNoticias = responseEquipos.body();
                    int i=0;
                    while(true){
                        noticia=listarNoticias.get(i);
                        Message mensaje= handler.obtainMessage(MOSTRAR_NOTICIA);
                        mensaje.sendToTarget();
                        sleep(3000);
                        i++;
                        if(i==listarNoticias.size()) i=0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message mensaje= handler.obtainMessage(ERROR);
                    mensaje.sendToTarget();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t= new Thread(r);
        t.start();
    }

    void auspiciantes(){
        Runnable r= new Runnable() {
            @Override
            public void run() {

                AuspicianteDao auspicianteDao= RestClient.getInstance().getRetrofit().create(AuspicianteDao.class);
                Call<List<Auspiciante>> callAuspiciante= auspicianteDao.listarAuspiciantes();
                try {
                    Response<List<Auspiciante>> responseAuspiciante= callAuspiciante.execute();
                    listarAuspiciantes = responseAuspiciante.body();
                    int i=0;
                    while(true){
                        auspiciante=listarAuspiciantes.get(i);
                        Message mensaje= handler.obtainMessage(MOSTRAR_AUSPICIANTE);
                        mensaje.sendToTarget();
                        sleep(4500);
                        i++;
                        if(i==listarAuspiciantes.size()) i=0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message mensaje= handler.obtainMessage(ERROR);
                    mensaje.sendToTarget();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t= new Thread(r);
        t.start();

    }

    Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String encodeImage= noticia.getImagen().getImagen();
                    String pureCodeBase64= encodeImage.substring(encodeImage.indexOf(",")+1);
                    byte[] decodeString= Base64.decode(pureCodeBase64, Base64.DEFAULT);
                    imagenNoticia.setImageBitmap(BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length));
                    break;
                case 2:
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
                case 3:
                    String encodeImageA= auspiciante.getImagen().getImagen();
                    String pureCodeBase64A= encodeImageA.substring(encodeImageA.indexOf(",")+1);
                    byte[] decodeStringA= Base64.decode(pureCodeBase64A, Base64.DEFAULT);
                    imgAuspiciante.setImageBitmap(BitmapFactory.decodeByteArray(decodeStringA, 0, decodeStringA.length));
                    textAuspiciante.setText(auspiciante.getImagen().getNombre().toUpperCase()+": "+auspiciante.getImagen().getDescripcion());
                    break;
            }
        }
    };
}
