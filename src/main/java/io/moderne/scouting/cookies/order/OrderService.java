package io.moderne.scouting.cookies.order;

import io.moderne.scouting.cookies.error.ApiError;
import io.moderne.scouting.cookies.error.ApiException;
import io.moderne.scouting.cookies.order.reservation.InventoryClient;
import io.moderne.scouting.cookies.order.reservation.Reservation;
import io.moderne.scouting.cookies.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final InventoryClient inventoryClient;
    private final Map<String, Order> db = new HashMap<>();

    public Order createOrder(User user, String reservationId) {
        Reservation reservation = inventoryClient.verified(new InventoryClient.ReservationVerifyRequest(user.id(), reservationId))
                .orElseThrow(() -> new ApiException(new ApiError("Order", "The reservation does not exist.", null)));
        if (reservation.getCookies().isEmpty()) {
            throw new ApiException(new ApiError("Order", "The order cannot be empty.", null));
        }
        Order order = new Order(user, reservation.getCookies(), reservation.getPrice());
        db.put(order.getId(), order);
        return order;
    }

    public Order findOrder(String id) {
        return db.get(id);
    }
}
