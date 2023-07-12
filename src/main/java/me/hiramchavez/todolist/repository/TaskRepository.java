package me.hiramchavez.todolist.repository;

import me.hiramchavez.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
