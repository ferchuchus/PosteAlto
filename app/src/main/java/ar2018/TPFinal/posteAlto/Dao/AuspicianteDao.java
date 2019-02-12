package ar2018.TPFinal.posteAlto.Dao;

import java.util.List;

import ar2018.TPFinal.posteAlto.Modelo.Auspiciante;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AuspicianteDao {
    @GET("auspiciante/")
    Call<List<Auspiciante>> listarAuspiciantes();
}
