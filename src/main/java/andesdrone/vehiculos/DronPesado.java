package andesdrone.vehiculos;

import andesdrone.modelo.Paquete;

/**
 * final: es una hoja de la jerarquia, nadie puede extenderla.
 * Implementa Asegurable ademas de heredar Operable a traves de VehiculoAutonomo.
 */
public final class DronPesado extends DronAereo implements Asegurable {

    // consumoPorKm fijo en 6.0
    public DronPesado(String id, String modelo, double capacidadMaxKg, int altitudMaxM) {
        super(id, modelo, capacidadMaxKg, 6.0, altitudMaxM);
    }

    // Reutiliza la formula de DronAereo con super y aplica un recargo de 25 %
    @Override
    public double calcularCostoEntrega(double distanciaKm, Paquete p) {
        return super.calcularCostoEntrega(distanciaKm, p) * 1.25;
    }

    @Override
    public double calcularPrima() {
        return getCapacidadMaxKg() * 12.0;
    }
}
