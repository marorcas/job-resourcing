package io.nology.resourcing.job;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<Job> createJob(@Valid @RequestBody CreateJobDTO data) throws Exception {
        Job createdJob = this.jobService.createJob(data);
        return new ResponseEntity<Job>(createdJob, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Job>> findJobs(@RequestParam(value = "assigned", required = false) Boolean assigned) {
        List<Job> jobs;

        if (assigned != null) {
            jobs = this.jobService.findJobsByIsAssigned(assigned);
        } else {
            jobs = this.jobService.findAllJobs();
        }

        return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable Long id) throws Exception {
        Optional<Job> job = this.jobService.findJobById(id);
        Job foundJob = job.orElseThrow();
        return new ResponseEntity<Job>(foundJob, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Job> updateJobById(@PathVariable Long id, @Valid @RequestBody UpdateJobDTO data)
            throws Exception {
        Optional<Job> job = this.jobService.updateJobById(id, data);
        Job foundJob = job.orElseThrow();
        return new ResponseEntity<Job>(foundJob, HttpStatus.OK);
    }
}
