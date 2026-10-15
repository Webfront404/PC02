package andesdrone.vehiculos;

import andesdrone.modelo.Paquete;

/** final: hoja de la rama sellada RobotTerrestre. */
public final class RobotRuedas extends RobotTerrestre {

    public RobotRuedas(String id, String modelo, double capacidadMaxKg,
                       double consumoPorKm, double velocidadMaxKmH) {
        super(id, modelo, capacidadMaxKg, consumoPorKm, velocidadMaxKmH);
    }

    // 3.00 + 1.20 x km + 1.00 x peso
    @Override
    public double calcularCostoEntrega(double distanciaKm, Paquete p) {
        return 3.00 + 1.20 * distanciaKm + 1.00 * p.getPesoKg();
    }
}
