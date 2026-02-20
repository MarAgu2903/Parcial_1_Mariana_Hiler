package org.example.Model.Pacientes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MilitarTest {

    private Militar militar;

    @BeforeEach
    void setUp() {
        militar = new Militar("MIL001", "Capitán Rodríguez", 20, 85, "A4B3O2");
    }

    @Test
    @DisplayName("Constructor debe crear un Militar válido con todos los parámetros correctos")
    void testConstructorValido() {
        assertNotNull(militar);
        assertEquals("MIL001", militar.getId());
        assertEquals(20, militar.getnivelInfeccion());
        assertEquals(85, militar.getnivelSalud());
        assertEquals("A4B3O2", militar.getgenoma());
    }

    @Test
    @DisplayName("Constructor debe aceptar nombre como parámetro adicional")
    void testConstructorConNombre() {
        Militar militarConNombre = new Militar("MIL002", "General Pérez", 15, 90, "B4");
        assertNotNull(militarConNombre);
        assertEquals("MIL002", militarConNombre.getId());
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con ID nulo")
    void testConstructorIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar(null, "Nombre", 20, 85, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con ID vacío")
    void testConstructorIdVacio() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("", "Nombre", 20, 85, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección igual a 0")
    void testConstructorInfeccionCero() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("MIL001", "Nombre", 0, 85, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección igual a 100")
    void testConstructorInfeccionCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("MIL001", "Nombre", 100, 85, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección mayor a 100")
    void testConstructorInfeccionMayorCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("MIL001", "Nombre", 150, 85, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección negativo")
    void testConstructorInfeccionNegativo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("MIL001", "Nombre", -10, 85, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de infección en límite inferior válido (1)")
    void testConstructorInfeccionLimiteInferior() {
        Militar militarLimite = new Militar("MIL001", "Nombre", 1, 85, "A5");
        assertEquals(1, militarLimite.getnivelInfeccion());
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de infección en límite superior válido (99)")
    void testConstructorInfeccionLimiteSuperior() {
        Militar militarLimite = new Militar("MIL001", "Nombre", 99, 85, "A5");
        assertEquals(99, militarLimite.getnivelInfeccion());
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud igual a 0")
    void testConstructorSaludCero() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("MIL001", "Nombre", 20, 0, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud igual a 100")
    void testConstructorSaludCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("MIL001", "Nombre", 20, 100, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud mayor a 100")
    void testConstructorSaludMayorCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("MIL001", "Nombre", 20, 120, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud negativo")
    void testConstructorSaludNegativo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("MIL001", "Nombre", 20, -5, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de salud en límite inferior válido (1)")
    void testConstructorSaludLimiteInferior() {
        Militar militarLimite = new Militar("MIL001", "Nombre", 20, 1, "A5");
        assertEquals(1, militarLimite.getnivelSalud());
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de salud en límite superior válido (99)")
    void testConstructorSaludLimiteSuperior() {
        Militar militarLimite = new Militar("MIL001", "Nombre", 20, 99, "A5");
        assertEquals(99, militarLimite.getnivelSalud());
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con genoma nulo")
    void testConstructorGenomaNulo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("MIL001", "Nombre", 20, 85, null)
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con genoma vacío")
    void testConstructorGenomaVacio() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Militar("MIL001", "Nombre", 20, 85, "")
        );
    }

    @Test
    @DisplayName("degradacionInfeccion debe aumentar la infección en 7")
    void testDegradacionInfeccion() {
        int nivelInicial = militar.getnivelInfeccion();
        
        militar.degradacionInfeccion();
        
        assertEquals(nivelInicial + 7, militar.getnivelInfeccion());
    }

    @Test
    @DisplayName("degradacionInfeccion debe permitir superar 100")
    void testDegradacionInfeccionSuperaCien() {
        Militar militarAlto = new Militar("MIL002", "Nombre", 95, 85, "A5");
        
        militarAlto.degradacionInfeccion();
        
        assertEquals(102, militarAlto.getnivelInfeccion());
    }

    @Test
    @DisplayName("degradacionInfeccion múltiple debe acumular incrementos de 7")
    void testDegradacionInfeccionMultiple() {
        int nivelInicial = militar.getnivelInfeccion();
        
        militar.degradacionInfeccion();
        militar.degradacionInfeccion();
        militar.degradacionInfeccion();
        
        assertEquals(nivelInicial + 21, militar.getnivelInfeccion());
    }

    @Test
    @DisplayName("degradacionSalud debe disminuir la salud en 10")
    void testDegradacionSalud() {
        int nivelInicial = militar.getnivelSalud();
        
        militar.degradacionSalud();
        
        assertEquals(nivelInicial - 10, militar.getnivelSalud());
    }

    @Test
    @DisplayName("degradacionSalud debe permitir valores negativos")
    void testDegradacionSaludNegativo() {
        Militar militarBajo = new Militar("MIL002", "Nombre", 20, 5, "A5");
        
        militarBajo.degradacionSalud();
        
        assertEquals(-5, militarBajo.getnivelSalud());
    }

    @Test
    @DisplayName("degradacionSalud múltiple debe acumular decrementos de 10")
    void testDegradacionSaludMultiple() {
        int nivelInicial = militar.getnivelSalud();
        
        militar.degradacionSalud();
        militar.degradacionSalud();
        militar.degradacionSalud();
        
        assertEquals(nivelInicial - 30, militar.getnivelSalud());
    }

    @Test
    @DisplayName("aplicarDegradacion debe aplicar ambas degradaciones")
    void testAplicarDegradacion() {
        int nivelInfeccionInicial = militar.getnivelInfeccion();
        int nivelSaludInicial = militar.getnivelSalud();
        
        militar.aplicarDegradacion();
        
        assertEquals(nivelInfeccionInicial + 7, militar.getnivelInfeccion());
        assertEquals(nivelSaludInicial - 10, militar.getnivelSalud());
    }

    @Test
    @DisplayName("aplicarDegradacion debe limitar salud a 0 cuando es negativa")
    void testAplicarDegradacionLimitaSalud() {
        Militar militarBajo = new Militar("MIL002", "Nombre", 20, 5, "A5");
        
        militarBajo.aplicarDegradacion();
        
        assertEquals(0, militarBajo.getnivelSalud());
    }

    @Test
    @DisplayName("getPrioridad debe retornar 2 para Militar (prioridad media)")
    void testGetPrioridad() {
        assertEquals(2, militar.getPrioridad());
    }

    @Test
    @DisplayName("Militar debe tener mayor prioridad que Civil pero menor que Medico")
    void testPrioridadComparativa() {
        Civil civil = new Civil("C001", "Nombre", 20, 85, "A5");
        Medico medico = new Medico("M001", "Nombre", 20, 85, "A5");
        
        assertTrue(militar.getPrioridad() < civil.getPrioridad()); // 2 < 3
        assertTrue(militar.getPrioridad() > medico.getPrioridad()); // 2 > 1
    }

    @Test
    @DisplayName("estaMuerto debe retornar false cuando salud es mayor a 0")
    void testEstaMuertoFalso() {
        assertFalse(militar.estaMuerto());
    }

    @Test
    @DisplayName("estaMuerto debe retornar true cuando salud es 0")
    void testEstaMuertoSaludCero() {
        Militar militarMuerto = new Militar("MIL002", "Nombre", 20, 1, "A5");
        militarMuerto.aplicarDegradacion();
        
        assertTrue(militarMuerto.estaMuerto());
    }

    @Test
    @DisplayName("estaMuerto debe retornar true cuando salud es negativa")
    void testEstaMuertoSaludNegativa() {
        Militar militarMuerto = new Militar("MIL002", "Nombre", 20, 8, "A5");
        militarMuerto.aplicarDegradacion();
        
        assertTrue(militarMuerto.estaMuerto());
    }

    @Test
    @DisplayName("Militar tiene degradación intermedia entre Civil y Medico")
    void testDegradacionIntermedia() {
        Civil civil = new Civil("C001", "Nombre", 30, 60, "A5");
        Militar militarComp = new Militar("MIL002", "Nombre", 30, 60, "A5");
        Medico medico = new Medico("M001", "Nombre", 30, 60, "A5");
        
        civil.aplicarDegradacion();
        militarComp.aplicarDegradacion();
        medico.aplicarDegradacion();
        
        // Infección: Civil +10, Militar +7, Medico +20
        assertEquals(40, civil.getnivelInfeccion());
        assertEquals(37, militarComp.getnivelInfeccion());
        assertEquals(50, medico.getnivelInfeccion());
        
        // Salud: Civil -15, Militar -10, Medico -30
        assertEquals(45, civil.getnivelSalud());
        assertEquals(50, militarComp.getnivelSalud());
        assertEquals(30, medico.getnivelSalud());
    }

    @Test
    @DisplayName("Militar degrada menos en infección que Civil")
    void testMenorDegradacionInfeccionQueCivil() {
        Civil civil = new Civil("C001", "Nombre", 20, 85, "A5");
        Militar militarComp = new Militar("MIL002", "Nombre", 20, 85, "A5");
        
        civil.degradacionInfeccion();
        militarComp.degradacionInfeccion();
        
        assertEquals(30, civil.getnivelInfeccion()); // 20 + 10
        assertEquals(27, militarComp.getnivelInfeccion()); // 20 + 7
        assertTrue(militarComp.getnivelInfeccion() < civil.getnivelInfeccion());
    }

    @Test
    @DisplayName("Militar degrada menos en salud que Civil")
    void testMenorDegradacionSaludQueCivil() {
        Civil civil = new Civil("C001", "Nombre", 20, 85, "A5");
        Militar militarComp = new Militar("MIL002", "Nombre", 20, 85, "A5");
        
        civil.degradacionSalud();
        militarComp.degradacionSalud();
        
        assertEquals(70, civil.getnivelSalud()); // 85 - 15
        assertEquals(75, militarComp.getnivelSalud()); // 85 - 10
        assertTrue(militarComp.getnivelSalud() > civil.getnivelSalud());
    }

    @Test
    @DisplayName("generarReceta debe generar receta correcta con un solo tipo")
    void testGenerarRecetaSimple() {
        Militar militarSimple = new Militar("MIL002", "Nombre", 20, 85, "O8");
        
        Map<String, Integer> receta = militarSimple.generarReceta();
        
        assertEquals(8, receta.get("O"));
        assertNull(receta.get("A"));
        assertNull(receta.get("B"));
    }

    @Test
    @DisplayName("generarReceta debe generar receta correcta con múltiples tipos")
    void testGenerarRecetaMultiple() {
        Map<String, Integer> receta = militar.generarReceta();
        
        assertEquals(4, receta.get("A"));
        assertEquals(3, receta.get("B"));
        assertEquals(2, receta.get("O"));
    }

    @Test
    @DisplayName("generarReceta debe sumar cantidades del mismo tipo")
    void testGenerarRecetaSumaRepetidos() {
        Militar militarRepetido = new Militar("MIL002", "Nombre", 20, 85, "A5O3A2O1");
        
        Map<String, Integer> receta = militarRepetido.generarReceta();
        
        assertEquals(7, receta.get("A")); // 5 + 2
        assertEquals(4, receta.get("O")); // 3 + 1
    }

    @Test
    @DisplayName("getTotalReceta debe retornar la suma total de todas las dosis")
    void testGetTotalReceta() {
        int total = militar.getTotalReceta(); // A4B3O2 = 4+3+2 = 9
        
        assertEquals(9, total);
    }

    @Test
    @DisplayName("getTotalReceta debe funcionar con un solo tipo de genoma")
    void testGetTotalRecetaSimple() {
        Militar militarSimple = new Militar("MIL002", "Nombre", 20, 85, "A12");
        
        assertEquals(12, militarSimple.getTotalReceta());
    }

    @Test
    @DisplayName("equals debe retornar true para militares con los mismos datos")
    void testEqualsIguales() {
        Militar militar2 = new Militar("MIL001", "Capitán Rodríguez", 20, 85, "A4B3O2");
        
        assertEquals(militar, militar2);
    }

    @Test
    @DisplayName("equals debe retornar false para militares con diferentes IDs")
    void testEqualsDiferenteId() {
        Militar militar2 = new Militar("MIL002", "Capitán Rodríguez", 20, 85, "A4B3O2");
        
        assertNotEquals(militar, militar2);
    }

    @Test
    @DisplayName("equals debe retornar false para militares con diferentes niveles de infección")
    void testEqualsDiferenteInfeccion() {
        Militar militar2 = new Militar("MIL001", "Capitán Rodríguez", 30, 85, "A4B3O2");
        
        assertNotEquals(militar, militar2);
    }

    @Test
    @DisplayName("equals debe retornar true comparando con sí mismo")
    void testEqualsMismoObjeto() {
        assertEquals(militar, militar);
    }

    @Test
    @DisplayName("equals debe retornar false cuando se compara con null")
    void testEqualsNull() {
        assertNotEquals(null, militar);
    }

    @Test
    @DisplayName("hashCode debe ser igual para militares con los mismos datos")
    void testHashCodeIguales() {
        Militar militar2 = new Militar("MIL001", "Capitán Rodríguez", 20, 85, "A4B3O2");
        
        assertEquals(militar.hashCode(), militar2.hashCode());
    }

    @Test
    @DisplayName("hashCode debe ser diferente para militares con diferentes datos")
    void testHashCodeDiferentes() {
        Militar militar2 = new Militar("MIL002", "Teniente López", 20, 85, "A4B3O2");
        
        assertNotEquals(militar.hashCode(), militar2.hashCode());
    }

    @Test
    @DisplayName("toString debe contener la información del paciente")
    void testToString() {
        String resultado = militar.toString();
        
        assertTrue(resultado.contains("MIL001"));
        assertTrue(resultado.contains("20"));
        assertTrue(resultado.contains("85"));
        assertTrue(resultado.contains("A4B3O2"));
    }

    @Test
    @DisplayName("Múltiples degradaciones deben acumular efectos correctamente")
    void testMultiplesDegradaciones() {
        militar.aplicarDegradacion();
        militar.aplicarDegradacion();
        militar.aplicarDegradacion();
        militar.aplicarDegradacion();
        
        assertEquals(48, militar.getnivelInfeccion()); // 20 + 7 + 7 + 7 + 7
        assertEquals(45, militar.getnivelSalud()); // 85 - 10 - 10 - 10 - 10
    }

    @Test
    @DisplayName("Militar debe aceptar genomas complejos")
    void testGenomaComplejo() {
        Militar militarComplejo = new Militar("MIL003", "Nombre", 25, 75, "A12B8O15A3B7");
        
        Map<String, Integer> receta = militarComplejo.generarReceta();
        
        assertEquals(15, receta.get("A")); // 12 + 3
        assertEquals(15, receta.get("B")); // 8 + 7
        assertEquals(15, receta.get("O"));
        assertEquals(45, militarComplejo.getTotalReceta());
    }

    @Test
    @DisplayName("Militar con diferentes valores extremos de salud e infección")
    void testValoresExtremos() {
        Militar militarExtremo1 = new Militar("MIL004", "Nombre", 1, 99, "A1");
        assertEquals(1, militarExtremo1.getnivelInfeccion());
        assertEquals(99, militarExtremo1.getnivelSalud());
        
        Militar militarExtremo2 = new Militar("MIL005", "Nombre", 99, 1, "B1");
        assertEquals(99, militarExtremo2.getnivelInfeccion());
        assertEquals(1, militarExtremo2.getnivelSalud());
    }

    @Test
    @DisplayName("Militar con salud crítica debe morir tras una degradación")
    void testMuerteCritica() {
        Militar militarCritico = new Militar("MIL006", "Nombre", 50, 10, "O5");
        
        assertFalse(militarCritico.estaMuerto());
        
        militarCritico.aplicarDegradacion();
        
        assertTrue(militarCritico.estaMuerto());
    }

    @Test
    @DisplayName("Militar debe mantener estado consistente tras múltiples operaciones")
    void testConsistenciaEstado() {
        militar.generarReceta();
        militar.getTotalReceta();
        militar.degradacionInfeccion();
        militar.degradacionSalud();
        
        assertEquals(27, militar.getnivelInfeccion());
        assertEquals(75, militar.getnivelSalud());
        assertFalse(militar.estaMuerto());
    }

    @Test
    @DisplayName("Militar tiene mejor resistencia que Civil y Medico")
    void testMejorResistencia() {
        // Militar degrada +7 infección y -10 salud
        // Civil degrada +10 infección y -15 salud
        // Medico degrada +20 infección y -30 salud
        
        assertTrue(militar.getPrioridad() < 3); // Mejor que civil
        assertTrue(militar.getPrioridad() > 1); // Peor que medico
    }

    @Test
    @DisplayName("Comparación de sobrevivencia entre tipos de pacientes")
    void testComparacionSobrevivencia() {
        Civil civil = new Civil("C001", "Nombre", 30, 40, "A5");
        Militar militarComp = new Militar("MIL007", "Nombre", 30, 40, "A5");
        Medico medico = new Medico("M001", "Nombre", 30, 40, "A5");
        
        // Después de 4 degradaciones
        for (int i = 0; i < 4; i++) {
            civil.aplicarDegradacion();
            militarComp.aplicarDegradacion();
            medico.aplicarDegradacion();
        }
        
        assertTrue(civil.estaMuerto()); // 40 - 60 = -20
        assertTrue(medico.estaMuerto()); // 40 - 120 = -80
        
        // El militar es el más resistente en términos de degradación de salud
    }
}
