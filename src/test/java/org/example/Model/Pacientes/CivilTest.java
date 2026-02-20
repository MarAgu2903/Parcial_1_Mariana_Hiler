package org.example.Model.Pacientes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CivilTest {

    private Civil civil;

    @BeforeEach
    void setUp() {
        civil = new Civil("C001", "Juan Pérez", 30, 70, "A5B3O2");
    }

    @Test
    @DisplayName("Constructor debe crear un Civil válido con todos los parámetros correctos")
    void testConstructorValido() {
        assertNotNull(civil);
        assertEquals("C001", civil.getId());
        assertEquals(30, civil.getnivelInfeccion());
        assertEquals(70, civil.getnivelSalud());
        assertEquals("A5B3O2", civil.getgenoma());
    }

    @Test
    @DisplayName("Constructor debe aceptar nombre como parámetro adicional")
    void testConstructorConNombre() {
        Civil civilConNombre = new Civil("C002", "María López", 25, 75, "A3");
        assertNotNull(civilConNombre);
        assertEquals("C002", civilConNombre.getId());
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con ID nulo")
    void testConstructorIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil(null, "Nombre", 30, 70, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con ID vacío")
    void testConstructorIdVacio() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("", "Nombre", 30, 70, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección igual a 0")
    void testConstructorInfeccionCero() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("C001", "Nombre", 0, 70, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección igual a 100")
    void testConstructorInfeccionCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("C001", "Nombre", 100, 70, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección mayor a 100")
    void testConstructorInfeccionMayorCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("C001", "Nombre", 150, 70, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de infección negativo")
    void testConstructorInfeccionNegativo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("C001", "Nombre", -10, 70, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de infección en límite inferior válido (1)")
    void testConstructorInfeccionLimiteInferior() {
        Civil civilLimite = new Civil("C001", "Nombre", 1, 70, "A5");
        assertEquals(1, civilLimite.getnivelInfeccion());
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de infección en límite superior válido (99)")
    void testConstructorInfeccionLimiteSuperior() {
        Civil civilLimite = new Civil("C001", "Nombre", 99, 70, "A5");
        assertEquals(99, civilLimite.getnivelInfeccion());
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud igual a 0")
    void testConstructorSaludCero() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("C001", "Nombre", 30, 0, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud igual a 100")
    void testConstructorSaludCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("C001", "Nombre", 30, 100, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud mayor a 100")
    void testConstructorSaludMayorCien() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("C001", "Nombre", 30, 120, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con nivel de salud negativo")
    void testConstructorSaludNegativo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("C001", "Nombre", 30, -5, "A5")
        );
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de salud en límite inferior válido (1)")
    void testConstructorSaludLimiteInferior() {
        Civil civilLimite = new Civil("C001", "Nombre", 30, 1, "A5");
        assertEquals(1, civilLimite.getnivelSalud());
    }

    @Test
    @DisplayName("Constructor debe aceptar nivel de salud en límite superior válido (99)")
    void testConstructorSaludLimiteSuperior() {
        Civil civilLimite = new Civil("C001", "Nombre", 30, 99, "A5");
        assertEquals(99, civilLimite.getnivelSalud());
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con genoma nulo")
    void testConstructorGenomaNulo() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("C001", "Nombre", 30, 70, null)
        );
    }

    @Test
    @DisplayName("Constructor debe lanzar excepción con genoma vacío")
    void testConstructorGenomaVacio() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Civil("C001", "Nombre", 30, 70, "")
        );
    }

    @Test
    @DisplayName("getDegradacionInfeccion debe aumentar la infección en 10")
    void testDegradacionInfeccion() {
        int nivelInicial = civil.getnivelInfeccion();
        
        civil.degradacionInfeccion();
        
        assertEquals(nivelInicial + 10, civil.getnivelInfeccion());
    }

    @Test
    @DisplayName("getDegradacionInfeccion debe permitir superar 100")
    void testDegradacionInfeccionSuperaCien() {
        Civil civilAlto = new Civil("C002", "Nombre", 95, 70, "A5");
        
        civilAlto.degradacionInfeccion();
        
        assertEquals(105, civilAlto.getnivelInfeccion());
    }

    @Test
    @DisplayName("getDegradacionSalud debe disminuir la salud en 15")
    void testDegradacionSalud() {
        int nivelInicial = civil.getnivelSalud();
        
        civil.degradacionSalud();
        
        assertEquals(nivelInicial - 15, civil.getnivelSalud());
    }

    @Test
    @DisplayName("getDegradacionSalud debe permitir valores negativos")
    void testDegradacionSaludNegativo() {
        Civil civilBajo = new Civil("C002", "Nombre", 30, 10, "A5");
        
        civilBajo.degradacionSalud();
        
        assertEquals(-5, civilBajo.getnivelSalud());
    }

    @Test
    @DisplayName("aplicarDegradacion debe aplicar ambas degradaciones")
    void testAplicarDegradacion() {
        int nivelInfeccionInicial = civil.getnivelInfeccion();
        int nivelSaludInicial = civil.getnivelSalud();
        
        civil.aplicarDegradacion();
        
        assertEquals(nivelInfeccionInicial + 10, civil.getnivelInfeccion());
        assertEquals(nivelSaludInicial - 15, civil.getnivelSalud());
    }

    @Test
    @DisplayName("aplicarDegradacion debe limitar salud a 0 cuando es negativa")
    void testAplicarDegradacionLimitaSalud() {
        Civil civilBajo = new Civil("C002", "Nombre", 30, 10, "A5");
        
        civilBajo.aplicarDegradacion();
        
        assertEquals(0, civilBajo.getnivelSalud());
    }

    @Test
    @DisplayName("getPrioridad debe retornar 3 para Civil")
    void testGetPrioridad() {
        assertEquals(3, civil.getPrioridad());
    }

    @Test
    @DisplayName("estaMuerto debe retornar false cuando salud es mayor a 0")
    void testEstaMuertoFalso() {
        assertFalse(civil.estaMuerto());
    }

    @Test
    @DisplayName("estaMuerto debe retornar true cuando salud es 0")
    void testEstaMuertoSaludCero() {
        Civil civilMuerto = new Civil("C002", "Nombre", 30, 1, "A5");
        civilMuerto.aplicarDegradacion();
        
        assertTrue(civilMuerto.estaMuerto());
    }

    @Test
    @DisplayName("estaMuerto debe retornar true cuando salud es negativa")
    void testEstaMuertoSaludNegativa() {
        Civil civilMuerto = new Civil("C002", "Nombre", 30, 5, "A5");
        civilMuerto.aplicarDegradacion();
        
        assertTrue(civilMuerto.estaMuerto());
    }

    @Test
    @DisplayName("generarReceta debe generar receta correcta con un solo tipo")
    void testGenerarRecetaSimple() {
        Civil civilSimple = new Civil("C002", "Nombre", 30, 70, "A5");
        
        Map<String, Integer> receta = civilSimple.generarReceta();
        
        assertEquals(5, receta.get("A"));
        assertNull(receta.get("B"));
        assertNull(receta.get("O"));
    }

    @Test
    @DisplayName("generarReceta debe generar receta correcta con múltiples tipos")
    void testGenerarRecetaMultiple() {
        Map<String, Integer> receta = civil.generarReceta();
        
        assertEquals(5, receta.get("A"));
        assertEquals(3, receta.get("B"));
        assertEquals(2, receta.get("O"));
    }

    @Test
    @DisplayName("generarReceta debe sumar cantidades del mismo tipo")
    void testGenerarRecetaSumaRepetidos() {
        Civil civilRepetido = new Civil("C002", "Nombre", 30, 70, "A5B3A2");
        
        Map<String, Integer> receta = civilRepetido.generarReceta();
        
        assertEquals(7, receta.get("A")); // 5 + 2
        assertEquals(3, receta.get("B"));
    }

    @Test
    @DisplayName("getTotalReceta debe retornar la suma total de todas las dosis")
    void testGetTotalReceta() {
        int total = civil.getTotalReceta(); // A5B3O2 = 5+3+2 = 10
        
        assertEquals(10, total);
    }

    @Test
    @DisplayName("getTotalReceta debe funcionar con un solo tipo de genoma")
    void testGetTotalRecetaSimple() {
        Civil civilSimple = new Civil("C002", "Nombre", 30, 70, "A10");
        
        assertEquals(10, civilSimple.getTotalReceta());
    }

    @Test
    @DisplayName("equals debe retornar true para civiles con los mismos datos")
    void testEqualsIguales() {
        Civil civil2 = new Civil("C001", "Juan Pérez", 30, 70, "A5B3O2");
        
        assertEquals(civil, civil2);
    }

    @Test
    @DisplayName("equals debe retornar false para civiles con diferentes IDs")
    void testEqualsDiferenteId() {
        Civil civil2 = new Civil("C002", "Juan Pérez", 30, 70, "A5B3O2");
        
        assertNotEquals(civil, civil2);
    }

    @Test
    @DisplayName("equals debe retornar false para civiles con diferentes niveles de infección")
    void testEqualsDiferenteInfeccion() {
        Civil civil2 = new Civil("C001", "Juan Pérez", 40, 70, "A5B3O2");
        
        assertNotEquals(civil, civil2);
    }

    @Test
    @DisplayName("equals debe retornar true comparando con sí mismo")
    void testEqualsMismoObjeto() {
        assertEquals(civil, civil);
    }

    @Test
    @DisplayName("equals debe retornar false cuando se compara con null")
    void testEqualsNull() {
        assertNotEquals(null, civil);
    }

    @Test
    @DisplayName("hashCode debe ser igual para civiles con los mismos datos")
    void testHashCodeIguales() {
        Civil civil2 = new Civil("C001", "Juan Pérez", 30, 70, "A5B3O2");
        
        assertEquals(civil.hashCode(), civil2.hashCode());
    }

    @Test
    @DisplayName("hashCode debe ser diferente para civiles con diferentes datos")
    void testHashCodeDiferentes() {
        Civil civil2 = new Civil("C002", "María", 30, 70, "A5B3O2");
        
        assertNotEquals(civil.hashCode(), civil2.hashCode());
    }

    @Test
    @DisplayName("toString debe contener la información del paciente")
    void testToString() {
        String resultado = civil.toString();
        
        assertTrue(resultado.contains("C001"));
        assertTrue(resultado.contains("30"));
        assertTrue(resultado.contains("70"));
        assertTrue(resultado.contains("A5B3O2"));
    }

    @Test
    @DisplayName("Múltiples degradaciones deben acumular efectos")
    void testMultiplesDegradaciones() {
        civil.aplicarDegradacion();
        civil.aplicarDegradacion();
        civil.aplicarDegradacion();
        
        assertEquals(60, civil.getnivelInfeccion()); // 30 + 10 + 10 + 10
        assertEquals(25, civil.getnivelSalud()); // 70 - 15 - 15 - 15
    }

    @Test
    @DisplayName("Civil debe aceptar genomas complejos")
    void testGenomaComplejo() {
        Civil civilComplejo = new Civil("C003", "Nombre", 40, 60, "A10B20O30A5B15");
        
        Map<String, Integer> receta = civilComplejo.generarReceta();
        
        assertEquals(15, receta.get("A")); // 10 + 5
        assertEquals(35, receta.get("B")); // 20 + 15
        assertEquals(30, receta.get("O"));
        assertEquals(80, civilComplejo.getTotalReceta());
    }

    @Test
    @DisplayName("Civil con diferentes valores extremos de salud e infección")
    void testValoresExtremos() {
        Civil civilExtremo1 = new Civil("C004", "Nombre", 1, 99, "A1");
        assertEquals(1, civilExtremo1.getnivelInfeccion());
        assertEquals(99, civilExtremo1.getnivelSalud());
        
        Civil civilExtremo2 = new Civil("C005", "Nombre", 99, 1, "B1");
        assertEquals(99, civilExtremo2.getnivelInfeccion());
        assertEquals(1, civilExtremo2.getnivelSalud());
    }
}
