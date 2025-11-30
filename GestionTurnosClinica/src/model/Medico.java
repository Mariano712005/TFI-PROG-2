package model;
public class Medico extends Persona {

    private String especialidad;

    public Medico(String dni, String nombreCompleto, String telefono, String email, String especialidad) {
        super(dni, nombreCompleto, telefono, email);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String mostrar() {
        return "Medico: " + super.mostrar() + " - Especialidad: " + especialidad;
    }
}