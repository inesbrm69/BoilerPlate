package com.ynov.boilerplate.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.ynov.boilerplate.config.autoincrement.SequenceGeneratorService;
import com.ynov.boilerplate.entity.User;
import com.ynov.boilerplate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @InjectMocks
    private UserService underTest;

    @Test
    void testCreateUser() {
        assertNotNull(underTest.sequenceGeneratorService);
        // Arrange
        User user = new User();
        when(sequenceGeneratorService.getNextSequence("user_sequence")).thenReturn(1);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = underTest.createUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());

        // Verify that the save method was called with the user
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUser() {
        // Arrange
        List<User> mockUsers = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        List<User> result = underTest.getAllUser();

        // Assert
        assertEquals(mockUsers, result);
    }

    @Test
    void testFindUserById() {
        // Arrange
        int userId = 1;
        User mockUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        User result = underTest.findUserbyId(userId);

        // Assert
        assertEquals(mockUser, result);
    }

    @Test
    void testFindUserByIdAndFirstName() {
        // Arrange
        int userId = 1;
        String firstName = "John";
        User mockUser = new User();
        when(userRepository.findUserByIdAndFirstname(userId, firstName)).thenReturn(mockUser);

        // Act
        User result = underTest.findUserByIdAndFirstName(userId, firstName);

        // Assert
        assertEquals(mockUser, result);
    }

    @Test
    void testDeleteUserById() {
        // Arrange
        int userId = 1;

        // Act
        underTest.deleteUserById(userId);

        // Verify that the deleteById method was called with the correct ID
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        int userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstname("OldFirstName");
        existingUser.setLastname("OldLastName");
        existingUser.setEmail("old@example.com");

        User updatedUser = new User();
        updatedUser.setFirstname("NewFirstName");
        updatedUser.setLastname("NewLastName");
        updatedUser.setEmail("new@example.com");

        Optional<User> optionalUser = Optional.of(existingUser);
        when(userRepository.findById(userId)).thenReturn(optionalUser);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = underTest.updateUser(userId, updatedUser);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(updatedUser.getFirstname(), result.getFirstname());
        assertEquals(updatedUser.getLastname(), result.getLastname());
        assertEquals(updatedUser.getEmail(), result.getEmail());

        // Verify that save method was called with the updated user
        verify(userRepository, times(1)).save(result);
    }
}
