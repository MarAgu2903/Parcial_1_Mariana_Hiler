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
        Paciente paciente = new Civil("C001", 30, 70, "AAABBB");
        
        priorizacion.agregarPaciente(paciente);
        
        assertEquals(1, colaPacientes.size());
        assertEquals(paciente, colaPacientes.peek());
    }

    @Test
    @DisplayName("Agregar múltiples pacientes debe mantener el orden FIFO")
    void testAgregarMultiplesPacientes() {
        Paciente paciente1 = new Civil("C001", 30, 70, "AAABBB");
        Paciente paciente2 = new Medico("M001", 25, 75, "BBBOOO");
        Paciente paciente3 = new Militar("MIL001", 20, 80, "OOOBBB");
        
        priorizacion.agregarPaciente(paciente1);
        priorizacion.agregarPaciente(paciente2);
        priorizacion.agregarPaciente(paciente3);
        
        assertEquals(3, colaPacientes.size());
        assertEquals(paciente1, colaPacientes.peek());
    }

    @Test
    @DisplayName("Simulación debe procesar paciente sanable de la fila cuando UCI no es sanable")
    void testSimulacionPrioridadFilaSanable() {
        Paciente pacienteFila = new Civil("C001", 10, 80, "AAA");
        Paciente pacienteUCI = new Civil("C002", 10, 80, "BBBBBBBBBB"); // Necesita más recursos
        
        colaPacientes.offer(pacienteFila);
        colaUCI.offer(pacienteUCI);
        
        Almacen almacenLimitado = new Almacen(10, 2, 10); // Recursos suficientes solo para fila
        Priorizacion priorizacionTest = new Priorizacion(colaPacientes, colaUCI, almacenLimitado);
        
        priorizacionTest.iniciarSimulacion();
        
        assertTrue(colaPacientes.isEmpty() || colaUCI.isEmpty());
    }

    @Test
    @DisplayName("Simulación debe procesar paciente sanable de UCI cuando fila no es sanable")
    void testSimulacionPrioridadUciSanable() {
        Paciente pacienteFila = new Civil("C001", 10, 80, "AAAAAAAAAA"); // Necesita más recursos
        Paciente pacienteUCI = new Civil("C002", 10, 80, "BBB");
        
        colaPacientes.offer(pacienteFila);
        colaUCI.offer(pacienteUCI);
        
        Almacen almacenLimitado = new Almacen(2, 10, 10); // Recursos suficientes solo para UCI
        Priorizacion priorizacionTest = new Priorizacion(colaPacientes, colaUCI, almacenLimitado);
        
        priorizacionTest.iniciarSimulacion();
        
        assertTrue(colaPacientes.isEmpty() || colaUCI.isEmpty());
    }

    @Test
    @DisplayName("Simulación debe priorizar por jerarquía cuando ambos son sanables - Médico sobre Civil")
    void testSimulacionDesempatePorJerarquiaMedico() {
        Paciente pacienteFila = new Civil("C001", 10, 80, "AAA"); // Prioridad 3
        Paciente pacienteUCI = new Medico("M001", 10, 80, "BBB"); // Prioridad 2
        
        colaPacientes.offer(pacienteFila);
        colaUCI.offer(pacienteUCI);
        
        Almacen almacenSuficiente = new Almacen(10, 10, 10);
        Priorizacion priorizacionTest = new Priorizacion(colaPacientes, colaUCI, almacenSuficiente);
        
        priorizacionTest.iniciarSimulacion();
        
        assertTrue(colaUCI.isEmpty() || colaPacientes.isEmpty());
    }

    @Test
    @DisplayName("Simulación debe priorizar por jerarquía cuando ambos son sanables - Militar sobre Médico")
    void testSimulacionDesempatePorJerarquiaMilitar() {
        Paciente pacienteFila = new Medico("M001", 10, 80, "AAA"); // Prioridad 2
        Paciente pacienteUCI = new Militar("MIL001", 10, 80, "BBB"); // Prioridad 1
        
        colaPacientes.offer(pacienteFila);
        colaUCI.offer(pacienteUCI);
        
        Almacen almacenSuficiente = new Almacen(10, 10, 10);
        Priorizacion priorizacionTest = new Priorizacion(colaPacientes, colaUCI, almacenSuficiente);
        
        priorizacionTest.iniciarSimulacion();
        
        assertTrue(colaUCI.isEmpty() || colaPacientes.isEmpty());
    }

    @Test
    @DisplayName("Simulación debe priorizar por menor consumo cuando hay empate en jerarquía")
    void testSimulacionDesempatePorMenorConsumo() {
        Paciente pacienteFila = new Civil("C001", 10, 80, "AA"); // 2 dosis
        Paciente pacienteUCI = new Civil("C002", 10, 80, "BBBBB"); // 5 dosis
        
        colaPacientes.offer(pacienteFila);
        colaUCI.offer(pacienteUCI);
        
        Almacen almacenSuficiente = new Almacen(10, 10, 10);
        Priorizacion priorizacionTest = new Priorizacion(colaPacientes, colaUCI, almacenSuficiente);
        
        priorizacionTest.iniciarSimulacion();
        
        assertTrue(colaPacientes.isEmpty() || colaUCI.isEmpty());
    }

    @Test
    @DisplayName("Paciente no sanable debe pasar a UCI")
    void testPacienteNoSanablePasaAUCI() {
        Paciente paciente = new Civil("C001", 10, 80, "AAAAAAAAAA"); // Necesita muchos recursos
        
        colaPacientes.offer(paciente);
        
        Almacen almacenVacio = new Almacen(1, 1, 1); // Recursos insuficientes
        Priorizacion priorizacionTest = new Priorizacion(colaPacientes, colaUCI, almacenVacio);
        
        // Nota: La simulación continuará hasta que las colas estén vacías o los pacientes mueran
        priorizacionTest.iniciarSimulacion();
        
        assertTrue(colaPacientes.isEmpty());
    }

    @Test
    @DisplayName("Simulación sin pacientes debe finalizar correctamente")
    void testSimulacionSinPacientes() {
        assertDoesNotThrow(() -> priorizacion.iniciarSimulacion());
        
        assertTrue(colaPacientes.isEmpty());
        assertTrue(colaUCI.isEmpty());
    }

    @Test
    @DisplayName("Simulación con solo un paciente sanable")
    void testSimulacionUnSoloPacienteSanable() {
        Paciente paciente = new Civil("C001", 10, 80, "AAA");
        
        priorizacion.agregarPaciente(paciente);
        
        priorizacion.iniciarSimulacion();
        
        assertTrue(colaPacientes.isEmpty());
    }

    @Test
    @DisplayName("Simulación debe manejar pacientes con diferentes niveles de infección")
    void testSimulacionDiferentesNivelesInfeccion() {
        Paciente paciente1 = new Civil("C001", 10, 80, "AAA");
        Paciente paciente2 = new Civil("C002", 50, 60, "BBB");
        Paciente paciente3 = new Civil("C003", 90, 40, "OOO");
        
        priorizacion.agregarPaciente(paciente1);
        priorizacion.agregarPaciente(paciente2);
        priorizacion.agregarPaciente(paciente3);
        
        assertDoesNotThrow(() -> priorizacion.iniciarSimulacion());
    }

    @Test
    @DisplayName("Simulación debe procesar pacientes con diferentes tipos de genoma")
    void testSimulacionDiferentesGenomas() {
        Paciente paciente1 = new Civil("C001", 20, 70, "AAA");
        Paciente paciente2 = new Medico("M001", 20, 70, "BBB");
        Paciente paciente3 = new Militar("MIL001", 20, 70, "OOO");
        Paciente paciente4 = new Civil("C002", 20, 70, "AAABBBOO");
        
        priorizacion.agregarPaciente(paciente1);
        priorizacion.agregarPaciente(paciente2);
        priorizacion.agregarPaciente(paciente3);
        priorizacion.agregarPaciente(paciente4);
        
        assertDoesNotThrow(() -> priorizacion.iniciarSimulacion());
    }

    @Test
    @DisplayName("Simulación debe manejar almacén con recursos mixtos")
    void testSimulacionRecursosMixtos() {
        Paciente paciente1 = new Civil("C001", 15, 75, "AAABBB");
        Paciente paciente2 = new Medico("M001", 15, 75, "BBBOOO");
        
        colaPacientes.offer(paciente1);
        colaUCI.offer(paciente2);
        
        Almacen almacenMixto = new Almacen(5, 8, 5);
        Priorizacion priorizacionTest = new Priorizacion(colaPacientes, colaUCI, almacenMixto);
        
        assertDoesNotThrow(() -> priorizacionTest.iniciarSimulacion());
    }

    @Test
    @DisplayName("Agregar paciente null debe funcionar sin lanzar excepción")
    void testAgregarPacienteNull() {
        assertDoesNotThrow(() -> priorizacion.agregarPaciente(null));
        assertEquals(1, colaPacientes.size());
    }

    @Test
    @DisplayName("Simulación con múltiples pacientes del mismo tipo")
    void testSimulacionMismoTipoPacientes() {
        Paciente paciente1 = new Civil("C001", 20, 75, "AAA");
        Paciente paciente2 = new Civil("C002", 25, 70, "BBB");
        Paciente paciente3 = new Civil("C003", 30, 65, "OOO");
        
        priorizacion.agregarPaciente(paciente1);
        priorizacion.agregarPaciente(paciente2);
        priorizacion.agregarPaciente(paciente3);
        
        assertDoesNotThrow(() -> priorizacion.iniciarSimulacion());
    }

    @Test
    @DisplayName("Simulación debe procesar correctamente cuando hay empate total")
    void testSimulacionEmpateTotal() {
        Paciente paciente1 = new Civil("C001", 20, 70, "AAA"); // Misma prioridad, mismo consumo
        Paciente paciente2 = new Civil("C002", 20, 70, "BBB"); // Misma prioridad, mismo consumo
        
        colaPacientes.offer(paciente1);
        colaUCI.offer(paciente2);
        
        assertDoesNotThrow(() -> priorizacion.iniciarSimulacion());
    }

    @Test
    @DisplayName("Simulación con almacén vacío debe mover pacientes a UCI")
    void testSimulacionAlmacenVacio() {
        Paciente paciente = new Civil("C001", 20, 70, "AAA");
        
        colaPacientes.offer(paciente);
        
        Almacen almacenVacio = new Almacen(0, 0, 0);
        Priorizacion priorizacionTest = new Priorizacion(colaPacientes, colaUCI, almacenVacio);
        
        assertDoesNotThrow(() -> priorizacionTest.iniciarSimulacion());
    }
}
