package BackEnd.ClinicaOdontologica;

import BackEnd.ClinicaOdontologica.entity.Odontologo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionOdontologoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registrarNuevoOdontologo() throws Exception {
        Odontologo odontologo = new Odontologo("Federico", "Camacho", "MPT72651");

        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(odontologo))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String respuestaJson = respuesta.getResponse().getContentAsString();
        Odontologo odontologoRespuesta = objectMapper.readValue(respuestaJson, Odontologo.class);

        assertNotNull(odontologoRespuesta.getId());
        assertTrue(odontologoRespuesta.getNombre().equals("Federico"));
        assertTrue(odontologoRespuesta.getApellido().equals("Camacho"));
        assertTrue(odontologoRespuesta.getMatricula().equals("MPT72651"));
    }

}
