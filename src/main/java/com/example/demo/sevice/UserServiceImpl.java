package com.example.demo.sevice;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Cacheable(value = "users")
    public List<User> getALl() {
        logger.info("Fetching all users");
        try {
            return repository.findAll();
        } catch (Exception e) {
            logger.error("Error occurred while fetching all users", e);
            throw e;
        }
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public Optional<User> getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        try {
            return repository.findById(id);
        } catch (Exception e) {
            logger.error("Error occurred while fetching user with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @CachePut(value = "users", key = "#user.id")
    public User saveUser(User user) {
        logger.info("Saving new user: {}", user.getUsername());
        try {
            return repository.save(user);
        } catch (Exception e) {
            logger.error("Error occurred while saving user: {}", user.getUsername(), e);
            throw e;
        }
    }

    @Override
    @CachePut(value = "users", key = "#id")
    public User updateUser(Long id, User updatedUser) {
        logger.info("Updating user with ID: {}", id);
        try {
            return repository.findById(id).map(user -> {
                user.setName(updatedUser.getName());
                user.setUsername(updatedUser.getUsername());
                user.setPassword(updatedUser.getPassword());
                user.setEmail(updatedUser.getEmail());
                logger.info("User updated successfully with ID: {}", id);
                return repository.save(user);
            }).orElseThrow(() -> {
                logger.error("User not found with ID: {}", id);
                return new RuntimeException("User not found");
            });
        } catch (Exception e) {
            logger.error("Error occurred while updating user with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error occurred while deleting user with ID: {}", id, e);
            throw e;
        }
    }
}
