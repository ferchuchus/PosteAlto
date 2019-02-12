package ar2018.TPFinal.posteAlto.Modelo;

public class Noticia {

    private int id;
    private String titulo;
    private String bajada;
    private String autor;
    private String fuente;
    private String fechaDePublicacion;
    private Imagen imagen;
    private String cuerpo;

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getBajada() {
        return bajada;
    }

    public String getAutor() {
        return autor;
    }

    public String getFuente() {
        return fuente;
    }

    public String getFechaDePublicacion() {
        return fechaDePublicacion;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setBajada(String bajada) {
        this.bajada = bajada;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public void setFechaDePublicacion(String fechaDePublicacion) {
        this.fechaDePublicacion = fechaDePublicacion;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
}
