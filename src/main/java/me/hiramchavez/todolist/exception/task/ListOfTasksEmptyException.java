package me.hiramchavez.todolist.exception.task;

public class ListOfTasksEmptyException extends RuntimeException {
    public ListOfTasksEmptyException() {
        super();
    }

    public ListOfTasksEmptyException(String message) {
        super(message);
    }

    public ListOfTasksEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListOfTasksEmptyException(Throwable cause) {
        super(cause);
    }

    protected ListOfTasksEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
