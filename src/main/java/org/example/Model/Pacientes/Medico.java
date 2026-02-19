package org.example.Model.Pacientes;

public class Medico extends Paciente{

    public Medico(String Id, String Nombre, int nivelInfeccion, int nivelSalud, String genoma) {
        super(Id, nivelInfeccion, nivelSalud, genoma);
    }


    @Override
    public void degradacionInfeccion() {
        this.nivelInfeccion += 20;
    }

    @Override
    public void degradacionSalud() {
        this.nivelSalud -= 30;
    }

    @Override
    public int getPrioridad() {
        return 1;
    }
}
