package BackEnd.ClinicaOdontologica.service;

import BackEnd.ClinicaOdontologica.entity.Domicilio;
import BackEnd.ClinicaOdontologica.entity.Paciente;
import BackEnd.ClinicaOdontologica.exception.BadRequestException;
import BackEnd.ClinicaOdontologica.exception.NoContentException;
import BackEnd.ClinicaOdontologica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    //Metodos a probar del service

    @Test
    @Order(1)
    public void guardarPaciente() throws BadRequestException {
        Paciente paciente= new Paciente( "Andres", "Gil", "111111", LocalDate.of(2024,06,19), new Domicilio("Calle falsa", 123, "Colada", "Cali"), "Andres@gmail.com" );
        Paciente pacienteGuardado= pacienteService.guardarPaciente(paciente);
        assertEquals(1L, pacienteGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarPaciente() throws BadRequestException, ResourceNotFoundException {
        Long id= 1L;
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPaciente(id);
        assertNotNull(pacienteBuscado.get());
    }

    @Test
    @Order(3)
    public void actualizarPaciente() throws BadRequestException, ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPaciente(1L);
        if(pacienteBuscado.isPresent()){
            pacienteBuscado.get().setApellido("Perez");
        }
        pacienteService.actualizarPaciente(pacienteBuscado.get());
        assertEquals("Perez",pacienteBuscado.get().getApellido());
    }

    @Test
    @Order(4)
    public void buscarTodos() throws NoContentException {
        List<Paciente> pacientes= pacienteService.buscarTodos();
        assertEquals(1, pacientes.size());
    }

    @Test
    @Order(5)
    public void eliminarPaciente() throws BadRequestException, ResourceNotFoundException {
        pacienteService.eliminarPaciente(1L);
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPaciente(1L);
        assertFalse(pacienteBuscado.isPresent());
    }


}
