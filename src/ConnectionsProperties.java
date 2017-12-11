import java.util.*;

class ConnectionsProperties extends Properties {
    int connectionLevel = 0;
    //Map<String, Object> propertiesList = new TreeMap<>();

    ConnectionsProperties(Map<String, Object> propertiesListInput, int connectionLevelInput) {
        propertiesList = propertiesListInput;
        connectionLevel = connectionLevelInput;
    }

    ConnectionsProperties(Map<String, Object> propertiesListInput) {
        propertiesList = propertiesListInput;
    }

    ConnectionsProperties () {}
}
