package BackEnd.ClinicaOdontologica.service;

import BackEnd.ClinicaOdontologica.entity.Odontologo;
import BackEnd.ClinicaOdontologica.entity.Paciente;
import BackEnd.ClinicaOdontologica.entity.Turno;
import BackEnd.ClinicaOdontologica.exception.*;
import BackEnd.ClinicaOdontologica.repository.OdontologoRepository;
import BackEnd.ClinicaOdontologica.repository.PacienteRepository;
import BackEnd.ClinicaOdontologica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private OdontologoRepository odontologoRepository;

    public Turno guardarTurno(Turno turno) throws PacienteNotFoundException, OdontologoNotFoundException, IllegalArgumentException {
        if (turno.getPaciente() == null || turno.getPaciente().getId() == null) {
            throw new IllegalArgumentException("Paciente no puede ser nulo");
        }

        Optional<Paciente> paciente = pacienteRepository.findById(turno.getPaciente().getId());
        if (!paciente.isPresent()) {
            throw new PacienteNotFoundException("Paciente no encontrado");
        }
        turno.setPaciente(paciente.get());

        if (turno.getOdontologo() == null || turno.getOdontologo().getId() == null) {
            throw new IllegalArgumentException("Odontologo no puede ser nulo");
        }

        Optional<Odontologo> odontologo = odontologoRepository.findById(turno.getOdontologo().getId());
        if (!odontologo.isPresent()) {
            throw new OdontologoNotFoundException("Odontologo no encontrado");
        }
        turno.setOdontologo(odontologo.get());

        return turnoRepository.save(turno);
    }



    public List<Turno> buscarTodos() throws NoContentException {
        List<Turno> turnos = turnoRepository.findAll();
        if (turnos.isEmpty()){
            throw new NoContentException("No hay turnos guardados");
        }
        return turnos;
    }

    public Optional<Turno> buscarTurno(Long id) throws BadRequestException, ResourceNotFoundException {
        if (id == null) {
            throw new BadRequestException("El id no puede ser nulo");
        }
        Optional<Turno> turno = turnoRepository.findById(id);
        if (!turno.isPresent()) {
            throw new ResourceNotFoundException("Turno no encontrado con ID: " + id);
        }
        return turno;
    }

    public Turno actualizarTurno(Turno turno) {
        if (turno.getId() == null || !turnoRepository.existsById(turno.getId())) {
            throw new IllegalArgumentException("Turno no encontrado");
        }

        Paciente paciente = turno.getPaciente();
        if (paciente != null) {
            pacienteRepository.save(paciente);
        }
        Odontologo odontologo = turno.getOdontologo();
        if (odontologo != null) {
            odontologoRepository.save(odontologo);
        }

        return turnoRepository.save(turno);
    }

    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }
}
