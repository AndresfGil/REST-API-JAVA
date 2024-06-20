package BackEnd.ClinicaOdontologica.controller;


import BackEnd.ClinicaOdontologica.entity.Paciente;
import BackEnd.ClinicaOdontologica.entity.Turno;
import BackEnd.ClinicaOdontologica.exception.*;
import BackEnd.ClinicaOdontologica.service.OdontologoService;
import BackEnd.ClinicaOdontologica.service.PacienteService;
import BackEnd.ClinicaOdontologica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")

public class TurnoController {
    @Autowired
    private TurnoService turnoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Optional<Turno> turnoBuscado = turnoService.buscarTurno(id);
            return ResponseEntity.ok(turnoBuscado.get());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTodosLosTurnos() throws NoContentException {
        List<Turno> turnos = turnoService.buscarTodos();
        return ResponseEntity.ok(turnos);
    }

    @PostMapping
    public ResponseEntity<String> guardarTurno(@RequestBody Turno turno) throws PacienteNotFoundException, OdontologoNotFoundException, IllegalArgumentException {
        try {
            turnoService.guardarTurno(turno);
            return ResponseEntity.ok("Turno guardado con éxito");
        } catch (PacienteNotFoundException | OdontologoNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el turno");
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) throws ResourceNotFoundException, BadRequestException {
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(turno.getId());
        if (turnoBuscado.isPresent()){
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("Turno actualizado con exito");
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(id);
        if (turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado");
        }else {
            throw new ResourceNotFoundException("No se encontro turno con id: "+id);
        }
    }

}
