package org.example.Model.Pacientes;

public class Militar extends Paciente{

    public Militar(String Id, int nivelInfeccion, int nivelSalud, String genoma) {
        super(Id, nivelInfeccion, nivelSalud, genoma);
    }

    @Override
    public void degradacionInfeccion() {
        this.nivelInfeccion += 7;
    }

    @Override
    public void degradacionSalud() {
        this.nivelSalud -= 10;
    }

    @Override
    public int getPrioridad() {
        return 2;
    }
}
