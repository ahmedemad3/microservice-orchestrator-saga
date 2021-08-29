package eg.com.orchestrator.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import eg.com.orchestrator.exception.ResourceNotFoundException;

@Component
public class RestClient {

	public String doPost(String url, Long orderId) throws ResourceNotFoundException {

		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

//			OrderRequest req = new OrderRequest();
//			req.setOrderId(orderId);

			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("orderId", orderId.toString());

			System.out.println("url ***** " + url);
			String response = restTemplate.postForObject(url, map, String.class);
			System.out.println("response *** " + response);
			return response;

		} catch (RestClientException e) {
			throw new ResourceNotFoundException("service unavailable");
		}

	}

}
