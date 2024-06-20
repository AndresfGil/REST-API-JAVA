package BackEnd.ClinicaOdontologica;

import BackEnd.ClinicaOdontologica.entity.Domicilio;
import BackEnd.ClinicaOdontologica.entity.Odontologo;
import BackEnd.ClinicaOdontologica.entity.Paciente;
import BackEnd.ClinicaOdontologica.entity.Turno;
import BackEnd.ClinicaOdontologica.exception.BadRequestException;
import BackEnd.ClinicaOdontologica.exception.OdontologoNotFoundException;
import BackEnd.ClinicaOdontologica.exception.PacienteNotFoundException;
import BackEnd.ClinicaOdontologica.service.OdontologoService;
import BackEnd.ClinicaOdontologica.service.PacienteService;
import BackEnd.ClinicaOdontologica.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionTurnoTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private MockMvc mockMvc;


    public void cargarDatos() throws OdontologoNotFoundException, PacienteNotFoundException, BadRequestException {
        Paciente pacienteGuardado= pacienteService.guardarPaciente( new Paciente( "Andres", "Gil", "111111", LocalDate.of(2024,06,19), new Domicilio("Calle falsa", 123, "Colada", "Cali"), "Andres@gmail.com" ));
        Odontologo odontologoGuardado= odontologoService.guardarOdontologo( new Odontologo("Federico", "Camacho", "MPT72651"));
        Turno turnoGuardado= turnoService.guardarTurno(new Turno(pacienteGuardado, odontologoGuardado, LocalDate.of(2024,06,25)));
    }

    @Test
    public void listarTodosLosTurnos() throws Exception{
        cargarDatos();
        MvcResult respuesta= (MvcResult) mockMvc.perform(MockMvcRequestBuilders.get("/turnos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }
}
