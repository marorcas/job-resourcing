package io.nology.resourcing.job;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;

public class UpdateJobDTO {
    @Pattern(regexp = ".*\\S.*", message = "Name cannot be empty")
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isAssigned;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate starDate) {
        this.startDate = starDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(Boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

}
