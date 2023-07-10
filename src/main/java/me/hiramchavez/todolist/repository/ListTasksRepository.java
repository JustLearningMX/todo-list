package me.hiramchavez.todolist.repository;

import me.hiramchavez.todolist.model.ListTasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListTasksRepository extends JpaRepository<ListTasks, Long> {
    ListTasks findByIdAndActiveTrue(Long id);
}
