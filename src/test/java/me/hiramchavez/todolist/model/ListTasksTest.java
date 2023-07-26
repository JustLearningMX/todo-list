package me.hiramchavez.todolist.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ListTasksTest {

    @InjectMocks
    private ListTasks listTasks;

    private User user;
    private Task task;

    /*Creando objetos antes de cada test*/
    @BeforeEach
    void setUp() {
        // User
        user = new User();
        user.setId(1L);
        user.setFirstName("Hiram");
        user.setLastName("Chavez");
        user.setEmail("hiram.chavez@gmail.com");
        user.setPassword("123456");
        user.setActive(true);
        user.setRole(Role.USER);
        user.setListTasks(new ArrayList<>());

        //ListTasks
        listTasks.setId(1L);
        listTasks.setName("Compras del super");
        listTasks.setDescription("Lista de compras del super");
        listTasks.setActive(true);
        listTasks.setUser(user);
        listTasks.setTasks(new ArrayList<>());

        task = new Task();
        task.setId(1L);
        task.setTitle("Comprar leche");
        task.setDescription("2 litros de leche deslactosada");
        task.setExpirationDate(new Date());
        task.setState(State.PENDING);
        task.setPriority(Priority.MEDIUM);
        task.setListTasks(listTasks);
    }

    /**Prueba de constructor*/
    @Test
    void testConstructor() {
        assertNotNull(listTasks);
    }

    /**Prueba de setters*/
    @Test
    void testSetters() {
        assertEquals(1L, listTasks.getId());
        assertEquals("Compras del super", listTasks.getName());
    }

    /**Prueba de getters*/
    @Test
    public void testGetters() {
        assertEquals(1L, listTasks.getId());
        assertEquals("Compras del super", listTasks.getName());
        assertEquals("Lista de compras del super", listTasks.getDescription());
        assertEquals(true, listTasks.getActive());
        assertEquals(user, listTasks.getUser());
        assertEquals(0, listTasks.getTasks().size());
    }

    /**Prueba del metodo addListTasks*/
    @Test
    void testAddTask() {
        listTasks.addTask(task);

        // Verificar que la lista de listTasks del usuario contenga el objeto ListTasks
        assertTrue(listTasks.getTasks().contains(task));

        // Verificar que la relación bidireccional ListTasks-Task se haya establecido correctamente
        assertEquals(
          task,
          listTasks.getTasks()
            .stream()
            .filter(task -> task.getId().equals(1L)).findFirst().orElse(null));
    }

}