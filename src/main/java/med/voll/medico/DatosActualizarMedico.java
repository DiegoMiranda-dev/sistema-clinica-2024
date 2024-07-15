package med.voll.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.direccion.DatosDireccion;

public record DatosActualizarMedico(
        @NotNull Long id,
        String nombre,
        String documento,
        DatosDireccion direccion
        ) {
}
