package net.talaatharb.jms.tibco.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private String id;
    private String name;
    @OneToOne
    private Users users;
}
