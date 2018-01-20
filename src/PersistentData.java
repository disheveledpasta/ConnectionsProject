import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class PersistentData {
    Map<Integer, IdentityCard> allIDCards = new TreeMap<>();
    Set<String> allNames = new TreeSet<>();
    PersistentData(Map<Integer, IdentityCard> allIDCards, Set<String> allNames) {
        this.allIDCards = allIDCards;
        this.allNames = allNames;
    }
}