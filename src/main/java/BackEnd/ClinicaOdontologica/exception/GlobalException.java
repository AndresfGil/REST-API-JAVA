package BackEnd.ClinicaOdontologica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> tratamientoResourceNotFoundException(ResourceNotFoundException rnfe) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mensaje: "+rnfe.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> tratamientoBadRequestException(BadRequestException bre) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mensaje: " + bre.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException iae) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + iae.getMessage());
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<String> tratamientoNoContentException(NoContentException nce) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Mensaje: " + nce.getMessage());
    }

    @ExceptionHandler(PacienteNotFoundException.class)
    public ResponseEntity<String> tratamientoPacienteNotFoundException(PacienteNotFoundException pnfe) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mensaje: " + pnfe.getMessage());
    }

    @ExceptionHandler(OdontologoNotFoundException.class)
    public ResponseEntity<String> tratamientoOdontologoNotFoundException(OdontologoNotFoundException onfe) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mensaje: " + onfe.getMessage());
    }
}
