package io.nology.resourcing.job;

// import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import jakarta.persistence.Temporal;
// import jakarta.persistence.TemporalType;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    // @Column
    // @Temporal(TemporalType.TIMESTAMP)
    // private Date startDate;

    // @Column
    // @Temporal(TemporalType.TIMESTAMP)
    // private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    // public Date getEndDate() {
    // return endDate;
    // }

    // public void setEndDate(Date endDate) {
    // this.endDate = endDate;
    // }
}
