package io.nology.resourcing.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public Job createJob(@Valid CreateJobDTO data) throws Exception {
        Job createdJob = new Job();
        createdJob.setName(data.getName());
        createdJob.setStartDate(data.getStartDate());
        createdJob.setEndDate(data.getEndDate());

        return this.jobRepository.save(createdJob);
    }

    public List<Job> findAllJobs() {
        return this.jobRepository.findAll();
    }
}
