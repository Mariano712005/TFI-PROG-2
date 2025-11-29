package model;

public class Paciente extends Persona {

    public Paciente(String dni, String nombreCompleto, String telefono, String email) {
        super(dni, nombreCompleto, telefono, email);
    }

    @Override
    public String mostrar() {
        return "Paciente: " + super.mostrar();
    }
}
