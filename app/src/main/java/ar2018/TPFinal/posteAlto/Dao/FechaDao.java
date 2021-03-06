package ar2018.TPFinal.posteAlto.Dao;

import java.util.List;

import ar2018.TPFinal.posteAlto.Modelo.Fecha;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FechaDao {

    @GET("fecha/{id}")
    Call<Fecha> buscarFechaPorId(@Path("id") int idFecha);

    @GET("fecha/")
    Call<List<Fecha>> listarFechas();
}
