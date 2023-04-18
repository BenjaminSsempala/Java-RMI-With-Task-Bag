
import java.util.*;
import java.rmi.*;

public interface TaskBagInterface extends Remote {

    public void placePairData(int id, int[] value) throws RemoteException;

    public void placePairTask(String key, int id) throws RemoteException;

    public void placePairResult(String key, int[] value) throws RemoteException;

    public int[] takePairData(int id) throws RemoteException;

    public int takePairTask(String key) throws RemoteException;

    public List<Integer> takePairResult(String key) throws RemoteException;

    public void printTasks() throws RemoteException;

    public void printResults() throws RemoteException;

    public void registerCallback(CallBackInterface callbackObj) throws RemoteException;

}
