package br.com.petrobras.ep.premissas.helper.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.Normalizer;

import br.com.petrobras.ep.premissas.helper.exception.AppException;

public class StringUtils {
	
	public static final String EMPTY_STRING = "";
	
	public static boolean isNotBlank(String str) {
		return org.apache.commons.lang.StringUtils.isNotBlank(str);
	}
	
	public static boolean isBlank(String str) {
		return org.apache.commons.lang.StringUtils.isBlank(str);
	}
	
	public static String getFileName(String str) {
		return str.substring(str.lastIndexOf("/") + 1, str.length());
	}
	
	public static String removerCaracteresEspeciais(String str) {
		return str.replaceAll("[^0-9]", "");
	}
	
	public static String getHorasFormatada(Double quantidadeHoras) {
		if(quantidadeHoras == null){ 
			return "";
		}
		String[] partesHoras = String.valueOf(quantidadeHoras).split("\\.");
		String horas = partesHoras[0];
		if(horas.length() == 1){
			horas = "0" + horas;	
		}
		String minutos = partesHoras[1];
		if(minutos.equals("5")){
			minutos = "30";	
		}
		if(minutos.length() == 1){
			minutos = "0" + minutos;	
		}
		return horas + ":" + minutos;
	}
	
	public static String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static String decode(String string) {
		try {
			return URLDecoder.decode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new AppException("Erro ao realizar o decode da string " + string, e);
		}
	}
	
	public static String trimInsideWithOneSpace(String string) {
		int amountBlanks = 0;
		StringBuilder newString = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if (c != ' ') {
				amountBlanks = 0;
			} else {
				amountBlanks += 1;
			}
			if (amountBlanks < 2) {
				newString.append(c);
			}
		}
		return newString.toString().trim();
	}
	
	public static String toString(Object obj) {
		if (obj == null) {
			return StringUtils.EMPTY_STRING;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		return obj.toString();
	}

}

