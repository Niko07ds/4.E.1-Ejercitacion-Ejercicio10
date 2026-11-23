package ejercicio10;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CustomerRiskProfile profile = new CustomerRiskProfile("C-1001", RiskLevel.LOW);

        System.out.println("Registrando los subsistemas regulatorios obligatorios");
        profile.addObserver(new LegalComplianceSystem());
        profile.addObserver(new AntiMoneyLaunderingSystem());
        profile.addObserver(new RegulatoryReportGenerator());

        System.out.println();
        System.out.println("Cambio 1: el riesgo del cliente pasa a Alto");
        System.out.println(formatReport(profile.changeRiskLevel(RiskLevel.HIGH, "Transferencias inusuales")));

        System.out.println();
        System.out.println("Cambio 2: el riesgo del cliente pasa a Crítico (un subsistema falla)");
        System.out.println(formatReport(profile.changeRiskLevel(RiskLevel.CRITICAL, "Coincidencia con lista de sanciones")));

        System.out.println();
        System.out.println("Cambios 3 y 4: dos hilos modifican el mismo perfil al mismo tiempo");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            Future<String> first = executor.submit(
                    () -> formatReport(profile.changeRiskLevel(RiskLevel.MEDIUM, "Revisión manual del analista")));
            Future<String> second = executor.submit(
                    () -> formatReport(profile.changeRiskLevel(RiskLevel.LOW, "Documentación validada")));
            System.out.println(first.get());
            System.out.println(second.get());
        } finally {
            executor.shutdown();
        }

        System.out.println();
        System.out.println("Historial de trazabilidad del cliente " + profile.getCustomerId());
        for (NotificationReport report : profile.getAuditTrail()) {
            System.out.println(report.change().previousLevel().getLabel() + " -> " + report.change().newLevel().getLabel()
                    + ": " + report.successCount() + " exitosa(s), " + report.failureCount() + " con incidencia");
        }
    }

    private static String formatReport(NotificationReport report) {
        RiskProfileChange change = report.change();
        StringBuilder text = new StringBuilder();
        text.append("Informe de notificación (").append(change.timestamp().format(FORMATTER)).append(") | ")
                .append(change.previousLevel().getLabel()).append(" -> ").append(change.newLevel().getLabel());
        for (DeliveryRecord delivery : report.deliveries()) {
            text.append(System.lineSeparator()).append("  - ").append(delivery.observerName()).append(": ")
                    .append(delivery.status().getLabel()).append(" (").append(delivery.detail()).append(")");
        }
        return text.toString();
    }
}
