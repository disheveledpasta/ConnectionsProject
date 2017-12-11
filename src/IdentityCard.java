import java.util.*;

class IdentityCard {

    ///// FIELDS  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Connections connections;
    private Properties properties;

    private int IDNumber;

    private int knowledgeLevel = 0; // rough estimate on how much the database knows on this guy
    private String name;

    ///// CONSTRUCTORS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    IdentityCard(int IDNumberInput, String nameInput, Map<String, Object> propertiesInput, Map<Integer, ConnectionsProperties> connectionsInput) {
        IDNumber = IDNumberInput;
        name = nameInput;
        properties = new Properties(propertiesInput);
        connections = new Connections(connectionsInput);
        addProperties(propertiesInput);
        addConnections(connectionsInput);
    }

    IdentityCard(int IDNumberInput, String nameInput, Map<String, Object> propertiesInput) {
        name = nameInput;
        IDNumber = IDNumberInput;
        properties = new Properties(propertiesInput);
        connections = new Connections();
        addProperties(propertiesInput);
    }

    IdentityCard(int IDNumberInput, String nameInput) {
        name = nameInput;
        IDNumber = IDNumberInput;
        properties = new Properties();
        connections = new Connections();
    }

    ///// GET METHODS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object getProperty(String requestedProperty) {
        return properties.propertiesList.get(requestedProperty);
    }

    Properties getProperties() {
        return properties;
    }

    ConnectionsProperties getConnection(Integer IDNumber) {
        return connections.connectionsList.get(IDNumber);
    }

    Connections getConnections() {
        return connections;
    }

    Object getConnectionsProperty(Integer IDNumber, String requestedProperty) {
        ConnectionsProperties connectionsProperty = connections.connectionsList.get(IDNumber);
        if (connectionsProperty != null) {
            return (connectionsProperty.propertiesList.get(requestedProperty));
        }
        return null;
    }

    ConnectionsProperties getConnectionsProperties(Integer IDNumber) {
        return connections.connectionsList.get(IDNumber);
    }

    String getName() { return name; }

    ///// ADD METHODS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void addProperty(String index, Object value) {
        if (!properties.propertiesList.containsKey(index)) {
            properties.propertiesList.put(index, value);
        }
    }

    void addProperties(Map<String, Object> propertiesListInput) { // do not set to private
        for (Map.Entry<String, Object> e : propertiesListInput.entrySet()) {
            if (!properties.propertiesList.containsKey(e.getKey())) {
                properties.propertiesList.put(e.getKey(), e.getValue());
            }
        }
    }

    void addConnection(Integer index, ConnectionsProperties value) {
        if (!connections.connectionsList.containsKey(index)) {
            connections.connectionsList.put(index, value);
        }
    }

    void addConnections(Map<Integer, ConnectionsProperties> connectionsListInput) {
        for (Map.Entry<Integer, ConnectionsProperties> e : connectionsListInput.entrySet()) {
            if (!connections.connectionsList.containsKey(e.getKey())) {
                connections.connectionsList.put(e.getKey(), e.getValue());
            }
        }
    }

    void addConnectionsProperty(Integer requestedIDNumber, String newProperty, Object newValue) {
        if (!connections.connectionsList.containsKey(requestedIDNumber)) {
            if (!connections.connectionsList.get(requestedIDNumber).propertiesList.containsKey(newProperty)) {
                connections.connectionsList.get(requestedIDNumber).propertiesList.put(newProperty, newValue);
            }
        }
    }

    void addConnectionsProperties(Integer requestedIDNumber, Map<String, Object> connectionsProperties) {
        if (!connections.connectionsList.containsKey(requestedIDNumber)) {
            for (Map.Entry<String, Object> e : connectionsProperties.entrySet()) {
                if (!connections.connectionsList.get(requestedIDNumber).propertiesList.containsKey(e.getKey())) {
                    connections.connectionsList.get(requestedIDNumber).propertiesList.put(e.getKey(),e.getValue());
                }
            }
        }
    }

    ///// EDIT METHODS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void editProperty(String requestedProperty, Object newValue) {
        properties.propertiesList.replace(requestedProperty, newValue); // <-- The replace() method automatically checks for null
    }

    void editConnection(Integer requestedIDNumber, ConnectionsProperties newProperties) {
        connections.connectionsList.replace(requestedIDNumber, newProperties);
    }

    void editConnectionProperty(Integer requestedIDNumber, String requestedProperty, Object newValue) {
        if (connections.connectionsList.containsKey(requestedIDNumber)) {
            connections.connectionsList.get(requestedIDNumber).propertiesList.replace(requestedProperty, newValue);
        }
    }

    void editConnectionLevel(Integer requestedIDNumber, int newConnectionLevel) {
        if (connections.connectionsList.containsKey(requestedIDNumber))
            connections.connectionsList.get(requestedIDNumber).connectionLevel = newConnectionLevel;
    }

    ///// CONNECTION VALUE CHANGES ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void incrementKnowledgeLevel(int num) {
        knowledgeLevel += num;
    }

    void incrementKnowledgeLevel() {
        knowledgeLevel++;
    }

}