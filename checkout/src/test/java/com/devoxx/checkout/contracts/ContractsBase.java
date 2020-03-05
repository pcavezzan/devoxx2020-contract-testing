package com.devoxx.checkout.contracts;

import com.devoxx.checkout.domain.Cashier;
import com.devoxx.checkout.domain.Delivery;
import com.devoxx.checkout.domain.Order;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureStubRunner(
        ids = {"com.devoxx:inventory:+:stubs:8080"},
        stubsMode = StubRunnerProperties.StubsMode.REMOTE,
        repositoryRoot = "git://file:///Users/ygrenzinger/git/contract.git",
        stubsPerConsumer = true,
        consumerName = "checkout"
)
@AutoConfigureMessageVerifier
@DirtiesContext
public class ContractsBase {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MessageVerifier verifier;

    @Autowired
    private Cashier cashier;

    @Autowired
    private Delivery delivery;

    @BeforeEach
    public void before() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    public void sendOrder() {
        LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Order order = new Order("d4d37e73-77a0-4616-8bd2-5ed983d45d14", 2, "yannick", createdAt);
        delivery.send(order);
    }


}
