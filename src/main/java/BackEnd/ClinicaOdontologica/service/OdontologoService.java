package BackEnd.ClinicaOdontologica.service;

import BackEnd.ClinicaOdontologica.entity.Odontologo;
import BackEnd.ClinicaOdontologica.entity.Paciente;
import BackEnd.ClinicaOdontologica.exception.BadRequestException;
import BackEnd.ClinicaOdontologica.exception.NoContentException;
import BackEnd.ClinicaOdontologica.exception.ResourceNotFoundException;
import BackEnd.ClinicaOdontologica.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public List<Odontologo> buscarTodos() throws NoContentException{
        List<Odontologo> odontologos = odontologoRepository.findAll();
        if (odontologos.isEmpty()){
            throw new NoContentException("no hay odontologos registrados");
        }
        return odontologos;
    }

    public Optional<Odontologo> buscarOdontologo(Long id) throws BadRequestException, ResourceNotFoundException{
        if (id == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        if (!odontologo.isPresent()) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        return odontologo;
    }

    public Optional<Odontologo> buscarPorMatricula(String matricula){
        return odontologoRepository.findByMatricula(matricula);
    }

    public void actualizarOdontologo(Odontologo odontologo){
        odontologoRepository.save(odontologo);
    }

    public void eliminarOdontologo(Long id){
        odontologoRepository.deleteById(id);
    }
}
