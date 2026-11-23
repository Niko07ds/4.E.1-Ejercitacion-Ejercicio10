package ejercicio10;

/**
 * Subsistema de prevención de lavado de dinero.
 * Simula una falla del servicio externo cuando el riesgo pasa a crítico.
 */
public class AntiMoneyLaunderingSystem implements ComplianceObserver {

    @Override
    public String getName() {
        return "Prevención de lavado de dinero";
    }

    @Override
    public void onRiskProfileChanged(RiskProfileChange change) {
        if (change.newLevel() == RiskLevel.CRITICAL) {
            throw new IllegalStateException("el servicio de listas restrictivas no está disponible");
        }
        System.out.println("Prevención de lavado de dinero: analizando transacciones del cliente " + change.customerId());
    }
}
