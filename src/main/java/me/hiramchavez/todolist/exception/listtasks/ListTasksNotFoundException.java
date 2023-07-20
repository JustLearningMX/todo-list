package me.hiramchavez.todolist.exception.listtasks;

public class ListTasksNotFoundException extends RuntimeException {
    public ListTasksNotFoundException() {
        super();
    }

    public ListTasksNotFoundException(String message) {
        super(message);
    }

    public ListTasksNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListTasksNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ListTasksNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
