package ejercicio10;

import java.util.List;

/**
 * Registro inmutable de un cambio de perfil: qué observadores fueron notificados
 * con éxito y cuáles reportaron incidencias.
 */
public record NotificationReport(RiskProfileChange change, List<DeliveryRecord> deliveries) {

    public NotificationReport {
        deliveries = List.copyOf(deliveries);
    }

    public long successCount() {
        return deliveries.stream().filter(d -> d.status() == DeliveryStatus.SUCCESS).count();
    }

    public long failureCount() {
        return deliveries.stream().filter(d -> d.status() == DeliveryStatus.FAILED).count();
    }
}
