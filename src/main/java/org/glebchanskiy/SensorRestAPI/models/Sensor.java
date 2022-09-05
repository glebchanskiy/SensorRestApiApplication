package org.glebchanskiy.SensorRestAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "sensor")
@Getter
@Setter
@ToString
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @Size(min = 3, max = 40, message = "sensor name should be between 3 and 40 characters")
    private String name;

    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
//    @JsonManagedReference("sensor")
    private List<Measurement> measurements;

    public Sensor () {}
}
