package ar2018.TPFinal.posteAlto.Dao;

import ar2018.TPFinal.posteAlto.Modelo.Resultado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ResultadoDao {

    @GET("resultado/{id}")
    Call<Resultado> buscarResultadoPorId(@Path("id") int idResultado);

}
