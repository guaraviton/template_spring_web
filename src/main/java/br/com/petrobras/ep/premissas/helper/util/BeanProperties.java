package br.com.petrobras.ep.premissas.helper.util;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.petrobras.ep.premissas.helper.exception.AppException;

public class BeanProperties {
	
	private static final Log LOGGER = LogFactory.getLog(BeanProperties.class);

	public static void copyProperties(Object dest, Object orig){
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalArgumentException e) {
			//ignora tentativa de copia de tipos diferentes
		} catch (Exception e) {
			LOGGER.error("Erro copyProperties", e);
		}
	}
	
	public static Object getProperty(Object bean, String campo){
	    try {
	    	Method m = bean.getClass().getMethod("get" + campo.substring(0,1).toUpperCase() + campo.substring(1, campo.length()), new Class[] {});
			return m.invoke(bean, new Object[] {});
	    } catch (NoSuchMethodException e) {
	    	return null;
	    } catch (Exception e) {
			throw new AppException("Erro getProperty", e);
		}
	}
}
