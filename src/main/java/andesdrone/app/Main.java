package andesdrone.app;

import andesdrone.gestion.Entrega;
import andesdrone.gestion.Flota;
import andesdrone.modelo.Paquete;
import andesdrone.vehiculos.DronAereo;
import andesdrone.vehiculos.DronPesado;
import andesdrone.vehiculos.RobotOruga;
import andesdrone.vehiculos.RobotRuedas;
import andesdrone.vehiculos.VehiculoAutonomo;

/** Programa de prueba de la Parte 3. */
public class Main {

    public static void main(String[] args) {

        System.out.println("===== 1. CREACI\u00d3N DE LA FLOTA =====");
        Flota flota = new Flota("AndesDrone Lima", 6);

        // UPCASTING: cada objeto concreto se guarda en una variable VehiculoAutonomo.
        // Todos nacen en la ubicacion por defecto (Lima centro) con bateria 100.
        VehiculoAutonomo da01 = new DronAereo("DA-01", "Falcon X", 5.0, 4.0, 150);
        VehiculoAutonomo dp02 = new DronPesado("DP-02", "C\u00f3ndor", 20.0, 100);
        VehiculoAutonomo rr03 = new RobotRuedas("RR-03", "Rodante", 12.0, 3.0, 25);
        VehiculoAutonomo ro04 = new RobotOruga("RO-04", "Tanque", 30.0, 5.0, 12);

        System.out.println("Agregar DA-01: " + flota.agregarVehiculo(da01));
        System.out.println("Agregar DP-02: " + flota.agregarVehiculo(dp02));
        System.out.println("Agregar RR-03: " + flota.agregarVehiculo(rr03));
        System.out.println("Agregar RO-04: " + flota.agregarVehiculo(ro04));

        // Quinto vehiculo con id repetido: debe retornar false
        VehiculoAutonomo repetido = new DronAereo("DA-01", "Falcon Y", 4.0, 3.5);
        System.out.println("Agregar quinto veh\u00edculo con id repetido DA-01: "
                + flota.agregarVehiculo(repetido));

        System.out.println();
        System.out.println("Reporte de la flota:");
        System.out.println(flota.reporte());

        System.out.println("===== 2. ENTREGAS =====");
        Paquete p100 = new Paquete("P-100", "Documentos", 1.5, -12.0600, -77.0300);
        Paquete p200 = new Paquete("P-200", "Equipo m\u00e9dico", 18.0, true, -12.0900, -77.0100);
        Paquete p300 = new Paquete("P-300", "Maquinaria", 40.0, -12.1000, -77.0000);

        procesar(flota, "E-001", p100);
        procesar(flota, "E-002", p200);
        procesar(flota, "E-003", p300);

        System.out.println("===== 3. SEGUROS =====");
        System.out.println("Total de primas del seguro: " + flota.totalPrimasSeguro());
        System.out.println();

        System.out.println("===== 4. RECARGA (upcasting) Y ALTITUDES (downcasting) =====");
        // UPCASTING: arreglo del tipo base que contiene objetos de las subclases
        VehiculoAutonomo[] vehiculos = {da01, dp02, rr03, ro04};
        for (VehiculoAutonomo v : vehiculos) {
            double antes = v.getNivelBateria();
            v.recargar(30);   // polimorfismo: drones +1.0 %/min, robots +0.5 %/min
            System.out.printf("%s recarg\u00f3 30 min: %.2f%% -> %.2f%%%n",
                    v.getId(), antes, v.getNivelBateria());
        }
        System.out.println();
        for (VehiculoAutonomo v : vehiculos) {
            // DOWNCASTING seguro con instanceof con patron (DronPesado tambien es DronAereo)
            if (v instanceof DronAereo d) {
                System.out.println("Altitud m\u00e1xima de " + d.getId() + ": " + d.getAltitudMaxM() + " m");
            }
        }
        System.out.println();
        System.out.println("Reporte final de la flota:");
        System.out.println(flota.reporte());

        // ===== 5. LINEAS ILEGALES (no compilan) =====
        //
        // (1) Clase abstracta:
        // VehiculoAutonomo v = new VehiculoAutonomo("X-01", "Generico", 10.0, 2.0);
        // No compila: VehiculoAutonomo es abstract y no se puede instanciar con new
        // (ademas declara el metodo abstracto calcularCostoEntrega sin cuerpo).
        //
        // (2) Jerarquia sellada:
        // class RobotPatas extends RobotTerrestre { }
        // No compila: RobotTerrestre es sealed y su clausula permits solo autoriza a
        // RobotRuedas y RobotOruga; ninguna otra clase puede extenderla.
    }

    // Asigna el mejor vehiculo, crea la Entrega, la ejecuta y muestra el resultado
    private static void procesar(Flota flota, String codigoEntrega, Paquete p) {
        System.out.println("Paquete " + p.getCodigo() + " (" + p.getDescripcion() + ", "
                + p.getPesoKg() + " kg, fr\u00e1gil: " + (p.isFragil() ? "S\u00ed" : "No") + ")");
        VehiculoAutonomo elegido = flota.asignarMejorVehiculo(p);
        if (elegido == null) {
            System.out.println("  No hay veh\u00edculo disponible (asignarMejorVehiculo retorn\u00f3 null)");
            System.out.println();
            return;
        }
        Entrega entrega = new Entrega(codigoEntrega, p, elegido);
        if (entrega.ejecutar()) {
            System.out.println("  Veh\u00edculo elegido : " + elegido.getId());
            System.out.printf("  Distancia        : %.2f km%n", entrega.getDistanciaKm());
            System.out.printf("  Costo            : %.2f%n", entrega.getCosto());
            System.out.printf("  Bater\u00eda restante : %.2f%%%n", elegido.getNivelBateria());
        } else {
            System.out.println("  La entrega no se pudo ejecutar.");
        }
        System.out.println();
    }
}
