package task;

import task.Status;
import task.Task;

import java.util.ArrayList;
import java.util.Objects;

public class Epic  extends Task {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasksIds, epic.subTasksIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasksIds);
    }

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
