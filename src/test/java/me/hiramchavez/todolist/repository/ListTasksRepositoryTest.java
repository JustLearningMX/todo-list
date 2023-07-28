package me.hiramchavez.todolist.repository;

import me.hiramchavez.todolist.model.ListTasks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListTasksRepositoryTest {

    @Mock
    private ListTasksRepository listTasksRepository;

    @Test
    void testFindByIdAndActiveTrue() {
        // Datos de prueba
        Long taskId = 1L;
        ListTasks listTask = new ListTasks();
        listTask.setId(taskId);
        listTask.setActive(true);

        // Configuración del comportamiento simulado del repositorio
        when(listTasksRepository.findByIdAndActiveTrue(taskId)).thenReturn(listTask);

        // Llamada al método del repositorio que se está probando
        ListTasks result = listTasksRepository.findByIdAndActiveTrue(taskId);

        // Verificar que el resultado no sea nulo
        assertNotNull(result);

        // Verificar que el resultado es el mismo que el objeto de prueba
        assertEquals(taskId, result.getId());
        assertTrue(result.getActive());

        // Verificar que el método del repositorio se haya llamado con los parámetros correctos
        verify(listTasksRepository, times(1)).findByIdAndActiveTrue(taskId);
    }
}
