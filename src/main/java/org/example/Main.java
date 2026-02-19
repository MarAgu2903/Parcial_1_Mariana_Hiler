package org.example;

import org.example.Model.Almacen.Almacen;
import org.example.Model.Pacientes.*;
import org.example.Model.Sistema.Priorizacion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class Main {

        // Ruta global del archivo
        private static final String RUTA_ARCHIVO = "src/main/resources/Simulacion_datosPaciente.txt";

        public static void main(String[] args) {

            Queue<Paciente> colaGeneral = new LinkedList<>();
            Queue<Paciente> colaUCI = new LinkedList<>();

            // Inicializa el almacén con valores para el ejemplo
            Almacen almacen = new Almacen(112, 112, 112);

            cargarPacientesDesdeArchivo(colaGeneral);

            Priorizacion sistema = new Priorizacion(colaGeneral, colaUCI, almacen);

            sistema.iniciarSimulacion();
        }

        private static void cargarPacientesDesdeArchivo(Queue<Paciente> cola) {

            try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {

                String linea;

                while ((linea = br.readLine()) != null) {

                    String[] datos = linea.split("/");

                    String id = datos[0];
                    String nombre = datos[1];
                    String tipo = datos[2];
                    int nivelInfeccion = Integer.parseInt(datos[3]);
                    int nivelSalud = Integer.parseInt(datos[4]);
                    String genoma = datos[5];

                    Paciente paciente = null;

                    switch (tipo) {
                        case "CIVIL":
                            paciente = new Civil(id, nombre, nivelInfeccion, nivelSalud, genoma);
                            break;
                        case "MILITAR":
                            paciente = new Militar(id, nombre, nivelInfeccion, nivelSalud, genoma);
                            break;
                        case "DOCTOR":
                            paciente = new Medico(id, nombre, nivelInfeccion, nivelSalud, genoma);
                            break;
                        default:
                            System.out.println("Tipo desconocido: " + tipo);
                    }

                    if (paciente != null) {
                        cola.offer(paciente);
                    }
                }

            } catch (IOException e) {
                System.out.println("Error leyendo archivo: " + e.getMessage());
            }
        }
}

