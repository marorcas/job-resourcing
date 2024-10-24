package io.nology.resourcing.temp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("temps")
public class TempController {
    @Autowired
    private TempService tempService;

    @PostMapping
    public ResponseEntity<Temp> createTemp(@Valid @RequestBody CreateTempDTO data) throws Exception {
        Temp createdTemp = this.tempService.createTemp(data);
        return new ResponseEntity<Temp>(createdTemp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Temp>> findAllTemps() {
        List<Temp> allTemps = this.tempService.findAllTemps();
        return new ResponseEntity<List<Temp>>(allTemps, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Temp> findTempById(@PathVariable Long id) throws Exception {
        Optional<Temp> temp = this.tempService.findTempById(id);
        Temp foundTemp = temp.orElseThrow();
        return new ResponseEntity<Temp>(foundTemp, HttpStatus.OK);
    }
}
