package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Model.Almacen.Almacen;
import org.example.Model.Pacientes.*;
import org.example.Model.Reporte.ReporteFinal;
import org.example.Model.Sistema.CargaViral;
import org.example.Model.Sistema.Priorizacion;

import java.io.BufferedReader;
import java.io.File;
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

            CargaViral cargaViral = new CargaViral();

            // Inicializa el almacén con valores para el ejemplo
            Almacen almacen = new Almacen(100, 100, 100);

            cargarPacientesDesdeArchivo(colaGeneral);

            Priorizacion sistema = new Priorizacion(colaGeneral, colaUCI, almacen, cargaViral);

            sistema.iniciarSimulacion();


            ObjectMapper mapper = new ObjectMapper();

            ReporteFinal reporte = new ReporteFinal(
                    sistema.obtenerSobrevivientesPorTipo(),
                    sistema.getListaFallecidos(),
                    sistema.getCargaViralFinal(),
                    sistema.isSistemaColapsado()
            );

            try {
                mapper.writerWithDefaultPrettyPrinter()
                        .writeValue(new File("reporte_final.json"), reporte);

                System.out.println("Reporte JSON generado correctamente.");

            } catch (IOException e) {
                System.out.println("Error al generar el archivo JSON.");
                e.getLocalizedMessage();
            }
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

