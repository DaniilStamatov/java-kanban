package manager;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(IOException e,String message) {
        super(message);
    }
}
