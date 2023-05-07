package com.github.Spring_Boot_Zadatak_izdavanje_kartica.repo;

import com.github.Spring_Boot_Zadatak_izdavanje_kartica.model.Osoba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OsobaRepo extends JpaRepository<Osoba, Long> {
    Optional<Osoba> findByOIB(Long OIB);
    void deleteByOIB(Long OIB);
}
