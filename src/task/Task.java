package task;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Status status;
    protected int id;


    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEpic(){
        return null;
    }

    public TaskType getType(){
        return TaskType.TASK;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, id);
    }

//    @Override
//    public String toString() {
//        return "Task{" +
//                "name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", status=" + status +
//                ", id=" + id +
//                '}';
//    }
    @Override
    public String toString(){
        return String.format("%d,%s,%s,%s,%s\n", id, TaskType.TASK, status, name, description);
    }

    public static Task fromString(String inputString){
        String[] input = inputString.split(",");
        Task task  = new Task(input[3], input[4], Status.valueOf(input[2]));
        task.setId(Integer.valueOf(input[0]));
        return task;
    }
}



