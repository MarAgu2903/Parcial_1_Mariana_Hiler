package org.example.Pacientes;

public class Militar extends Paciente{

    public Militar(String Id, int nivelInfeccion, int nivelSalud, String genoma, String tipoPaciente) {
        super(Id, nivelInfeccion, nivelSalud, genoma, tipoPaciente);
    }


    @Override
    public int degradacionInfeccion() {
        int nivelInfeccionActual = getnivelInfeccion();
        nivelInfeccionActual = nivelInfeccionActual + 7;

        return nivelInfeccionActual;
    }

    @Override
    public int degradacionSalud() {
        int nivelSaludActual = getnivelSalud();
        nivelSaludActual = nivelSaludActual - 10;
        return nivelSaludActual;
    }
}
