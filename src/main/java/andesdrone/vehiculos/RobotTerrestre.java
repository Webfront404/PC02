package andesdrone.vehiculos;

/**
 * abstract sealed: no se instancia y solo permite RobotRuedas y RobotOruga.
 * No implementa calcularCostoEntrega: lo deja a sus subclases concretas.
 */
public abstract sealed class RobotTerrestre extends VehiculoAutonomo
        permits RobotRuedas, RobotOruga {

    private double velocidadMaxKmH;   // debe ser > 0

    public RobotTerrestre(String id, String modelo, double capacidadMaxKg,
                          double consumoPorKm, double velocidadMaxKmH) {
        super(id, modelo, capacidadMaxKg, consumoPorKm);
        this.velocidadMaxKmH = (velocidadMaxKmH > 0) ? velocidadMaxKmH : 1.0;   // 1.0 si es invalido
    }

    public double getVelocidadMaxKmH() {
        return velocidadMaxKmH;
    }

    public boolean setVelocidadMaxKmH(double velocidadMaxKmH) {
        if (velocidadMaxKmH <= 0) {
            return false;
        }
        this.velocidadMaxKmH = velocidadMaxKmH;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " | velocidad m\u00e1x: " + velocidadMaxKmH + " km/h";
    }
}
