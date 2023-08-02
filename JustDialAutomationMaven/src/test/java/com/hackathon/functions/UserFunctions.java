package com.hackathon.functions;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserFunctions {
	public static String TIMESTAMP=null;
	
	// User function to sleep thread with pre-implemented try/catch block
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// User function to get report store path reference to timeStamp
	public static String getReportBasePath() {
		DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy_MM_dd_k-m-s");
		if(TIMESTAMP==null) {
			TIMESTAMP = System.getProperty("user.dir")+ "/TESTREPORT/Log[" + LocalDateTime.now().format(d).toString() + "]/";
			File folder = new File(TIMESTAMP);
			folder.mkdir();
		}
		return TIMESTAMP;
	}
}
