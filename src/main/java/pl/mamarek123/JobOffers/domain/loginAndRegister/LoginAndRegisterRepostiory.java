package pl.mamarek123.JobOffers.domain.loginAndRegister;

import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDTO;

import java.util.Optional;

interface LoginAndRegisterRepostiory {
    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    Optional<UserResultDTO> registerUser(User user);
}
