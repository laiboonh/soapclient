package hello;

import hello.wsdl.GetCountryRequest;
import hello.wsdl.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class CountryClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(CountryClient.class);

	public GetCountryResponse getCountryResponse(String countryName) {

		GetCountryRequest request = new GetCountryRequest();
		request.setName(countryName);

		log.info("Requesting country information for " + countryName);

		GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://localhost:8000/ws", request);
		return response;
	}

}