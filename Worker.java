
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

public class Worker {
    public static void main(String args[]) {
        try {
            System.out.println("Worker is booting....");
            int[] task,result;
            int taskId;
            
            TaskBagInterface taskBag = (TaskBagInterface) Naming.lookup("taskBag");

            while (true) {
                taskId = taskBag.pairInTask("NextTask");
            
                task = taskBag.pairInData(taskId);
                result = getPrimeNumbers(task);
                taskBag.pairOutResult("result", result);
                Thread.sleep(3000);
                System.out.println("Worker has finished a Task....");
            }
            
        } catch (Exception e) {
            System.out.println("Exception in worker side" + e);
        }
    }

    public static int[] getPrimeNumbers(int[] task) {
    List<Integer> primeNumbersList = new ArrayList<>();
    for (int i = 0; i < task.length; i++) {
        if (isPrime(task[i])) {
            primeNumbersList.add(task[i]);
        }
    }
    int[] primeNumbersArray = new int[primeNumbersList.size()];
    for (int i = 0; i < primeNumbersList.size(); i++) {
        primeNumbersArray[i] = primeNumbersList.get(i);
    }
    return primeNumbersArray;
}



    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}