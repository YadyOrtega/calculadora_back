package co.com.ath.calculadora.pruebas.serviceimpl;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.stereotype.Service;

import co.com.ath.calculadora.pruebas.util.Constants;

import co.com.ath.calculadora.pruebas.dto.LoginDto;
import co.com.ath.calculadora.pruebas.service.ILdapService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LdapServiceImpl implements ILdapService {
	
	private static final String CODIGO_CN = "CN";
	private static final String CODIGO_DC = "DC=Almaviva,DC=loc";

	@Override
	public boolean loginLdap(LoginDto dto) {
		log.info(Constants.LOG_IN, Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			Hashtable<String, String> environment = new Hashtable<String, String>();
			String distinguishedNameInitial = CODIGO_CN + "=" + "Administrador texto" + "," + "OU=Sophos,OU=Contratistas,OU=Gerencia de Tecnología,OU=ALFA";
			environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			environment.put(Context.PROVIDER_URL, "ldap://10.61.56.130:389");
			environment.put(Context.SECURITY_AUTHENTICATION, "simple");
			environment.put(Context.SECURITY_PRINCIPAL, distinguishedNameInitial);
			environment.put(Context.SECURITY_CREDENTIALS, "Factel@lfa2020*");

			log.info("Conectando con LDAP");
			DirContext dirContext = new InitialDirContext(environment);

			String identifyingAttribute = "SamAccountName";
			String[] attributeFilter = { identifyingAttribute };
			SearchControls sc = new SearchControls();
			sc.setReturningAttributes(attributeFilter);
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

			String searchFilter = "(" + identifyingAttribute + "=" + dto.getUser() + ")";
			NamingEnumeration<SearchResult> results = dirContext.search(CODIGO_DC, searchFilter, sc);

			if (results.hasMore()) {
				SearchResult result = results.next();
				String distinguishedName = result.getNameInNamespace();

				Properties authEnv = new Properties();
				authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
				authEnv.put(Context.PROVIDER_URL,  "ldap://10.61.56.130:389");
				authEnv.put(Context.SECURITY_PRINCIPAL, distinguishedName);
				authEnv.put(Context.SECURITY_CREDENTIALS, dto.getPassword());
				new InitialDirContext(authEnv);
				dirContext.close();
			}
		} catch (AuthenticationNotSupportedException | AuthenticationException exception) {
			log.error("Error con logeo ldap: {} ", exception.getMessage());
			return false;
		} catch (NamingException e) {
			log.error("Error con logeo ldap: {} ", e.getMessage());
			return false;
		}
		return true;
	}

}
