package andesdrone.vehiculos;

/**
 * HERENCIA MULTIPLE ENTRE INTERFACES: Operable extiende Rastreable y Recargable.
 * Ambas traen un metodo default estadoResumido() con la misma firma, por lo que
 * el compilador exige resolver el conflicto. Se sobrescribe aqui invocando
 * explicitamente cada version con la sintaxis Interfaz.super.metodo().
 */
public interface Operable extends Rastreable, Recargable {

    @Override
    default String estadoResumido() {
        return Rastreable.super.estadoResumido() + " | " + Recargable.super.estadoResumido();
    }
}
