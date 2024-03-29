package com.topica.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import com.topica.mail.CheckingMails;

public class CodeExecutor implements Callable<Integer> {
	static Logger logger = Logger.getLogger(CheckingMails.class.getName());
	private String filePath;
	private String classPath;

	public CodeExecutor(String filePath) {
		super();
		this.filePath = filePath;
		classPath = filePath.substring(0,filePath.lastIndexOf(File.separatorChar));
	}

	@Override
	public Integer call() {	
		int[] inputs = new int[] { 0, 1, 2, 3, 5, 6, 7, 8, 9, 13 };
		int[] result = new int[] { -1, 31, 28, 31, 31, 30, 31, 31, 30, -1 };
		int score = 0;
		// compile code
		try {
			Process codeCompiling = Runtime.getRuntime().exec("C:\\Program Files\\Java\\jdk1.8.0_211\\bin\\javac.exe " + filePath);
			codeCompiling.waitFor();
			for (int i = 0; i < inputs.length; i++) {
				if (testCode(inputs[i], result[i]))
					score++;
			}
		} catch (IOException | InterruptedException e) {
			logger.info(e.getMessage());
			return -1;
		} finally {
			Thread.currentThread().interrupt();
		}
		return score;
	}

	private boolean testCode(int input, int result) throws IOException, InterruptedException {
		String runFileName = filePath.substring(filePath.lastIndexOf('\\') + 1, filePath.lastIndexOf('.'));
		Process codeRunner = Runtime.getRuntime().exec("java -cp " + classPath + " " + runFileName + " " + input);
		BufferedReader reader = new BufferedReader(new InputStreamReader(codeRunner.getInputStream()));
		String line = reader.readLine();
		if (line != null) {
			try {
				int output = Integer.parseInt(line);
				return output == result;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		codeRunner.waitFor();
		return false;
	}

}
