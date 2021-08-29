package eg.com.payment.service.impl;

import java.time.YearMonth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eg.com.payment.DateUtil;
import eg.com.payment.kafka.source.PaymentFailedSource;
import eg.com.payment.kafka.source.PaymentReceivedSource;
import eg.com.payment.model.CreditCardInfo;
import eg.com.payment.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImp implements PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	private PaymentReceivedSource paymentReceivedSource;

	@Autowired
	private PaymentFailedSource paymentFailedSource;

	@Override
	public void createPayment(Long orderId) {
		logger.info("create payement for order id : {} ", orderId);
		// get the customer credit card details
		CreditCardInfo creditCardInfo = getCustomerCreditCardInfo(orderId);
		Double totalOrderPrice = getTotalOrderPrice(orderId);

		// validate if total is more Than 100 money value
		// we should have system setting table with this threshold 100 and threshold of
		// 1500 for fraud user
		Double fraudThreshold = getFraudUserThreshold();
		Double moneyValueThreshold = getBasketMoneyValueThreshold();
		if (totalOrderPrice > moneyValueThreshold || totalOrderPrice > fraudThreshold) {
			logger.info("the total basket money value above 100 or order basket has more than 1500 money value");
			paymentFailedSource.publishPaymentFailedEvent(orderId);
		}

		// check the credit expiration date
		boolean isValidPayment = false;
		Long paymentId = null;
		if (isCreditCardExpirationValid(creditCardInfo)) {
			// doPaymentIntegration and deduct from the customer card
			paymentId = deductFromCustomerCard(creditCardInfo, totalOrderPrice);
			if (paymentId != null)
				isValidPayment = true;
		}
		if (isValidPayment) {
			// create payment record in db with order id , customer id , payment Transaction
			// id , total order price 
			// create transaction record in db
			paymentReceivedSource.publishPaymentSuccessEvent(orderId, paymentId);
			logger.info("Payment is received successfully");
		} else {
			paymentFailedSource.publishPaymentFailedEvent(orderId);
			logger.info("Payment is failed");
		}
	}

	private Double getBasketMoneyValueThreshold() {
		// should get the value from payment setting threshold table but i will return
		// it as static value
		return 100d;
	}

	private Double getFraudUserThreshold() {
		// should get the value from payment setting threshold table but i will return
		// it as static value
		return 1500d;
	}

	private Double getTotalOrderPrice(Long orderId) {
		// call order Service to calculate the Total Order price
		// assume that the service will return 12000
		return Double.valueOf(12000d);
	}

	private Long deductFromCustomerCard(CreditCardInfo creditCardInfo, Double totalOrderPrice) {
		// deduct totalOrderPrice from the customer card and integration payment
		// will check the customer has the total amount or not and return paymentId
		Long paymentId = 56545667689L;
		return paymentId;
	}

	private boolean isCreditCardExpirationValid(CreditCardInfo creditCardInfo) {
		YearMonth yearMonth = DateUtil.getValidYearMonthOfCard(creditCardInfo.getExpirationMonth(),
				creditCardInfo.getExpirationYear());
		return DateUtil.isCardExpirationValid(yearMonth);
	}

	private CreditCardInfo getCustomerCreditCardInfo(Long orderId) {
		// get customer credit card details
		CreditCardInfo cardInfo = new CreditCardInfo(1L, "8171999927660000", "737", "05", "2022");
		return cardInfo;
	}

}
