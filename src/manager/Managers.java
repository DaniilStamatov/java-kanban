package manager;

import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;
import task.Task;
import task.TaskType;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Managers {

    private static File taskStorageFile = new File("tasks.csv");
    public static TaskManager getDefault(){
        return  new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }


    public static FileBackedTaskManager loadFromFile(){
        FileBackedTaskManager manager = getDefaultFileBackedTaskManager();
        manager.loadFromFile();
        return manager;
    }

    public  static  FileBackedTaskManager getDefaultFileBackedTaskManager(){
        return  new FileBackedTaskManager(taskStorageFile);
    }
}
