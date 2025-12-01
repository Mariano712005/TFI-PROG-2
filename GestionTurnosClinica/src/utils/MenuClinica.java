package utils;

import exceptions.DatosObligatoriosException;
import exceptions.FechaHoraInvalidaException;
import exceptions.TurnoNoDisponibleException;
import model.Medico;
import model.Paciente;
import model.Turno;
import services.Clinica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class MenuClinica {

    private Clinica clinica;
    private Scanner scanner;

    public MenuClinica(Clinica clinica) {
        this.clinica = clinica;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = 0;
        do {
            System.out.println("\n===== SISTEMA DE GESTION DE TURNOS =====");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Registrar medico");
            System.out.println("3. Registrar turno");
            System.out.println("4. Listar pacientes");
            System.out.println("5. Listar medicos");
            System.out.println("6. Listar turnos");
            System.out.println("7. Listar turnos por fecha");
            System.out.println("8. Cancelar turno");
            System.out.println("9. Marcar turno como atendido");
            System.out.println("10. Salir");
            System.out.print("Opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Opción inválida. Intente otra vez.");
                continue;
            }


            switch (opcion) {
                case 1 -> registrarPaciente();
                case 2 -> registrarMedico();
                case 3 -> registrarTurno();
                case 4 -> listarPacientes();
                case 5 -> listarMedicos();
                case 6 -> listarTurnos();
                case 7 -> listarTurnosPorFecha();
                case 8 -> cancelarTurno();
                case 9 -> marcarTurnoAtendido();
                case 10 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 10);
    }

    // ---- Opciones ----

    private void registrarPaciente() {
        System.out.println("\n--- Registrar Paciente ---");
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Telefono: ");
        String tel = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            Paciente paciente = clinica.registrarPaciente(dni, nombre, tel, email);
            System.out.println("Paciente registrado: " + paciente.mostrar());
        } catch (DatosObligatoriosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarMedico() {
        System.out.println("\n--- Registrar Medico ---");
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Telefono: ");
        String tel = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Especialidad: ");
        String esp = scanner.nextLine();

        try {
            Medico medico = clinica.registrarMedico(dni, nombre, tel, email, esp);
            System.out.println("Medico registrado: " + medico.mostrar());
        } catch (DatosObligatoriosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarTurno() {
        System.out.println("\n--- Registrar Turno ---");
        System.out.print("DNI Paciente: ");
        String dniPaciente = scanner.nextLine();
        System.out.print("DNI Medico: ");
        String dniMedico = scanner.nextLine();
        System.out.print("Fecha (AAAA-MM-DD): ");
        String fechaStr = scanner.nextLine();
        System.out.print("Hora (HH:MM): ");
        String horaStr = scanner.nextLine();

        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            LocalTime hora = LocalTime.parse(horaStr);

            Turno turno = clinica.registrarTurno(dniPaciente, dniMedico, fecha, hora);
            System.out.println("Turno registrado: " + turno.mostrar());

        } catch (DatosObligatoriosException |
                 FechaHoraInvalidaException |
                 TurnoNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error de formato en fecha/hora.");
        }
    }

    private void listarPacientes() {
        System.out.println("\n--- Pacientes ---");
        List<Paciente> lista = clinica.listarPacientes();
        if (lista.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            lista.forEach(p -> System.out.println(p.mostrar()));
        }
    }

    private void listarMedicos() {
        System.out.println("\n--- Medicos ---");
        List<Medico> lista = clinica.listarMedicos();
        if (lista.isEmpty()) {
            System.out.println("No hay medicos registrados.");
        } else {
            lista.forEach(m -> System.out.println(m.mostrar()));
        }
    }

    private void listarTurnos() {
        System.out.println("\n--- Turnos ---");
        List<Turno> lista = clinica.listarTurnos();
        if (lista.isEmpty()) {
            System.out.println("No hay turnos registrados.");
        } else {
            lista.forEach(t -> System.out.println(t.mostrar()));
        }
    }

    private void listarTurnosPorFecha() {
        System.out.print("\nFecha (AAAA-MM-DD): ");
        String fechaStr = scanner.nextLine();
        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            List<Turno> lista = clinica.listarTurnosPorFecha(fecha);
            if (lista.isEmpty()) {
                System.out.println("No hay turnos para esa fecha.");
            } else {
                lista.forEach(t -> System.out.println(t.mostrar()));
            }
        } catch (Exception e) {
            System.out.println("Formato de fecha invalido.");
        }
    }

    private void cancelarTurno() {
        System.out.print("\nID de turno a cancelar: ");
        int id = leerEntero();
        boolean ok = clinica.cancelarTurno(id);
        if (ok){
            System.out.println("Turno cancelado.");
        }else {
            System.out.println("No se pudo cancelar (ID invalido).");
        }
    }

    private void marcarTurnoAtendido() {
        System.out.print("\nID de turno a marcar como atendido: ");
        int id = leerEntero();
        boolean ok = clinica.marcarTurnoAtendido(id);
        if (ok){
            System.out.println("Turno marcado como ATENDIDO");
        }else{
            System.out.println("No se pudo marcar como atendido.");
        }
    }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }
}
