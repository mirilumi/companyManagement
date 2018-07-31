package com.project.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
//@Getter
//@Setter
public abstract class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdDate;

    @LastModifiedBy
    @Column
    private Long lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatedBy() {
        return createdBy;
    }


    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
    @PrePersist
    public void setCreatedDate() {
        this.createdDate = new Date();
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    @PreUpdate
    public void setLastModifiedDate() {
        this.lastModifiedDate = new Date();
    }
}
