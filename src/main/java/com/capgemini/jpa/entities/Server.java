package com.capgemini.jpa.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update server set is_active=false where id=?")
@Where(clause = "is_active IS NULL OR is_active=true")
public class Server {

    @Id
    @SequenceGenerator(name = "SERVER_ID_GENERATOR", sequenceName = "SERVER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVER_ID_GENERATOR")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdateDate;

    @Version
    @Column
    private Long version;

    @Column(nullable = false)
    private Boolean isActive;

    public Server(String name, String ip) {
        super();
        this.name = name;
        this.ip = ip;
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.lastUpdateDate = now;
        isActive = true;
    }
    @PreUpdate
    public void updateLastUpdateDate() {
        this.lastUpdateDate = LocalDateTime.now();
    }
}
