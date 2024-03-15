package pl.mamarek123.JobOffers.infrastructure.loginandregister.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.mamarek123.JobOffers.infrastructure.loginandregister.controller.dto.LoginRequestDto;
import pl.mamarek123.JobOffers.infrastructure.loginandregister.controller.dto.LoginResponseDto;
import pl.mamarek123.JobOffers.infrastructure.security.jwt.JwTAuthenthicator;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LoginController {

    JwTAuthenthicator jwTAuthenthicator;
    @PostMapping("/token")
    ResponseEntity<LoginResponseDto> authenthicateAndGenerateToken(@RequestBody @Valid LoginRequestDto tokenRequestDto){
        LoginResponseDto loginResponseDto = jwTAuthenthicator.authenticateAndGenerateToken(tokenRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

}
