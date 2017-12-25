import java.util.*;

class Properties {

    Map<String, Object> propertiesList = new TreeMap<>();

    Properties(Map<String, Object> propertiesListInput) {
        propertiesList = propertiesListInput;
    }

    Properties () {}

    Object getProperty(String i) {
        return propertiesList.get(i);
    }

    void addProperty(String i, Object v) {
        propertiesList.put(i, v);
    }

    void editProperty(String o, Object n) {
        propertiesList.replace(o, n);
    }

    void removeProperty(String i) {
        propertiesList.remove(i);
    }

}