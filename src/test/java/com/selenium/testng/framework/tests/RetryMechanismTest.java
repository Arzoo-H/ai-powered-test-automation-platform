package com.selenium.testng.framework.tests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.selenium.testng.base.BaseTest;

public class RetryMechanismTest extends BaseTest {

    private static int attemptCount = 0;

    @Test
    public void verifyRetryMechanism() {

        attemptCount++;

        System.out.println("Attempt number: " + attemptCount);

        Assert.assertTrue(
            attemptCount >= 3,
            "Intentional failure to verify retry behavior"
        );
    }
}