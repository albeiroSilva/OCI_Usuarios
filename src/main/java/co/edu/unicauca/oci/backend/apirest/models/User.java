package co.edu.unicauca.oci.backend.apirest.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer id;

    @NotEmpty
    @Column(name = "USUARIO", nullable = false, unique = true)
    private String username;

    @NotEmpty
    @Column(name = "PASSWORD", nullable = false, unique = true)
    private String password;

    @ManyToOne
    @JoinColumn(name = "ID_ROL")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Role objRole;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "ID_PERSONA")
    private Person objPerson;

    @Transient
    private List<Role> roles;

    // @JsonIgnoreProperties(value = { "objUser" }, allowSetters = true)
    // @OneToMany(mappedBy = "objUser", fetch = FetchType.LAZY, orphanRemoval =
    // true)
    // private List<Login> logins;

    public User() {
        this.roles = new ArrayList<>();

    }

    /**
     * @return Integer return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return String return the userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Person return the objPerson
     */
    public Person getObjPerson() {
        return objPerson;
    }

    /**
     * @param objPerson the objPerson to set
     */
    public void setObjPerson(Person objPerson) {
        this.objPerson = objPerson;
    }

    /**
     * @return Role return the objRole
     */
    public Role getObjRole() {
        return objRole;
    }

    /**
     * @param objRole the objRole to set
     */
    public void setObjRole(Role objRole) {
        this.objRole = objRole;
    }

    /**
     * @return List<Login> return the logins
     */
    // public List<Login> getLogins() {
    // return logins;
    // }

    // /**
    // * @param logins the logins to set
    // */
    // public void setLogins(List<Login> logins) {
    // this.logins = logins;
    // }

    // public void addLogin(Login login) {
    // this.logins.add(login);
    // login.setObjUser(this);
    // }

    /**
     * @return List<Role> return the roles
     */
    public List<Role> getRoles() {
        this.roles.add(objRole);
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}