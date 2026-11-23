package ejercicio10;

/**
 * Registro inmutable del resultado de notificar a un observador.
 */
public record DeliveryRecord(String observerName, DeliveryStatus status, String detail) {
}
