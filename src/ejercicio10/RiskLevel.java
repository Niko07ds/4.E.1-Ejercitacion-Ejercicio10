package ejercicio10;

/**
 * Niveles del perfil de riesgo de un cliente.
 */
public enum RiskLevel {
    LOW("Bajo"),
    MEDIUM("Medio"),
    HIGH("Alto"),
    CRITICAL("Crítico");

    private final String label;

    RiskLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
