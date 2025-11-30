package model;
import interfaces.Mostrable;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno implements Mostrable {

    public static final String ESTADO_PENDIENTE = "PENDIENTE";
    public static final String ESTADO_CANCELADO = "CANCELADO";
    public static final String ESTADO_ATENDIDO  = "ATENDIDO";

    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;

    public Turno(int id, Paciente paciente, Medico medico, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = ESTADO_PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    @Override
    public String mostrar() {
        return "Turno #" + id +
                " | " + paciente.mostrar() +
                " | " + medico.mostrar() +
                " | Fecha: " + fecha +
                " " + hora +
                " | Estado: " + estado;
    }

    @Override
    public String toString() {
        return mostrar();
    }
}