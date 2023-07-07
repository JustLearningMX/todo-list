package me.hiramchavez.todolist.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
    ) throws ServletException, IOException {
        //Get authHeader from header
        String authHeader = request.getHeader("Authorization");

        //Cuando la peticion desde JS viene con el header Authorization, pero con el valor "null"
        if (authHeader != null && authHeader.equals("null"))
            authHeader = null;

        //Validar que el authHeader no sea nulo o vacío
        if (authHeader != null) {
            String token = authHeader.replace("Bearer ", "");

            String subject = tokenService.getSubject(token);

            if (subject != null) {
                //Buscar usuario en BD
                var usuario = userRepository.findByEmailAndActiveTrue(subject);

                var authentication = new UsernamePasswordAuthenticationToken(
                  usuario,
                  null,
                  usuario.getAuthorities() //Forzamos el inicio de sesion
                );

                //Seteamos manualmente el usuario autenticado
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
