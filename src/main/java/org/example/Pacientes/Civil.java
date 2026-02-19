package org.example.Pacientes;

public class Civil extends Paciente{

        public Civil(String Id, int nivelInfeccion, int nivelSalud, String genoma, String tipoPaciente) {
        super(Id, nivelInfeccion, nivelSalud, genoma, tipoPaciente);
    }

    @Override
    public int degradacionInfeccion() {
        int nivelInfeccionActual = getnivelInfeccion();
        nivelInfeccionActual = nivelInfeccionActual + 10;

        return nivelInfeccionActual;
    }

    @Override
    public int degradacionSalud() {
        int nivelSaludActual = getnivelSalud();
        nivelSaludActual = nivelSaludActual - 15;
        return nivelSaludActual;
    }
}
