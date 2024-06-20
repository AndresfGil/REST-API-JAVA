package BackEnd.ClinicaOdontologica.security;

import BackEnd.ClinicaOdontologica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class ConfigWebSecurity {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(usuarioService);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        // Rutas accesibles solo por ADMIN
                        .requestMatchers("/post_odontologos.html", "/post_pacientes.html", "/post_turnos.html", "/get_odontologos.html", "/get_pacientes.html").hasRole("ADMIN")
                        .requestMatchers("/js/get_odontologos.js", "/js/delete_odontologo.js", "/js/update_odontologo.js").hasRole("ADMIN")
                        .requestMatchers("/js/get_pacientes.js", "/js/delete_paciente.js", "/js/update_paciente.js").hasRole("ADMIN")

                        // Rutas accesibles por ADMIN y USER
                        .requestMatchers("/get_turnos.html").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/js/get_turnos.js", "/js/post_turno.js").hasAnyRole("ADMIN", "USER")

                        // Rutas públicas (ej. recursos estáticos comunes)
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // Cualquier otra solicitud debe estar autenticada
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .logout(withDefaults());

        http.authenticationProvider(daoAuthenticationProvider());

        return http.build();
    }


}
