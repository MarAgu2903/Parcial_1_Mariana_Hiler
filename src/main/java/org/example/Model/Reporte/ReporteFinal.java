package org.example.Model.Reporte;

import java.util.List;
import java.util.Map;

public class ReporteFinal {

        private Map<String, Integer> sobrevivientesPorTipo;
        private List<String> fallecidos;
        private int cargaViralFinal;
        private boolean sistemaColapsado;

        public ReporteFinal(Map<String, Integer> sobrevivientesPorTipo,
                            List<String> fallecidos,
                            int cargaViralFinal,
                            boolean sistemaColapsado) {

            this.sobrevivientesPorTipo = sobrevivientesPorTipo;
            this.fallecidos = fallecidos;
            this.cargaViralFinal = cargaViralFinal;
            this.sistemaColapsado = sistemaColapsado;
        }

        public Map<String, Integer> getSobrevivientesPorTipo() { return sobrevivientesPorTipo; }
        public List<String> getFallecidos() { return fallecidos; }
        public int getCargaViralFinal() { return cargaViralFinal; }
        public boolean isSistemaColapsado() { return sistemaColapsado; }
}


