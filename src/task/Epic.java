package task;

import task.Status;
import task.Task;

import java.util.ArrayList;

public class Epic  extends Task {
    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    public void setSubTasksIds(ArrayList<Integer> subTasksIds) {
        this.subTasksIds = subTasksIds;
    }

    ArrayList<Integer> subTasksIds;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        subTasksIds = new ArrayList<>();
    }

    public void addSubTaskId(int subTaskId){
        subTasksIds.add(subTaskId);
    }
}
