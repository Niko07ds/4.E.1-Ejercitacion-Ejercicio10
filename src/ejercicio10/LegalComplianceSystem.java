package ejercicio10;

/**
 * Subsistema de cumplimiento legal.
 */
public class LegalComplianceSystem implements ComplianceObserver {

    @Override
    public String getName() {
        return "Cumplimiento legal";
    }

    @Override
    public void onRiskProfileChanged(RiskProfileChange change) {
        System.out.println("Cumplimiento legal: revisando el cliente " + change.customerId() + " ("
                + change.previousLevel().getLabel() + " -> " + change.newLevel().getLabel() + ")");
    }
}
