package com.awesome.testing.contract;

import au.com.dius.pact.consumer.junit.ConsumerPactTest;
import com.awesome.testing.ProviderService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractPactTest extends ConsumerPactTest {

    private static final String PROVIDER_NAME = "ExampleProvider";
    private static final String CUSTOMER_NAME = "Messi";

    @Autowired
    ProviderService providerService;

    @Override
    protected String providerName() {
        return PROVIDER_NAME;
    }

    @Override
    protected String consumerName() {
        return CUSTOMER_NAME;
    }

}
