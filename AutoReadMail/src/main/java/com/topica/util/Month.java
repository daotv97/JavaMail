package com.topica.util;

import java.util.logging.Logger;

import com.topica.mail.CheckingMails;

public class Month {
	static Logger logger = Logger.getLogger(CheckingMails.class.getName());
	public static void main(String[] args) {
		if (args.length != 1) {
			logger.info("wrong parameters");
		} else {
			try {
				int month = Integer.parseInt(args[0]);
				logger.info("RESULT: " + month);
			} catch (NumberFormatException e) {
				logger.info(e.getMessage());
			}
		}
	}

	public static int getDay(int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;

		case 4:
		case 6:
		case 9:
		case 11:
			return 30;

		case 2:
			return 28;
		default:
			return -1;
		}
	}

}
