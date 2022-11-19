package com.hybrid.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemCall extends Thread{
	private static Logger logger = LogManager.getLogger();

	public int executeCommand(String sysCmd, String command, File dir)
			throws IOException, Exception, InterruptedException {

		int exitVal = 0;
		Process p = null;
		String s = null;

		try {

			// Execute command
			logger.info("        Executing Command: " + sysCmd + command + " From Folder: " + dir);

			// Create new process for shell command
			p = Runtime.getRuntime().exec(sysCmd + command, null, dir);

			// Evaluate if we need to read an input stream in an extra thread for running
			// external programs out of Java (so that stdoutand stderr are not blocking the
			// process from work)
			if (dir.toString().contains("jenkins")) {
				start();
			} else {

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

				// read the output from the command
				logger.info("        Standard Output");
				while ((s = stdInput.readLine()) != null) {
					logger.info(s);
				}

				// read any errors from the attempted command
				logger.info("        Standard Error");
				while ((s = stdError.readLine()) != null) {
					logger.info(s);
				}
			}

			// Wait for process to finish and get exit value
			exitVal = p.waitFor();
			logger.info("        Process Exit Value: " + exitVal);
		} catch (IOException e) {
			logger.info("        Exception Occurred");
			e.printStackTrace();
			logger.info("        Process Exit Value: " + exitVal);
			System.exit(-1);
		}
		return exitVal;
	}

	public int executeShellCommand(String command) throws IOException, Exception, InterruptedException {

		int exitVal = 0;
		Process p = null;
		String s = null;

		try {

			// Execute command
			logger.info("        Executing Command: " + command);

			// Create new process for shell command
			p = Runtime.getRuntime().exec(command);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			logger.info("        Standard Output");
			while ((s = stdInput.readLine()) != null) {
				logger.info(s);
			}

			// read any errors from the attempted command
			logger.info("        Standard Error");
			while ((s = stdError.readLine()) != null) {
				logger.info(s);
			}

			// Wait for process to finish and get exit value
			exitVal = p.waitFor();
			logger.info("        Process Exit Value: " + exitVal);
		} catch (IOException e) {
			logger.info("        Exception Occurred");
			e.printStackTrace();
			logger.info("        Process Exit Value: " + exitVal);
			System.exit(-1);
		}
		return exitVal;
	}
}
