package br.com.petrobras.ep.premissas.helper.util;

public abstract class ConverterUtils {

	/**
	 * 
	 */

	public static final double M3_BBL = 6.2898;

	public static final double BLL_MBBL = 1000;

	/**
	 * Converte milliseconds para dias.
	 * 
	 * @param milliseconds
	 * @return
	 */
	public static int toDays(long milliseconds) {
		return (int) (milliseconds / 1000 / 60 / 60 / 24);
	}


	public static int toSeconds(long milliseconds) {
		return Math.round(milliseconds / 1000);
	}

	
	public static int toMins(long milliseconds) {
		return Math.round(milliseconds / 1000 / 60);
	}
	
	public static int toHours(long milliseconds) {
		return Math.round(milliseconds / 1000 / 60 / 60);
	}
	
	public static String toTimeLabel(long millis){
		String label = StringUtils.EMPTY_STRING;
		if (millis <= 1000){
			label = millis + " mills"; 
		}else if ((millis / 1000) <= 60){
			label = ConverterUtils.toSeconds(millis) + " secs";
		} else if ((millis / 1000 / 60) <= 60){
			label = ConverterUtils.toMins(millis) + " mins";
		} else if ((millis / 1000 / 60 / 60) <= 60){
			label = ConverterUtils.toHours(millis) + " hours";
		}
		return label;
	}


}
