package eg.com.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import eg.com.order.dto.OrderProductDto;
import eg.com.order.dto.ProductDto;

public class OrderControllerIntegrationTest extends AbstractTest {

	private static final Logger logger = LoggerFactory.getLogger(OrderControllerIntegrationTest.class);

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void createOrder() throws Exception {
		logger.info("Test : create order");
		Long customerId = 1L;
		String uri = "/api/orders/save/" + customerId;
		List<OrderProductDto> orderProductDto = initializeOrderRequest();
		String inputJson = super.mapToJson(orderProductDto);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		logger.info("content : " + mvcResult.getResponse().getContentAsString());
		String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		assertNotNull(content, "Order is created successfully");
	}

	private List<OrderProductDto> initializeOrderRequest() {
		logger.info("initializeOrderRequest for create order");
		List<OrderProductDto> list = new ArrayList<OrderProductDto>();
		OrderProductDto dto = new OrderProductDto();
		ProductDto productDto = new ProductDto();
		productDto.setName("TV");
		productDto.setPrice(1000d);
		productDto.setId(1L);
		dto.setProductDto(productDto);
		dto.setQuantity(1);
		list.add(dto);
		return list;
	}

}
