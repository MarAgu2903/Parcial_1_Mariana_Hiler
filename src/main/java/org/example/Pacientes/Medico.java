package org.example.Pacientes;

public class Medico extends Paciente{

    public Medico(String Id, int nivelInfeccion, int nivelSalud, String genoma, String tipoPaciente) {
        super(Id, nivelInfeccion, nivelSalud, genoma, tipoPaciente);
    }


    @Override
    public int degradacionInfeccion() {
        int nivelInfeccionActual = getnivelInfeccion();
        nivelInfeccionActual = nivelInfeccionActual + 20;

        return nivelInfeccionActual;
    }

    @Override
    public int degradacionSalud() {
        int nivelSaludActual = getnivelSalud();
        nivelSaludActual = nivelSaludActual - 30;
                return nivelSaludActual;
    }
}
