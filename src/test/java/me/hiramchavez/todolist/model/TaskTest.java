package me.hiramchavez.todolist.model;

import static org.junit.jupiter.api.Assertions.*;

import me.hiramchavez.todolist.dto.task.TaskBodyResDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Date;

import static org.mockito.Mockito.*;

class TaskTest {

    private Task task;
    private ListTasks listTasks;

    @BeforeEach
    void setUp() {
        task = Task.builder()
          .id(1L)
          .title("Task 1")
          .description("Description of Task 1")
          .expirationDate(new Date())
          .state(State.PENDING)
          .priority(Priority.HIGH)
          .listTasks(mock(ListTasks.class))
          .build();

        listTasks = new ListTasks();

    }

    @Test
    void testUpdate() {
        // Given
        TaskBodyResDto taskBodyReqDto = new TaskBodyResDto(
          1L,
            "Updated Task",
            "Updated description",
            new Date(),
            State.COMPLETED,
            Priority.LOW
        );

        // When
        task.update(taskBodyReqDto);

        // Then
        assertEquals("Updated Task", task.getTitle());
        assertEquals("Updated description", task.getDescription());
        assertEquals(State.COMPLETED, task.getState());
        assertEquals(Priority.LOW, task.getPriority());
    }

    @Test
    void testManyToOneRelationship() {
        // Asignar la relación @ManyToOne en la tarea
        task.setListTasks(listTasks);
        listTasks.getTasks().add(task);

        // Verificar que la relación se haya establecido correctamente
        assertEquals(listTasks, task.getListTasks());

        System.out.println(listTasks.getTasks());
        // Verificar que la tarea también esté asociada con la lista de tareas
        assertTrue(listTasks.getTasks().contains(task)); /*ERROR AQUI*/
    }
}
