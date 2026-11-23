package ejercicio10;

/**
 * Interfaz común de los subsistemas regulatorios obligatorios.
 * Puede lanzar cualquier excepción: el sujeto la aísla y la registra.
 */
public interface ComplianceObserver {

    String getName();

    void onRiskProfileChanged(RiskProfileChange change) throws Exception;
}
