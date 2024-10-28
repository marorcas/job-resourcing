package io.nology.resourcing.temp;

import jakarta.validation.constraints.Pattern;

public class UpdateTempDTO {
    @Pattern(regexp = ".*\\S.*", message = "First name cannot be empty")
    private String firstName;

    @Pattern(regexp = ".*\\S.*", message = "Last name cannot be empty")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
