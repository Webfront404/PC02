package andesdrone.vehiculos;

import andesdrone.modelo.Paquete;

/** final: hoja de la rama sellada RobotTerrestre. Ademas es Asegurable. */
public final class RobotOruga extends RobotTerrestre implements Asegurable {

    public RobotOruga(String id, String modelo, double capacidadMaxKg,
                      double consumoPorKm, double velocidadMaxKmH) {
        super(id, modelo, capacidadMaxKg, consumoPorKm, velocidadMaxKmH);
    }

    // 4.00 + 1.50 x km + 0.80 x peso
    @Override
    public double calcularCostoEntrega(double distanciaKm, Paquete p) {
        return 4.00 + 1.50 * distanciaKm + 0.80 * p.getPesoKg();
    }

    // SOBRESCRITURA con super: reutiliza la validacion de peso y agrega la
    // restriccion propia (las orugas vibran demasiado para paquetes fragiles)
    @Override
    public boolean puedeTransportar(Paquete p) {
        return super.puedeTransportar(p) && !p.isFragil();
    }

    @Override
    public double calcularPrima() {
        return getCapacidadMaxKg() * 7.5;
    }
}
