package com.sistemas.distribuidos.ups.backend_veterinaria.controllers;

import com.sistemas.distribuidos.ups.backend_veterinaria.dto.CitaDTO;
import com.sistemas.distribuidos.ups.backend_veterinaria.models.Cita;
import com.sistemas.distribuidos.ups.backend_veterinaria.models.EstadoCita;
import com.sistemas.distribuidos.ups.backend_veterinaria.models.Mensaje;
import com.sistemas.distribuidos.ups.backend_veterinaria.services.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<Cita> list(){
        return citaService.findAll();
    }

    @GetMapping("/estado-citas")
    public List<EstadoCita> listEstadoCitas(){
        return citaService.findAllEstadosCita();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return citaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mascotas/{idMascota}")
    public List<Cita> findAllByMascotaId(@PathVariable Long idMascota){
        return citaService.findAllByMascotaId(idMascota);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CitaDTO citaDTO){
        Cita cita = citaService.save(citaDTO);
        return ResponseEntity.ok(cita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CitaDTO citaDTO, @PathVariable Long id){
        return citaService.update(id, citaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Cita> citaOptional = citaService.deleteById(id);
        if (citaOptional.isPresent()) {
            return ResponseEntity.ok(new Mensaje("La cita con id: " + id + " ha sido eliminada correctamente."));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Mensaje("La cita con id: " + id + " no existe."));
    }

    @PutMapping("/{cita_id}/{estado_cita_id}")
    public ResponseEntity<?> changeEstadoCita(@PathVariable Long cita_id, @PathVariable Long estado_cita_id){
        return citaService.changeEstadoCita(cita_id, estado_cita_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
