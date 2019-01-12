package ar2018.TPFinal.posteAlto.Dao;

import java.util.List;

import ar2018.TPFinal.posteAlto.Modelo.Noticia;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NoticiaDao {

    @GET("noticia/")
    Call<List<Noticia>> listarNoticias();
}
