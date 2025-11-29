package model;

import interfaces.Mostrable;

public abstract class Persona implements Mostrable {

    private String dni;
    private String nombreCompleto;
    private String telefono;
    private String email;

    public Persona(String dni, String nombreCompleto, String telefono, String email) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String mostrar() {
        return nombreCompleto + " (DNI " + dni + ")";
    }
}
