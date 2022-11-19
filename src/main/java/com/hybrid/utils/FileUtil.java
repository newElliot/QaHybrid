package com.hybrid.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtil {
	private static Logger logger = LogManager.getLogger();

	public boolean writeFileToFolder(String fileContents, String filePath, String fileName) throws Exception {

		boolean flag = true;

		try {
			String file = filePath + fileName;
			BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(file)));
			bwr.write(fileContents);
			bwr.flush();
			bwr.close();
			logger.info("        File stored : " + file);
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean deleteFile(String filePath, String fileName) {
		boolean flag = false;

		String file = filePath + fileName;
		File f = new File(file);

		if (f.delete()) {
			flag = true;
			logger.info("        Deleted File: " + file);
		} else {
			flag = false;
		}

		return flag;
	}

	public boolean deleteAllFilesInDirectory(String filePath) {
		boolean flag = false;
		File f = new File(filePath);

		for (File file : f.listFiles())
			if (!file.isDirectory()) {
				file.delete();
				logger.info("        File : " + file + " is deleted.");
			}

		if (String.valueOf(f.listFiles().length).equals("0")) {
			flag = true;
			logger.info("        Deleted all files under " + filePath + " folder.");
		} else {
			flag = false;
			logger.info("        Can not deleted all files under " + filePath + " folder.");
		}

		return flag;
	}
	
	public static void cleanQuietly(File dir) {
		try {
			FileUtils.cleanDirectory(dir);
		} catch (Exception e) {
			// quietly...
		}
	}
}
