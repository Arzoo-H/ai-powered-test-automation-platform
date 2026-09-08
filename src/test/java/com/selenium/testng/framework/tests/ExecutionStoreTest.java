package com.selenium.testng.framework.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.selenium.testng.base.BaseTest;

public class ExecutionStoreTest extends BaseTest {

    @Test
    public void passingTest() {

        Assert.assertTrue(true);
    }

    @Test
    public void failingTest() {

        Assert.fail("Intentional failure for ExecutionStore testing");
    }

    @Test
    public void skippedTest() {

        throw new SkipException("Intentional skip for ExecutionStore testing");
    }
}
