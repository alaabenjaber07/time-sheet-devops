package tn.esprit.spring.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@TestMethodOrder(OrderAnnotation.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User addedUser = userServiceImpl.addUser(user);

        assertNotNull(addedUser);
        assertEquals(1L, addedUser.getId());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userServiceImpl.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals(1L, updatedUser.getId());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userServiceImpl.deleteUser("1");

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRetrieveUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User retrievedUser = userServiceImpl.retrieveUser("1");

        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getId());
    }
}
