package ar2018.TPFinal.posteAlto.Dao;

import ar2018.TPFinal.posteAlto.Modelo.Partido;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PartidoDao {

    @GET("partido/{id}")
    Call<Partido> buscarPartidoPorId(@Path("id") int idPartido);
}
