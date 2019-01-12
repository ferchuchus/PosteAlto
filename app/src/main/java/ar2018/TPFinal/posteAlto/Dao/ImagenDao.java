package ar2018.TPFinal.posteAlto.Dao;

import ar2018.TPFinal.posteAlto.Modelo.Imagen;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImagenDao {
    @GET("imagen/{id}")
    public Call<Imagen> buscarImagenId(@Path("id") int idImagen);
}
