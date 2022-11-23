package task;

import java.util.Objects;

public class SubTask extends Task {
    private int epicId;
    @Override
    public Integer getEpic() {
        return epicId;
    }
    @Override
    public TaskType getType(){
        return TaskType.SUBTASK;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public String toString(){
        return String.format("%d,%s,%s,%s,%s,%d\n", id, getType(), status, name, description, epicId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    public static SubTask fromString(String inputString){
        String[] input = inputString.split(",");
        switch (input[2]){
            case ("NEW"):
                SubTask subTask =  new SubTask(input[3], input[4], Status.NEW, Integer.parseInt(input[5]));
                subTask.setId(Integer.parseInt(input[0]));
                return subTask;
            case ("DONE"):
                subTask =  new SubTask(input[3], input[4], Status.DONE, Integer.parseInt(input[5]));
                subTask.setId(Integer.parseInt(input[0]));
                return subTask;
            case ("IN_PROGRESS"):
                subTask =  new SubTask(input[3], input[4], Status.IN_PROGRESS, Integer.parseInt(input[5]));
                subTask.setId(Integer.parseInt(input[0]));
                return subTask;
            default:
                throw  new IllegalArgumentException();
        }
    }
}
