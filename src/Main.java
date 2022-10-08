import manager.TaskManager;
import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Task task = new Task("Задача", "Description", Status.DONE);
        Epic epic = new Epic("Задача большая", "Description", Status.NEW);
        SubTask subTask = new SubTask("SubTask", "description", Status.DONE, 1);
        SubTask subTask1 = new SubTask("SubTask1", "description", Status.NEW, 1);
        SubTask subTask2 = new SubTask("SubTask2", "description", Status.DONE, 1);

        manager.addTask(task);
        manager.addEpic(epic);
        manager.addSubTask(subTask);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.getSubTasksFromEpic(1);
        manager.deleteTasks();
        manager.deleteEpics();
        manager.deleteSubTasks();
        manager.deleteEpic(1);
        System.out.println("Поехали!");
    }
}
