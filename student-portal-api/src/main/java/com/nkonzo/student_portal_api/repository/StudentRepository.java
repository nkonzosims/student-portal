package com.nkonzo.student_portal_api.repository;

import com.nkonzo.student_portal_api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);
    Optional<Student> findByStudentNumber(String studentNumber);
    Optional<Student> findByPhoneNumber(String phoneNumber);
    Optional<Student> findByPassportNumber(String passportNumber);
    Optional<Student> findByIdNumber(String idNumber);
}
