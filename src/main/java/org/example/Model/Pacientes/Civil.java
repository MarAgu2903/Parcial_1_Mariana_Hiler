package org.example.Model.Pacientes;

public class Civil extends Paciente{

        public Civil(String Id, int nivelInfeccion, int nivelSalud, String genoma) {
        super(Id, nivelInfeccion, nivelSalud, genoma);
    }

    @Override
    public void degradacionInfeccion() {
        this.nivelInfeccion += 10;
    }

    @Override
    public void degradacionSalud() {
        this.nivelSalud -= 15;
    }

    @Override
    public int getPrioridad() {
        return 3;
    }
}
