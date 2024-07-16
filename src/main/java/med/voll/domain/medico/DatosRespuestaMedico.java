package med.voll.domain.medico;

import med.voll.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        Especialidad especialidad,
        DatosDireccion direccion

) {}

