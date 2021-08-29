package eg.com.payment.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long id;
	@Column(name = "order_id", nullable = false)
	private Long orderId;
	@Column(name = "customer_id", nullable = false)
	private Long customerId;
	@Column(name = "total_order_price", nullable = false)
	private Double totalOderPrice;
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "created_on", nullable = false)
	private LocalDate createdOn;
	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
	@JsonManagedReference
	private Transaction transaction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Double getTotalOderPrice() {
		return totalOderPrice;
	}

	public void setTotalOderPrice(Double totalOderPrice) {
		this.totalOderPrice = totalOderPrice;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", orderId=" + orderId + ", customerId=" + customerId + ", totalOderPrice="
				+ totalOderPrice + ", createdOn=" + createdOn + ", createdBy=" + createdBy + "]";
	}

}
