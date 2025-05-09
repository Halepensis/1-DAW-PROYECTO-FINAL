package src.Classes;

import java.util.Objects;

public class Visitante {
    private int id;
    private String nombre,email;
    private int edad;

    public Visitante(String nombre, String email, int edad){
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }
    public Visitante(int id,String nombre, String email, int edad){
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }
    public int getEdad() {
        return edad;
    }
    public String getEmail() {
        return email;
    }
    public int getId() {
        return id;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Visitante visitante = (Visitante) o;
        return Objects.equals(email, visitante.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "nombre:'" + nombre + '\'' +
                ", email:'" + email + '\'' +
                ", edad:" + edad;
    }
}
