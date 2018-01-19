import com.thoughtworks.xstream.XStream;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

class PersistenceManager {

    public static final Map<String, Class> ALIASES; static {
         ALIASES = new TreeMap<>();
         ALIASES.put("IdentityCard",IdentityCard.class);
         ALIASES.put("Properties",Properties.class);
         ALIASES.put("Connections",Connections.class);
         ALIASES.put("ConnectionsProperties",ConnectionsProperties.class);
    }

    private static void assignAliases(XStream xstream) throws IOException {
        for (Map.Entry<String, Class> e : ALIASES.entrySet()) {
            xstream.alias(e.getKey(), e.getValue());
        }
    }

    static void writeIDs(IdentityCard[] IDCards) throws IOException {
        XStream xstream = new XStream();
        assignAliases(xstream);
        for (IdentityCard IDCard : IDCards) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("IDCards\\" + IDCard.getName() + ".xml"));
            String IDCardXML = xstream.toXML(IDCard);
            writer.append(IDCardXML);
            writer.close();
        }
    }
}