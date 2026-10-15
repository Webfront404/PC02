package andesdrone.vehiculos;

/** Algo que tiene bateria y puede recargarse. */
public interface Recargable {

    void recargar(int minutos);

    double getNivelBateria();

    default String estadoResumido() {
        return "bater\u00eda: " + getNivelBateria() + "%";
    }
}
