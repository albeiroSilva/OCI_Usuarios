package co.edu.unicauca.oci.backend.apirest.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.oci.backend.apirest.models.Role;
import co.edu.unicauca.oci.backend.apirest.models.User;
import co.edu.unicauca.oci.backend.apirest.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository objRepository;

    @Override
    public Iterable<User> findAllUsers() {
        return objRepository.findAll();
    }

    @Override
    public Optional<User> findByIdUser(Integer id) {
        return objRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return objRepository.save(user);
    }

    @Override
    public void deleteByIdUser(Integer id) {
        objRepository.deleteById(id);
    }

    @Override
    public List<Role> findAllRoles() {
        return objRepository.findAllRoles();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return objRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usuario = objRepository.findByUsername(username);

        if (usuario == null) {
            logger.error("Error en el login: no existe el usuario '" + username + "' en el sistema!");
            throw new UsernameNotFoundException(
                    "Error en el login: no existe el usuario '" + username + "' en el sistema!");
        }

        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .peek(authority -> logger.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(usuario.getUsername(), usuario.getPassword(),
                true, true, true, true, authorities);

    }

}