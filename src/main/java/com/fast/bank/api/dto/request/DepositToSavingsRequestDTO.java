package com.fast.bank.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DepositToSavingsRequestDTO {

    @ApiModelProperty(required = true)
    private Long userId;

    @ApiModelProperty(required = true)
    private double amount;

    public DepositToSavingsRequestDTO() {
    }

    public DepositToSavingsRequestDTO(Long userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepositToSavingsRequestDTO that = (DepositToSavingsRequestDTO) o;

        if (Double.compare(that.amount, amount) != 0) return false;
        return userId != null ? userId.equals(that.userId) : that.userId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userId != null ? userId.hashCode() : 0;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
