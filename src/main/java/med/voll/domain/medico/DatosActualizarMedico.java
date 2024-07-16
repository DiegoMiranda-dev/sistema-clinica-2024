package med.voll.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.domain.direccion.DatosDireccion;

public record DatosActualizarMedico(
        @NotNull Long id,
        String nombre,
        String documento,
        DatosDireccion direccion
        ) {
}
