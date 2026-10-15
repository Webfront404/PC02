package andesdrone.modelo;

/**
 * Representa un punto geografico (latitud, longitud) en grados.
 * Se usa por COMPOSICION dentro de VehiculoAutonomo y Paquete.
 */
public class Coordenada {

    // Valores por defecto: Lima centro
    private static final double LAT_LIMA = -12.0464;
    private static final double LON_LIMA = -77.0428;

    private double latitud;   // rango valido [-90, 90]
    private double longitud;  // rango valido [-180, 180]

    // Constructor por defecto: encadena con this(...) a Lima centro
    public Coordenada() {
        this(LAT_LIMA, LON_LIMA);
    }

    // Constructor principal: valida los rangos; si un valor es invalido
    // se conserva el valor de Lima centro para no dejar el objeto inconsistente
    public Coordenada(double latitud, double longitud) {
        this.latitud = (latitud >= -90 && latitud <= 90) ? latitud : LAT_LIMA;
        this.longitud = (longitud >= -180 && longitud <= 180) ? longitud : LON_LIMA;
    }

    // Constructor copia: encadena con this(...) usando los valores de "otra"
    public Coordenada(Coordenada otra) {
        this(otra != null ? otra.latitud : LAT_LIMA,
             otra != null ? otra.longitud : LON_LIMA);
    }

    public double getLatitud() {
        return latitud;
    }

    public boolean setLatitud(double latitud) {
        if (latitud < -90 || latitud > 90) {
            return false;
        }
        this.latitud = latitud;
        return true;
    }

    public double getLongitud() {
        return longitud;
    }

    public boolean setLongitud(double longitud) {
        if (longitud < -180 || longitud > 180) {
            return false;
        }
        this.longitud = longitud;
        return true;
    }

    // Distancia aproximada: sqrt(?lat2 + ?lon2) x 111.0 (? en grados)
    public double distanciaKm(Coordenada otra) {
        double dLat = this.latitud - otra.latitud;
        double dLon = this.longitud - otra.longitud;
        return Math.sqrt(dLat * dLat + dLon * dLon) * 111.0;
    }

    @Override
    public String toString() {
        return "(" + latitud + ", " + longitud + ")";
    }
}
