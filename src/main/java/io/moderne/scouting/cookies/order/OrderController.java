package io.moderne.scouting.cookies.order;

import io.moderne.scouting.cookies.error.ApiError;
import io.moderne.scouting.cookies.error.ApiException;
import io.moderne.scouting.cookies.error.ErrorHandling;
import io.moderne.scouting.cookies.order.user.UserClient;
import io.moderne.scouting.cookies.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;


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
                .orElseThrow(() -> new ApiException(new ApiError("User", "The user does not exist.", 500)));
        return orderService.createOrder(user, order.reservationId());
    }

    @GetMapping
    public Order getOrder(@RequestParam String id) {
        return orderService.findOrder(id);
    }

    record OrderRequest(String userId, String reservationId) {
    }
}
