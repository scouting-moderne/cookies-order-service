package io.moderne.scouting.cookies.order.user;

import io.moderne.scouting.cookies.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user", url = "http://cookies-user-service:8080")
public interface UserClient {
    @PostMapping
    User createUser(@RequestBody User user);

    @GetMapping("/{id}")
    User findUser(@PathVariable String id);

    @PutMapping("/{id}")
    User updateUser(@PathVariable String id, @RequestBody User user);

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable String id);
}
