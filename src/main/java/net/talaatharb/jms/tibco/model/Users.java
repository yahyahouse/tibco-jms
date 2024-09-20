package net.talaatharb.jms.tibco.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    private String id;
    private String name;
    private String password;
    private String email;
    private String role;
}
