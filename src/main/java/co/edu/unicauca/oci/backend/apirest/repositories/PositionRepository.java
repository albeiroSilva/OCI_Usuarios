package co.edu.unicauca.oci.backend.apirest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.oci.backend.apirest.models.Dependence;
import co.edu.unicauca.oci.backend.apirest.models.Position;

public interface PositionRepository extends CrudRepository<Position, Integer> {

    @Query("select d from Dependence d")
    public List<Dependence> findAllDependences();
}