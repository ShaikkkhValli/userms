package com.edureka.userms.service;

import com.edureka.userms.model.OrderTO;
import com.edureka.userms.model.User;
import com.edureka.userms.repository.UserRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final OrderService orderService;

    public UserServiceImpl(UserRepository userRepository, OrderService orderService) {
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    @Override
    public List<User> getAllUsers(Optional<String> name) {
        LOGGER.info("************");
        LOGGER.info("getting all users");
        LOGGER.debug("***");
        LOGGER.error("^^^");

        if (name.isPresent()) {
            Optional<List<User>> optionalUserList = userRepository.findByNameContainingIgnoreCase(name.get());
            return optionalUserList.orElse(Collections.emptyList());
        }
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getSingleUser(Long userId) {
        LOGGER.info("************");
        LOGGER.info("getting single user");
        return userRepository.findById(userId);
    }

    @Override
    public User createUser(User user) {
        User userCreated = userRepository.save(user);
        return userCreated;
    }

    @Override
    public void partiallyUpdateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public OrderTO[] getAllOrders() {
        LOGGER.info("$$$$$$$$$$$$$");
        System.out.println("UserResource");
        OrderTO[] allOrders = orderService.getAllOrders(); // service-1
        System.out.println(allOrders.length);
        OrderTO[] catalogues = orderService.getCatalogues(); // service-2
        System.out.println(catalogues.length);
        OrderTO[] combinedOrders = Stream.of(allOrders, catalogues)
                .flatMap(Stream::of)
                .toArray(OrderTO[]::new);
        return combinedOrders;
    }

}
