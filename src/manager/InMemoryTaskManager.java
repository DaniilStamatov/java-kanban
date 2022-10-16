package manager;

import manager.history.HistoryManager;
import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements  TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int generatorId = 0;
    HistoryManager defaultHistory = Managers.getDefaultHistory();

   @Override
   public ArrayList<Task> getTasks(){
        return new ArrayList<>(tasks.values());
   }

   @Override
   public  ArrayList<SubTask> getSubTasks(){
        return new ArrayList<>(subTasks.values());
    }

   @Override
   public  ArrayList<Epic> getEpics(){
        return new ArrayList<>(epics.values());
    }

   @Override
    public List<Task> getHistory() {
       return defaultHistory.getHistory();
    }

    @Override
    public void deleteTasks(){
        tasks.clear();
    }

   @Override
   public void deleteSubTasks(){
        for(Epic epic:epics.values()){
            epic.getSubTasksIds().clear();
            updateEpicStatus(epic);
        }
        subTasks.clear();

    }
   @Override
   public void deleteEpics(){
        for(Epic epic:epics.values()){
            epic.getSubTasksIds().clear();
            deleteSubTasks();
        }
        epics.clear();
    }

    @Override
    public  Task getTask(int id){
       var task = tasks.get(id);
       defaultHistory.add(task);
        return task;
    }

    @Override
    public  SubTask getSubTask(int id){
        defaultHistory.add(subTasks.get(id));

        return subTasks.get(id);
    }

   @Override
   public Epic getEpic(int id){
       defaultHistory.add(epics.get(id));
       return epics.get(id);
    }

   @Override
   public void updateTask(Task task){
        if(tasks.containsKey(task.getId())){
            tasks.put(task.getId(), task);
        }
    }

   @Override
   public void updateTask(SubTask subTask){
        if(subTasks.containsKey(subTask.getId())){
            subTasks.put(subTask.getId(), subTask);
        }
    }

   @Override
   public void updateTask(Epic epic){
        if(epics.containsKey(epic.getId())){
            epics.put(epic.getId(), epic);
            updateEpicStatus(epic);
        }
    }

   @Override
   public void deleteTask(int id){
        if(tasks.containsKey(id)){
            tasks.remove(id);
        }
    }

   @Override
   public void deleteSubTask(int id){
        if(subTasks.containsKey(id)){
            Epic epic = epics.get(subTasks.get(id).getEpicId());
            subTasks.remove(id);
            epic.getSubTasksIds().remove((Integer) id);
            updateEpicStatus(epic);
        }
    }

   @Override
   public void deleteEpic(int id){
        if(epics.containsKey(id)){
            for(SubTask task: getSubTasksFromEpic(id)){
                subTasks.remove(task.getId());
            }
            epics.remove(id);
        }
    }



   @Override
   public ArrayList<SubTask> getSubTasksFromEpic(int epicId){
        ArrayList<SubTask> subTasksFromEpic = new ArrayList<>();

        if(epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            ArrayList<Integer> subTaskIds = epic.getSubTasksIds();
            for (Integer subTaskId : subTaskIds) {
                if (subTasks.containsKey(subTaskId)) {
                    subTasksFromEpic.add(subTasks.get(subTaskId));
                }
            }
        }
        return subTasksFromEpic;
    }

    private void updateEpicStatus(Epic epic){
        ArrayList<Integer> subtaskIds = epic.getSubTasksIds();
        if(subtaskIds.isEmpty()){
            epic.setStatus(Status.NEW);
            return;
        }
        Status status = null;
        for(int subtaskId: subtaskIds){
            SubTask subTask = subTasks.get(subtaskId);

            if(status == null){
                status = subTask.getStatus();
                continue;
            }
            if(status.equals(subTask.getStatus())){
                continue;
            }

            epic.setStatus(Status.IN_PROGRESS);
            return;
        }
        epic.setStatus(status);
    }

   @Override
   public void add(Task task){
        task.setId(generatorId);
        tasks.put(task.getId(), task);
        generatorId++;
    }

   @Override
   public void add(SubTask subTask){
        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);

        if(epic == null){
            return;
        }

        subTask.setId(generatorId);
        subTasks.put(subTask.getId(), subTask);
        epic.addSubTaskId(subTask.getId());

        updateEpicStatus(epic);

        generatorId++;
    }

   @Override
   public void add(Epic epic){
        epic.setId(generatorId);
        epics.put(epic.getId(), epic);
        generatorId++;
    }
}
