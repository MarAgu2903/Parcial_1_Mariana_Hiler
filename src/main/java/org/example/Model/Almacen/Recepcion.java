package org.example.Model.Almacen;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Recepcion {


    //VARIABLES GLOBALES
    private static final String RutaArchivo = "src/main/resources/Simulacion_datosPaciente.txt";

    //Cargar pacientes
    public static Queue<String[]> CargarPacientes() {

        Queue<String[]> Cola_Recepcion = new LinkedList<>();
        BufferedReader Buffer = null;

        try {

            Buffer = new BufferedReader(new FileReader(RutaArchivo));

            System.out.println("Archivo abierto correctamente.\n");

            Cola_Recepcion = Recorrer_Archivo(Buffer);

        } catch (IOException e) {

            System.out.println("Error al abrir archivo.\n");

        } finally {

            if (Buffer != null) {
                try {
                    Buffer.close();
                    System.out.println("\nArchivo cerrado correctamente.");
                } catch (IOException _E) {
                    System.out.println("Error al cerrar archivo.\n");
                }
            }
        }

        return Cola_Recepcion;
    }


  //Recorrer archivo

    private static Queue<String[]> Recorrer_Archivo (BufferedReader Buffer) throws IOException {

        Queue<String[]> Cola = new LinkedList<>();

        String Linea;

        int NumLinea = 0;

        System.out.println("Iniciando Recorrer Archivo");

        while ((Linea = Buffer.readLine()) != null) {

            NumLinea++;

            if (Linea.trim().isEmpty()) {
                continue;
            }

            String[] Datos = Linea.split("/");

            if (Datos.length != 6) {
                System.err.println("Formato inválido en línea " + NumLinea);
                continue;
            }

            System.out.println("Línea válida leída: " + Linea);

            Cola.offer(Datos);
        }

        System.out.println("\n Lectura finalizada.");

        return Cola;
    }

}