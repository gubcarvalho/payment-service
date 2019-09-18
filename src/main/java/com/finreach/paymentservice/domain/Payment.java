package com.finreach.paymentservice.domain;

public class Payment {
	
	private String id;

	private Double amount;
    
	private PaymentState state;
	
	private String sourceAccountId;
    
	private String destinationAccountId;
    
	protected Payment() {
		super();
	}
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(String sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public String getDestinationAccountId() {
		return destinationAccountId;
	}

	public void setDestinationAccountId(String destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	public PaymentState getState() {
		return state;
	}

	public void setState(PaymentState state) {
		this.state = state;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return id;
    }
}
