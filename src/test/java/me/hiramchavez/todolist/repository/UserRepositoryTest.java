package me.hiramchavez.todolist.repository;

import me.hiramchavez.todolist.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindByEmailAndActiveTrue() {
        String userEmail = "test@example.com";
        User userToFind = new User();
        userToFind.setId(1L);
        userToFind.setFirstName("John");
        userToFind.setLastName("Doe");
        userToFind.setEmail(userEmail);
        userToFind.setActive(true);

        // Configuración del comportamiento simulado del repositorio
        when(userRepository.findByEmailAndActiveTrue(userEmail)).thenReturn(userToFind);

        // Llamada al método del repositorio que se está probando
        User foundUser = (User) userRepository.findByEmailAndActiveTrue(userEmail);

        // Verificar que el resultado no sea nulo
        assertNotNull(foundUser);

        // Verificar que el resultado es el mismo que el objeto de prueba
        assertEquals(1L, foundUser.getId());
        assertEquals("John", foundUser.getFirstName());
        assertEquals("Doe", foundUser.getLastName());
        assertEquals(userEmail, foundUser.getEmail());
        assertTrue(foundUser.getActive());

        // Verificar que el método del repositorio se haya llamado con los parámetros correctos
        verify(userRepository, times(1)).findByEmailAndActiveTrue(userEmail);
    }

    @Test
    void testExistsByEmailAndActiveIsTrue() {
        String userEmail = "test@example.com";

        // Configuración del comportamiento simulado del repositorio
        when(userRepository.existsByEmailAndActiveIsTrue(userEmail)).thenReturn(true);

        // Llamada al método del repositorio que se está probando
        boolean userExists = userRepository.existsByEmailAndActiveIsTrue(userEmail);

        // Verificar que el resultado es verdadero (true)
        assertTrue(userExists);

        // Verificar que el método del repositorio se haya llamado con los parámetros correctos
        verify(userRepository, times(1)).existsByEmailAndActiveIsTrue(userEmail);
    }
}
