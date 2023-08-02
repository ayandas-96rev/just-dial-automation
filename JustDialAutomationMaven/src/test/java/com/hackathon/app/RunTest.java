package com.hackathon.app;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class RunTest {

	public static void main(String[] args) {
		
		TestNG testNG = new TestNG();
		
		List<String> list = new ArrayList<>();
		list.add("JustDialAutomationMaven/testng.xml");
		
		testNG.setTestSuites(list);
		
		testNG.run();

	}

}
