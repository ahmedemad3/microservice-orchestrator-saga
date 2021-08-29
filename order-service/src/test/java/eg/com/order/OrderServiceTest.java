package eg.com.order;

import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eg.com.order.dto.OrderProductDto;
import eg.com.order.dto.ProductDto;
import eg.com.order.model.Order;
import eg.com.order.repository.OrderRepository;
import eg.com.order.service.impl.OrderServiceImp;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);

	
	@InjectMocks
    OrderServiceImp orderService;
     
    @Mock
    private OrderRepository orderRepository;
 
    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
	
	
	@Test
	public void createOrder() {
		logger.info("OrderServiceTest : create order");
		Long customerId = 1L;
		List<OrderProductDto> orderProducts= initializeOrder();
		logger.info("orderProducts : " + orderProducts.toString());
		orderService.createOrder(orderProducts, customerId);
	    verify(orderRepository).save(Mockito.any(Order.class));
	}


	private List<OrderProductDto> initializeOrder() {
		logger.info("initializeOrder for create order");
		List<OrderProductDto> list = new ArrayList<OrderProductDto>();
		OrderProductDto dto = new OrderProductDto();
		ProductDto productDto = new ProductDto();
		productDto.setName("TV Show");
		productDto.setPrice(2000d);
		productDto.setId(2L);
		dto.setProductDto(productDto);
		dto.setQuantity(1);
		list.add(dto);
		return list;
	}


}
