package io.nology.resourcing.job;

// import java.util.Date;

import jakarta.validation.constraints.NotBlank;

public class CreateJobDTO {
    @NotBlank
    private String name;

    // @NotBlank
    // private Date startDate;

    // @NotBlank
    // private Date enDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public Date getStartDate() {
    // return startDate;
    // }

    // public void setStartDate(Date startDate) {
    // this.startDate = startDate;
    // }

    // public Date getEnDate() {
    // return enDate;
    // }

    // public void setEnDate(Date enDate) {
    // this.enDate = enDate;
    // }
}
