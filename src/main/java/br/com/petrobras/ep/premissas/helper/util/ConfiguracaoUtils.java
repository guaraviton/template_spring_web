package br.com.petrobras.ep.premissas.helper.util;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe para leitura das propriedades de configuracao do arquivo configuracao.properties
 * @author y2jm
 *
 */
@Component
public class ConfiguracaoUtils {
	
	private static FileBasedConfiguration configuracaoGlobal;
	private static FileBasedConfiguration configuracaoLocal;
	
	public static String get(String key){
		String txt;
		txt = configuracaoLocal.getString(key);		
		if(!StringUtils.isBlank(txt)) {
			return txt;
		}
		return configuracaoGlobal.getString(key);
	}
	
	@Autowired
	public void setConfiguracaoGlobal(FileBasedConfiguration configuracaoGlobal) {
		ConfiguracaoUtils.configuracaoGlobal = configuracaoGlobal;
	}
	
	@Autowired
	public void setConfiguracaoLocal(FileBasedConfiguration configuracaoLocal) {
		ConfiguracaoUtils.configuracaoLocal = configuracaoLocal;
	}
}
