package io.moderne.scouting.cookies.order;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.moderne.scouting.cookies.CookieType;
import io.moderne.scouting.cookies.error.ApiError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("wiremock")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {WireMockConfig.class})
class OrderControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private WireMockServer mockUserService;

    @Autowired
    private WireMockServer mockInventoryService;

    @BeforeEach
    void setUp() {
        UserMocks.setupUserMocks(mockUserService);
        InventoryMocks.setupUserMocks(mockInventoryService);
    }

    @Test
    void createOrder() {
        Map<CookieType, Integer> cookies = Map.of(CookieType.STROOPWAFEL, 1, CookieType.KANO, 2);
        OrderController.OrderRequest request = new OrderController.OrderRequest("abc123", "12345");
        ResponseEntity<Order> response = testRestTemplate.postForEntity("/orders", request, Order.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getPrice()).isEqualTo(BigDecimal.valueOf(2.5));
        assertThat(response.getBody().getCookies()).isEqualTo(cookies);
        assertThat(response.getBody().getUser().id()).isEqualTo("abc123");
    }

    @Test
    void createOrderWithUnknownUserThrowsApiError() {
        Map<CookieType, Integer> cookies = Map.of(CookieType.STROOPWAFEL, 1, CookieType.KANO, 2);
        OrderController.OrderRequest request = new OrderController.OrderRequest("unknown", "12345");
        ResponseEntity<ApiError> response = testRestTemplate.postForEntity("/orders", request, ApiError.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo(new ApiError("User", "The user does not exist."));
    }

    @Test
    void createOrderWithUnknownReservationThrowsApiError() {
        OrderController.OrderRequest request = new OrderController.OrderRequest("abc123", "unknown");
        ResponseEntity<ApiError> response = testRestTemplate.postForEntity("/orders", request, ApiError.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo(new ApiError("Order", "The reservation does not exist."));
    }

}