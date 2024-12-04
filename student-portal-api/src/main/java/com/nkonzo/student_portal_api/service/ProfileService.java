package com.nkonzo.student_portal_api.service;

import com.nkonzo.student_portal_api.common.exception.custom.StudentNotFoundException;
import com.nkonzo.student_portal_api.common.request.UpdateProfileRequestDto;
import com.nkonzo.student_portal_api.common.response.ProfileResponseDto;
import com.nkonzo.student_portal_api.entity.Student;
import com.nkonzo.student_portal_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProfileService {

    private final StudentRepository studentRepository;
    private final JwtService jwtService;

    public ProfileService(StudentRepository studentRepository,
                          JwtService jwtService) {
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
    }

    public ProfileResponseDto updateStudentProfile(UpdateProfileRequestDto dto, String token) {

        String email = jwtService.extractUsername(token.replace("Bearer ", ""));
        Optional<Student> optionalStudent = studentRepository.findByEmail(email);

        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException("Student profile not found");
        }

        Student student = optionalStudent.get();
        student.setPassword(dto.getPassword());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setUpdatedAt(LocalDateTime.now());

        var updatedStudent = studentRepository.save(student);

        return ProfileResponseDto.builder()
                .studentNumber(updatedStudent.getStudentNumber())
                .firstName(updatedStudent.getFirstName())
                .lastName(updatedStudent.getLastName())
                .phoneNumber(updatedStudent.getPhoneNumber())
                .email(updatedStudent.getEmail())
                .dateOfBirth(updatedStudent.getDateOfBirth())
                .build();
    }

    public ProfileResponseDto getStudent(String token) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));

        Optional<Student> optionalStudent = studentRepository.findByEmail(email);

        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException("Student profile not found");
        }

        Student student = optionalStudent.get();
        return ProfileResponseDto.builder()
                .studentNumber(student.getStudentNumber())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .dateOfBirth(student.getDateOfBirth())
                .build();
    }

}
