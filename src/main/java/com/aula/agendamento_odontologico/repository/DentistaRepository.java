package com.aula.agendamento_odontologico.repository;

import com.aula.agendamento_odontologico.model.Dentista;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
    Page<Dentista> findAllByAtivoTrue(Pageable pageable);
}
