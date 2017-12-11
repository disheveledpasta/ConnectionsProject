import java.util.*;

class Connections {

    Map<Integer, ConnectionsProperties> connectionsList = new TreeMap<>();

    Connections(Map<Integer, ConnectionsProperties> connectionsListInput) {
        connectionsList.putAll(connectionsListInput);
    }

    Connections () {}

}
