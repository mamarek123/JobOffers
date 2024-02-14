package pl.mamarek123.JobOffers.domain.loginAndRegister;

import java.util.Optional;

class RegisterRepositoryCheckerIfExists {
    private LoginAndRegisterRepostiory registerRepostiory;
    boolean checkIfUserWithGivenEmailExists(String email) {
        Optional<User> optionalUser = registerRepostiory.getUserByEmail(email);
        return optionalUser.isPresent();
    }

    boolean checkIfUserWithGivenUsernameExists(String username) {
        Optional<User> optionalUser = registerRepostiory.getUserByUsername(username);
        return optionalUser.isPresent();
    }
}
