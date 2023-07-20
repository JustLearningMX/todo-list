package me.hiramchavez.todolist.exception.listtasks;

public class ListTasksEmptyException extends RuntimeException {
    public ListTasksEmptyException() {
        super();
    }

    public ListTasksEmptyException(String message) {
        super(message);
    }

    public ListTasksEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListTasksEmptyException(Throwable cause) {
        super(cause);
    }

    protected ListTasksEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
