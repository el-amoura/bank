package com.fast.bank.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.lang.NonNull;

@ApiModel
public class UserRegistrationDTO {

    @ApiModelProperty(required = true)
    @NonNull
    private String identificationNumber;

    @ApiModelProperty
    @NonNull
    private String firstName;

    @ApiModelProperty(required = true)
    @NonNull
    private String lastName;

    @ApiModelProperty
    private String middleName;

    @ApiModelProperty(required = true)
    @NonNull
    private String email;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String identificationNumber, String firstName, String lastName, String middleName, String email) {
        this.identificationNumber = identificationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRegistrationDTO that = (UserRegistrationDTO) o;

        if (identificationNumber != null ? !identificationNumber.equals(that.identificationNumber) : that.identificationNumber != null)
            return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        int result = identificationNumber != null ? identificationNumber.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
