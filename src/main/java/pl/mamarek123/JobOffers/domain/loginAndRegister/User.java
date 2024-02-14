package pl.mamarek123.JobOffers.domain.loginAndRegister;

import lombok.Builder;

@Builder
record User(Long ID, String username, String password, String email) {
}
