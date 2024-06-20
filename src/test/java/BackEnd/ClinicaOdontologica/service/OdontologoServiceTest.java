package BackEnd.ClinicaOdontologica.service;


import BackEnd.ClinicaOdontologica.entity.Odontologo;
import BackEnd.ClinicaOdontologica.exception.BadRequestException;
import BackEnd.ClinicaOdontologica.exception.NoContentException;
import BackEnd.ClinicaOdontologica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;


    @Test
    @Order(1)
    public void guardarOdontologo(){
        Odontologo odontologo = new Odontologo("Julian", "Camacho", "AV9837");
        Odontologo odontologoGuardado= odontologoService.guardarOdontologo(odontologo);
        assertEquals(1L, odontologoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarOdontologo() throws BadRequestException, ResourceNotFoundException {
        Long id=1L;
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarOdontologo(id);
        assertNotNull(odontologoBuscado.get());
    }

    @Test
    @Order(3)
    public void actualizarOdontologo() throws BadRequestException, ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarOdontologo(1L);
        if(odontologoBuscado.isPresent()){
            odontologoBuscado.get().setApellido("Marin");
        }
        odontologoService.actualizarOdontologo((odontologoBuscado.get()));
        assertEquals("Marin", odontologoBuscado.get().getApellido());
    }

    @Test
    @Order(4)
    public void buscarTodos() throws NoContentException {
        List<Odontologo> odontologos= odontologoService.buscarTodos();
        assertEquals(1, odontologos.size());
    }

    @Test
    @Order(5)
    public void eliminarOdontologo() throws BadRequestException, ResourceNotFoundException {
        odontologoService.eliminarOdontologo(1L);
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarOdontologo(1L);
        assertFalse(odontologoBuscado.isPresent());
    }


}
