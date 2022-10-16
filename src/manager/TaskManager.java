package manager;

import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    ArrayList<Task> getTasks();

    ArrayList<SubTask> getSubTasks();
    ArrayList<Epic> getEpics();

    List<Task> getHistory();

    void deleteTasks();

    void  deleteSubTasks();
    void deleteEpics();

    Task getTask(int id);

    SubTask getSubTask(int id);

    Epic getEpic(int id);

    void updateTask(Task task);

    void updateTask(SubTask subTask);

    void updateTask(Epic epic);

    void deleteTask(int id);

    void deleteSubTask(int id);

    void deleteEpic(int id);

    ArrayList<SubTask> getSubTasksFromEpic(int epicId);


    void add(Task task);

    void add(SubTask subTask);

    void add(Epic epic);
}
