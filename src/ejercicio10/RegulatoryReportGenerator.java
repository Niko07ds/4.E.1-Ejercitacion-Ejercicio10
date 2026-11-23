package ejercicio10;

/**
 * Generador de reportes regulatorios obligatorios.
 */
public class RegulatoryReportGenerator implements ComplianceObserver {

    @Override
    public String getName() {
        return "Reportes regulatorios";
    }

    @Override
    public void onRiskProfileChanged(RiskProfileChange change) {
        System.out.println("Reportes regulatorios: reporte obligatorio generado para el cliente " + change.customerId()
                + " por el motivo \"" + change.reason() + "\"");
    }
}
