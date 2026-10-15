package andesdrone.vehiculos;

import andesdrone.modelo.Coordenada;

/** Algo cuya ubicacion se puede consultar. */
public interface Rastreable {

    Coordenada getUbicacion();

    default String estadoResumido() {
        return "ubicaci\u00f3n: " + getUbicacion();
    }
}
