package co.edu.unicauca.oci.backend.apirest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.oci.backend.apirest.models.User;
import co.edu.unicauca.oci.backend.apirest.services.IMailService;
import co.edu.unicauca.oci.backend.apirest.services.IUserService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private IUserService serviceUser;

    @Autowired
    private IMailService serviceMail;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @PostMapping("/saveUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {

        user.setUserName(user.getObjPerson().getEmail());

        String password = user.getObjPerson().getIdPerson().toString();
        String passwordEncode = this.passwordEncoder.encode(password);

        user.setPassword(passwordEncode);

        Map<String, Object> response = new HashMap<>();

        User objUser;

        if (result.hasErrors()) {

            List<String> listaErrores = new ArrayList<>();

            for (FieldError error : result.getFieldErrors()) {
                listaErrores.add("El campo '" + error.getField() + "‘ " + error.getDefaultMessage());
            }

            response.put("errors", listaErrores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        }

        try {
            objUser = this.serviceUser.saveUser(user);

            // ennviar correo con la contraseña
            String userMail = objUser.getObjPerson().getEmail();

            this.serviceMail.sendMail(userMail,
                    "Estimado usuario: " + objUser.getObjPerson().getNames() + "\n\nSe le ha registrado con el rol de: "
                            + objUser.getObjRole().getRoleName()
                            + "\n\nPara ingresar al sistema utilice los siguientes credenciales:" + "\n\nUsuario = "
                            + objUser.getUsername() + "\nContraseña = " + password);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la inserción en la base de datos");
            response.put("error", e.getMessage() + "" + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<User>(objUser, HttpStatus.OK);

    }

    @GetMapping("/users")
    public ResponseEntity<?> showUsers() {
        return ResponseEntity.ok(this.serviceUser.findAllUsers());

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Integer id) {

        User user = null;
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<User> o = this.serviceUser.findByIdUser(id);
            if (o.isPresent()) {
                user = o.get();
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + "" + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (user == null) {
            response.put("mensaje", "El usuario ID: " + id + " no existe en la base de datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        // mirar si el cliente existe con Optional.ispresent

        try {
            this.serviceUser.deleteByIdUser(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido eliminado con éxito!");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Integer id, BindingResult result) {
        Optional<User> o = this.serviceUser.findByIdUser(id);
        User currentUser = o.get();

        User updateUser = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> listaErrores = new ArrayList<>();

            for (FieldError error : result.getFieldErrors()) {
                listaErrores.add("El campo '" + error.getField() + "‘ " + error.getDefaultMessage());
            }

            response.put("errors", listaErrores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        }

        if (currentUser == null) {
            response.put("mensaje", "Error: no se pudo editar, el usuario ID: "
                    .concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            currentUser.setUserName(user.getObjPerson().getEmail());
            currentUser.setObjPerson(user.getObjPerson());
            currentUser.setObjRole(user.getObjRole());

            updateUser = this.serviceUser.saveUser(currentUser);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el usuario en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido actualizado con éxito!");
        response.put("user", updateUser);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(serviceUser.findAllRoles());
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestParam("newPassword") String newPassword) {

        Optional<User> o = this.serviceUser.findByIdUser(id);
        User currentUser = o.get();

        User updateUser = null;

        Map<String, Object> response = new HashMap<>();

        // if (result.hasErrors()) {

        // List<String> listaErrores = new ArrayList<>();

        // for (FieldError error : result.getFieldErrors()) {
        // listaErrores.add("El campo '" + error.getField() + "‘ " +
        // error.getDefaultMessage());
        // }

        // response.put("errors", listaErrores);
        // return new ResponseEntity<Map<String, Object>>(response,
        // HttpStatus.BAD_REQUEST);

        // }

        if (currentUser == null) {
            response.put("mensaje", "Error: no se pudo editar, el usuario ID: "
                    .concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            String passwordEncode = this.passwordEncoder.encode(newPassword);

            currentUser.setPassword(passwordEncode);

            updateUser = this.serviceUser.saveUser(currentUser);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar la contraseña en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Contraseña actualizada con éxito!");
        response.put("user", updateUser);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}