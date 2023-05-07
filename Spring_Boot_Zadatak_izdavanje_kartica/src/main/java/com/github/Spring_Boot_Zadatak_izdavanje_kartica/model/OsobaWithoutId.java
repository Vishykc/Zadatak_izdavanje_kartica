package com.github.Spring_Boot_Zadatak_izdavanje_kartica.model;

import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OsobaWithoutId {
    private String Ime;

    private String Prezime;

    private Long OIB;

    private String Status;
}
