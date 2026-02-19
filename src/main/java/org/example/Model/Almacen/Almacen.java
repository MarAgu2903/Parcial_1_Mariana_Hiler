package org.example.Model.Almacen;

import java.util.Map;
import java.util.Random;

public class Almacen {

    private int tipoA;
    private int tipoB;
    private int tipoO;

    public Almacen(int tipoA, int tipoB, int tipoO){

        try {

            if (tipoA < 0 || tipoB < 0 || tipoO < 0) {
                throw new IllegalArgumentException("Los recursos iniciales no pueden ser negativos");
            }

            this.tipoA = tipoA;
            this.tipoB = tipoB;
            this.tipoO = tipoO;

        } catch (IllegalArgumentException e) {

            throw new IllegalArgumentException(
                    "Error al crear el Almacen: " + e.getMessage()
            );
        }
    }

    //Es una validación inicial para comprobar que existen recursos suficientes
    public boolean puedeSanar(Map<String, Integer> dosisgenoma) {

        if (dosisgenoma == null) {
            throw new IllegalArgumentException("La receta no puede ser nula");
        }

        return tipoA >= dosisgenoma.getOrDefault("A", 0)
                && tipoB >= dosisgenoma.getOrDefault("B", 0)
                && tipoO >= dosisgenoma.getOrDefault("O", 0);
    }

    // Luego de validar que hay recursos suficientes, se procede a sanar al Paciente
    public void consumirRecursos(Map<String, Integer> dosisgenoma) {

        try {

            if (!puedeSanar(dosisgenoma)) {
                throw new IllegalStateException("No hay recursos suficientes para consumir");
            }

            tipoA -= dosisgenoma.getOrDefault("A", 0);
            tipoB -= dosisgenoma.getOrDefault("B", 0);
            tipoO -= dosisgenoma.getOrDefault("O", 0);

        } catch (IllegalStateException e) {

            throw new IllegalStateException(
                    "Error al consumir recursos: " + e.getMessage()
            );
        }
    }

    //Aumentar los recursos de forma controlada
    public void aumentarRecursos() {

        try {

            Random r = new Random();

            tipoA += r.nextInt(4); // 0 a 3
            tipoB += r.nextInt(3); // 0 a 2
            tipoO += r.nextInt(2); // 0 a 1

            if (tipoA < 0 || tipoB < 0 || tipoO < 0) {
                throw new IllegalStateException("Error en incremento de recursos");
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error al aumentar recursos: " + e.getMessage()
            );
        }
    }


    public int getTipoA() {
        return tipoA;
    }

    public int getTipoB() {
        return tipoB;
    }

    public int getTipoO() {
        return tipoO;
    }
}
