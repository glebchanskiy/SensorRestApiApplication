package org.glebchanskiy.SensorRestAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "measurement")
@Getter
@Setter
@ToString
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Max(value = 100, message = "value greater than 100")
    @Min(value = -100, message = "value less than -100")
    @Column(name = "value", nullable = false)
    private Integer value;

    @Column(name = "raining")
    private Boolean raining;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor", referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private Sensor sensor;

    public Measurement () {}
}
