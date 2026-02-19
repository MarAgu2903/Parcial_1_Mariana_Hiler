package org.example;

import org.example.Model.Almacen.Almacen;
import org.example.Model.Pacientes.Civil;
import org.example.Model.Pacientes.Medico;
import org.example.Model.Pacientes.Militar;
import org.example.Model.Pacientes.Paciente;
import org.example.Model.Sistema.Priorizacion;

import java.util.LinkedList;
import java.util.Queue;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

    // 1️⃣ Crear colas
    Queue<Paciente> cola = new LinkedList<>();
    Queue<Paciente> uci = new LinkedList<>();

    // 2️⃣ Crear almacén
    Almacen almacen = new Almacen(2, 2, 2);

    // 3️⃣ Crear sistema de priorización
    Priorizacion sistema = new Priorizacion(cola, uci, almacen);

    // 4️⃣ Crear pacientes manualmente
    Paciente p1 = new Militar("1", 20, 80, "A3B1O1");
    Paciente p2 = new Civil("2", 30, 60, "A5B6O1");
    Paciente p3 = new Medico("3", 15, 90, "A2B1O8");

    // 5️⃣ Agregarlos a la cola
        sistema.agregarPaciente(p1);
        sistema.agregarPaciente(p2);
        sistema.agregarPaciente(p3);

    // 6️⃣ Ejecutar algunos turnos (NO infinito)
        sistema.iniciarSimulacion();

    }

}
