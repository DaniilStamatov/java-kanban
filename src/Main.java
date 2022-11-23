import manager.Managers;
import manager.TaskManager;
import task.*;

public class Main {

    public static void main(String[] args) {
        TaskManager loadManager = Managers.loadFromFile();
        TaskManager saveManager = Managers.getDefaultFileBackedTaskManager();
        Task task = new Task("Task1", "First task", Status.DONE);
        Epic epic = new Epic("BigTask", "Description", Status.NEW);
        SubTask subTask = new SubTask("SubTask", "description", Status.DONE, 1);
        SubTask subTask1 = new SubTask("SubTask1", "description", Status.NEW, 1);
        SubTask subTask2 = new SubTask("SubTask2", "description", Status.DONE, 1);
        Epic epic1 = new Epic("BigTask2", "Description", Status.NEW);
        Epic epic2 = new Epic("BigTask2", "Description", Status.NEW);


        saveManager.add(task);
        saveManager.getTask(0);
        saveManager.add(epic);
        saveManager.add(subTask);
        saveManager.getEpic(1);
        saveManager.add(subTask1);
        saveManager.add(subTask2);
        saveManager.getSubTask(3);
        saveManager.deleteEpic(epic.getId());

        saveManager.add(epic1);
        saveManager.add(epic2);
        TaskManager loadManager1 = Managers.loadFromFile();


        System.out.println("Поехали!");
    }
}
