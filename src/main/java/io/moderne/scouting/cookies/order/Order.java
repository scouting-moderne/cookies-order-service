package io.moderne.scouting.cookies.order;

import io.moderne.scouting.cookies.CookieCalculator;
import io.moderne.scouting.cookies.CookieType;
import io.moderne.scouting.cookies.user.User;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Value
public class Order {
    String id;
    User user;
    Map<CookieType, Integer> cookies;
    BigDecimal price;
    Instant createdAt;
    Instant payedAt;
    Instant deliveredAt;

    public Order(User user, Map<CookieType, Integer> cookies) {
        id = java.util.UUID.randomUUID().toString();
        this.user = user;
        this.cookies = cookies;
        this.createdAt = Instant.now();
        this.price = CookieCalculator.calculatePrice(cookies);
        this.payedAt = null;
        this.deliveredAt = null;
    }
}
