package ejercicio10;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Perfil de riesgo de un cliente (sujeto observable de grado empresarial).
 * - La lista de observadores es segura para varios hilos y conserva el orden de registro.
 * - El cambio de perfil es sincronizado: las notificaciones son secuenciales y nunca se mezclan.
 * - Cada observador se ejecuta dentro de su propio try-catch para evitar fallos en cascada.
 * - Cada cambio deja un informe inmutable en el historial de trazabilidad.
 */
public class CustomerRiskProfile {

    private final String customerId;
    private RiskLevel riskLevel;
    private final CopyOnWriteArrayList<ComplianceObserver> observers = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<NotificationReport> auditTrail = new CopyOnWriteArrayList<>();

    public CustomerRiskProfile(String customerId, RiskLevel initialLevel) {
        this.customerId = customerId;
        this.riskLevel = initialLevel;
    }

    public void addObserver(ComplianceObserver observer) {
        if (observer != null) {
            observers.addIfAbsent(observer);
        }
    }

    public void removeObserver(ComplianceObserver observer) {
        observers.remove(observer);
    }

    /**
     * Cambia el nivel de riesgo y notifica en orden a todos los observadores.
     *
     * @return informe inmutable con el resultado de cada notificación
     */
    public synchronized NotificationReport changeRiskLevel(RiskLevel newLevel, String reason) {
        RiskProfileChange change = new RiskProfileChange(customerId, riskLevel, newLevel, reason, LocalDateTime.now());
        riskLevel = newLevel;

        List<DeliveryRecord> deliveries = new ArrayList<>();
        for (ComplianceObserver observer : observers) {
            String observerName = observer.getName();
            try {
                observer.onRiskProfileChanged(change);
                deliveries.add(new DeliveryRecord(observerName, DeliveryStatus.SUCCESS, "Procesado correctamente"));
            } catch (Exception e) {
                // Aislamiento de errores: se registra la incidencia y se continúa con el siguiente observador
                deliveries.add(new DeliveryRecord(observerName, DeliveryStatus.FAILED,
                        e.getClass().getSimpleName() + ": " + e.getMessage()));
            }
        }

        NotificationReport report = new NotificationReport(change, deliveries);
        auditTrail.add(report);
        return report;
    }

    public List<NotificationReport> getAuditTrail() {
        return List.copyOf(auditTrail);
    }

    public String getCustomerId() {
        return customerId;
    }
}
