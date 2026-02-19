package org.example.Model.Pacientes;

import org.example.Model.Pacientes.Interface.CicloMutacion;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Paciente implements CicloMutacion {

    protected String Id;
    protected int nivelInfeccion;
    protected int nivelSalud;
    protected String genoma;

    public Paciente (String Id, int nivelInfeccion, int nivelSalud, String genoma){
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

    // Metodo para validar la cantidad de dosis que necesita por tipo de genoma
    public Map<String, Integer> generarReceta() {

        Map<String, Integer> receta = new HashMap<>();

        int i = 0;

        while (i < genoma.length()) {

            char tipo = genoma.charAt(i);

            // Validar que sea A, B u O
            if (tipo != 'A' && tipo != 'B' && tipo != 'O') {
                throw new IllegalArgumentException("Genoma inválido: " + genoma);
            }

            i++;

            StringBuilder numero = new StringBuilder();

            while (i < genoma.length() && Character.isDigit(genoma.charAt(i))) {
                numero.append(genoma.charAt(i));
                i++;
            }

            if (numero.length() == 0) {
                throw new IllegalArgumentException("Cantidad faltante en genoma: " + genoma);
            }

            int cantidad = Integer.parseInt(numero.toString());

            // Si ya existe el tipo, suma (más robusto)
            receta.put(
                    String.valueOf(tipo),
                    receta.getOrDefault(String.valueOf(tipo), 0) + cantidad
            );
        }

        return receta;
    }



    //Metodo para saber la cantidad total de la dosis (para comparar quien gasta menos dosis entre fila vs UCI)
    public int getTotalReceta() {

        Map<String, Integer> receta = generarReceta();

        int total = 0;

        for (Integer cantidad : receta.values()) {
            total += cantidad;
        }

        return total;
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


    public abstract int getPrioridad();

    @Override
    public abstract void degradacionInfeccion();

    @Override
    public abstract void degradacionSalud();

    public void aplicarDegradacion() {

        degradacionInfeccion();
        degradacionSalud();

        if (this.nivelSalud < 0)
            this.nivelSalud = 0;
    }

    public boolean estaMuerto() {
        return this.nivelSalud <= 0;
    }




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
