import java.rmi.*;
import java.util.*;

public interface CallBackInterface extends Remote {
    public void notifyTaskAvailable(String key) throws RemoteException;

    public void notifyTaskComplete(String key) throws RemoteException;
}
