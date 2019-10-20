package provider;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import provider.ulti.Nationality;

@RunWith(SpringRestPactRunner.class)
@Provider("ExampleProvider")
@PactBroker(host = "localhost", port = "9292")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProviderContractTest {

    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    @State("Default nationality change")
    public void changeToPortugal() {
        Nationality.setNationality("Portugal");
    }

    @State("Default nationality")
    public void defaultState() {
        Nationality.setNationality("Argentina");
    }
}

