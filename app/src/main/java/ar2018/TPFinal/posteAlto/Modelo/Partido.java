package ar2018.TPFinal.posteAlto.Modelo;

import com.google.gson.annotations.SerializedName;

public class Partido {

    public enum Estado {Creado, Iniciado, Fin2doCuarto, Finalizado}

    private int id;
    private int estado;
    private String fecha;
    private Equipo local;
    private Equipo visitante;
    @SerializedName("idResultado")
    private Resultado resultado;
    @SerializedName("idFecha")
    private Fecha fechaCompetencia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Equipo getLocal() {
        return local;
    }

    public void setLocal(Equipo local) {
        this.local = local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    public void setVisitante(Equipo visitante) {
        this.visitante = visitante;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Fecha getFechaCompetencia() {
        return fechaCompetencia;
    }

    public void setFechaCompetencia(Fecha fechaCompetencia) {
        this.fechaCompetencia = fechaCompetencia;
    }
}
