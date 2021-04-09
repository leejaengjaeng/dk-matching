package com.ddukeong.dkmatching.controller.jpaSample;

import com.ddukeong.dkmatching.model.jpaSample.Food;
import com.ddukeong.dkmatching.model.jpaSample.Order;
import com.ddukeong.dkmatching.model.jpaSample.Person;
import com.ddukeong.dkmatching.service.jpaSample.JpaSampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/jpa/api")
public class JpaSampleRestController {

    private final JpaSampleService service;

    public JpaSampleRestController(JpaSampleService service) {
        this.service = service;
    }

    @PostMapping("/sample/person")
    public Person createPerson(@RequestBody Person person) {
        return service.addPerson(person);
    }

    @PostMapping("/sample/food")
    public Food createFood(@RequestBody Food food) {
        return service.addFood(food);
    }

    @PostMapping("/sample/order")
    public Order createOrder(@RequestBody Order order) {
        return service.addOrder(order);
    }

    @DeleteMapping("/sample/{type}/{entityId}")
    public void createOrder(@PathVariable String type, @PathVariable String entityId) {
        service.deleteEntity(type, entityId);
    }
}
