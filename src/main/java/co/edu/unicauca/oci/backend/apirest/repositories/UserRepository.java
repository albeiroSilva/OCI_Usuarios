package co.edu.unicauca.oci.backend.apirest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.oci.backend.apirest.models.Role;
import co.edu.unicauca.oci.backend.apirest.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select r from Role r")
    public List<Role> findAllRoles();

    public User findByUsername(String userName);

    @Query("select u from User u where u.username=?1")
    public User findByUsername2(String username);

}