package med.voll.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.domain.direccion.DatosDireccion;
import med.voll.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private medicoRepository medicosRepository;

    @PostMapping // POST /medico
    public ResponseEntity<DatosRespuestaMedico> medico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriBuilder) {
        Medico medico = medicosRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(),
                medico.getNombre(), medico.getEmail(), medico.getTelefono(),
                medico.getDocumento(), medico.getEspecialidad(), new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()));
        URI url = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping // GET /medico
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 8, sort = "nombre") Pageable paginacion) {
        //return medicosRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicosRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));

    }


    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicosRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(),
                medico.getNombre(), medico.getEmail(), medico.getTelefono(),
                medico.getDocumento(), medico.getEspecialidad(), new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento())));
    }


    // BORRAR DE VISTA PERO NO DE LA BASE DE DATOS
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Medico medico = medicosRepository.getReferenceById(id);
        medico.darDeBajaMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity retornaDatosMedico(@PathVariable Long id) {
        Medico medico = medicosRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getDocumento(),
                medico.getEspecialidad(),
            new DatosDireccion(
                medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),
                medico.getDireccion().getCiudad(),
                medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento())

        );
        return ResponseEntity.ok(datosMedico);
    }


    //BORRAR DE LA BASE DE DATOS  <-- NO TOCAR
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarMedico(@PathVariable Long id) {
//        Medico medico = medicosRepository.getReferenceById(id);
//        medicosRepository.delete(medico);
//
//    }

}
