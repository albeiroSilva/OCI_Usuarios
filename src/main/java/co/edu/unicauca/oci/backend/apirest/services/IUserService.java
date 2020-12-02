package co.edu.unicauca.oci.backend.apirest.services;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.oci.backend.apirest.models.Role;
import co.edu.unicauca.oci.backend.apirest.models.User;

public interface IUserService {

    public Iterable<User> findAllUsers();

    public Optional<User> findByIdUser(Integer id);

    public User saveUser(User user);

    public void deleteByIdUser(Integer id);

    public List<Role> findAllRoles();

    public User findByUsername(String username);

}