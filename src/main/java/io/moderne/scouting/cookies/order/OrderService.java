package io.moderne.scouting.cookies.order;

import io.moderne.scouting.cookies.CookieType;
import io.moderne.scouting.cookies.user.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {
    private final Map<String, Order> db = new HashMap<>();
    public Order createOrder(User user, Map<CookieType, Integer> cookies) {
        Order order = new Order(user, cookies);
        db.put(order.getId(), order);
        return order;
    }
}
