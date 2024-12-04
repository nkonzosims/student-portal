package com.nkonzo.student_portal_api.service;

import com.nkonzo.student_portal_api.common.exception.custom.InvalidCredentialsException;
import com.nkonzo.student_portal_api.common.exception.custom.StudentAlreadyExistException;
import com.nkonzo.student_portal_api.common.exception.custom.StudentNotFoundException;
import com.nkonzo.student_portal_api.common.exception.custom.StudentNotVerifiedException;
import com.nkonzo.student_portal_api.common.request.LoginStudentRequestDto;
import com.nkonzo.student_portal_api.common.request.RegisterStudentDto;
import com.nkonzo.student_portal_api.common.response.RegisterStudentResponseDto;
import com.nkonzo.student_portal_api.entity.Student;
import com.nkonzo.student_portal_api.repository.StudentRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;
    private final JavaMailSender emailSender;

    public AuthenticationService(PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 StudentRepository studentRepository,
                                 JavaMailSender emailSender
                                 ) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.studentRepository = studentRepository;
        this.emailSender = emailSender;
    }

    public RegisterStudentResponseDto register(RegisterStudentDto requestDto) {

        System.out.println("Request dto: " + requestDto.getPassword() + " " + requestDto.getEmail());
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Student student = Student.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .studentNumber(generateStudentNumber())
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .dateOfBirth(requestDto.getDateOfBirth())
                .phoneNumber(requestDto.getPhoneNumber())
                .verificationCode(generateVerificationCode())
                .isVerified(false)
                .verificationCodeExpiresAt(LocalDateTime.now().plusMinutes(10))
                .passportNumber(requestDto.getPassportNumber())
                .idNumber(requestDto.getIdNumber())
                .build();

        var savedStudent = studentRepository.save(student);

        System.out.println("Saved student: ***" + savedStudent);

        try {
            sendVerificationEmail(student.getEmail(), "Verify your email", "Your verification code is: " + student.getVerificationCode());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

       return RegisterStudentResponseDto.builder()
                .id(savedStudent.getId())
                .studentNumber(student.getStudentNumber())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .idNumber(student.getIdNumber())
                .passportNumber(student.getPassportNumber())
                .dateOfBirth(student.getDateOfBirth())
                .build();
    }

    public Student authenticateStudent(LoginStudentRequestDto dto) {
        Student student = studentRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        if (!student.isEnabled()) {
            throw new StudentNotVerifiedException("Account not verified. Please verify your account");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return student;
    }


    public void verifyEmail(String email, String verificationCode) {

        Optional<Student> studentOptional = studentRepository.findByEmail(email);

        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found!");
        }

        Student student = studentOptional.get();

        if (student.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification code has expired");
        }

        if (student.getVerificationCode().equals(verificationCode)) {
            student.setVerified(true);
            student.setVerificationCode(null);
            studentRepository.save(student);
        } else {
            throw new RuntimeException("Invalid verification code!");
        }
    }


    public String generateStudentNumber() {

        String yearPrefix = String.valueOf(LocalDate.now().getYear()).substring(2);
        String numericUUID = UUID.randomUUID().toString().replaceAll("\\D", "");
        String uniquePart = numericUUID.substring(0, 7);
        return yearPrefix + uniquePart;
    }

    private void sendVerificationEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        emailSender.send(message);
    }

    public void resendVerificationCode(String email) {
        Optional<Student> optionalStudent = studentRepository.findByEmail(email);

        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }

        Student student = optionalStudent.get();

        if (student.isEnabled()) {
            throw new StudentAlreadyExistException("Account has already been verified");
        }

        student.setVerificationCode(generateVerificationCode());
        student.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(10));

        try {
            sendVerificationEmail(student.getEmail(), "Verify your email", "Your verification code is: " + student.getVerificationCode());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }



}
