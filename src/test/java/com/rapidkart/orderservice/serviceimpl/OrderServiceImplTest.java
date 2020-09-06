package com.rapidkart.orderservice.serviceimpl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before all.. ");
    }

    @BeforeEach
    void setup() {
        System.out.println("Before each method call...");
    }

    @Test
    void createOrder() {
        assertEquals(1, 1);
    }

    @Test
    void listOrders() {
        assertEquals(1, 1);
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After all");
    }
}