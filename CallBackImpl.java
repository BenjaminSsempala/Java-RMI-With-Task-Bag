import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class CallBackImpl extends UnicastRemoteObject implements CallBackInterface {
    public CallBackImpl() throws RemoteException {
        super();
    }

    public void notifyTaskAvailable(String key) throws RemoteException {
        System.out.println("New task available");
    }

    public void notifyTaskComplete(String key) throws RemoteException {
        System.out.println("Task complete");
    }
}
