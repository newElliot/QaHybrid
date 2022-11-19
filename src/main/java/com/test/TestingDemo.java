package com.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestingDemo {
	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] ae) {
		logger.info("This is test log info");
		logger.error("This is error");
	}
}
