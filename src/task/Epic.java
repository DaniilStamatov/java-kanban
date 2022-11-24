package task;

import java.util.ArrayList;
import java.util.Objects;

public class Epic  extends Task {
    private ArrayList<Integer> subTasksIds;
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



    public Epic(String name, String description, Status status) {
        super(name, description, status);
        subTasksIds = new ArrayList<>();
    }

    public void addSubTaskId(int subTaskId){
        subTasksIds.add(subTaskId);
    }


    @Override
    public TaskType getType(){
        return  TaskType.EPIC;
    }
    @Override
    public String toString(){
        return String.format("%d,%s,%s,%s,%s\n", id, TaskType.EPIC, status, name, description);
    }

    @Override
    public Integer getEpic(){
        return null;
    }
    public static Epic fromString(String inputString){
        String [] input = inputString.split(",");
        Epic epic = new Epic(input[3], input[4], Status.valueOf(input[2]));
        epic.setId(Integer.valueOf(input[0]));
        return epic;
    }
}
