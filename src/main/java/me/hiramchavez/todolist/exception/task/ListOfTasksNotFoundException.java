package me.hiramchavez.todolist.exception.task;

public class ListOfTasksNotFoundException extends RuntimeException {
    public ListOfTasksNotFoundException() {
        super();
    }

    public ListOfTasksNotFoundException(String message) {
        super(message);
    }

    public ListOfTasksNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListOfTasksNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ListOfTasksNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
