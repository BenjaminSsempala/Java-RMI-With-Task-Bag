
import java.util.*;
import java.rmi.*;

public interface TaskBagInterface extends Remote {

    public void pairOutData(int id, int[] value) throws RemoteException;

    public void pairOutTask(String key, int id) throws RemoteException;

    public void pairOutResult(String key, int[] value) throws RemoteException;

    public int[] pairInData(int id) throws RemoteException;

    public int pairInTask(String key) throws RemoteException;

    public List<Integer> pairInResult(String key) throws RemoteException;

    public void printTasks() throws RemoteException;

    public void printResults() throws RemoteException;

}
