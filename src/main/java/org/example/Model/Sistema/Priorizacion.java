package org.example.Model.Sistema;

import org.example.Model.Almacen.Almacen;
import org.example.Model.Pacientes.Paciente;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Priorizacion {

        private Queue<Paciente> colaPacientes;
        private Queue<Paciente> colaUCI;
        private Almacen almacen;

        public Priorizacion(Queue<Paciente> colaPacientes,
                            Queue<Paciente> colaUCI,
                            Almacen almacen) {

            this.colaPacientes = colaPacientes;
            this.colaUCI = colaUCI;
            this.almacen = almacen;
        }

        public void agregarPaciente(Paciente paciente) {
            colaPacientes.offer(paciente);
        }

        public void iniciarSimulacion() {

            while (!colaPacientes.isEmpty() || !colaUCI.isEmpty()) {

                Paciente pacienteFila = colaPacientes.peek();
                Paciente pacienteUCI = colaUCI.peek();

                Paciente seleccionado = null;

                Map<String, Integer> recetaFila = null;
                Map<String, Integer> recetaUCI = null;

                if (pacienteFila != null)
                    recetaFila = pacienteFila.generarReceta();

                if (pacienteUCI != null)
                    recetaUCI = pacienteUCI.generarReceta();

                boolean filaSanable = pacienteFila != null && almacen.puedeSanar(recetaFila);
                boolean uciSanable = pacienteUCI != null && almacen.puedeSanar(recetaUCI);

                // Prioridad por posibilidad de cura
                if (filaSanable && !uciSanable) {
                    seleccionado = colaPacientes.poll();
                    System.out.println("Sana Paciente Fila");
                }

                else if (!filaSanable && uciSanable) {
                    seleccionado = colaUCI.poll();
                    System.out.println("Sana Paciente UCI");
                }

                //Ambos sanables, aplicar desempate
                else if (filaSanable && uciSanable) {

                    int prioridadFila = pacienteFila.getPrioridad();
                    int prioridadUCI = pacienteUCI.getPrioridad();

                    if (prioridadFila < prioridadUCI) {
                        seleccionado = colaPacientes.poll();
                        System.out.println("Sana Paciente Fila (Jerarquía)");
                    }

                    else if (prioridadFila > prioridadUCI) {
                        seleccionado = colaUCI.poll();
                        System.out.println("Sana Paciente UCI (Jerarquía)");
                    }

                    else {
                        // Empate real, menor consumo
                        int totalFila = pacienteFila.getTotalReceta();
                        int totalUCI = pacienteUCI.getTotalReceta();

                        if (totalFila <= totalUCI) {
                            seleccionado = colaPacientes.poll();
                            System.out.println("Sana Paciente Fila (Menor Consumo)");
                        } else {
                            seleccionado = colaUCI.poll();
                            System.out.println("Sana Paciente UCI (Menor Consumo)");
                        }
                    }
                }

                //Ninguno sanable, pasa a UCI
                else {
                    if (pacienteFila != null) {
                        colaUCI.offer(colaPacientes.poll());
                        System.out.println("Paciente pasa a UCI");
                    }
                    degradarAumentar();
                    registrarMuertes(colaPacientes);
                    registrarMuertes(colaUCI);
                    continue;
                }

                // Si alguien fue atendido
                if (seleccionado != null) {

                    Map<String, Integer> receta = seleccionado.generarReceta();
                    almacen.consumirRecursos(receta);
                }

                degradarAumentar();
                registrarMuertes(colaPacientes);
                registrarMuertes(colaUCI);
            }

            System.out.println("No hay más pacientes. Fin de la simulación.");
        }

        private void degradarAumentar() {
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

