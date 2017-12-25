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
        return properties.getProperty(requestedProperty);
    }

    Properties getProperties() {
        return properties;
    }

    ConnectionsProperties getConnection(Integer IDNumber) {
        return connections.getConnection(IDNumber);
    }

    Connections getConnections() {
        return connections;
    }

    Object getConnectionsProperty(Integer IDNumber, String requestedProperty) {
        ConnectionsProperties connectionsProperty = connections.getConnection(IDNumber);
        if (connectionsProperty != null) {
            return (connectionsProperty.getProperty(requestedProperty));
        }
        return null;
    }

    ConnectionsProperties getConnectionsProperties(Integer IDNumber) {
        return connections.getConnection(IDNumber);
    }

    String getName() { return name; }

    ///// ADD METHODS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void addProperty(String index, Object value) {
        if (getProperty(index) == null) {
            properties.addProperty(index, value);
        }
    }

    void addProperties(Map<String, Object> propertiesListInput) { // do not set to private
        for (Map.Entry<String, Object> e : propertiesListInput.entrySet()) {
            if (properties.getProperty(e.getKey()) == null) {
                properties.addProperty(e.getKey(), e.getValue());
            }
        }
    }

    void addConnection(Integer index, ConnectionsProperties value) {
        if (connections.getConnection(index) == null) {
            connections.addConnection(index, value);
        }
    }

    void addConnections(Map<Integer, ConnectionsProperties> connectionsListInput) {
        for (Map.Entry<Integer, ConnectionsProperties> e : connectionsListInput.entrySet()) {
            if (connections.getConnection(e.getKey()) == null) {
                connections.addConnection(e.getKey(), e.getValue());
            }
        }
    }

    void addConnectionsProperty(Integer requestedIDNumber, String newProperty, Object newValue) {
        if (connections.getConnection(requestedIDNumber) == null) {
            if (connections.getConnection(requestedIDNumber).getProperty(newProperty) != null) {
                connections.getConnection(requestedIDNumber).addProperty(newProperty, newValue);
            }
        }
    }

    void addConnectionsProperties(Integer requestedIDNumber, Map<String, Object> connectionsProperties) {
        if (!connections.connectionsList.containsKey(requestedIDNumber)) {
            for (Map.Entry<String, Object> e : connectionsProperties.entrySet()) {
                if (connections.getConnection(requestedIDNumber).getProperty(e.getKey()) == null) {
                    connections.getConnection(requestedIDNumber).addProperty(e.getKey(), e.getValue());
                }
            }
        }
    }

    ///// EDIT METHODS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void editProperty(String requestedProperty, Object newValue) {
        properties.editProperty(requestedProperty, newValue); // <-- The replace() method automatically checks for null
    }

    void editConnection(Integer requestedIDNumber, ConnectionsProperties newProperties) {
        connections.editConnection(requestedIDNumber, newProperties);
    }

    void editConnectionProperty(Integer requestedIDNumber, String requestedProperty, Object newValue) {
        if (connections.getConnection(requestedIDNumber) != null) {
            connections.getConnection(requestedIDNumber).editProperty(requestedProperty, newValue);
        }
    }

    ///// CONNECTION VALUE CHANGES ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void incrementConnectionLevel(Integer requestedIDNumber, int newConnectionLevel) {
        if (connections.getConnection(requestedIDNumber) != null) {
            connections.getConnection(requestedIDNumber).connectionLevel += newConnectionLevel;
        }
    }

    void editConnectionLevel(Integer requestedIDNumber, int newConnectionLevel) {
        if (connections.getConnection(requestedIDNumber) != null)
            connections.getConnection(requestedIDNumber).connectionLevel = newConnectionLevel;
    }

    void incrementKnowledgeLevel(int num) {
        knowledgeLevel += num;
    }

    void incrementKnowledgeLevel() {
        knowledgeLevel++;
    }

}