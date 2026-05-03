package com.university.management.service;

import com.university.management.model.Student;
import com.university.management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student save(Student student) {
        // Auto-generate student ID if not provided
        if (student.getStudentId() == null || student.getStudentId().isEmpty()) {
            long count = studentRepository.count();
            student.setStudentId("STU" + String.format("%04d", count + 1));
        }
        return studentRepository.save(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> search(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll();
        }
        return studentRepository.searchStudents(searchTerm.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return studentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    public boolean existsByStudentId(String studentId) {
        return studentRepository.existsByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> authenticate(String email, String password) {
        return studentRepository.findByEmailAndPassword(email, password);
    }
}