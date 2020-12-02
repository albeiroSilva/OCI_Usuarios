package co.edu.unicauca.oci.backend.apirest.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "cargos")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARGO")
    private Integer id;

    @Column(name = "NOMBRE_CARGO")
    private String positioName;

    @ManyToOne
    @JoinColumn(name = "ID_DEPENDENCIA")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Dependence objDependencce;

    public Position() {
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
     * @return String return the positioName
     */
    public String getPositioName() {
        return positioName;
    }

    /**
     * @param positioName the positioName to set
     */
    public void setPositioName(String positioName) {
        this.positioName = positioName;
    }

    /**
     * @return Dependence return the objDependencce
     */
    public Dependence getObjDependencce() {
        return objDependencce;
    }

    /**
     * @param objDependencce the objDependencce to set
     */
    public void setObjDependencce(Dependence objDependencce) {
        this.objDependencce = objDependencce;
    }

}