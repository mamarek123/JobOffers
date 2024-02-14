package pl.mamarek123.JobOffers.domain.loginAndRegister;

import pl.mamarek123.JobOffers.domain.ResultStatus;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDTO;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LoginAndRegisterTestInMemoryRepository implements LoginAndRegisterRepostiory{
    private final Set<User> userRepository = new HashSet<>();
    @Override
    public Optional<User> getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.stream().filter(user -> user.username().equals(username)).findFirst();
        return optionalUser;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.stream().filter(user -> user.email().equals(email)).findFirst();
        return optionalUser;
    }

    @Override
    public Optional<UserResultDTO> registerUser(User user) {
        userRepository.add(user);
        return Optional.of(UserMapper.userToUserResultDTO(user, ResultStatus.SUCCESS,"Success"));
    }
}
