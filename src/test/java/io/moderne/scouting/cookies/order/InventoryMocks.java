package io.moderne.scouting.cookies.order;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class InventoryMocks {

    public static void setupUserMocks(WireMockServer mockService) {
        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/inventory/verify"))
                .withRequestBody(WireMock.equalToJson("{\"userId\":\"abc123\",\"reservationId\":\"12345\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("""
                                {
                                    "price": 2.5,
                                    "cookies": {
                                        "STROOPWAFEL": 1,
                                        "KANO": 2
                                    },
                                    "user": {
                                        "id": "abc123",
                                        "username": "User Name"
                                    },
                                    "expiresAt": "2030-01-01T00:00:00Z",
                                    "reservedAt": "2022-01-01T00:00:00Z",
                                    "id": "12345"
                                }
                                """)));

        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/inventory/verify"))
                .withRequestBody(WireMock.equalToJson("{\"userId\":\"unknown\",\"reservationId\":\"12345\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));

        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/inventory/verify"))
                .withRequestBody(WireMock.equalToJson("{\"userId\":\"abc123\",\"reservationId\":\"unknown\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));
    }
}
