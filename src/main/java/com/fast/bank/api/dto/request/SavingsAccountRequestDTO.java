package com.fast.bank.api.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.lang.NonNull;

@ApiModel
public class SavingsAccountRequestDTO {

    @ApiModelProperty(required = true)
    @NonNull
    private Long userId;

    @ApiModelProperty
    private double balance;

    @ApiModelProperty
    private double interestRate;

    public SavingsAccountRequestDTO() {
    }

    public SavingsAccountRequestDTO(Long userId, double balance, double interestRate) {
        this.userId = userId;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public Long getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavingsAccountRequestDTO that = (SavingsAccountRequestDTO) o;

        if (Double.compare(that.balance, balance) != 0) return false;
        if (Double.compare(that.interestRate, interestRate) != 0) return false;
        return userId != null ? userId.equals(that.userId) : that.userId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userId != null ? userId.hashCode() : 0;
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(interestRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
