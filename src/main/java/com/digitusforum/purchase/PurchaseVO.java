package com.digitusforum.purchase;

import java.time.ZonedDateTime;

public class PurchaseVO {
	private String purchaseId;
	private String userId;
	private String trainingId;
	private String stripeCheckoutSessionId;
	private String stripePaymentIntentId;
	private String status;
	private ZonedDateTime createdIn;
	private boolean deleted;
	private boolean hasPurchase;

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(String trainingId) {
		this.trainingId = trainingId;
	}

	public String getStripeCheckoutSessionId() {
		return stripeCheckoutSessionId;
	}

	public void setStripeCheckoutSessionId(String stripeCheckoutSessionId) {
		this.stripeCheckoutSessionId = stripeCheckoutSessionId;
	}

	public String getStripePaymentIntentId() {
		return stripePaymentIntentId;
	}

	public void setStripePaymentIntentId(String stripePaymentIntentId) {
		this.stripePaymentIntentId = stripePaymentIntentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ZonedDateTime getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(ZonedDateTime createdIn) {
		this.createdIn = createdIn;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isHasPurchase() {
		return hasPurchase;
	}

	public void setHasPurchase(boolean hasPurchase) {
		this.hasPurchase = hasPurchase;
	}

}
