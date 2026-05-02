package com.university.management.service;

import com.university.management.model.Admin;
import com.university.management.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Admin> search(String searchTerm) {
        return findAll(); // Simplified
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return adminRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Admin> authenticate(String username, String password) {
        Optional<Admin> admin = adminRepository.findByUsernameAndPassword(username, password);
        admin.ifPresent(a -> {
            a.recordLogin();  // Update lastLogin time — uses our OOP method
            adminRepository.save(a);
        });
        return admin;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}