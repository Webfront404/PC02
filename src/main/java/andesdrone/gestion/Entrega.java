package andesdrone.gestion;

import andesdrone.modelo.Paquete;
import andesdrone.vehiculos.VehiculoAutonomo;

/**
 * - AGREGACION con VehiculoAutonomo: la entrega usa un vehiculo que existe por
 *   su cuenta (pertenece a la flota); solo guarda su referencia.
 * - ASOCIACION unidireccional Entrega -> Paquete: la entrega conoce al paquete,
 *   el paquete no conoce a la entrega.
 * Ninguno de los dos se crea ni se copia aqui.
 */
public class Entrega {

    private String codigo;
    private Paquete paquete;
    private VehiculoAutonomo vehiculo;
    private double distanciaKm;
    private double costo;
    private boolean completada;

    // Solo guarda las referencias recibidas
    public Entrega(String codigo, Paquete paquete, VehiculoAutonomo vehiculo) {
        this.codigo = codigo;
        this.paquete = paquete;
        this.vehiculo = vehiculo;
        this.completada = false;
    }

    public boolean ejecutar() {
        if (paquete == null || vehiculo == null || completada) {
            return false;
        }
        if (!vehiculo.puedeTransportar(paquete)) {
            return false;
        }
        // La distancia se calcula ANTES de mover (despues la ubicacion ya seria el destino)
        double distancia = vehiculo.getUbicacion().distanciaKm(paquete.getDestino());
        if (!vehiculo.mover(paquete.getDestino())) {
            return false;
        }
        distanciaKm = distancia;
        costo = vehiculo.calcularCostoEntrega(distancia, paquete);   // polimorfico
        completada = true;
        return true;
    }

    public String getCodigo() {
        return codigo;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public VehiculoAutonomo getVehiculo() {
        return vehiculo;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public double getCosto() {
        return costo;
    }

    public boolean isCompletada() {
        return completada;
    }

    @Override
    public String toString() {
        return "Entrega " + codigo
                + " | paquete: " + (paquete != null ? paquete.getCodigo() : "-")
                + " | veh\u00edculo: " + (vehiculo != null ? vehiculo.getId() : "-")
                + " | distancia: " + String.format("%.2f", distanciaKm) + " km"
                + " | costo: S/ " + String.format("%.2f", costo)
                + " | completada: " + completada;
    }
}
