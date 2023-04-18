
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;

public class Master {

    public static void main(String[] args) {
        try {
            System.out.println("Master is booting....");

            Scanner sc = new Scanner(System.in);
            int max = 0;

            while (max != -1) {
                System.out.println("Enter -1 to quit: ");
                System.out.println("Enter the Max value for Prime numbers: ");
                max = sc.nextInt();

                List batchTasks = getNumberRange(max);
                List<Integer> results = new ArrayList<>();

                TaskBagImpl taskBag = new TaskBagImpl();
                Naming.rebind("taskBag", taskBag);

                System.out.println("Master is ready....");
                taskBag.pairOutTask("NextTask", 0);
                taskBag.pairOutData(0, (int[]) batchTasks.get(0));
                // for loop to insert each batch into the taskBag
                for (int i = 1; i < batchTasks.size(); i++) {
                    taskBag.pairOutTask("Task" + i, i);
                    taskBag.pairOutData(i, (int[]) batchTasks.get(i));
                }

                while (true) {
                    Thread.sleep(5000);
                    List<Integer> result = taskBag.pairInResult("result");
                    results.addAll(result);

                    // break the loop if added result is null
                    if (result.isEmpty()) {
                        break;
                    }

                    System.out.println("Master Results:");
                    for (int num : results) {
                        System.out.print(num + " ");
                    }
                    System.out.println();
                }
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Exception in Master side" + e);
        }
    }

    public static List getNumberRange(int max) {
        int min = 1; // minimum number to process
        int batchSize = 100; // size of each batch

        // Compute the number of batches required
        int numBatches = (int) Math.ceil((double) (max - min + 1) / batchSize);

        // Break the range into batches
        List<int[]> batches = new ArrayList<>();
        for (int i = 0; i < numBatches; i++) {
            int start = min + i * batchSize;
            int end = Math.min(start + batchSize - 1, max);
            int[] batch = new int[end - start + 1];
            for (int j = start; j <= end; j++) {
                batch[j - start] = j;
            }
            batches.add(batch);
        }

        

        return batches;
    }

}