package com.finreach.paymentservice.model;

import java.util.Date;

public class Transaction {

	private String id;
    
	private String account;
    
	private Double amount;
    
	private Date created;

	protected Transaction() {
		super();
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isEligibleForEstatistics(Date from, Integer seconds) {
        if (this.getAmount() <= 0 || from == null || this.getCreated() == null)
            return false;

        final long diff = from.toInstant().getEpochSecond() - this.getCreated().toInstant().getEpochSecond();
        return diff >= 0 && diff < seconds;
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
		Transaction other = (Transaction) obj;
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
