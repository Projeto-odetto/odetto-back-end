package com.odetto.repository;

import com.odetto.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByCpf(String cpf);
}
