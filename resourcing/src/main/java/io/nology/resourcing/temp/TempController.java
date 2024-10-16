package io.nology.resourcing.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

}
