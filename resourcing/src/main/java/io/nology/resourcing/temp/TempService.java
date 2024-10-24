package io.nology.resourcing.temp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TempService {
    @Autowired
    private TempRepository tempRepository;

    public Temp createTemp(@Valid CreateTempDTO data) throws Exception {
        Temp createdTemp = new Temp();
        createdTemp.setFirstName(data.getFirstName());
        createdTemp.setLastName(data.getLastName());

        return this.tempRepository.save(createdTemp);
    }

    public List<Temp> findAllTemps() {
        return this.tempRepository.findAll();
    }

    public Optional<Temp> findTempById(Long id) {
        return this.tempRepository.findById(id);
    }
}
