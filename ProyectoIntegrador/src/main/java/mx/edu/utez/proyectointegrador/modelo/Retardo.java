package mx.edu.utez.proyectointegrador.modelo;

import java.sql.Date;
import java.sql.Timestamp;

public class Retardo {
    private int numRetardo;
    private String matricula;
    private Date fechaRetardo;
    private Timestamp horaEntrada;
    private Timestamp tiempoRetardo;
    private String justificado;
    //Constructores
    public Retardo() {}
    public Retardo(int numRetardo, String matricula, Date fechaRetardo, Timestamp horaEntrada, Timestamp tiempoRetardo, String justificado) {
        this.numRetardo = numRetardo;
        this.matricula = matricula;
        this.fechaRetardo = fechaRetardo;
        this.horaEntrada = horaEntrada;
        this.tiempoRetardo = tiempoRetardo;
        this.justificado = justificado;
    }
    //Numero de retardo
    public int getNumRetardo() {
        return numRetardo;
    }
    public void setNumRetardo(int numRetardo) {
        this.numRetardo = numRetardo;
    }
    //Matricula
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    //Fecha del retardo
    public Date getFechaRetardo() {
        return fechaRetardo;
    }
    public void setFechaRetardo(Date fechaRetardo) {
        this.fechaRetardo = fechaRetardo;
    }
    //Hora de entrada
    public Timestamp getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(Timestamp horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    //Tiempo de retardo
    public Timestamp getTiempoRetardo() {
        return tiempoRetardo;
    }
    public void setTiempoRetardo(Timestamp tiempoRetardo) {
        this.tiempoRetardo = tiempoRetardo;
    }
    //Esta justificado?
    public String getJustificado() {
        return justificado;
    }
    public void setJustificado(String justificado) {
        this.justificado = justificado;
    }
}
