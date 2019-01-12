package ar2018.TPFinal.posteAlto.Dao;

import java.util.List;

import ar2018.TPFinal.posteAlto.Modelo.Equipo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EquipoDao {

    @GET("equipo/")
    public Call<List<Equipo>> listarEquipos();

    @GET("equipo/{id}")
    public Call<Equipo> buscarEquipoPorId(@Path("id") int idEquipo);
}
