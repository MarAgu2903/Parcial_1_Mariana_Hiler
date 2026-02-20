package org.example.Model.Sistema;

public class CargaViral {

    private int CargaActual;

    private static final int CargaInicial = 500;
    private static final int UmbralColapso = 1200;
    private static final int IncrementoNatural = 20;
    private static final int PenalizacionMutacion = 70;
    private static final int BonificacionRecuperado = 100;

    public CargaViral() {
        this.CargaActual = CargaInicial;
    }

    // Incremento por turno
    public void incrementarNatural() {
        CargaActual += IncrementoNatural;
    }


    // Penalización acumulada por focos infecciosos activos.
    public void aplicarPenalizacionMutacion(int cantidadFocos) {
        CargaActual += (PenalizacionMutacion * cantidadFocos);
    }

    //Bonificación por recuperación exitosa.
    public void aplicarBonificacionRecuperacion() {
        CargaActual -= BonificacionRecuperado;
        if (CargaActual < 0) {
            CargaActual = 0;
        }
    }

    //Verificar colapso
    public boolean sistemaColapsado() {
        return CargaActual >= UmbralColapso;
    }

    public int getCargaActual() {
        return CargaActual;
    }

    @Override
    public String toString() {
        return "Carga Viral Actual: " + CargaActual;
    }

}
