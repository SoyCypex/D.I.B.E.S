package mx.edu.utez.proyectointegrador.modelo;

public class Falta {
   private int idFalta;
   private Alumno alumno;
   private String fechaFalta;
   private String justificada;
   //Constructores
    public Falta() {}
    public Falta(int idFalta, Alumno alumno, String fechaFalta, String justificada) {
        this.idFalta = idFalta;
        this.alumno = alumno;
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
    public Alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    //Fecha de la falta
    public String getFechaFalta() {
        return fechaFalta;
    }
    public void setFechaFalta(String fechaFalta) {
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
