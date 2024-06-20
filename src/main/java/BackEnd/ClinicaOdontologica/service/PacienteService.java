package BackEnd.ClinicaOdontologica.service;

import BackEnd.ClinicaOdontologica.entity.Domicilio;
import BackEnd.ClinicaOdontologica.entity.Paciente;
import BackEnd.ClinicaOdontologica.exception.BadRequestException;
import BackEnd.ClinicaOdontologica.exception.IllegalArgumentException;
import BackEnd.ClinicaOdontologica.exception.NoContentException;
import BackEnd.ClinicaOdontologica.exception.ResourceNotFoundException;
import BackEnd.ClinicaOdontologica.repository.DomicilioRepository;
import BackEnd.ClinicaOdontologica.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    DomicilioRepository domicilioRepository;

    @Transactional
    public Paciente guardarPaciente(Paciente paciente) throws BadRequestException {
        if (paciente.getDomicilio() == null) {
            throw new BadRequestException("El domicilio es requerido para registrar un paciente.");
        }

        Domicilio domicilio = paciente.getDomicilio();
        domicilio = domicilioRepository.save(domicilio);
        paciente.setDomicilio(domicilio);

        return pacienteRepository.save(paciente);
    }

    public List<Paciente> buscarTodos() throws NoContentException {
        List<Paciente> pacientes = pacienteRepository.findAll();
        if (pacientes.isEmpty()) {
            throw new NoContentException("No hay pacientes registrados");
        }
        return pacientes;
    }

    public Optional<Paciente> buscarPaciente(Long id) throws BadRequestException, ResourceNotFoundException {
        if (id == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (!paciente.isPresent()) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        return paciente;
    }

    public Optional<Paciente> buscarPorEmail(String email) throws BadRequestException, ResourceNotFoundException {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("El email no puede estar vacío");
        }
        Optional<Paciente> paciente = pacienteRepository.findByEmail(email);
        if (!paciente.isPresent()) {
            throw new ResourceNotFoundException("No se encontró paciente con email: " + email);
        }
        return paciente;
    }

    public void actualizarPaciente(Paciente paciente){
        pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Long id){
        pacienteRepository.deleteById(id);
    }

}
