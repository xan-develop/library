package com.fct.library.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fct.library.dto.UserDTO;
import com.fct.library.dto.user.CreateUserDTO;
import com.fct.library.dto.user.UpdateUserDTO;
import com.fct.library.dto.user.UserLoansYearDTO;
import com.fct.library.model.User;
import com.fct.library.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User usuario;
    private CreateUserDTO createUserDTO;
    private UpdateUserDTO updateUserDTO;

    @BeforeEach
    void setUp() {
       

        User usuario1 = new User();
        usuario1.setId(1L);
        usuario1.setName("Juan Pérez");
        usuario1.setEmail("juan@example.com");
        usuario1.setPhone("123456789");

        User usuario2 = new User();
        usuario2.setId(2L);
        usuario2.setName("María López");
        usuario2.setEmail("maria@example.com");
        usuario2.setPhone("987654321");

        User usuario3 = new User();
        usuario3.setId(3L);
        usuario3.setName("Carlos García");
        usuario3.setEmail("carlos@example.com");
        usuario3.setPhone("456123789");

        usuario = usuario1;
        
        // Crear DTOs para las pruebas
        createUserDTO = new CreateUserDTO();
        createUserDTO.setName("Juan Pérez");
        createUserDTO.setEmail("juan@example.com");
        createUserDTO.setPhone("123456789");
        
        updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setName("Nuevo Nombre");
        updateUserDTO.setEmail("nuevo@example.com");
        updateUserDTO.setPhone("123456789");

        lenient().when(userRepository.findAll()).thenReturn(List.of(usuario1, usuario2, usuario3));
        lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(usuario1));
    }

    @Test
    void testFindAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
       
        assertNotNull(users, "La lista de usuarios no debería ser null");
        assertFalse(users.isEmpty(), "La lista de usuarios no debería estar vacía");
        assertEquals(3, users.size(), "Debería haber exactamente 1 usuario");
        assertEquals("Juan Pérez", users.get(0).getName());

        // Verifica que findAll() fue llamado 1 vez
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserById_Exists() {

        Optional<UserDTO> result = userService.findUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("Juan Pérez", result.get().getName());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindUserById_NotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.findUserById(1L);

        assertFalse(result.isPresent());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveUser_Success() {
        when(userRepository.existsByEmail(createUserDTO.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(usuario);

        User savedUser = userService.saveUser(createUserDTO);

        assertNotNull(savedUser);
        assertEquals("Juan Pérez", savedUser.getName());

        verify(userRepository, times(1)).existsByEmail(createUserDTO.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSaveUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(createUserDTO.getEmail())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(createUserDTO));

        assertEquals("El email ya está registrado", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail(createUserDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(userRepository.existsByEmail("nuevo@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(usuario);

        Optional<User> result = userService.updateUser(1L, updateUserDTO);

        assertTrue(result.isPresent());
        assertEquals("Nuevo Nombre", result.get().getName());
        assertEquals("nuevo@example.com", result.get().getEmail());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).existsByEmail("nuevo@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_EmailAlreadyExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(userRepository.existsByEmail("nuevo@example.com")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(1L, updateUserDTO));

        assertEquals("El email ya está registrado", exception.getMessage());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).existsByEmail("nuevo@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testExistsByEmail() {
        when(userRepository.existsByEmail("juan@example.com")).thenReturn(true);

        boolean exists = userService.existsByEmail("juan@example.com");

        assertTrue(exists);

        verify(userRepository, times(1)).existsByEmail("juan@example.com");
    }

    @Test
    void testFindUserWithMoreLoansThisYear() {
        List<UserLoansYearDTO> mockList = List.of(new UserLoansYearDTO(Long.valueOf(1), "Juan Pérez", Long.valueOf(5)));
        when(userRepository.findUserWithMoreLoansThisYear()).thenReturn(mockList);

        List<UserLoansYearDTO> result = userService.findUserWithMoreLoansThisYear();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Juan Pérez", result.get(0).getName());

        verify(userRepository, times(1)).findUserWithMoreLoansThisYear();
    }
}
