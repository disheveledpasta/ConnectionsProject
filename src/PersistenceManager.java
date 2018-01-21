import com.sun.scenario.effect.Identity;
import com.thoughtworks.xstream.XStream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class PersistenceManager {

    static final Map<String, Class> ALIASES; static {
         ALIASES = new TreeMap<>();
         ALIASES.put("IdentityCard",IdentityCard.class);
         ALIASES.put("Properties",Properties.class);
         ALIASES.put("Connections",Connections.class);
         ALIASES.put("ConnectionsProperties",ConnectionsProperties.class);
    } // doesn't have to private
    static final File IDPATH = new File("IDCards"); // doesn't have to private

    private static void assignAliases(XStream xstream) throws IOException {
        for (Map.Entry<String, Class> e : ALIASES.entrySet()) {
            xstream.alias(e.getKey(), e.getValue());
        }
    }

    static void writeIDs(IdentityCard[] IDCards, int groupID) throws IOException { // implement groupings and add to given grouping
        boolean success = false;
        XStream xstream = new XStream();
        assignAliases(xstream);
        File[] groups = IDPATH.listFiles();
        if (groups != null) {
            groupLoop:
            for (File groupDir : groups) {
                if (groupDir.isDirectory()) {
                    File[] IDCardFiles = groupDir.listFiles();
                    if (IDCardFiles != null) {
                        for (File groupFile : IDCardFiles) {
                            if (groupFile.getName().endsWith(".IDGROUP")) {
                                Group group = (Group)xstream.fromXML(groupFile);
                                if (group.groupID == groupID) {
                                    for (IdentityCard IDCard : IDCards) {
                                        String pathName = (IDPATH + "\\" + groupDir.getName() + "\\" + IDCard.getName() + ".xml");
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(pathName));
                                        String IDCardXML = xstream.toXML(IDCard);
                                        writer.write(IDCardXML);
                                        writer.close();
                                    }
                                    //BufferedWriter writer = new BufferedWriter(new FileWriter(groupFile));
                                    //writer.write(xstream.toXML(group));
                                    success = true;
                                    break groupLoop;
                                }
                                break;
                            }
                        }
                    }
                }
            }
            if (!success) {
                System.out.println("Group not found, creating new group with ID " + groupID + " and name \"Group" + groupID + "\".");
                String groupPathName = (IDPATH + "\\" + "Group" + groupID);
                File groupDir = new File(groupPathName);
                groupDir.mkdirs();
                Group group = new Group(groupID);
                for (IdentityCard IDCard : IDCards) {
                    String pathName = (IDPATH + "\\" + groupDir.getName() + "\\" + IDCard.getName() + ".xml");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(pathName));
                    String IDCardXML = xstream.toXML(IDCard);
                    writer.write(IDCardXML);
                    writer.close();
                }
                String pathName = (groupPathName + "\\" + "groupdata.IDGROUP");
                File groupFile = new File(pathName);
                String groupXML = xstream.toXML(group);
                BufferedWriter writer = new BufferedWriter(new FileWriter(groupFile));
                writer.write(groupXML);
                writer.close();
            }
        }
    }

    static PersistentData readIDs(int groupID) { // implement groupings and read from given grouping
        Map<Integer, IdentityCard> allIDCards = new TreeMap<>();
        Set<String> allNames = new TreeSet<>();
        XStream xstream = new XStream();
        File[] groupDirs = IDPATH.listFiles();
        if (groupDirs != null) {
            groupLoop:
            for (File groupDir : groupDirs) {
                if (groupDir.isDirectory()) {
                    File[] IDCardFiles = groupDir.listFiles();
                    if (IDCardFiles != null) {
                        for (File groupFile : IDCardFiles) {
                            if (groupFile.getName().endsWith(".IDGROUP")) {
                                Group group = (Group) xstream.fromXML(groupFile);
                                if (group.groupID == groupID) {
                                    for (File IDCardFile : IDCardFiles) {
                                        if (IDCardFile.getName().endsWith(".xml")) {
                                            IdentityCard IDCard = (IdentityCard) xstream.fromXML(IDCardFile);
                                            allIDCards.put(IDCard.getIDNumber(), IDCard);
                                            allNames.add(IDCard.getName());
                                        }
                                    }
                                    break groupLoop;
                                }
                            }
                        }
                    }
                }
            }
        }
        return (new PersistentData(allIDCards, allNames));
    }

}