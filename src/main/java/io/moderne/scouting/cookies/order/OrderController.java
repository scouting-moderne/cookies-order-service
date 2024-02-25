package io.moderne.scouting.cookies.order;

import io.moderne.scouting.cookies.CookieType;
import io.moderne.scouting.cookies.error.ApiError;
import io.moderne.scouting.cookies.error.ApiException;
import io.moderne.scouting.cookies.error.ErrorHandling;
import io.moderne.scouting.cookies.order.user.UserClient;
import io.moderne.scouting.cookies.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Import(ErrorHandling.class)
class OrderController {

    private final OrderService orderService;
    private final UserClient userClient;

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest order) {
        User user = userClient.findUser(order.userId())
                .orElseThrow(() -> new ApiException(new ApiError("User", "The user does not exist.")));
        return orderService.createOrder(user, order.cookies());
    }

    record OrderRequest(String userId, Map<CookieType, Integer> cookies) {
    }
}
