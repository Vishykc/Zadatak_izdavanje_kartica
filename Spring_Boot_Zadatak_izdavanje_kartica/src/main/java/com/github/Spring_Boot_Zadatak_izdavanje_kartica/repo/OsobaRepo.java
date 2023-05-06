package com.github.Spring_Boot_Zadatak_izdavanje_kartica.repo;

import com.github.Spring_Boot_Zadatak_izdavanje_kartica.model.Osoba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OsobaRepo extends JpaRepository<Osoba, Long> {
}
