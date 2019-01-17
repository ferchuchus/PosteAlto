package ar2018.TPFinal.posteAlto.Dao;

import java.util.List;

import ar2018.TPFinal.posteAlto.Modelo.Partido;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PartidoDao {

    @GET("partido/{id}")
    Call<Partido> buscarPartidoPorId(@Path("id") int idPartido);

    @GET("partido/idfecha/{nombre}")
    Call<List<Partido>> listarParidosEnFecha(@Path("nombre") String fecha);
}
