package com.hylanda.config;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.Ssl;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/** 
 * @author zhangy
 * @E-mail:blackoon88@gmail.com 
 * @qq:846579287
 * @version created at：2017年11月8日 下午5:56:26 
 * 证书生成 keytool -genkey -alias tomcat -keyalg RSA
 */
@Configuration
@PropertySource("classpath:/tomcat.https.properties")
@EnableConfigurationProperties(WebConfiguration.TomcatSslConnectorProperties.class)
public class WebConfiguration extends WebMvcConfigurerAdapter{
	@ConfigurationProperties(prefix = "custom.tomcat.https")
	public static class TomcatSslConnectorProperties {
	    private Integer port;
	    private Boolean ssl = true;
	    private Boolean secure = true;
	    private String scheme = "https";
	    private File keystore;
	    private String keystorePassword;
	    //这里为了节省空间，省略了getters和setters，读者在实践的时候要加上

	    public void configureConnector(Connector connector) {
	        if (port != null) {
	            connector.setPort(port);
	        }
	        if (secure != null) {
	            connector.setSecure(secure);
	        }
	        if (scheme != null) {
	            connector.setScheme(scheme);
	        }
	        if (ssl != null) {
	            connector.setProperty("SSLEnabled", ssl.toString());
	        }
	        if (keystore != null && keystore.exists()) {
	            connector.setProperty("keystoreFile", keystore.getAbsolutePath());
	            connector.setProperty("keystorePassword", keystorePassword);
	        }
	        System.out.println(connector.getMaxPostSize());
	    }

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public Boolean getSsl() {
			return ssl;
		}

		public void setSsl(Boolean ssl) {
			this.ssl = ssl;
		}

		public Boolean getSecure() {
			return secure;
		}

		public void setSecure(Boolean secure) {
			this.secure = secure;
		}

		public String getScheme() {
			return scheme;
		}

		public void setScheme(String scheme) {
			this.scheme = scheme;
		}

		public File getKeystore() {
			return keystore;
		}

		public void setKeystore(File keystore) {
			this.keystore = keystore;
		}

		public String getKeystorePassword() {
			return keystorePassword;
		}

		public void setKeystorePassword(String keystorePassword) {
			this.keystorePassword = keystorePassword;
		}
	    
	    
	}
	/**
	 * 配置Https
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	    return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {
	            Ssl ssl = new Ssl();
	            //.keystore中包含服务器私钥和证书
	            ssl.setKeyStore(".keystore");
	            ssl.setKeyStorePassword("changeit");
	            container.setSsl(ssl);
	            container.setPort(8443);
	        }
	    };
	}
	/**
	 * 配置Http使其自动重定向到Https
	 * @param properties
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory(){
	    	 @Override
	            protected void postProcessContext(Context context) {
	                //SecurityConstraint必须存在，可以通过其为不同的URL设置不同的重定向策略。
	                SecurityConstraint securityConstraint = new SecurityConstraint();
	                securityConstraint.setUserConstraint("CONFIDENTIAL");
	                SecurityCollection collection = new SecurityCollection();
	                collection.addPattern("/*");
	                securityConstraint.addCollection(collection);
	                context.addConstraint(securityConstraint);
	            }
	    };
	    tomcat.addAdditionalTomcatConnectors(createSslConnector());
	    return tomcat;
	}
	public Connector createSslConnector() {
	    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	    connector.setScheme("http");
	    connector.setSecure(false);
	    connector.setPort(8080);
	    connector.setRedirectPort(8443);
//	    properties.configureConnector(connector);
	    return connector;
	}

}
