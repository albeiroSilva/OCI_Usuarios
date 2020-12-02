package co.edu.unicauca.oci.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.oci.backend.apirest.models.Dependence;
import co.edu.unicauca.oci.backend.apirest.models.Position;
import co.edu.unicauca.oci.backend.apirest.repositories.PositionRepository;

@Service
public class PositionServiceImpl implements IPositionService {

    @Autowired
    private PositionRepository objPositionRepository;

    @Override
    public Iterable<Position> findAllPosition() {

        return objPositionRepository.findAll();
    }

    @Override
    public List<Dependence> findAllDependences() {

        return objPositionRepository.findAllDependences();
    }

}