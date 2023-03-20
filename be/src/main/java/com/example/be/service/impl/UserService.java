package com.example.be.service.impl;

import com.example.be.dto.request.UpdateUserForm;
import com.example.be.model.user.Role;
import com.example.be.model.user.User;
import com.example.be.repository.user.IUserRepository;
import com.example.be.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }

    @Override
    public void updateUser(UpdateUserForm updateUserForm) {
        iUserRepository.updateUser(updateUserForm.getName(),
                updateUserForm.getPhoneNumber(),
                updateUserForm.getEmail(),
                updateUserForm.getAddress(),
                updateUserForm.getAge(),
                updateUserForm.getGender(),
                updateUserForm.getDateOfBirth(),
                updateUserForm.getAvatar(),
                updateUserForm.getUsername());
    }

    @Override
    public void changePassword(String password, String username) {
        iUserRepository.changePassword(password,username);
    }

    @Override
    public void save(User user) {
        iUserRepository.save(user.getName(),user.getUsername(), user.getEmail(), user.getPassword());
        User user1 = iUserRepository.findByUsername(user.getUsername()).orElse(null);
        for (Role x: user.getRoles()) {
            assert user1 != null;
            iUserRepository.insertRole(user1.getId(), x.getId());
        }
    }

    @Override
    public Boolean existsByUsername(String username) {
        for (int i = 0; i < iUserRepository.getAllUser().size(); i++) {
            if (iUserRepository.getAllUser().get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean existsByEmail(String email) {
        for (int i = 0; i < iUserRepository.getAllUser().size(); i++) {
            if (iUserRepository.getAllUser().get(i).getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        return iUserRepository.getAllUser();
    }

    @Override
    public List<User> findAllCustomer() {
        return iUserRepository.findAllCustomer();
    }

    @Override
    public List<User> findAllEmployee() {
        return iUserRepository.findAllEmployee();
    }

    @Override
    public List<User> findAllAdmin() {
        return iUserRepository.findAllAdmin();
    }

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Page<User> findAllCustomer(Pageable pageable, String name, String address) {
        return userRepository.findAllCustomer(pageable, name, address);
    }

    @Override
    public Optional<User> findCustomerById(Integer id) {
        return userRepository.findCustomerById(id);
    }

    @Override
    public Page<User> findAllCustomerNoParam(Pageable pageable) {
        return userRepository.findAllCustomerNoParam(pageable);
    }

    @Override
    public Page<User> findAll(String genderSearch, String ageSearch, Pageable pageable) {
        if (genderSearch.equals("Nam")) {
            return userRepository.findAllByGender(true, ageSearch, pageable);
        }
        if (genderSearch.equals("Nữ")) {
            return userRepository.findAllByGender(false, ageSearch, pageable);
        }
        return userRepository.findAll(genderSearch, ageSearch, pageable);
    }

    @Override
    public User userLogin(String username) {
        return iUserRepository.userLogin(username);
    }
}
