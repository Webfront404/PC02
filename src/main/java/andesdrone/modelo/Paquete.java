package andesdrone.modelo;

/**
 * Paquete a entregar. Su destino es una Coordenada por COMPOSICION:
 * se crea dentro del constructor, no tiene setter y su getter devuelve una copia.
 */
public class Paquete {

    private String codigo;
    private String descripcion;
    private double pesoKg;        // debe ser > 0
    private boolean fragil;
    private Coordenada destino;   // composicion 1 a 1

    // Constructor sobrecargado: encadena con this(...) asumiendo fragil = false
    public Paquete(String codigo, String descripcion, double pesoKg, double lat, double lon) {
        this(codigo, descripcion, pesoKg, false, lat, lon);
    }

    // Constructor principal: el destino se CREA aqui (la parte nace con el todo)
    public Paquete(String codigo, String descripcion, double pesoKg, boolean fragil,
                   double lat, double lon) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.pesoKg = (pesoKg > 0) ? pesoKg : 0.01;   // 0.01 si el peso recibido es invalido
        this.fragil = fragil;
        this.destino = new Coordenada(lat, lon);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public boolean isFragil() {
        return fragil;
    }

    // Devuelve una COPIA: quien la reciba no puede alterar el destino interno
    public Coordenada getDestino() {
        return new Coordenada(destino);
    }

    // Unico setter permitido (validado). No existe setDestino por ser composicion.
    public boolean setPesoKg(double pesoKg) {
        if (pesoKg <= 0) {
            return false;
        }
        this.pesoKg = pesoKg;
        return true;
    }

    @Override
    public String toString() {
        return "Paquete[" + codigo + ", " + descripcion + ", " + pesoKg + " kg, fragil="
                + fragil + ", destino=" + destino + "]";
    }
}
