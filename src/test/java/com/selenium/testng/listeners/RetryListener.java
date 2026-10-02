package com.selenium.testng.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.selenium.testng.utils.RetryAnalyzer;

/**
 * 
 * The RetryListener implements IAnnotationTransformer and automatically attaches RetryAnalyzer to test annotations. 
 * That means we don't need to add retryAnalyzer = RetryAnalyzer.class to every @Test method.
 * @author Arzoo Hingorani
 *
 */
public class RetryListener implements IAnnotationTransformer {

    @SuppressWarnings("rawtypes")
	@Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}