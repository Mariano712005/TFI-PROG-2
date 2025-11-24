package model;

import java.util.Objects;

public class Jugador {
    private String nombre;
    private String nickname;
    private int edad;
    private int kills;
    private int deaths;

    public Jugador(String nombre, String nickname, int edad) {
        this.nombre = nombre;
        this.nickname = nickname;
        this.edad = edad;
        this.kills = 0;
        this.deaths = 0;
    }

    public double getKDRatio() {
        // Evita la división por cero
        return deaths == 0 ? kills : (double) kills / deaths;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public String getNickname() { return nickname; }
    public int getEdad() { return edad; }
    public int getKills() { return kills; }
    public int getDeaths() { return deaths; }
    public void setKills(int kills) { this.kills = kills; }
    public void setDeaths(int deaths) { this.deaths = deaths; }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(nombre, jugador.nombre);
    }

    // Metodo Hash
    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }
}