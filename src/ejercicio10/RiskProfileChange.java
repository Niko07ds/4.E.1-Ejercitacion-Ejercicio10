package ejercicio10;

import java.time.LocalDateTime;

/**
 * Cambio de perfil de riesgo. Es inmutable y viaja a todos los observadores regulatorios.
 */
public record RiskProfileChange(String customerId, RiskLevel previousLevel, RiskLevel newLevel,
                                String reason, LocalDateTime timestamp) {
}
