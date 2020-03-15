package com.devoxx.checkout.contracts;

import com.devoxx.checkout.domain.Delivery;
import com.devoxx.checkout.domain.ValidatedOrder;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureStubRunner
@AutoConfigureMessageVerifier
@DirtiesContext
public class ContractsBase {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Delivery delivery;

    @BeforeEach
    public void before() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    public void sendOrder() {
        ValidatedOrder validatedOrder = new ValidatedOrder("d4d37e73-77a0-4616-8bd2-5ed983d45d14", 10, "yannick", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        delivery.send(validatedOrder);
    }


}
