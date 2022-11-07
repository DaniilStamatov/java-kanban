package manager.history;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    public final CustomLinkedList history = new CustomLinkedList();


    @Override
    public void add(Task task) {
        if(history.getNodes().containsKey(task.getId())){
            remove(task.getId());
        }
        history.linkLast(task);

    }

    @Override
    public void remove(int id) {
        history.removeTaskHashed(id);
    }

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }



}

class CustomLinkedList{
    private Node<Task> head;
    private Node<Task> tail;

    private final HashMap<Integer, Node<Task>> nodes = new HashMap<>();

    public void linkLast(Task task){
        final Node<Task> oldTail = tail;
        final Node<Task> newTail = new Node<>(task, null, oldTail);
        tail = newTail;
        if(oldTail == null) {
            head = newTail;
        }else{
            oldTail.next = newTail;
        }
        nodes.put(task.getId(), newTail);
    }



    public void linkFirst(Task task){
        final Node<Task> oldHead = head;
        final Node<Task> newNode = new Node<>(task, oldHead, null);
        head = newNode;
        if (oldHead == null)
            tail = newNode;
        else
            oldHead.previous = newNode;
    }

    public List<Task> getTasks(){
        List<Task> tasks = new ArrayList<>();
        Node<Task> currentNode = head;
        tasks.add(currentNode.data);
        while(currentNode!=null){
            tasks.add(currentNode.data);
            currentNode = currentNode.next;
        }

        return tasks;
    }

    public void removeTaskHashed(Integer value){
        this.removeNode(nodes.get(value));
        this.nodes.remove(value);
    }
    public void removeNode(Node<Task> node){
        if(node.previous == null){
            head = node.next;
            if(node.next == null){
                tail = null;
                return;
            }
            node.next.previous = null;
            return;
        }
        node.previous.next = node.next;

        if(node == tail){
            tail = node.previous;
            return;
        }

        node.next.previous = node.previous;

    }
    private Node<Task> createNode(Task task){
        Node newNode = new Node(task,null, null);
        nodes.put(task.getId(), newNode);
        return newNode;
    }


    public HashMap<Integer, Node<Task>> getNodes() {
        return nodes;
    }
}

class Node<T> {
    public T data;
    public Node<T> next;
    public Node<T> previous;

    public Node(T data, Node<T> next, Node<T> previous) {
        this.data = data;
        this.next = next;
        this.previous = previous;
    }
}

