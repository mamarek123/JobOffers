package pl.mamarek123.JobOffers.infrastructure.loginandregister.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserRequestDto;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDto;
import pl.mamarek123.JobOffers.domain.loginAndRegister.LoginAndRegisterFacade;
import pl.mamarek123.JobOffers.infrastructure.loginandregister.controller.dto.RegisterResponseDto;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RegisterController {
    LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    ResponseEntity<RegisterResponseDto> RegisterUser(@RequestBody @Valid UserRequestDto userRequestDto){
        UserRequestDto userEncodedRequestDto = UserRequestDto.builder()
                .email(userRequestDto.email())
                .password(bCryptPasswordEncoder.encode(userRequestDto.password()))
                .username(userRequestDto.username())
                .build();
        UserResultDto registerResult = loginAndRegisterFacade.register(userEncodedRequestDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponseDto(registerResult.username()));
    }

}
