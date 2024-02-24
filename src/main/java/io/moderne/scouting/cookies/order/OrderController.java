package io.moderne.scouting.cookies.order;

import io.moderne.scouting.cookies.CookieType;
import io.moderne.scouting.cookies.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestParam String user, @RequestBody OrderRequest order) {
        return orderService.createOrder(new User(user), order.cookies());
    }

    record OrderRequest(Map<CookieType, Integer> cookies) {
    }
}
