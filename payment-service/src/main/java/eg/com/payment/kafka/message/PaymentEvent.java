package eg.com.payment.kafka.message;
import eg.com.payment.model.PaymentStatus;

public class PaymentEvent {

	private Long orderId;
	private PaymentStatus paymentStatus;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
