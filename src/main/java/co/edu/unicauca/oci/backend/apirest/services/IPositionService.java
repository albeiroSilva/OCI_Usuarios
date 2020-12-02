package co.edu.unicauca.oci.backend.apirest.services;

import java.util.List;

import co.edu.unicauca.oci.backend.apirest.models.Dependence;
import co.edu.unicauca.oci.backend.apirest.models.Position;

public interface IPositionService {

    public Iterable<Position> findAllPosition();

    public List<Dependence> findAllDependences();

}