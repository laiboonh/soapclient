package hello;

import javax.net.ssl.TrustManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

@Configuration
public class CountryConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("hello.wsdl");
		return marshaller;
	}

	@Bean
	public CountryClient countryClient(Jaxb2Marshaller marshaller) {
		CountryClient client = new CountryClient();
		client.setDefaultUri("http://localhost:8000/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);

		String username = "spike";
		String password = "spike123";

		HttpsUrlConnectionMessageSender messageSender = new BasicAuthHttpsConnectionMessageSender(username, password);
		messageSender.setTrustManagers(new TrustManager[]{new UnTrustworthyTrustManager()});

		// otherwise: java.security.cert.CertificateException: No name matching localhost found
		messageSender.setHostnameVerifier((hostname, sslSession) -> {
			if (hostname.equals("localhost")) {
				return true;
			}
			return false;
		});

		client.setMessageSender(messageSender);
		return client;
	}

}