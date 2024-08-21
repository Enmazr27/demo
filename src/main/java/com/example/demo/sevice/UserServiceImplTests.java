package com.example.demo.sevice;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User(1L, "John", "john_doe", "password", "john@example.com");
        User user2 = new User(2L, "Jane", "jane_doe", "password", "jane@example.com");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        assertEquals(2, userService.getALl().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        User user = new User(1L, "John", "john_doe", "password", "john@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertTrue(userService.getUserById(1L).isPresent());
        assertEquals("John", userService.getUserById(1L).get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveUser() {
        User user = new User(1L, "John", "john_doe", "password", "john@example.com");
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);
        assertEquals("John", savedUser.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User(1L, "John", "john_doe", "password", "john@example.com");
        User updatedUser = new User(1L, "John Doe", "john_doe_updated", "newpassword", "john.doe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        assertEquals("John Doe", result.getName());
        assertEquals("john_doe_updated", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
