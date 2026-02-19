package org.example.Model.Almacen;

import java.util.LinkedList;
import java.util.Queue;
import org.example.Model.Pacientes.Paciente;
import org.example.Model.Pacientes.Civil;
import org.example.Model.Pacientes.Medico;
import org.example.Model.Pacientes.Militar;


public class AsignacionPacientes {

    public static Queue<Paciente> ConstruirPacientes(Queue<String[]> DatosCrudos) {

        Queue<Paciente> ColaPacientes = new LinkedList<>();

        while (!DatosCrudos.isEmpty()) {

            String[] datos = DatosCrudos.poll();

            String Id = (datos[0]);
            String Nombre = datos[1];
            String Tipo = datos[2].toUpperCase();
            int NivelInfeccion = Integer.parseInt(datos[3]);
            int NivelSalud = Integer.parseInt(datos[4]);
            String Genoma = datos[5];

            Paciente paciente = CrearPaciente(Id, Nombre, Tipo, NivelInfeccion, NivelSalud, Genoma);

            if (paciente != null) {
                ColaPacientes.offer(paciente);
            }
        }

        return ColaPacientes;
    }

    private static Paciente CrearPaciente(String Id, String Nombre, String Tipo,
                                          int NivelInfeccion, int NivelSalud, String Genoma) {

        switch (Tipo) {

            case "CIVIL":
                return new Civil(Id, Nombre, NivelInfeccion, NivelSalud, Genoma);


            case "MILITAR":
                return new Militar(Id, Nombre, NivelInfeccion, NivelSalud, Genoma);


            case "MEDICO":
                return new Medico(Id, Nombre, NivelInfeccion, NivelSalud, Genoma);


            default:
                System.out.println("Tipo de paciente desconocido: " + Tipo);
                return null;
        }

    }
}
