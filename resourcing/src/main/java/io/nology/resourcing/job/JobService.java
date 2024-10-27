package io.nology.resourcing.job;

import java.util.List;
import java.util.Optional;

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

    public List<Job> findJobsByIsAssigned(Boolean assigned) {
        return this.jobRepository.findByIsAssigned(assigned);
    }

    public Optional<Job> findJobById(Long id) {
        return this.jobRepository.findById(id);
    }

    public Optional<Job> updateJobById(Long id, @Valid UpdateJobDTO data) throws Exception {
        Optional<Job> job = this.findJobById(id);

        if (job.isEmpty()) {
            return job;
        }

        Job foundJob = job.get();

        if (data.getName() != null) {
            foundJob.setName(data.getName());
        }

        if (data.getStartDate() != null) {
            foundJob.setStartDate(data.getStartDate());
        }

        if (data.getEndDate() != null) {
            foundJob.setEndDate(data.getEndDate());
        }

        if (data.getIsAssigned() != null) {
            foundJob.setIsAssigned(data.getIsAssigned());
        }

        return Optional.of(this.jobRepository.save(foundJob));
    }
}
