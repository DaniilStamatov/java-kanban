package manager;

import manager.history.HistoryManager;
import task.Epic;
import task.SubTask;
import task.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private  final File file;
    private final String TASK_HEADER = "id,type,name,status,description,epic";

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public void loadFromFile(){
        boolean areTasksRead = false;
        int generatorId = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String input = reader.readLine();

                if (input.isEmpty()) {
                    areTasksRead = true;
                    continue;
                }
                if(input.equals(TASK_HEADER)){
                    continue;
                }
                if (areTasksRead) {
                    historyFromString(input);
                    break;
                }
                generatorId = fillMaps(generatorId, input);

            }
        } catch (IOException e) {
            throw new ManagerSaveException(e, "Ошибка при загрузке из файла");
        }
        this.generatorId = generatorId+1;

        for (SubTask subtask: subTasks.values()) {
           addSubtaskInEpicWithoutID(subtask);
        }
    }

    private int fillMaps(int generatorID, String input) {
        String[] splitLine = input.split(",");

        switch (splitLine[1]) {
            case "TASK":
                Task task = Task.fromString(input);
                tasks.put(task.getId(), task);
                if(generatorID < task.getId())
                    generatorID = task.getId();
                break;
            case "EPIC":
                Epic epic = Epic.fromString(input);
                epics.put(epic.getId(), epic);
                if(generatorID <epic.getId())
                    generatorID = epic.getId();
                break;
            case "SUBTASK":
                SubTask subTask = SubTask.fromString(input);
                subTasks.put(subTask.getId(), subTask);
                if(generatorID <subTask.getId())
                    generatorID = subTask.getId();
                break;
        }
        return generatorID;
    }


    @Override
    public List<Task> getHistory() {
        save();
        return super.getHistory();
    }

    private void save(){
       try(BufferedWriter writer = new BufferedWriter(new FileWriter(file,false)) ) {
            writer.write(TASK_HEADER);
            writer.newLine();
            for(Task task : tasks.values()){
                writer.write(task.toString());
            }
            for(Epic epic : epics.values()){
                writer.write(epic.toString());
            }
            for(SubTask subTask : subTasks.values()){
                writer.write(subTask.toString());
            }
            writer.newLine();
            writer.write(historyToString(defaultHistory));
       }catch (IOException e){
           throw new ManagerSaveException(e,"Ошибка при создании файла");
       }
    }


    public void historyFromString(String value){
        String [] splitString = value.split(",");
        for (int i = 0; i < splitString.length; i++) {
            int index = Integer.valueOf(splitString[i]);
            if(tasks.containsKey(index)){
                defaultHistory.add(tasks.get(index));
            }else if(epics.containsKey(index)){
                defaultHistory.add(epics.get(index));
            }else if(subTasks.containsKey(index)){
                defaultHistory.add(subTasks.get(index));
            }
        }
    }


    public static String historyToString(HistoryManager historyManager){
        String returnString = "";
        for (Task task : historyManager.getHistory()) {
            returnString += task.getId()  + ",";
        }
        return returnString;
    }

    public void compareLoadAndSaveManagers(FileBackedTaskManager manager){
        if(!tasks.equals(manager.tasks)||!epics.equals(manager.epics)||!subTasks.equals(manager.subTasks)
                ||!defaultHistory.getHistory().equals(manager.defaultHistory.getHistory())){
            System.out.println("false");
        }
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteSubTasks() {
        super.deleteSubTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();

    }

    @Override
    public void updateTask(SubTask subTask) {
        super.updateTask(subTask);
        save();
    }

    @Override
    public Task getTask(int id) {
        super.getTask(id);
        save();

        return  super.getTask(id);

    }

    @Override
    public SubTask getSubTask(int id) {
        super.getSubTask(id);
        save();
        return super.getSubTask(id);
    }

    @Override
    public Epic getEpic(int id) {
        super.getEpic(id);
        save();
        return super.getEpic(id);
    }

    @Override
    public void updateTask(Epic epic) {
        super.updateTask(epic);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();

    }

    @Override
    public void deleteSubTask(int id) {
        super.deleteSubTask(id);
        save();

    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();

    }

    @Override
    public void add(Task task) {
        super.add(task);
        save();

    }

    @Override
    public void add(SubTask subTask) {
        super.add(subTask);
        save();

    }

    @Override
    public void add(Epic epic) {
        super.add(epic);
        save();

    }
}
