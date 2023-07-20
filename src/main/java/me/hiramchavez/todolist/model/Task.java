package me.hiramchavez.todolist.model;


import jakarta.persistence.*;
import lombok.*;
import me.hiramchavez.todolist.dto.task.TaskBodyResDto;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "expiration_date", columnDefinition = "TIMESTAMP")
    private Date expirationDate;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_task_id", referencedColumnName = "id")
    private ListTasks listTasks;

    public void update(TaskBodyResDto taskBodyReqDto) {
        if (taskBodyReqDto.title() != null)
            this.title = taskBodyReqDto.title();

        if (taskBodyReqDto.description() != null)
            this.description = taskBodyReqDto.description();

        if (taskBodyReqDto.expirationDate() != null)
            this.expirationDate = taskBodyReqDto.expirationDate();

        if (taskBodyReqDto.state() != null)
            this.state = taskBodyReqDto.state();

        if (taskBodyReqDto.priority() != null)
            this.priority = taskBodyReqDto.priority();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(expirationDate, task.expirationDate) && state == task.state && priority == task.priority && Objects.equals(listTasks, task.listTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, expirationDate, state, priority, listTasks);
    }

    @Override
    public String toString() {
        return "Task{" +
          "id=" + id +
          ", title='" + title + '\'' +
          ", description='" + description + '\'' +
          ", expirationDate=" + expirationDate +
          ", state=" + state +
          ", priority=" + priority +
          '}';
    }
}
