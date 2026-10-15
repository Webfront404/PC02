package andesdrone.gestion;

import andesdrone.modelo.Coordenada;
import andesdrone.modelo.Paquete;
import andesdrone.vehiculos.VehiculoAutonomo;

/**
 * Clase de utilidad: constructor privado, sin atributos, solo metodos static.
 * DEPENDENCIA con VehiculoAutonomo, Paquete y Coordenada: solo los recibe como
 * parametros, no guarda referencias a ellos.
 */
public class CalculadoraRutas {

    // Constructor privado: impide crear instancias
    private CalculadoraRutas() {
    }

    // distancia desde la ubicacion actual del vehiculo x consumo por km
    public static double bateriaNecesaria(VehiculoAutonomo v, Coordenada destino) {
        return v.getUbicacion().distanciaKm(destino) * v.getConsumoPorKm();
    }

    public static boolean esViable(VehiculoAutonomo v, Paquete p) {
        return v.puedeTransportar(p)
                && v.getNivelBateria() >= bateriaNecesaria(v, p.getDestino());
    }

    // Llamada polimorfica: se ejecuta la formula del tipo real del vehiculo
    public static double costoEstimado(VehiculoAutonomo v, Paquete p) {
        double distancia = v.getUbicacion().distanciaKm(p.getDestino());
        return v.calcularCostoEntrega(distancia, p);
    }
}
