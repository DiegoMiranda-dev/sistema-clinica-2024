package med.voll.medico;

import med.voll.direccion.DatosDireccion;
import med.voll.direccion.Direccion;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DatosDireccion direccion
) {

}
