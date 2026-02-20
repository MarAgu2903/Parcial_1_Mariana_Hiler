package org.example.Model.Pacientes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MedicoTest {

    private Medico medico;

    @BeforeEach
    void setUp() {
        medico = new Medico("M001", "Dr. García", 25, 80, "A3B2O1");
    }

    @Test
    @DisplayName("Constructor debe crear un Medico válido con todos los parámetros correctos")
    void testConstructorValido() {
        assertNotNull(medico);
        assertEquals("M001", medico.getId());
        assertEquals(25, medico.getnivelInfeccion());
        assertEquals(80, medico.getnivelSalud());
        assertEquals("A3B2O1", medico.getgenoma());
    }

    @Test
    @DisplayName("Constructor debe aceptar nombre como parámetro adicional")
    void testConstructorConNombre() {
        Medico medicoConNombre = new Medico("M002", "Dra. Martínez", 30, 75, "B5");
        assertNotNull(medicoConNombre);
        assertEquals("M002", medicoConNombre.getId());
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con ID nulo")
    void testConstructorIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico(null, "Nombre", 25, 80, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con ID vacío")
    void testConstructorIdVacio() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("", "Nombre", 25, 80, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección igual a 0")
    void testConstructorInfeccionCero() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("M001", "Nombre", 0, 80, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección igual a 100")
    void testConstructorInfeccionCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("M001", "Nombre", 100, 80, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección mayor a 100")
    void testConstructorInfeccionMayorCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("M001", "Nombre", 150, 80, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección negativo")
    void testConstructorInfeccionNegativo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("M001", "Nombre", -10, 80, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de infección en límite inferior válido (1)")
    void testConstructorInfeccionLimiteInferior() {
        Medico medicoLimite = new Medico("M001", "Nombre", 1, 80, "A5");
        assertEquals(1, medicoLimite.getnivelInfeccion());
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de infección en límite superior válido (99)")
    void testConstructorInfeccionLimiteSuperior() {
        Medico medicoLimite = new Medico("M001", "Nombre", 99, 80, "A5");
        assertEquals(99, medicoLimite.getnivelInfeccion());
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud igual a 0")
    void testConstructorSaludCero() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("M001", "Nombre", 25, 0, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud igual a 100")
    void testConstructorSaludCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("M001", "Nombre", 25, 100, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud mayor a 100")
    void testConstructorSaludMayorCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("M001", "Nombre", 25, 120, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud negativo")
    void testConstructorSaludNegativo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("M001", "Nombre", 25, -5, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de salud en límite inferior válido (1)")
    void testConstructorSaludLimiteInferior() {
        Medico medicoLimite = new Medico("M001", "Nombre", 25, 1, "A5");
        assertEquals(1, medicoLimite.getnivelSalud());
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de salud en límite superior válido (99)")
    void testConstructorSaludLimiteSuperior() {
        Medico medicoLimite = new Medico("M001", "Nombre", 25, 99, "A5");
        assertEquals(99, medicoLimite.getnivelSalud());
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con genoma nulo")
    void testConstructorGenomaNulo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("M001", "Nombre", 25, 80, null)
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con genoma vacío")
    void testConstructorGenomaVacio() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Medico("M001", "Nombre", 25, 80, "")
        );
    }

    @Test
    @DisplayName("degradacionInfeccion debe aumentar la infección en 20")
    void testDegradacionInfeccion() {
        int nivelInicial = medico.getnivelInfeccion();
        
        medico.degradacionInfeccion();
        
        assertEquals(nivelInicial + 20, medico.getnivelInfeccion());
    }

    @Test
    @DisplayName("degradacionInfeccion debe permitir superar 100")
    void testDegradacionInfeccionSuperaCien() {
        Medico medicoAlto = new Medico("M002", "Nombre", 90, 80, "A5");
        
        medicoAlto.degradacionInfeccion();
        
        assertEquals(110, medicoAlto.getnivelInfeccion());
    }

    @Test
    @DisplayName("degradacionInfeccion múltiple debe acumular incrementos de 20")
    void testDegradacionInfeccionMultiple() {
        int nivelInicial = medico.getnivelInfeccion();
        
        medico.degradacionInfeccion();
        medico.degradacionInfeccion();
        
        assertEquals(nivelInicial + 40, medico.getnivelInfeccion());
    }

    @Test
    @DisplayName("degradacionSalud debe disminuir la salud en 30")
    void testDegradacionSalud() {
        int nivelInicial = medico.getnivelSalud();
        
        medico.degradacionSalud();
        
        assertEquals(nivelInicial - 30, medico.getnivelSalud());
    }

    @Test
    @DisplayName("degradacionSalud debe permitir valores negativos")
    void testDegradacionSaludNegativo() {
        Medico medicoBajo = new Medico("M002", "Nombre", 25, 20, "A5");
        
        medicoBajo.degradacionSalud();
        
        assertEquals(-10, medicoBajo.getnivelSalud());
    }

    @Test
    @DisplayName("degradacionSalud múltiple debe acumular decrementos de 30")
    void testDegradacionSaludMultiple() {
        int nivelInicial = medico.getnivelSalud();
        
        medico.degradacionSalud();
        medico.degradacionSalud();
        
        assertEquals(nivelInicial - 60, medico.getnivelSalud());
    }

    @Test
    @DisplayName("aplicarDegradacion debe aplicar ambas degradaciones")
    void testAplicarDegradacion() {
        int nivelInfeccionInicial = medico.getnivelInfeccion();
        int nivelSaludInicial = medico.getnivelSalud();
        
        medico.aplicarDegradacion();
        
        assertEquals(nivelInfeccionInicial + 20, medico.getnivelInfeccion());
        assertEquals(nivelSaludInicial - 30, medico.getnivelSalud());
    }

    @Test
    @DisplayName("aplicarDegradacion debe limitar salud a 0 cuando es negativa")
    void testAplicarDegradacionLimitaSalud() {
        Medico medicoBajo = new Medico("M002", "Nombre", 25, 20, "A5");
        
        medicoBajo.aplicarDegradacion();
        
        assertEquals(0, medicoBajo.getnivelSalud());
    }

    @Test
    @DisplayName("getPrioridad debe retornar 1 para Medico (mayor prioridad que Civil)")
    void testGetPrioridad() {
        assertEquals(1, medico.getPrioridad());
    }

    @Test
    @DisplayName("Medico debe tener mayor prioridad que Civil")
    void testPrioridadVsCivil() {
        Civil civil = new Civil("C001", "Nombre", 25, 80, "A5");
        
        assertTrue(medico.getPrioridad() < civil.getPrioridad());
    }

    @Test
    @DisplayName("estaMuerto debe retornar false cuando salud es mayor a 0")
    void testEstaMuertoFalso() {
        assertFalse(medico.estaMuerto());
    }

    @Test
    @DisplayName("estaMuerto debe retornar true cuando salud es 0")
    void testEstaMuertoSaludCero() {
        Medico medicoMuerto = new Medico("M002", "Nombre", 25, 1, "A5");
        medicoMuerto.aplicarDegradacion();
        
        assertTrue(medicoMuerto.estaMuerto());
    }

    @Test
    @DisplayName("estaMuerto debe retornar true cuando salud es negativa")
    void testEstaMuertoSaludNegativa() {
        Medico medicoMuerto = new Medico("M002", "Nombre", 25, 10, "A5");
        medicoMuerto.aplicarDegradacion();
        
        assertTrue(medicoMuerto.estaMuerto());
    }

    @Test
    @DisplayName("Medico puede morir más rápido que Civil por mayor degradación de salud")
    void testDegradacionMayorQueCivil() {
        Civil civil = new Civil("C001", "Nombre", 25, 50, "A5");
        Medico medicoComparacion = new Medico("M002", "Nombre", 25, 50, "A5");
        
        civil.aplicarDegradacion();
        medicoComparacion.aplicarDegradacion();
        
        assertTrue(medicoComparacion.getnivelSalud() < civil.getnivelSalud());
    }

    @Test
    @DisplayName("generarReceta debe generar receta correcta con un solo tipo")
    void testGenerarRecetaSimple() {
        Medico medicoSimple = new Medico("M002", "Nombre", 25, 80, "A7");
        
        Map<String, Integer> receta = medicoSimple.generarReceta();
        
        assertEquals(7, receta.get("A"));
        assertNull(receta.get("B"));
        assertNull(receta.get("O"));
    }

    @Test
    @DisplayName("generarReceta debe generar receta correcta con múltiples tipos")
    void testGenerarRecetaMultiple() {
        Map<String, Integer> receta = medico.generarReceta();
        
        assertEquals(3, receta.get("A"));
        assertEquals(2, receta.get("B"));
        assertEquals(1, receta.get("O"));
    }

    @Test
    @DisplayName("generarReceta debe sumar cantidades del mismo tipo")
    void testGenerarRecetaSumaRepetidos() {
        Medico medicoRepetido = new Medico("M002", "Nombre", 25, 80, "A5B3A2O1B2");
        
        Map<String, Integer> receta = medicoRepetido.generarReceta();
        
        assertEquals(7, receta.get("A")); // 5 + 2
        assertEquals(5, receta.get("B")); // 3 + 2
        assertEquals(1, receta.get("O"));
    }

    @Test
    @DisplayName("getTotalReceta debe retornar la suma total de todas las dosis")
    void testGetTotalReceta() {
        int total = medico.getTotalReceta(); // A3B2O1 = 3+2+1 = 6
        
        assertEquals(6, total);
    }

    @Test
    @DisplayName("getTotalReceta debe funcionar con un solo tipo de genoma")
    void testGetTotalRecetaSimple() {
        Medico medicoSimple = new Medico("M002", "Nombre", 25, 80, "B15");
        
        assertEquals(15, medicoSimple.getTotalReceta());
    }

    @Test
    @DisplayName("equals debe retornar true para medicos con los mismos datos")
    void testEqualsIguales() {
        Medico medico2 = new Medico("M001", "Dr. García", 25, 80, "A3B2O1");
        
        assertEquals(medico, medico2);
    }

    @Test
    @DisplayName("equals debe retornar false para medicos con diferentes IDs")
    void testEqualsDiferenteId() {
        Medico medico2 = new Medico("M002", "Dr. García", 25, 80, "A3B2O1");
        
        assertNotEquals(medico, medico2);
    }

    @Test
    @DisplayName("equals debe retornar false para medicos con diferentes niveles de infección")
    void testEqualsDiferenteInfeccion() {
        Medico medico2 = new Medico("M001", "Dr. García", 35, 80, "A3B2O1");
        
        assertNotEquals(medico, medico2);
    }

    @Test
    @DisplayName("equals debe retornar true comparando con sí mismo")
    void testEqualsMismoObjeto() {
        assertEquals(medico, medico);
    }

    @Test
    @DisplayName("equals debe retornar false cuando se compara con null")
    void testEqualsNull() {
        assertNotEquals(null, medico);
    }

    @Test
    @DisplayName("hashCode debe ser igual para medicos con los mismos datos")
    void testHashCodeIguales() {
        Medico medico2 = new Medico("M001", "Dr. García", 25, 80, "A3B2O1");
        
        assertEquals(medico.hashCode(), medico2.hashCode());
    }

    @Test
    @DisplayName("hashCode debe ser diferente para medicos con diferentes datos")
    void testHashCodeDiferentes() {
        Medico medico2 = new Medico("M002", "Dra. López", 25, 80, "A3B2O1");
        
        assertNotEquals(medico.hashCode(), medico2.hashCode());
    }

    @Test
    @DisplayName("toString debe contener la información del paciente")
    void testToString() {
        String resultado = medico.toString();
        
        assertTrue(resultado.contains("M001"));
        assertTrue(resultado.contains("25"));
        assertTrue(resultado.contains("80"));
        assertTrue(resultado.contains("A3B2O1"));
    }

    @Test
    @DisplayName("Múltiples degradaciones deben acumular efectos")
    void testMultiplesDegradaciones() {
        medico.aplicarDegradacion();
        medico.aplicarDegradacion();
        medico.aplicarDegradacion();
        
        assertEquals(85, medico.getnivelInfeccion()); // 25 + 20 + 20 + 20
        assertEquals(0, medico.getnivelSalud()); // 80 - 30 - 30 - 30 = -10, limitado a 0
    }

    @Test
    @DisplayName("Medico debe aceptar genomas complejos")
    void testGenomaComplejo() {
        Medico medicoComplejo = new Medico("M003", "Nombre", 30, 70, "A15B25O10A5B5");
        
        Map<String, Integer> receta = medicoComplejo.generarReceta();
        
        assertEquals(20, receta.get("A")); // 15 + 5
        assertEquals(30, receta.get("B")); // 25 + 5
        assertEquals(10, receta.get("O"));
        assertEquals(60, medicoComplejo.getTotalReceta());
    }

    @Test
    @DisplayName("Medico con diferentes valores extremos de salud e infección")
    void testValoresExtremos() {
        Medico medicoExtremo1 = new Medico("M004", "Nombre", 1, 99, "A1");
        assertEquals(1, medicoExtremo1.getnivelInfeccion());
        assertEquals(99, medicoExtremo1.getnivelSalud());
        
        Medico medicoExtremo2 = new Medico("M005", "Nombre", 99, 1, "B1");
        assertEquals(99, medicoExtremo2.getnivelInfeccion());
        assertEquals(1, medicoExtremo2.getnivelSalud());
    }

    @Test
    @DisplayName("Medico con salud crítica debe morir tras una degradación")
    void testMuerteCritica() {
        Medico medicoCritico = new Medico("M006", "Nombre", 50, 30, "O5");
        
        assertFalse(medicoCritico.estaMuerto());
        
        medicoCritico.aplicarDegradacion();
        
        assertTrue(medicoCritico.estaMuerto());
    }

    @Test
    @DisplayName("Comparación de infección entre Medico y Civil tras degradación")
    void testComparacionInfeccionConCivil() {
        Civil civil = new Civil("C001", "Nombre", 30, 70, "A5");
        Medico medicoComparacion = new Medico("M007", "Nombre", 30, 70, "A5");
        
        civil.degradacionInfeccion();
        medicoComparacion.degradacionInfeccion();
        
        assertEquals(40, civil.getnivelInfeccion()); // 30 + 10
        assertEquals(50, medicoComparacion.getnivelInfeccion()); // 30 + 20
        assertTrue(medicoComparacion.getnivelInfeccion() > civil.getnivelInfeccion());
    }

    @Test
    @DisplayName("Medico debe mantener estado consistente tras múltiples operaciones")
    void testConsistenciaEstado() {
        medico.generarReceta();
        medico.getTotalReceta();
        medico.degradacionInfeccion();
        
        assertEquals(45, medico.getnivelInfeccion());
        assertEquals(80, medico.getnivelSalud());
        assertFalse(medico.estaMuerto());
    }
}
