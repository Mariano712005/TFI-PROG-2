package services;

import exceptions.DatosObligatoriosException;
import exceptions.FechaHoraInvalidaException;
import exceptions.TurnoNoDisponibleException;
import model.Medico;
import model.Paciente;
import model.Turno;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Clinica {

    private List<Paciente> pacientes = new ArrayList<>();
    private List<Medico> medicos = new ArrayList<>();
    private List<Turno> turnos = new ArrayList<>();
    private int nextTurnoId = 1;

    // ---------- PACIENTES ----------

    public Paciente registrarPaciente(String dni, String nombre, String telefono, String email)
            throws DatosObligatoriosException {

        if (dni == null || dni.isBlank() || nombre == null || nombre.isBlank()) {
            throw new DatosObligatoriosException("DNI y nombre del paciente son obligatorios.");
        }

        if (buscarPacientePorDni(dni) != null) {
            throw new DatosObligatoriosException("Ya existe un paciente con DNI " + dni);
        }

        Paciente paciente = new Paciente(dni, nombre, telefono, email);
        pacientes.add(paciente);
        return paciente;
    }

    public List<Paciente> listarPacientes() {
        return new ArrayList<>(pacientes);
    }

    public Paciente buscarPacientePorDni(String dni) {
        for (Paciente paciente : pacientes) {
            if (paciente.getDni().equals(dni)) {
                return paciente;
            }
        }
        return null;
    }

    // ---------- MEDICOS ----------

    public Medico registrarMedico(String dni, String nombre, String telefono, String email, String especialidad)

            throws DatosObligatoriosException {

        if (dni == null || dni.isBlank() || nombre == null || nombre.isBlank() ||
                especialidad == null || especialidad.isBlank()) {
            throw new DatosObligatoriosException("DNI, nombre y especialidad del médico son obligatorios.");
        }

        if (buscarMedicoPorDni(dni) != null) {
            throw new DatosObligatoriosException("Ya existe un médico con DNI " + dni);
        }

        Medico medico = new Medico(dni, nombre, telefono, email, especialidad);
        medicos.add(medico);
        return medico;
    }

    public List<Medico> listarMedicos() {
        return new ArrayList<>(medicos);
    }

    public Medico buscarMedicoPorDni(String dni) {
        for (Medico medico : medicos) {
            if (medico.getDni().equals(dni)) {
                return medico;
            }
        }
        return null;
    }

    // ---------- TURNOS ----------

    public Turno registrarTurno(String dniPaciente, String dniMedico, LocalDate fecha, LocalTime hora)
            throws DatosObligatoriosException, FechaHoraInvalidaException, TurnoNoDisponibleException {

        Paciente paciente = buscarPacientePorDni(dniPaciente);
        Medico medico = buscarMedicoPorDni(dniMedico);

        if (paciente == null) {
            throw new DatosObligatoriosException("No existe paciente con DNI " + dniPaciente);
        }
        if (medico == null) {
            throw new DatosObligatoriosException("No existe médico con DNI " + dniMedico);
        }
        if (fecha == null || hora == null) {
            throw new DatosObligatoriosException("Fecha y hora del turno son obligatorias.");
        }

        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new FechaHoraInvalidaException("La fecha y hora del turno no pueden ser anteriores al momento actual.");
        }

        if (!estaDisponible(medico, fecha, hora)) {
            throw new TurnoNoDisponibleException("El médico ya tiene un turno en ese horario.");
        }

        Turno turno = new Turno(nextTurnoId++, paciente, medico, fecha, hora);
        turnos.add(turno);
        return turno;
    }

    private boolean estaDisponible(Medico medico, LocalDate fecha, LocalTime hora) {
        for (Turno turno : turnos) {
            if (turno.getMedico().getDni().equals(medico.getDni())
                    && turno.getFecha().equals(fecha)
                    && !turno.getEstado().equals(Turno.ESTADO_CANCELADO)
                    && turno.getHora().equals(hora)) {
                return false;
            }
        }
        return true;
    }

    public List<Turno> listarTurnos() {
        return new ArrayList<>(turnos);
    }

    public List<Turno> listarTurnosPorFecha(LocalDate fecha) {
        List<Turno> resultado = new ArrayList<>();
        for (Turno turno : turnos) {
            if (turno.getFecha().equals(fecha)) {
                resultado.add(turno);
            }
        }
        return resultado;
    }

    public Turno buscarTurnoPorId(int id) {
        for (Turno turno : turnos) {
            if (turno.getId() == id) {
                return turno;
            }
        }
        return null;
    }

    public boolean cancelarTurno(int id) {
        Turno turno = buscarTurnoPorId(id);
        if (turno != null && !turno.getEstado().equals(Turno.ESTADO_CANCELADO)) {
            turno.setEstado(Turno.ESTADO_CANCELADO);
            return true;
        }
        return false;
    }

    public boolean marcarTurnoAtendido(int id) {
        Turno turno = buscarTurnoPorId(id);
        if (turno != null && turno.getEstado().equals(Turno.ESTADO_PENDIENTE)) {
            turno.setEstado(Turno.ESTADO_ATENDIDO);
            return true;
        }
        return false;
    }
}
