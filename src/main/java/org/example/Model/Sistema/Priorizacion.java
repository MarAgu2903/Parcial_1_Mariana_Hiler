package org.example.Model.Sistema;

import org.example.Model.Almacen.Almacen;
import org.example.Model.Pacientes.Paciente;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.example.Model.Sistema.CargaViral;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;


public class Priorizacion {

    private static final Logger logger = LogManager.getLogger(Priorizacion.class.getName());

        private Queue<Paciente> colaPacientes;
        private Queue<Paciente> colaUCI;
        private Almacen almacen;

        private CargaViral cargaViral;
        private List<String> listaFallecidos = new ArrayList<>();
        private List<Paciente> listaSanados = new ArrayList<>();

    public Priorizacion(Queue<Paciente> colaPacientes,
                        Queue<Paciente> colaUCI,
                        Almacen almacen,
                        CargaViral cargaViral) {

        this.colaPacientes = colaPacientes;
        this.colaUCI = colaUCI;
        this.almacen = almacen;
        this.cargaViral = cargaViral;
    }

        public void agregarPaciente(Paciente paciente) {
            colaPacientes.offer(paciente);
        }

        public void iniciarSimulacion() {

            int turno = 1;

            while (!colaPacientes.isEmpty() || !colaUCI.isEmpty()) {

                logger.info("Inicio Turno {}", turno);
                cargaViral.incrementarNatural();
                logger.info("Carga viral tras incremento natural: {}", cargaViral.getCargaActual());
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

                }

                // Si alguien fue atendido
                if (seleccionado != null) {

                    Map<String, Integer> receta = seleccionado.generarReceta();
                    almacen.consumirRecursos(receta);
                    cargaViral.aplicarBonificacionRecuperacion();

                    listaSanados.add(seleccionado);

                    logger.info("Bonificación aplicada por recuperación. Carga viral actual: {}",
                            cargaViral.getCargaActual());
                    logger.info("Paciente {} fue sanado. Cantidad recursos utilizados: {}",
                            seleccionado.getId(),
                            receta);

                    }

                degradarAumentar();
                registrarMuertes(colaPacientes);
                registrarMuertes(colaUCI);

                int focos = contarFocosActivos();
                cargaViral.aplicarPenalizacionMutacion(focos);

                if (cargaViral.sistemaColapsado()) {
                    logger.fatal("COLAPSO DEL SISTEMA - GAME OVER");
                    break;
                }

                logger.warn("Focos infecciosos activos: {}. Carga viral actual: {}",
                        focos,
                        cargaViral.getCargaActual());

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
                    logger.error("Paciente {} ha fallecido en el turno actual", paciente.getId());
                    listaFallecidos.add(paciente.getId());
                }
            }
        }

    private int contarFocosActivos() {

        int contador = 0;

        for (Paciente p : colaPacientes) {
            if (p.esFocoInfeccioso()) contador++;
        }

        for (Paciente p : colaUCI) {
            if (p.esFocoInfeccioso()) contador++;
        }

        return contador;
    }


    //Para el manejo de Json
    public Map<String, Integer> obtenerSobrevivientesPorTipo() {

        Map<String, Integer> conteo = new HashMap<>();

        for (Paciente p : listaSanados) {
            conteo.merge(p.getClass().getSimpleName(), 1, Integer::sum);
        }

        return conteo;
    }

    public List<String> getListaFallecidos() {
        return listaFallecidos;
    }

    public int getCargaViralFinal() {
        return cargaViral.getCargaActual();
    }

    public boolean isSistemaColapsado() {
        return cargaViral.sistemaColapsado();
    }


}

