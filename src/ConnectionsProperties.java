import java.util.*;

class ConnectionsProperties extends Properties {
    int connectionLevel = 0;

    ConnectionsProperties(Map<String, Object> propertiesListInput, int connectionLevelInput) {
        propertiesList.putAll(propertiesListInput);
        connectionLevel = connectionLevelInput;
    }

    ConnectionsProperties(Map<String, Object> propertiesListInput) {
        propertiesList.putAll(propertiesListInput);
    }

    ConnectionsProperties () {}
}
