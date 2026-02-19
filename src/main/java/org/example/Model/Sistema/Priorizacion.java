package org.example.Model.Sistema;

import org.example.Model.Almacen.Almacen;
import org.example.Model.Pacientes.Paciente;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Priorizacion {

    // Cola que almacena los pacientes
    private Queue<Paciente> colaPacientes;
    private Queue<Paciente> colaUCI;
    private Almacen almacen;

    public Priorizacion(Queue colaPacientes, Queue colaUCI, Almacen almacen) {
        this.colaPacientes = new LinkedList<>();
        this.colaUCI = new LinkedList<>();
        this.almacen = almacen;
    }

    //Metodo para agregar a los Pacientes, que se usará en la lectura del archivo
    public void agregarPaciente(Paciente paciente) {
        colaPacientes.offer(paciente);
    }

    // Aplicar las Reglas de Desempate y Priorización

    public void iniciarSimulacion() {

        while (true) {

            Paciente pacienteFila = colaPacientes.peek(); //Miramos los elementos antes de procesarlos
            Paciente pacienteUCI = colaUCI.peek();

            if (pacienteFila == null && pacienteUCI == null) {
                degradarAumentar();
                continue;
            }

            Paciente seleccionado = null; // Es la variable del paciente elegido en la priorización

            Map<String, Integer> recetaFila = null; // Inicializar
            Map<String, Integer> recetaUCI = null; // Inicializar

            if (pacienteFila != null)
                recetaFila = pacienteFila.generarReceta();

            if (pacienteUCI != null)
                recetaUCI = pacienteUCI.generarReceta();

            System.out.println("Recurso Almacen: A" + almacen.getTipoA()+ "B"+ almacen.getTipoB()
                    + "C" + almacen.getTipoO() + "\n"+ "RecetaFila: " + recetaFila + "\n" + "RecetaUCI: " + recetaUCI);


            // 1. Atienda al paciente al frente de la cola, determine si puede sanarlo con los
            // suministros del almacén, si no, se le asigna una cama en UCI.
            boolean filaSanable = pacienteFila != null && almacen.puedeSanar(recetaFila);
            boolean uciSanable = pacienteUCI != null && almacen.puedeSanar(recetaUCI);

            //a. Se prioriza al paciente que sea posible sanar, es decir, si el paciente de la
            //fila consume una cantidad de suministros al que no se disponer, pero el de
            //UCI si, se cura al paciente de la UCI.
            if (filaSanable && !uciSanable) {
                seleccionado = colaPacientes.poll();
                System.out.println("Sana Paciente Fila");
            } else if (!filaSanable && uciSanable) {
                seleccionado = colaUCI.poll();
                System.out.println("Sana Paciente UCI");
            }

            // Ambos son sanables
            else if (filaSanable && uciSanable) {

                //b. Rango Jerárquico: Si ambos (Fila vs. UCI) son sanables, la prioridad es:
                //Personal Médico (1) > Militares (2) > Civiles (3). Es decir que,
                //si en UCI se encuentra un Civil, pero, en la fila entro un Personal Médico, se
                //le da prioridad al personal medico

                if (pacienteFila.getPrioridad() < pacienteUCI.getPrioridad()) {
                    seleccionado = colaPacientes.poll();
                    System.out.println("Sana Paciente Fila");
                } else if (pacienteFila.getPrioridad() > pacienteUCI.getPrioridad()) {
                    seleccionado = colaUCI.poll();
                    System.out.println("Sana Paciente UCI");
                }

                //c. Optimización de Recursos: Si persiste el empate en el rango, se prioriza al
                //sujeto cuya receta genómica consuma la menor cantidad total de
                //unidades de suministros.

                int totalFila = pacienteFila.getTotalReceta();
                int totalUCI = pacienteUCI.getTotalReceta();

                if (totalFila <= totalUCI) {
                    seleccionado = colaPacientes.poll();
                    System.out.println("Sana Paciente Fila");
                } else {
                    seleccionado = colaUCI.poll();
                    System.out.println("Sana Paciente UCI");
                }

            }
            // Ninguno de los 2 Pacientes es Sanable, el Paciente de la fila pasa a UCI

            else {
                if (pacienteFila != null) {
                    colaUCI.offer(colaPacientes.poll());
                    System.out.println("ninguno se sana, Pasa a UCI");
                }
                degradarAumentar();
                continue;
            }

            if (seleccionado != null) {

                Map<String, Integer> receta = seleccionado.generarReceta();
                almacen.consumirRecursos(receta);
                degradarAumentar();
            }

            if (pacienteFila == null && pacienteUCI == null) {
                System.out.println("No hay más pacientes. Fin de la simulación.");
                break;
            }


        }


    }

    private void degradarAumentar(){
        degradarPacientes(colaPacientes);
        degradarPacientes(colaUCI);
        almacen.aumentarRecursos();
    }

    private void degradarPacientes(Queue<Paciente> cola) {

        int size = cola.size();

        for (int i = 0; i < size; i++) {

            Paciente paciente = cola.poll();

            paciente.aplicarDegradacion();

            cola.offer(paciente);
        }
    }

    private void registrarMuertes(Queue<Paciente> cola) {

        int size = cola.size();

        for (int i = 0; i < size; i++) {

            Paciente paciente = cola.poll();

            if (paciente.estaMuerto()) {
                System.out.println("Paciente falleció: " + paciente.getId());
            } else {
                cola.offer(paciente);
            }
        }
    }
}

