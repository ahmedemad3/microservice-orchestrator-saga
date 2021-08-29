package eg.com.payment.model;

public class CreditCardInfo {

	private Long customerId;
	private String cardNumber;
	private String cvNumber;
	private String expirationMonth;
	private String expirationYear;

	public CreditCardInfo() {
		// TODO Auto-generated constructor stub
	}

	public CreditCardInfo(Long customerId, String cardNumber, String cvNumber, String expirationMonth,
			String expirationYear) {
		super();
		this.customerId = customerId;
		this.cardNumber = cardNumber;
		this.cvNumber = cvNumber;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCvNumber() {
		return cvNumber;
	}

	public void setCvNumber(String cvNumber) {
		this.cvNumber = cvNumber;
	}

	public String getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public String getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

}
