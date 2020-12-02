package co.edu.unicauca.oci.backend.apirest.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "personas")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSONA")
    private Integer id;

    @NotEmpty
    @Column(name = "NOMBRES", nullable = false)
    private String names;

    @NotEmpty
    @Column(name = "APELLIDOS", nullable = false)
    private String surnames;

    @Column(name = "IDENTIFICACION", nullable = false, unique = true)
    private Integer idPerson;

    @NotEmpty
    @Column(name = "TIPO_DOCUMENTO", nullable = false)
    private String documentType;

    @NotEmpty
    @Email
    @Column(name = "EMAIL", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "ID_CARGO")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Position objPosition;

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(final String names) {
        this.names = names;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(final String surnames) {
        this.surnames = surnames;
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(final Integer idPerson) {
        this.idPerson = idPerson;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(final String documentType) {
        this.documentType = documentType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return Position return the objPosition
     */
    public Position getObjPosition() {
        return objPosition;
    }

    /**
     * @param objPosition the objPosition to set
     */
    public void setObjPosition(Position objPosition) {
        this.objPosition = objPosition;
    }

}