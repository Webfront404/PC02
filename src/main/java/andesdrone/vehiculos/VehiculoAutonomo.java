package andesdrone.vehiculos;

import andesdrone.modelo.Coordenada;
import andesdrone.modelo.Paquete;

/**
 * Raiz de la jerarquia.
 * - abstract: no tiene sentido crear un "veh\u00edculo gen\u00e9rico"; cada tipo concreto
 *   define su propio costo (calcularCostoEntrega es abstracto).
 * - sealed ... permits: solo DronAereo y RobotTerrestre pueden extenderla directamente.
 *   Todas las clases de la jerarquia estan en el mismo paquete porque el proyecto
 *   no usa module-info.java.
 * - implements Operable: hereda (via Operable) Rastreable y Recargable.
 */
public abstract sealed class VehiculoAutonomo implements Operable
        permits DronAereo, RobotTerrestre {

    private String id;
    private String modelo;
    private double capacidadMaxKg;
    private Coordenada ubicacion;   // COMPOSICION 1 a 1: se crea dentro del constructor

    // protected: las subclases (DronAereo, RobotTerrestre, ...) necesitan leer y
    // modificar la bateria directamente, p. ej. DronAereo.recargar() la sobrescribe.
    // Clases de otros paquetes (gestion, app) NO pueden acceder a ellos.
    protected double bateria = 100.0;   // 0-100, inicia en 100
    protected double consumoPorKm;      // % de bateria por km

    // Constructor principal: crea la ubicacion internamente (composicion)
    public VehiculoAutonomo(String id, String modelo, double capacidadMaxKg,
                            double consumoPorKm, double lat, double lon) {
        this.id = id;
        this.modelo = modelo;
        this.capacidadMaxKg = capacidadMaxKg;
        this.consumoPorKm = consumoPorKm;
        this.ubicacion = new Coordenada(lat, lon);
    }

    // Constructor sobrecargado: encadena con this(...) usando la ubicacion por defecto
    // de Coordenada() (Lima centro)
    public VehiculoAutonomo(String id, String modelo, double capacidadMaxKg, double consumoPorKm) {
        this(id, modelo, capacidadMaxKg, consumoPorKm,
             new Coordenada().getLatitud(), new Coordenada().getLongitud());
    }

    // Cada subclase concreta define su formula (polimorfismo)
    public abstract double calcularCostoEntrega(double distanciaKm, Paquete p);

    public boolean puedeTransportar(Paquete p) {
        return p.getPesoKg() <= capacidadMaxKg;
    }

    // protected: solo la jerarquia puede descontar bateria (lo usa mover)
    protected boolean consumirBateria(double porcentaje) {
        if (porcentaje > bateria) {
            return false;
        }
        bateria -= porcentaje;
        return true;
    }

    // SOBRECARGA de mover (1): con Coordenada
    public boolean mover(Coordenada destino) {
        if (destino == null) {
            return false;
        }
        double km = ubicacion.distanciaKm(destino);
        if (!consumirBateria(km * consumoPorKm)) {
            return false;               // no alcanza la bateria: no se mueve
        }
        ubicacion = new Coordenada(destino);   // se guarda una copia propia
        return true;
    }

    // SOBRECARGA de mover (2): con lat/lon, delega en la primera
    public boolean mover(double lat, double lon) {
        return mover(new Coordenada(lat, lon));
    }

    // Devuelve COPIA para no exponer la referencia interna (composicion)
    @Override
    public Coordenada getUbicacion() {
        return new Coordenada(ubicacion);
    }

    @Override
    public double getNivelBateria() {
        return bateria;
    }

    // Recarga base: 0.5 % por minuto, tope 100 (los robots usan esta version)
    @Override
    public void recargar(int minutos) {
        if (minutos <= 0) {
            return;
        }
        bateria = Math.min(100.0, bateria + minutos * 0.5);
    }

    public String getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public double getCapacidadMaxKg() {
        return capacidadMaxKg;
    }

    public double getConsumoPorKm() {
        return consumoPorKm;
    }

    @Override
    public String toString() {
        return id + " - " + modelo + " | capacidad: " + capacidadMaxKg + " kg | bater\u00eda: "
                + bateria + "%";
    }
}
