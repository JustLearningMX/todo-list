package me.hiramchavez.todolist.model;

import me.hiramchavez.todolist.dto.user.UserToUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @InjectMocks
    private User user;

    private ListTasks listTasks;

    /*Creando objetos antes de cada test*/
    @BeforeEach
    void setUp() {
        // User
        user.setId(1L);
        user.setFirstName("Hiram");
        user.setLastName("Chavez");
        user.setEmail("hiram.chavez@gmail.com");
        user.setPassword("123456");
        user.setActive(true);
        user.setRole(Role.USER);
        user.setListTasks(new ArrayList<>());

        //ListTasks
        listTasks = new ListTasks();
        listTasks.setId(1L);
        listTasks.setName("Lista de tareas");
        listTasks.setDescription("Lista de tareas de prueba");
        listTasks.setActive(true);
        listTasks.setUser(user);
        listTasks.setTasks(new ArrayList<>());
    }

    /**Prueba de constructor*/
    @Test
    void testConstructor() {
        assertNotNull(user);
    }

    /**Prueba de getters*/
    @Test
    void testGetters() {
        assertEquals(1L, user.getId());
        assertEquals("Hiram", user.getFirstName());
        assertEquals("Chavez", user.getLastName());
        assertEquals("hiram.chavez@gmail.com", user.getEmail());
        assertEquals("123456", user.getPassword());
        assertEquals(true, user.getActive());
        assertEquals(Role.USER, user.getRole());
        assertEquals(0, user.getListTasks().size());
    }

    /**Prueba de setters*/
    @Test
    void testSetters() {
        user.setId(2L);
        user.setFirstName("Gabriela");
        user.setLastName("Chavez");
        user.setEmail("gaby.chavez@gmail.com");
        user.setPassword("654321");
        user.setActive(true);
        user.setRole(Role.ADMIN);
        user.setListTasks(new ArrayList<>());

        assertEquals(2L, user.getId());
        assertEquals("Gabriela", user.getFirstName());
    }

    /**Prueba del metodo addListTasks*/
    @Test
    void testAddListTasks() {
        user.addListTasks(listTasks);

        // Verificar que la lista de listTasks del usuario contenga el objeto ListTasks
        assertTrue(user.getListTasks().contains(listTasks));

        // Verificar que la relación bidireccional se haya establecido correctamente
        assertEquals(user, listTasks.getUser());
    }

    /**Prueba del metodo getListTasksById*/
    @Test
    void testGetListTasksById() {
        user.addListTasks(listTasks);
        assertEquals(listTasks, user.getListTasksById(1L));
    }

    /*Prueba del método update*/
    @Test
    void testUpdate() {
        user.update(new UserToUpdateDto("Daniela", "Delgado"));
        assertEquals("Daniela", user.getFirstName());
        assertEquals("Delgado", user.getLastName());
    }
}