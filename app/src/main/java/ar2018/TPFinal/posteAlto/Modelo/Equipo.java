package ar2018.TPFinal.posteAlto.Modelo;

public class Equipo {

    private int id;
    private String nombre;
    private String direccion;
    private Imagen imagen;
    private long latitud;
    private long lingitud;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public long getLatitud() {
        return latitud;
    }

    public void setLatitud(long latitud) {
        this.latitud = latitud;
    }

    public long getLingitud() {
        return lingitud;
    }

    public void setLingitud(long lingitud) {
        this.lingitud = lingitud;
    }
}
