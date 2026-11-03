package com.aula.agendamento_odontologico.infra.security;

import com.aula.agendamento_odontologico.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){

        try {
            var algotirmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API PEDRO")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algotirmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Ero ao gerar o token", exception);
        }

    }

    public String getSubject(String tokenJWT){
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("API PEDRO")
                    // reusable verifier instance
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token jwt inválido");
        }
    }

    private Instant dataExpiracao() {
        return Instant.now().plusSeconds(7200);
    }

}
