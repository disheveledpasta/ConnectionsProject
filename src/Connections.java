import java.util.*;

class Connections {

    Map<Integer, ConnectionsProperties> connectionsList = new TreeMap<>();

    Connections(Map<Integer, ConnectionsProperties> connectionsListInput) {
        connectionsList.putAll(connectionsListInput);
    }

    Connections () {}

    ConnectionsProperties getConnection(Integer i) {
        return connectionsList.get(i);
    }

    void addConnection(Integer i, ConnectionsProperties v) {
        connectionsList.put(i, v);
    }

    void editConnection(Integer o, ConnectionsProperties n) {
        connectionsList.replace(o, n);
    }

    void removeConnection(Integer i) {
        connectionsList.remove(i);
    }

}
