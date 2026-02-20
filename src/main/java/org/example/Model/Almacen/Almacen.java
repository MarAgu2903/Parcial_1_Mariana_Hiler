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

    //validacion criterios de recursos suficientes
    public boolean puedeSanar(Map<String, Integer> dosisgenoma) {

        if (dosisgenoma == null) {
            throw new IllegalArgumentException("La receta no puede ser nula");
        }

        return tipoA >= dosisgenoma.getOrDefault("A", 0)
                && tipoB >= dosisgenoma.getOrDefault("B", 0)
                && tipoO >= dosisgenoma.getOrDefault("O", 0);
    }

    // validar que hay recursos suficientes
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

            tipoA += r.nextInt(2) + 1;
            tipoB += r.nextInt(2) + 1;
            tipoO += r.nextInt(2) + 1;

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
