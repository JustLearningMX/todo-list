package me.hiramchavez.todolist.repository;

import me.hiramchavez.todolist.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskRepositoryTest {

    @Mock
    private TaskRepository taskRepository;

    @Test
    void testSaveTask() {
        Task taskToSave = new Task();
        taskToSave.setTitle("Test Task");

        // Configuración del comportamiento simulado del repositorio
        when(taskRepository.save(taskToSave)).thenReturn(taskToSave);

        // Llamada al método del repositorio que se está probando
        Task savedTask = taskRepository.save(taskToSave);

        // Verificar que el resultado no sea nulo
        assertNotNull(savedTask);

        // Verificar que el resultado es el mismo que el objeto de prueba
        assertEquals("Test Task", savedTask.getTitle());

        // Verificar que el método del repositorio se haya llamado con los parámetros correctos
        verify(taskRepository, times(1)).save(taskToSave);
    }
}