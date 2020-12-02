package co.edu.unicauca.oci.backend.apirest.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dependencias")
public class Dependence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEPENDENCIA")
    private Integer id;

    @Column(name = "DEPENDENCIA")
    private String dependenceName;

    public Dependence() {
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
     * @return String return the dependenceName
     */
    public String getDependenceName() {
        return dependenceName;
    }

    /**
     * @param dependenceName the dependenceName to set
     */
    public void setDependenceName(String dependenceName) {
        this.dependenceName = dependenceName;
    }

}