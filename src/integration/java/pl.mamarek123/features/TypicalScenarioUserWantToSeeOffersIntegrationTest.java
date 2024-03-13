package pl.mamarek123.features;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.mamarek123.BaseIntegrationTest;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDto;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferRequestDto;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferResponseDto;
import pl.mamarek123.JobOffers.infrastructure.offer.scheduler.HttpOffersScheduler;
import pl.mamarek123.JobOffers.infrastructure.tokenandregister.controller.dto.TokenResponseDto;
import pl.mamarek123.SampleJobOfferResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


public class TypicalScenarioUserWantToSeeOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOfferResponse {


    @Autowired
    HttpOffersScheduler offersScheduler;

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {
        //step 1: there are no offers in external HTTP server
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithZeroOffersJson())));

        //step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        List<OfferResponseDto> savedOffers = offersScheduler.fetchAllOffersAndSaveAllIfNotExists();
        assertTrue(savedOffers.isEmpty());
        //step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        ResultActions resultActions = mockMvc.perform(post("/token")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        resultActions.andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                        {
                          "message": "Bad Credentials",
                          "status": "UNAUTHORIZED"
                        }
                        """.trim()));
        //step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
        //step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        mockMvc.perform(post("/register")
                        .content("""
                        {
                        "username": "someUser",
                        "email": "someEmail@email.com",
                        "password": "somePassword"
                        }
                        """.trim())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isCreated());
        //step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        //given & when
        MvcResult mvcResult = mockMvc.perform(post("/token")
                        .content("""
                                {
                                "username": "someUser",
                                "email": "someEmail@email.com",
                                "password": "somePassword"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        TokenResponseDto tokenResponse = objectMapper.readValue(contentAsString, TokenResponseDto.class);
        String token = tokenResponse.token();
        //then
        assertAll(
                () -> assertThat(tokenResponse.username()).isEqualTo("someUser"),
                () -> assertThat(token).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );
        //step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        //given
        String bearerToken = "Bearer " + token;
        //when
        mockMvc.perform(get("/offers")
                        .header("Authorization", bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //step 8: there are 2 new offers in external HTTP server
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithTwoOffersJson())));

        //step 9: scheduler ran 2nd time and made GET to external server
        //and system added 2 new offers with ids: firstId and secondId to database
        savedOffers = offersScheduler.fetchAllOffersAndSaveAllIfNotExists();
        assertEquals(2, savedOffers.size());
        String firstId = savedOffers.get(0).id();
        String secondId = savedOffers.get(1).id();
        //step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC”
        // and system returned OK(200) with 2 offers with ids: firstId and secondId
        mockMvc.perform(get("/offers")
                        .header("Authorization", bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':'" + firstId + "'},{'id':'" + secondId + "'}]"));
        //step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        mockMvc.perform(get("/offers/9999")
                        .header("Authorization", bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(content().string("Offer with id:9999 does not exist"));
        //step 12: user made GET /offers/1000 and system returned OK(200) with offer
        mockMvc.perform(get("/offers/" + firstId)
                        .header("Authorization", bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':'" + firstId + "'}"));
        //step 13: there are 2 new offers in external HTTP server
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithFourOffersJson())));
        //step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        savedOffers = offersScheduler.fetchAllOffersAndSaveAllIfNotExists();
        assertEquals(2, savedOffers.size());
        String thirdId = savedOffers.get(0).id();
        String fourthId = savedOffers.get(1).id();
        //step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
        mockMvc.perform(get("/offers")
                        .header("Authorization", bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':'" + firstId + "'},{'id':'" + secondId + "'},{'id':'" + thirdId + "'},{'id':'" + fourthId + "'}]"));
        //step 16: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and offer as body and system returned CREATED(201) with saved offer
        //can be done in two ways, with a string or with object mapper
        //this is with string
        String jsonBodyString = bodyWithOneOfferJson();
        //let's use object mapper
        OfferRequestDto newOffer = OfferRequestDto.builder()
                .offerUrl("https://nofluffjobs.com/pl/job/newUrlWithNewJob9999")
                .title("Software Engineer - Mobile (m/f/d)")
                .company("Cybersource")
                .salary("4k - 8k PLN")
                .build();
        String jsonDtoString = objectMapper.writeValueAsString(newOffer);
        System.out.println(jsonBodyString);
        mockMvc.perform(post("/offers")
                        .header("Authorization", bearerToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDtoString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.offerUrl", is("https://nofluffjobs.com/pl/job/newUrlWithNewJob9999")));
        //step 17: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 5 offers
        mockMvc.perform(get("/offers")
                        .header("Authorization", bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5))); // This line asserts that the top-level array has exactly 5 elements
    }
}
