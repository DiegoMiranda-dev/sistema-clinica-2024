package med.voll.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private medicoRepository medicosRepository;

    @PostMapping // POST /medico
    public void medico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico) {
         medicosRepository.save(new Medico(datosRegistroMedico));
    }

    @GetMapping // GET /medico
    public Page<DatosListadoMedico> listadoMedicos(@PageableDefault(size = 8, sort = "nombre" ) Pageable paginacion) {
        //return medicosRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return medicosRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);

    }

    @PutMapping
    @Transactional
    public void actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicosRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
    }


    // BORRAR DE VISTA PERO NO DE LA BASE DE DATOS
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id) {
        Medico medico = medicosRepository.getReferenceById(id);
        medico.darDeBajaMedico();
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
