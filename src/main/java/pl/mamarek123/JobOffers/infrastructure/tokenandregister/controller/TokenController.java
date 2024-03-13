package pl.mamarek123.JobOffers.infrastructure.tokenandregister.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.mamarek123.JobOffers.infrastructure.tokenandregister.controller.dto.TokenRequestDto;
import pl.mamarek123.JobOffers.infrastructure.tokenandregister.controller.dto.TokenResponseDto;
import pl.mamarek123.JobOffers.infrastructure.security.jwt.JwTAuthenthicator;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class TokenController {

    JwTAuthenthicator jwTAuthenthicator;
    @PostMapping("/token")
    ResponseEntity<TokenResponseDto> authenthicateAndGenerateToken(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        TokenResponseDto tokenResponseDto = jwTAuthenthicator.authenticateAndGenerateToken(tokenRequestDto);
        return ResponseEntity.ok(tokenResponseDto);
    }

}
