package org.example.Model.Sistema;

import org.example.Model.Almacen.Almacen;
import org.example.Model.Pacientes.Civil;
import org.example.Model.Pacientes.Medico;
import org.example.Model.Pacientes.Militar;
import org.example.Model.Pacientes.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class PriorizacionTest {

    private Queue<Paciente> colaPacientes;
    private Queue<Paciente> colaUCI;
    private Almacen almacen;
    private Priorizacion priorizacion;

    @BeforeEach
    void setUp() {
        colaPacientes = new LinkedList<>();
        colaUCI = new LinkedList<>();
        almacen = new Almacen(100, 100, 100);
        priorizacion = new Priorizacion(colaPacientes, colaUCI, almacen);
    }

    @Test
    @DisplayName("Constructor debe inicializar correctamente las colas y el almacén")
    void testConstructor() {
        assertNotNull(priorizacion);
        assertTrue(colaPacientes.isEmpty());
        assertTrue(colaUCI.isEmpty());
    }

    @Test
    @DisplayName("Agregar paciente debe añadirlo a la cola de pacientes")
    void testAgregarPaciente() {
        Paciente paciente = new Civil("C001", "Pepe", 30, 70, "AAABBB");

        priorizacion.agregarPaciente(paciente);

        assertEquals(1, colaPacientes.size());
        assertEquals(paciente, colaPacientes.peek());
    }

    @Test
    @DisplayName("Agregar múltiples pacientes debe mantener el orden FIFO")
    void testAgregarMultiplesPacientes() {
        Paciente paciente1 = new Civil("C001", "Mayo", 30, 70, "AAABBB");
        Paciente paciente2 = new Medico("M001", "Roberto", 25, 75, "BBBOOO");
        Paciente paciente3 = new Militar("MIL001", "Danilo", 20, 80, "OOOBBB");

        priorizacion.agregarPaciente(paciente1);
        priorizacion.agregarPaciente(paciente2);
        priorizacion.agregarPaciente(paciente3);

        assertEquals(3, colaPacientes.size());
        assertEquals(paciente1, colaPacientes.peek());
    }

    @Test
    @DisplayName("Simulación sin pacientes debe finalizar correctamente")
    void testSimulacionSinPacientes() {
        assertDoesNotThrow(() -> priorizacion.iniciarSimulacion());

        assertTrue(colaPacientes.isEmpty());
        assertTrue(colaUCI.isEmpty());
    }

    @Test
    @DisplayName("Agregar paciente null debe funcionar sin lanzar excepción")
    void testAgregarPacienteNull() {
        assertDoesNotThrow(() -> priorizacion.agregarPaciente(null));
        assertEquals(1, colaPacientes.size());
    }

}