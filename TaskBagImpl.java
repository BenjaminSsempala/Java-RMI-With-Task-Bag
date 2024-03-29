
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class TaskBagImpl extends UnicastRemoteObject implements TaskBagInterface {

    private Map<Integer, int[]> taskData;
    private Map<String, Integer> taskDescriptions;
    private Map<String, List<Integer>> taskResults;
    private int maxTaskId;

    public TaskBagImpl() throws RemoteException {
        taskData = new HashMap<>();
        taskDescriptions = new HashMap<>();
        taskResults = new HashMap<>();
        maxTaskId = 0;
    }

    public void printTasks() {
        for (Map.Entry<String, Integer> entry : taskDescriptions.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

    }

    public void printResults() {
        for (Map.Entry<String, List<Integer>> entry : taskResults.entrySet()) {
            String key = entry.getKey();
            List<Integer> value = entry.getValue();
            System.out.println(key + ": " + value.toString());
        }

    }

    public synchronized void pairOutData(int id, int[] value) throws RemoteException {
        taskData.put(id, value);
    }

    public synchronized void pairOutTask(String key, int id) throws RemoteException {
        taskDescriptions.put(key, id);
        if (id > maxTaskId) {
            maxTaskId = id;
        }
    }

    public synchronized void pairOutResult(String key, int[] value) throws RemoteException {
        List<Integer> results = taskResults.getOrDefault(key, new ArrayList<Integer>());
        for (int num : value) {
            results.add(num);
        }
        taskResults.put(key, results);
    }

    public synchronized int[] pairInData(int id) throws RemoteException {

        return taskData.remove(id);
    }

    public synchronized int pairInTask(String key) throws RemoteException {
        while (taskDescriptions.containsKey("NextTask")) {
            try {
                int taskId = taskDescriptions.remove(key);
        if (taskId < maxTaskId) {
            taskDescriptions.put("NextTask", taskId + 1);
            taskDescriptions.remove("Task" + (taskId + 1));
        }

        return taskId;
               
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
        return -1;
        
    }

    public synchronized List<Integer> pairInResult(String key) throws RemoteException {
        List<Integer> result = new ArrayList<>();
        for (String taskKey : taskResults.keySet()) {
            if (taskKey.endsWith(key)) {
                result = taskResults.remove(taskKey);
                break;
            }
        }
        return result;
    }

}