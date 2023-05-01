package com.pragma.powerup.usermicroservice.domain.model;

/**
 * @Author Jhoan Perez
 * Role POJO class for business logic
 */
public class RoleModel {

    /**
     * Role class attributes
     */
    private Long id;
    private String roleName;
    private String description;

    /**
     * Empty constructor
     */
    public RoleModel() {
    }

    /**
     * Constructor with all parameters
     * @param id is the identifier
     * @param roleName is the name
     * @param description is the description
     */
    public RoleModel(Long id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
    }

    /**
     * getter and setter methods of the class
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
