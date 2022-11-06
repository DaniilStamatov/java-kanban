import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Задача", "Description", Status.DONE);
        Epic epic = new Epic("Задача большая", "Description", Status.NEW);
        SubTask subTask = new SubTask("SubTask", "description", Status.DONE, 1);
        SubTask subTask1 = new SubTask("SubTask1", "description", Status.NEW, 1);
        SubTask subTask2 = new SubTask("SubTask2", "description", Status.DONE, 1);
        Epic epic1 = new Epic("Задача большая", "Description", Status.NEW);


        manager.add(task);
        manager.add(epic);
        manager.add(subTask);
        manager.add(subTask1);
        manager.add(subTask2);
        manager.add(epic1);

        manager.getSubTasksFromEpic(1);
        manager.getTask(0);
        manager.getHistory();

        manager.getEpic(1);
        manager.getHistory();
        manager.getEpic(1);
        manager.getHistory();
        manager.getTask(0);
        manager.getHistory();
        manager.getEpic(1);

        manager.getSubTask(2);
        manager.getHistory();
        manager.getSubTask(4);
        manager.getSubTask(3);
        manager.getHistory();

        manager.getSubTask(4);
        manager.getHistory();

        manager.getTask(0);
        manager.getSubTask(2);
        manager.getEpic(1);
        manager.getHistory();
        manager.deleteTasks();
        manager.updateTask(epic);
        manager.getSubTasksFromEpic(1);
        manager.deleteEpic(1);
        System.out.println("Поехали!");
    }
}
