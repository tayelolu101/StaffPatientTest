package com.example.hospitalcasestudy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "Staff")
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name ="ID")
    Long id;

    @Column(name = "name")
    String name;

    @JsonIgnore
    @Column(name = "uuid")
    String uuid;

    @Column(name = "registration_date")
    @JsonProperty("registration_date")
    Timestamp registrationDate;
}
