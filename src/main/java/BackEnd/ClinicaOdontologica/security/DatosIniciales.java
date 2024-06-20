package BackEnd.ClinicaOdontologica.security;

import BackEnd.ClinicaOdontologica.entity.Usuario;
import BackEnd.ClinicaOdontologica.entity.UsuarioRole;
import BackEnd.ClinicaOdontologica.repository.UsuarioRepository;
import BackEnd.ClinicaOdontologica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String passSinCifrar= "admin";
        String passCifrado= passwordEncoder.encode(passSinCifrar);
        Usuario usuario= new Usuario("andres", "andresg98" , "admin@admin.com", passCifrado, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuario);

        String passSinCifrar2= "user";
        String passCifrado2= passwordEncoder.encode(passSinCifrar2);
        Usuario usuario2= new Usuario("felipe", "felipe98" , "user@user.com", passCifrado2, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuario2);
    }


}
