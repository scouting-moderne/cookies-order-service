package io.moderne.scouting.cookies.order.reservation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(value = "inventory", url = "${service.inventory.url:http://cookies-inventory-service:8080}", dismiss404 = true)
public interface InventoryClient {

    @PostMapping("/inventory/verify")
    Optional<Reservation> verified(@RequestBody ReservationVerifyRequest request);

    record ReservationVerifyRequest(String userId, String reservationId) {
    }
}
