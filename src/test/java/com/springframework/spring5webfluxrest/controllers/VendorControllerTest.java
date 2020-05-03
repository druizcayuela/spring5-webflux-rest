package com.springframework.spring5webfluxrest.controllers;

import com.springframework.spring5webfluxrest.domain.Vendor;
import com.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void list() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Fred").lastName("Flinstone").build(),
                        Vendor.builder().firstName("Barney").lastName("Rubble").build()));

        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("Fred").lastName("Flinstone").build()));

        webTestClient.get().uri("/api/v1/vendors/foo")
                .exchange()
                .expectBody(Vendor.class);
    }
}