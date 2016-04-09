package com.mimiczo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mimiczo on 15. 12. 17..
 */
@Entity
@Data
public class Test {

        @Id
        @GeneratedValue
        @Column(name = "TEST_ID")
        private Long id;

        @Column(nullable = false, length=20)
        private String name;

        @Temporal(TemporalType.TIMESTAMP)
        private Date createDate;
        @PrePersist
        public void prePersist() {
                createDate = new Date();
        }
}