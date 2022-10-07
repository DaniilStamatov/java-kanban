package manager;

import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private int generatorId = 0;

    public ArrayList<Task> getTasks(){
        return new ArrayList<>(tasks.values());
    }

    public  ArrayList<SubTask> getSubTasks(){
        return new ArrayList<>(subTasks.values());
    }
    public  ArrayList<Epic> getSubEpics(){
        return new ArrayList<>(epics.values());
    }

    public void deleteTasks(){
        tasks.clear();
    }

    public void deleteSubTasks(){
        subTasks.clear();
    }
    public void deleteEpics(){
        epics.clear();
    }

    public  Task getTask(int id){
         return tasks.get(id);
    }

    public  SubTask getSubTask(int id){
        return subTasks.get(id);
    }

    public Epic getEpic(int id){
        return epics.get(id);
    }

    public void updateTask(Task task){
        if(tasks.containsKey(task.getId())){
            tasks.put(task.getId(), task);
        }
    }

    public void updateSubTask(SubTask subTask){
        if(subTasks.containsKey(subTask.getId())){
            subTasks.put(subTask.getId(), subTask);
        }
    }

    public void updateEpic(Epic epic){
        if(epics.containsKey(epic.getId())){
            epics.put(epic.getId(), epic);
        }
    }

    public void deleteTask(int id){
        if(tasks.containsKey(id)){
            tasks.remove(id);
        }
    }

    public void deleteSubTask(int id){
        if(subTasks.containsKey(id)){
            Epic epic = epics.get(subTasks.get(id).getEpicId());
            subTasks.remove(id);
            epic.getSubTasksIds().remove((Integer) id);
            updateEpicStatus(epic);
        }
    }

    public void deleteEpic(int id){
        if(epics.containsKey(id)){
            for(SubTask task: getSubTasksFromEpic(id)){
                subTasks.remove(task.getId());
            }
            epics.remove(id);
        }
    }



    public ArrayList<SubTask> getSubTasksFromEpic(int epicId){
        if(epics.containsKey(epicId)){

        }
        Epic epic = epics.get(epicId);
        ArrayList<SubTask> subTaskFromEpic= new ArrayList<>();
        ArrayList<Integer> subTaskIds =  epic.getSubTasksIds();
        for (int i = 0; i < subTaskIds.size(); i++) {
            if(subTasks.containsKey(subTaskIds.get(i))){
                subTaskFromEpic.add(subTasks.get(subTaskIds.get(i)));
            }
        }
        return subTaskFromEpic;
    }


    public void updateEpicStatus(Epic epic){
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

    public void add(Task task){
        task.setId(generatorId);
        tasks.put(task.getId(), task);
        generatorId++;
    }

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

    public  void add(Epic epic){
        epic.setId(generatorId);
        epics.put(epic.getId(), epic);
        generatorId++;
    }
}
