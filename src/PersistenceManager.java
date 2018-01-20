import com.sun.scenario.effect.Identity;
import com.thoughtworks.xstream.XStream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class PersistenceManager {

    public static final Map<String, Class> ALIASES; static {
         ALIASES = new TreeMap<>();
         ALIASES.put("IdentityCard",IdentityCard.class);
         ALIASES.put("Properties",Properties.class);
         ALIASES.put("Connections",Connections.class);
         ALIASES.put("ConnectionsProperties",ConnectionsProperties.class);
    }
    public static final File IDPATH = new File("IDCards");

    private static void assignAliases(XStream xstream) throws IOException {
        for (Map.Entry<String, Class> e : ALIASES.entrySet()) {
            xstream.alias(e.getKey(), e.getValue());
        }
    }

    static void writeIDs(IdentityCard[] IDCards) throws IOException {
        XStream xstream = new XStream();
        assignAliases(xstream);
        for (IdentityCard IDCard : IDCards) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(IDPATH + "\\" + IDCard.getName() + ".xml"));
            String IDCardXML = xstream.toXML(IDCard);
            writer.append(IDCardXML);
            writer.close();
        }
    }

    static PersistentData readIDs() {
        Map<Integer, IdentityCard> allIDCards = new TreeMap<>();
        Set<String> allNames = new TreeSet<>();
        XStream xstream = new XStream();
        File[] IDCards = IDPATH.listFiles();
        if (IDCards != null) {
            for (File IDCardFile : IDCards) {
                if (IDCardFile.getName().endsWith(".xml")) {
                    IdentityCard IDCard = (IdentityCard) xstream.fromXML(IDCardFile);
                    allIDCards.put(IDCard.getIDNumber(), IDCard); // the whole getIDNumber method is a bit sloppy and haphazard
                    allNames.add(IDCard.getName());
                }
            }
        } else {
            //
        }
        return (new PersistentData(allIDCards, allNames));
    }

}