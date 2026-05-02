package com.university.management.service;

import com.university.management.model.Lecturer;
import com.university.management.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * =====================================================
 * LECTURER SERVICE IMPLEMENTATION
 * =====================================================
 * POLYMORPHISM: Implements LecturerService.
 * Same method names as DepartmentServiceImpl, but operates on Lecturer data.
 * This is exactly what polymorphism is — same interface, different behavior.
 * =====================================================
 */
@Service
@Transactional
public class LecturerServiceImpl implements LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public Lecturer save(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Lecturer> findById(Long id) {
        return lecturerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecturer> findAll() {
        return lecturerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        lecturerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecturer> search(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll();
        }
        return lecturerRepository.searchByName(searchTerm.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return lecturerRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecturer> findByDepartmentId(Long departmentId) {
        return lecturerRepository.findByDepartmentId(departmentId);
    }

    @Override
    public boolean existsByEmployeeId(String employeeId) {
        return lecturerRepository.existsByEmployeeId(employeeId);
    }
}