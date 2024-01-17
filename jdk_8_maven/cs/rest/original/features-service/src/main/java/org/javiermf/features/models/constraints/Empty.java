package org.javiermf.features.models.constraints;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Empty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
