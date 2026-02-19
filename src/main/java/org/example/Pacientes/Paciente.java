package org.example.Pacientes;

import org.example.Pacientes.Interface.CicloMutacion;

import java.util.Objects;

public abstract class Paciente implements CicloMutacion {

    private String Id;
    private int nivelInfeccion;
    private int nivelSalud;
    private String genoma;

    public Paciente (String Id, int nivelInfeccion, int nivelSalud, String genoma, String tipoPaciente){
        try {

            if (Id == null || Id.isEmpty()) {
                throw new IllegalArgumentException();
            }
            if (nivelInfeccion <= 0 || nivelInfeccion >= 100) {
                throw new IllegalArgumentException();
            }

            if (nivelSalud <= 0 || nivelSalud >= 100) {
                throw new IllegalArgumentException();
            }
            if (genoma == null || genoma.isEmpty()) {
                throw new IllegalArgumentException();
            }

            this.Id = Id;
            this.nivelInfeccion = nivelInfeccion;
            this.nivelSalud = nivelSalud;
            this.genoma = genoma;

        }
        catch (Exception e){
            throw new IllegalArgumentException("Error al crear el Paciente");
        }

    }

    public String getId() {
        return Id;
    }
    public int getnivelInfeccion() {
        return nivelInfeccion;
    }
    public int getnivelSalud() {
        return nivelSalud;
    }
    public String getgenoma() {
        return genoma;
    }

    //metodo abstracto privado Ci
    //private abstract cicloMutacion int


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paciente paciente = (Paciente) obj;
        return Objects.equals(Id, paciente.Id) &&
                Objects.equals(nivelInfeccion, paciente.nivelInfeccion) &&
                Objects.equals(nivelSalud, paciente.nivelSalud) &&
                Objects.equals(genoma, paciente.genoma);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "Id='" + Id + '\'' +
                ", nivelInfeccion='" + nivelInfeccion + '\'' +
                ", nivelSalud='" + nivelSalud + '\'' +
                ", genoma='" + genoma + '\'' +
                '}';
    }

    @Override
    public int hashCode(){

        return Objects.hash(Id, nivelInfeccion, nivelSalud, genoma);

    }


}
