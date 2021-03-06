package ar2018.TPFinal.posteAlto.Modelo;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public class FilaTabla implements Comparable<FilaTabla> {
    private String nombreEquipo;
    private Bitmap imagenEquipo;
    private Integer pg;
    private Integer pp;
    private Integer tf;
    private Integer tc;
    private Integer d;

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public Bitmap getImagenEquipo() {
        return imagenEquipo;
    }

    public void setImagenEquipo(Bitmap imagenEquipo) {
        this.imagenEquipo = imagenEquipo;
    }

    public Integer getPg() {
        return pg;
    }

    public void setPg(Integer pg) {
        this.pg = pg;
    }

    public Integer getPp() {
        return pp;
    }

    public void setPp(Integer pp) {
        this.pp = pp;
    }

    public Integer getTf() {
        return tf;
    }

    public void setTf(Integer tf) {
        this.tf = tf;
    }

    public Integer getTc() {
        return tc;
    }

    public void setTc(Integer tc) {
        this.tc = tc;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    @Override
    public int compareTo(@NonNull FilaTabla o) {
        return o.pg.compareTo(this.pg);
    }
}
