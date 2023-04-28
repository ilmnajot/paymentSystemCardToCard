package com.example.elbekjonumarovpaymenttask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "outcome")
public class Outcome {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Double commissionAmount;

    @ManyToOne(optional = false)
    private Card to;
    @ManyToOne(optional = false)
    private Card from;
}
