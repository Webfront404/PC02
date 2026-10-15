package andesdrone.gestion;

import andesdrone.modelo.Paquete;
import andesdrone.vehiculos.Asegurable;
import andesdrone.vehiculos.VehiculoAutonomo;

/**
 * AGREGACION 1 a 0..* con VehiculoAutonomo: la flota guarda referencias a
 * vehiculos creados afuera; si la flota desaparece, los vehiculos siguen existiendo.
 * Se usa un arreglo de tamano fijo (no se permiten colecciones).
 */
public class Flota {

    private String nombre;
    private VehiculoAutonomo[] vehiculos;
    private int cantidad;

    public Flota(String nombre, int capacidad) {
        this.nombre = nombre;
        this.vehiculos = new VehiculoAutonomo[capacidad > 0 ? capacidad : 1];
        this.cantidad = 0;
    }

    // UPCASTING: recibe cualquier subclase (DronAereo, RobotOruga, ...) como VehiculoAutonomo
    public boolean agregarVehiculo(VehiculoAutonomo v) {
        if (v == null || cantidad >= vehiculos.length || buscarPorId(v.getId()) != null) {
            return false;
        }
        vehiculos[cantidad] = v;
        cantidad++;
        return true;
    }

    public VehiculoAutonomo buscarPorId(String id) {
        for (int i = 0; i < cantidad; i++) {
            if (vehiculos[i].getId().equals(id)) {
                return vehiculos[i];
            }
        }
        return null;
    }

    // DEPENDENCIA con Paquete (parametro) y CalculadoraRutas (metodos static)
    public VehiculoAutonomo asignarMejorVehiculo(Paquete p) {
        if (p == null) {
            return null;
        }
        VehiculoAutonomo mejor = null;
        double menorCosto = Double.MAX_VALUE;
        for (int i = 0; i < cantidad; i++) {
            if (CalculadoraRutas.esViable(vehiculos[i], p)) {
                double costo = CalculadoraRutas.costoEstimado(vehiculos[i], p);
                if (costo < menorCosto) {
                    menorCosto = costo;
                    mejor = vehiculos[i];
                }
            }
        }
        return mejor;
    }

    // instanceof con patron: comprueba el tipo y hace el downcasting en un solo paso
    public double totalPrimasSeguro() {
        double total = 0;
        for (int i = 0; i < cantidad; i++) {
            if (vehiculos[i] instanceof Asegurable a) {
                total += a.calcularPrima();
            }
        }
        return total;
    }

    // POLIMORFISMO: estadoResumido() se resuelve en tiempo de ejecucion segun el
    // objeto real; no hace falta ningun if por tipo.
    public String reporte() {
        String texto = "";
        for (int i = 0; i < cantidad; i++) {
            texto += vehiculos[i].getClass().getSimpleName() + ": "
                    + vehiculos[i].estadoResumido() + "\n";
        }
        return texto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }
}
