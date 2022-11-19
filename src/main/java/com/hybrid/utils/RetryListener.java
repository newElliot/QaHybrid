package com.hybrid.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;


public class RetryListener implements IAnnotationTransformer {
	
	@Override
	public void transform(ITestAnnotation testAnnotation, Class testClass, Constructor testConstructor,
			Method testMethod) {
		if (testAnnotation.getRetryAnalyzerClass() == null) {
			testAnnotation.setRetryAnalyzer(RetryAnalyzer.class);
		}
	}
}
