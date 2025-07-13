package mx.edu.utez.proyectointegrador.modelo;

import java.sql.Timestamp;

public class Falta {
   private int idFalta;
   private String matricula;
   private Timestamp fechaFalta;
   private String justificada;
   //Constructores
    public Falta() {
    }
    public Falta(String matricula, Timestamp fechaFalta, String justificada) {
        this.matricula = matricula;
        this.fechaFalta = fechaFalta;
        this.justificada = justificada;
    }
    //Id de falta
    public int getIdFalta() {
        return idFalta;
    }
    public void setIdFalta(int idFalta) {
        this.idFalta = idFalta;
    }
    //Matricula
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    //Fecha de la falta
    public Timestamp getFechaFalta() {
        return fechaFalta;
    }
    public void setFechaFalta(Timestamp fechaFalta) {
        this.fechaFalta = fechaFalta;
    }
    //La falta esta justificada?
    public String getJustificada() {
        return justificada;
    }
    public void setJustificada(String justificada) {
        this.justificada = justificada;
    }
}
