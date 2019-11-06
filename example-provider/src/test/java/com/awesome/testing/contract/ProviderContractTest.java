package com.awesome.testing.contract;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import com.awesome.testing.service.InformationService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.awesome.testing.state.ContractState;

@RunWith(SpringRestPactRunner.class)
@Provider("ExampleProvider")
@PactBroker(host = "localhost", port = "9292", tags = {"master"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProviderContractTest {

    @Autowired
    private InformationService informationService;

    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    @State("Empty database state")
    public void emptyDatabase() {
        ContractState.EMPTY.setState(informationService);
    }

    @State("Two entries exist")
    public void defaultState() {
        ContractState.DEFAULT.setState(informationService);
    }
}

