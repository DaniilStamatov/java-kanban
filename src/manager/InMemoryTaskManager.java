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
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected  HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected  HashMap<Integer, Epic> epics = new HashMap<>();
    protected int generatorId = 0;
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
        for (Task task: tasks.values()) {
            defaultHistory.remove(task.getId());
        }
    }

   @Override
   public void deleteSubTasks(){
        for(Epic epic:epics.values()){
            epic.getSubTasksIds().clear();
            updateEpicStatus(epic);
        }
        subTasks.clear();
        for(SubTask subTask: subTasks.values()){
            defaultHistory.remove(subTask.getId());
        }
    }
   @Override
   public void deleteEpics(){
        for(Epic epic:epics.values()){
            epic.getSubTasksIds().clear();
            deleteSubTasks();
            defaultHistory.remove(epic.getId());
        }
        epics.clear();
    }

    @Override
    public  Task getTask(int id){
       var task = tasks.get(id);
        if(task!=null){
            defaultHistory.add(task);
        }

        return task;
    }

    @Override
    public  SubTask getSubTask(int id){
       var subTask  = subTasks.get(id);
       if(subTask!=null){
           defaultHistory.add(subTasks.get(id));
       }
        return subTasks.get(id);
    }

   @Override
   public Epic getEpic(int id){
       var task = epics.get(id);

       if(task!=null){
           defaultHistory.add(epics.get(id));
       }

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
   public void deleteTask(int id) {
       if (tasks.containsKey(id)) {
           defaultHistory.remove(id);
           tasks.remove(id);
       }
   }

   @Override
   public void deleteSubTask(int id){
        if(subTasks.containsKey(id)){
            Epic epic = epics.get(subTasks.get(id).getEpic());
            defaultHistory.remove(id);
            subTasks.remove(id);
            epic.getSubTasksIds().remove((Integer) id);
            updateEpicStatus(epic);
        }
    }

   @Override
   public void deleteEpic(int id){
        if(epics.containsKey(id)){
            for(SubTask task: getSubTasksFromEpic(id)){
                var history = defaultHistory.getHistory();
                if(history.contains(task)){
                    System.out.println("true");
                    defaultHistory.remove(task.getId());
                }

                subTasks.remove(task.getId());
            }
            defaultHistory.remove(id);
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

    protected void updateEpicStatus(Epic epic){
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

        subTask.setId(generatorId);
        subTasks.put(subTask.getId(), subTask);
        addSubtaskInEpicWithoutID(subTask);
        generatorId++;
   }

    public  void addSubtaskInEpicWithoutID(SubTask subTask){
        int epicId = subTask.getEpic();
        Epic epic = epics.get(epicId);

        if(epic == null){
            return;
        }

        epic.addSubTaskId(subTask.getId());
        updateEpicStatus(epic);
    }

   @Override
   public void add(Epic epic){
        epic.setId(generatorId);
        epics.put(epic.getId(), epic);
        generatorId++;
    }
}
