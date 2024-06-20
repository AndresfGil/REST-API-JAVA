package BackEnd.ClinicaOdontologica.controller;


import BackEnd.ClinicaOdontologica.entity.Paciente;
import BackEnd.ClinicaOdontologica.exception.BadRequestException;
import BackEnd.ClinicaOdontologica.exception.IllegalArgumentException;
import BackEnd.ClinicaOdontologica.exception.NoContentException;
import BackEnd.ClinicaOdontologica.exception.ResourceNotFoundException;
import BackEnd.ClinicaOdontologica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController
@RequestMapping("/pacientes")

public class PacienteController {
    @Autowired
    private PacienteService pacienteService;


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
            return ResponseEntity.ok(pacienteBuscado.get());
        } catch (BadRequestException | ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable(required = false) String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email no puede estar vacío");
        }
        try {
            Optional<Paciente> pacienteBuscado = pacienteService.buscarPorEmail(email);
            return ResponseEntity.ok(pacienteBuscado.get());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al buscar por email: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodosLosPacientes() throws NoContentException {
        List<Paciente> pacientes = pacienteService.buscarTodos();
        return ResponseEntity.ok(pacientes);
    }

    @PostMapping
    public ResponseEntity<?> registrarUnPaciente(@RequestBody Paciente paciente) {
        try {
            Paciente nuevoPaciente = pacienteService.guardarPaciente(paciente);
            return ResponseEntity.ok(nuevoPaciente);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException, BadRequestException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(paciente.getId());
        if (pacienteBuscado.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("Paciente actualizado con exito");
        }else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
        if (pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("Paciente eliminado con exito");
        }else {
            throw new ResourceNotFoundException("Paciente no encontrado id: "+id);
        }
    }

}
