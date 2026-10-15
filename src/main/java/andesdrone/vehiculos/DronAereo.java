package andesdrone.vehiculos;

import andesdrone.modelo.Paquete;

/**
 * non-sealed: rompe el sellado en esta rama, asi se pueden crear nuevos tipos de
 * dron (por ejemplo DronPesado) sin modificar la lista permits de VehiculoAutonomo.
 */
public non-sealed class DronAereo extends VehiculoAutonomo {

    private int altitudMaxM;   // debe ser > 0

    public DronAereo(String id, String modelo, double capacidadMaxKg,
                     double consumoPorKm, int altitudMaxM) {
        super(id, modelo, capacidadMaxKg, consumoPorKm);
        this.altitudMaxM = (altitudMaxM > 0) ? altitudMaxM : 120;   // 120 si llega uno invalido
    }

    // Constructor sobrecargado: encadena con altitudMaxM = 120
    public DronAereo(String id, String modelo, double capacidadMaxKg, double consumoPorKm) {
        this(id, modelo, capacidadMaxKg, consumoPorKm, 120);
    }

    public int getAltitudMaxM() {
        return altitudMaxM;
    }

    public boolean setAltitudMaxM(int altitudMaxM) {
        if (altitudMaxM <= 0) {
            return false;
        }
        this.altitudMaxM = altitudMaxM;
        return true;
    }

    // 5.00 + 2.00 x km + 1.50 x peso
    @Override
    public double calcularCostoEntrega(double distanciaKm, Paquete p) {
        return 5.00 + 2.00 * distanciaKm + 1.50 * p.getPesoKg();
    }

    // SOBRESCRITURA: los drones recargan 1.0 % por minuto (tope 100).
    // Accede directo a "bateria" porque es protected en la superclase.
    @Override
    public void recargar(int minutos) {
        if (minutos <= 0) {
            return;
        }
        bateria = Math.min(100.0, bateria + minutos * 1.0);
    }

    // Reutiliza el toString() de la superclase con super
    @Override
    public String toString() {
        return super.toString() + " | altitud m\u00e1x: " + altitudMaxM + " m";
    }
}
