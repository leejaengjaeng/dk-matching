package com.ddukeong.dkmatching.service.jpaSample;

import com.ddukeong.dkmatching.model.jpaSample.Food;
import com.ddukeong.dkmatching.model.jpaSample.Order;
import com.ddukeong.dkmatching.model.jpaSample.Person;
import com.ddukeong.dkmatching.repository.jpaSample.FoodRepository;
import com.ddukeong.dkmatching.repository.jpaSample.OrderRepository;
import com.ddukeong.dkmatching.repository.jpaSample.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JpaSampleService {
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;

    public JpaSampleService(FoodRepository foodRepository, OrderRepository orderRepository, PersonRepository personRepository) {
        this.foodRepository = foodRepository;
        this.orderRepository = orderRepository;
        this.personRepository = personRepository;
    }

    public List<Food> getFoods() {
        return foodRepository.findAll()
                .stream()
                .filter(food -> !food.isDeleted())
                .collect(Collectors.toList());
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public List<Person> getPersons() {
        return personRepository.findAll()
                .stream()
                .filter(person -> !person.isDeleted())
                .collect(Collectors.toList());
    }

    public Food addFood(Food food) {
        return foodRepository.save(food);
    }

    public Order addOrder(Order order) {
        System.out.println(order);

        Food food = foodRepository.findById(order.getFood().getId()).orElse(null);
        Person person = personRepository.findById(order.getOrderedPerson().getId()).orElse(null);

        order.setFood(food);
        order.setOrderedPerson(person);
        return orderRepository.save(order);
    }

    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    public void deleteEntity(String type, String id) {
        Long entityId = Long.valueOf(id);
        switch (type) {
            case "food":
                Food food = foodRepository.findById(entityId).get();
                food.setDeleted(true);
                /**
                 * save 안해도 동작해야 하는거 아닌가?
                 * -> select 해서 꺼낸 온건 관리안한다..?
                 * -> Persist Context 보면 select 해서 1차 캐쉬에 넣고 그대로 반환해서 반영 돼야 할것 같은데
                 */
                foodRepository.save(food);
                return;
            case "person":
                Person person = personRepository.findById(entityId).get();
                person.setDeleted(true);
                personRepository.save(person);
                return;
            case "order":
                Order order = orderRepository.findById(entityId).orElse(null);
                if (order == null) {
                    return;
                }
                //FK 관계 끊어줘야함
                order.setFood(null);
                order.setOrderedPerson(null);
                orderRepository.deleteById(entityId);
        }
    }
}
