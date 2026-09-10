package com.selenium.testng.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;

import com.selenium.testng.context.ExecutionContext;

public class LoggerConfig {

	public static void configure() {

		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

		context.reset();

		PatternLayoutEncoder encoder = new PatternLayoutEncoder();

		encoder.setContext(context);

		encoder.setPattern("%date [%thread] %-5level %logger - %msg%n");

		encoder.start();

		FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();

		fileAppender.setContext(context);

		fileAppender.setFile(ExecutionContext.getLogPath());

		fileAppender.setEncoder(encoder);

		fileAppender.start();

//		// this logs everything coming from all packages like com.selenium.testng.*, org.testng.*, org.openqa.selenium.*, org.apache.*
//		ch.qos.logback.classic.Logger rootLogger = context.getLogger("ROOT");
//		rootLogger.addAppender(fileAppender);
		
		// this logs everything coming from all packages called as 'com.selenium.testng'
		ch.qos.logback.classic.Logger frameworkLogger = context.getLogger("com.selenium.testng");
		frameworkLogger.addAppender(fileAppender);
		frameworkLogger.setAdditive(false);
		frameworkLogger.setLevel(ch.qos.logback.classic.Level.INFO);

	}
}
