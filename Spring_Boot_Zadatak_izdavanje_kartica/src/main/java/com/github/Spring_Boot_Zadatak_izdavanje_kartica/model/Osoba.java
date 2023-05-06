package com.github.Spring_Boot_Zadatak_izdavanje_kartica.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Osobe")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Osoba {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String Ime;
    private String Prezime;
    private Long OIB;
    private String Status;

}
