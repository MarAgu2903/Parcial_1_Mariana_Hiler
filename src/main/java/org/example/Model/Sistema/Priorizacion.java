package org.example.Model.Sistema;

import org.example.Model.Almacen.Almacen;
import org.example.Model.Pacientes.Paciente;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Priorizacion {

    private static final Logger logger = LogManager.getLogger(Priorizacion.class.getName());

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

            int turno = 1;

            while (!colaPacientes.isEmpty() || !colaUCI.isEmpty()) {

                logger.info("Inicio Turno {}", turno);
                logger.info("Pacientes en fila: {}", colaPacientes.size());
                logger.info("Pacientes en UCI: {}", colaUCI.size());
                logger.info("Recursos disponibles -> A:{} B:{} O:{}",
                        almacen.getTipoA(),
                        almacen.getTipoB(),
                        almacen.getTipoO());

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
                        //System.out.println("Paciente pasa a UCI");
                        logger.warn("Paciente {} pasa a UCI", pacienteFila.getId());
                    }
                    degradarAumentar();
                    registrarMuertes(colaPacientes);
                    registrarMuertes(colaUCI);
                    logger.info("Fin Turno {} ", turno);
                    turno++;
                    continue;
                }

                // Si alguien fue atendido
                if (seleccionado != null) {

                    Map<String, Integer> receta = seleccionado.generarReceta();
                    almacen.consumirRecursos(receta);
                    logger.info("Paciente {} fue sanado. Cantidad recursos utilizados: {}",
                            seleccionado.getId(),
                            receta);

                }

                degradarAumentar();
                registrarMuertes(colaPacientes);
                registrarMuertes(colaUCI);

                logger.info("Fin Turno {} ", turno);
                turno++;
            }

            //System.out.println("No hay más pacientes. Fin de la simulación.");
            logger.info("No hay más pacientes. Fin de la simulación.");
            logger.info("Estado final recursos en Almacen -> A:{} B:{} O:{}",
                    almacen.getTipoA(),
                    almacen.getTipoB(),
                    almacen.getTipoO());
        }


        private void degradarAumentar() {
            logger.info("Cantidad recursos restantes -> A:{} B:{} O:{}",
                    almacen.getTipoA(),
                    almacen.getTipoB(),
                    almacen.getTipoO());
            logger.debug("Aplicando degradación de salud y aumento de infección");
            degradarPacientes(colaPacientes);
            degradarPacientes(colaUCI);
            almacen.aumentarRecursos();
            logger.info("Recursos luego de aumento -> A:{} B:{} O:{}",
                    almacen.getTipoA(),
                    almacen.getTipoB(),
                    almacen.getTipoO());
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
                    //System.out.println("Paciente falleció: " + paciente.getId());
                    logger.error("Paciente {} ha fallecido en el turno actual", paciente.getId());
                } else {
                    cola.offer(paciente);
                }
            }
        }
}

