package mx.edu.utez.proyectointegrador.modelo;

import java.sql.Date;

public class Falta {
   private int idFalta;
   private String matricula;
   private Date fechaFalta;
   private String justificada;
   //Constructores
    public Falta() {
    }
    public Falta(String matricula, Date fechaFalta, String justificada) {
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
    public Date getFechaFalta() {
        return fechaFalta;
    }
    public void setFechaFalta(Date fechaFalta) {
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
