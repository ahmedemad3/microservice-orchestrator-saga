package eg.com.order.dto;

public class OrderProductDto {

	private ProductDto productDto;
	private Integer quantity;

	public OrderProductDto() {
		// TODO Auto-generated constructor stub
	}

	public OrderProductDto(ProductDto productDto, Integer quantity) {
		super();
		this.productDto = productDto;
		this.quantity = quantity;
	}

	public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderProductDto [productDto=" + productDto + ", quantity=" + quantity + "]";
	}

}
