package io.moderne.scouting.cookies.order;

import io.moderne.scouting.cookies.CookieType;
import io.moderne.scouting.cookies.order.user.UserClient;
import io.moderne.scouting.cookies.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
class OrderController {

    private final OrderService orderService;
    private final UserClient userClient;

    @PostMapping
    public Order createOrder(@RequestParam String userId, @RequestBody OrderRequest order) {
        User user = userClient.findUser(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return orderService.createOrder(user, order.cookies());
    }

    record OrderRequest(Map<CookieType, Integer> cookies) {
    }
}
