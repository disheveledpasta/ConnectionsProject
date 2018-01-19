import java.io.IOException;
import java.util.*;
//import java.io.*;

class IdentityCardManager {

    ///// CONSTANTS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final int MAX_CAPACITY = 1000; // max capacity of TreeMaps, if needed, can be subject to change
    private static final String[] COMMANDS = new String[] {"exit", "Automatically saves changes and exits the program.", "nsexit", "Does not save changes and exits the program.", "help", "Displays the list of commands with descriptions of each.", "createid", "Creates a new ID Card."};// MORE TO COME
    // ^^ HIGHLY OUTDATED ^^
    private static final int COMMANDS_ARRAY_SPLIT = 2;
    // create a list/array/whatever of help strings for each command
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final Map<Integer, IdentityCard> allIDCards = new TreeMap<>(); // final ok?
    private static final Set<String> allNames = new TreeSet<>();

    private static int nextIDNumber = 1;

    private static IdentityCard createIDCard(String nameInput, Map<String, Object> propertiesInput, Map<Integer, ConnectionsProperties> connectionsInput) { // with preexisting connections
        IdentityCard newIDCard = new IdentityCard(nextIDNumber, nameInput, propertiesInput, connectionsInput);
        allIDCards.put(nextIDNumber, newIDCard);
        allNames.add(nameInput);
        nextIDNumber++;
        return newIDCard;
    }

    private static IdentityCard createIDCard(String nameInput, Map<String, Object> propertiesInput) { // without preexisting connections
        IdentityCard newIDCard = new IdentityCard(nextIDNumber, nameInput, propertiesInput);
        allIDCards.put(nextIDNumber, newIDCard);
        allNames.add(nameInput);
        nextIDNumber++;
        return newIDCard;
    }

    private static IdentityCard getIDCard(Integer IDNumber) { //throws NullPointerException { // always put this method in a try block
        return allIDCards.get(IDNumber);
    }

    private static void removeIDCard(Integer IDNumber) {
        allIDCards.remove(IDNumber);
    }

    private static void printArray(String[] a, int multipleOf) {
        for (int i = 0; i < a.length; i += multipleOf) {
            for (int j = i; j < i + multipleOf; j++) {
                System.out.print(a[j]);
                if (j != i + multipleOf - 1) System.out.print(" | ");
            }
            System.out.println();
        }
    }

    private static void printArray(String[] a) {
        for (String m : a) {
            System.out.println(m);
        }
    }

    public static void main(String[] args) {
        mainLoop: while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Input a command: "); // make more user-friendly, add reference to exit or help commands?
            String nextString = sc.nextLine();

            String[] commandArgs = nextString.split("\\s+"); // Splits by whitespace, so the user could theoretically type "/properties      get"
            int argNum = commandArgs.length;
            if (argNum == 0)
                continue;
            if (commandArgs[0] != null) {
                switch (commandArgs[0]) {
                    case "/exit":
                        if (argNum > 1) {
                            System.out.println("Invalid syntax.");
                            continue mainLoop;
                        }
                        System.out.println("Goodbye");


                        // SAVE
                        IdentityCard[] writeArray = allIDCards.values().toArray(new IdentityCard[allIDCards.size()]);
                        try {
                            PersistenceManager.writeIDs(writeArray);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }


                        try {
                            Thread.sleep(1500);
                        }
                        catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        break mainLoop;

                    case "/nsexit":
                        if (argNum > 1) {
                            System.out.println("Invalid syntax.");
                            continue mainLoop;
                        }
                        System.out.print("Are you sure you want to exit without saving (y/n)? ");
                        String confirmation = sc.nextLine();
                        if (!confirmation.toLowerCase().equals("y"))
                            break;
                        System.out.println("Goodbye.");
                        try {
                            Thread.sleep(1500);
                        }
                        catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        break mainLoop;

                    case "/help":
                        printArray(COMMANDS, COMMANDS_ARRAY_SPLIT);
                        break;

                    case "/addproperty": // /addproperty (ID#) (PropertyName) (PropertyValue)
                        if (argNum < 2 || argNum == 3 || argNum > 4) {
                            System.out.println("Invalid syntax.");
                            continue mainLoop;
                        } else if (argNum == 2) {
                            String IDNumberInputString_AddProperty = commandArgs[1];
                            int IDNumberInput_AddProperty;
                            try {
                                IDNumberInput_AddProperty = Integer.parseInt(IDNumberInputString_AddProperty);
                            } catch (NumberFormatException ex) {
                                System.out.println("You did not enter an ID Number.");
                                continue;
                            }
                            if (!allIDCards.containsKey(IDNumberInput_AddProperty)) {
                                System.out.println("That ID Number does not exist.");
                                continue;
                            }
                            while (true) {
                                Scanner sc2 = new Scanner(System.in);
                                System.out.print("Input the name of a property (input /end to end): ");
                                String propertyName = sc2.nextLine();
                                if (propertyName.toLowerCase().equals("/end")) {
                                    break;
                                } else if (propertyName.equals("")) {
                                    System.out.println("Invalid input.");
                                    continue;
                                }
                                if (allIDCards.get(IDNumberInput_AddProperty).getProperties().propertiesList.containsKey(propertyName)) {
                                    System.out.println("Property already exists. Invalid input.");
                                    continue;
                                }
                                System.out.print("Input the value of this property: ");
                                // add support for objects later
                                String propertyValue = sc2.nextLine();
                                if (propertyValue == null) {
                                    System.out.println("Invalid input.");
                                    continue;
                                }
                                allIDCards.get(IDNumberInput_AddProperty).addProperty(propertyName, propertyValue);
                                System.out.println("Success.");
                                break;
                            }
                        }
                        else { // if argNum == 4
                            String IDNumberInputString_AddProperty = commandArgs[1];
                            String PropertyNameInput_AddProperty = commandArgs[2];
                            String PropertyValueInput_AddProperty = commandArgs[3];
                            int IDNumberInput_AddProperty;
                            try {
                                IDNumberInput_AddProperty = Integer.parseInt(IDNumberInputString_AddProperty);
                            } catch (NumberFormatException ex) {
                                System.out.println("You did not enter an ID Number.");
                                continue mainLoop;
                            }
                            if (!allIDCards.containsKey(IDNumberInput_AddProperty)) {
                                System.out.println("That ID Number does not exist.");
                                continue mainLoop;
                            }
                            if (allIDCards.get(IDNumberInput_AddProperty).getProperties().propertiesList.containsKey(PropertyNameInput_AddProperty)) {
                                System.out.println("That property name already exists. Invalid input.");
                                continue mainLoop;
                            }
                            allIDCards.get(IDNumberInput_AddProperty).addProperty(PropertyNameInput_AddProperty, PropertyValueInput_AddProperty);
                            System.out.println("Success.");
                        }
                        break;

                    case "/removeproperty":
                        if (argNum < 3 || argNum > 3) {
                            System.out.println("Invalid syntax.");
                            continue mainLoop;
                        } else { // if argNum == 3
                            String IDNumberInputString_RemoveProperty = commandArgs[1];
                            int IDNumberInput_RemoveProperty;
                            try {
                                IDNumberInput_RemoveProperty = Integer.parseInt(IDNumberInputString_RemoveProperty);
                            } catch (NumberFormatException ex) {
                                System.out.println("You did not enter an ID Number.");
                                continue;
                            }
                            if (!allIDCards.containsKey(IDNumberInput_RemoveProperty)) {
                                System.out.println("That ID Number does not exist.");
                                continue;
                            }
                            String PropertyInput_RemoveProperty = commandArgs[2];
                            if (allIDCards.get(IDNumberInput_RemoveProperty).getProperty(PropertyInput_RemoveProperty) == null) {
                                System.out.println("That property does not exist.");
                                continue;
                            }
                            allIDCards.get(IDNumberInput_RemoveProperty).getProperties().propertiesList.remove(PropertyInput_RemoveProperty);
                            System.out.println("Success.");
                        }
                        break;

                    case "/addconnection":
                        if (argNum != 2 && argNum != 5) {
                            System.out.println("Invalid syntax.");
                            continue mainLoop;
                        } else if (argNum == 2) {
                            String IDNumberInputString_AddConnection = commandArgs[1];
                            int IDNumberInput_AddConnection;
                            try {
                                IDNumberInput_AddConnection = Integer.parseInt(IDNumberInputString_AddConnection);
                            } catch (NumberFormatException ex) {
                                System.out.println("You did not enter an ID Number.");
                                continue;
                            }
                            if (!allIDCards.containsKey(IDNumberInput_AddConnection)) {
                                System.out.println("That ID Number does not exist.");
                                continue;
                            }
                            while (true) {
                                Scanner sc2 = new Scanner(System.in);
                                System.out.print("Input the ID Card to make a connection with ID #" + IDNumberInput_AddConnection + " (/end to end): ");
                                String IDNumber2InputString = sc2.next();
                                if (IDNumber2InputString.toLowerCase().equals("/end")) {
                                    break;
                                } else if (IDNumber2InputString.equals("")) {
                                    System.out.println("Invalid input.");
                                    break;
                                }
                                int IDNumber2Input;
                                try {
                                    IDNumber2Input = Integer.parseInt(IDNumber2InputString);
                                } catch (NumberFormatException ex) {
                                    System.out.println("You did not enter an ID Number.");
                                    continue;
                                }
                                if (!allIDCards.containsKey(IDNumber2Input)) {
                                    System.out.println("That ID Number does not exist.");
                                    continue;
                                }
                                if (allIDCards.get(IDNumberInput_AddConnection).getConnection(IDNumber2Input) != null) {
                                    System.out.println("Connection already exists. Invalid input.");
                                    continue;
                                }
                                Map<String, Object> newConnectionsProperties = new TreeMap<>();
                                while (true) {
                                    Scanner sc3 = new Scanner(System.in);
                                    System.out.print("Input the name of a property of the connection with ID #" + IDNumberInput_AddConnection + " (input /end to end): ");
                                    String propertyName = sc3.nextLine();
                                    if (propertyName.equals("/end")) {
                                        break;
                                    } else if (propertyName.equals("")) {
                                        System.out.println("Invalid input.");
                                        continue;
                                    }
                                    if (newConnectionsProperties.containsKey(propertyName)) {
                                        System.out.println("Property already exists. Invalid input.");
                                        continue;
                                    }

                                    System.out.print("Input the value of this property: ");
                                    // add support for objects later
                                    String propertyValue = sc3.nextLine();
                                    if (propertyValue == null) {
                                        System.out.println("Invalid input.");
                                        continue;
                                    }
                                    newConnectionsProperties.put(propertyName, propertyValue);
                                }
                                System.out.print("Input initial connection level (/end for none): ");
                                String initialConnectionLevelString = sc.next();
                                int initialConnectionLevel;
                                if (!initialConnectionLevelString.toLowerCase().equals("/end")) {
                                    while (true) {
                                        try {
                                            initialConnectionLevel = Integer.parseInt(initialConnectionLevelString);
                                        } catch (NumberFormatException ex) {
                                            System.out.println("You did not enter a number.");
                                            continue;
                                        }
                                        if (initialConnectionLevel >= 0)
                                            break;
                                        else System.out.println("You cannot enter a negative value.");
                                    }
                                } else {
                                    initialConnectionLevel = 0;
                                }
                                allIDCards.get(IDNumberInput_AddConnection).addConnection(IDNumber2Input, new ConnectionsProperties(newConnectionsProperties, initialConnectionLevel));
                                break;
                            }
                            System.out.println("Success.");
                        }
                        break;

                    case "/removeconnection":
                        if (argNum < 3 || argNum > 3) { // FOR NOW; SOON TO BE A 2-ARG VERSION
                            System.out.println("Invalid syntax.");
                            continue;
                        } else { // if (argNum == 3)
                            String IDNumberInputString_RemoveConnection = commandArgs[1];
                            String IDNumberInput2String_RemoveConnection = commandArgs[2];
                            int IDNumberInput_RemoveConnection;
                            int IDNumberInput2_RemoveConnection;
                            try {
                                IDNumberInput_RemoveConnection = Integer.parseInt(IDNumberInputString_RemoveConnection);
                                IDNumberInput2_RemoveConnection = Integer.parseInt(IDNumberInput2String_RemoveConnection);
                            } catch (NumberFormatException ex) {
                                System.out.println("You did not enter an ID Number.");
                                continue;
                            }
                            if (!allIDCards.containsKey(IDNumberInput_RemoveConnection)) {
                                System.out.println("That ID Number does not exist.");
                                continue;
                            }
                            if (allIDCards.get(IDNumberInput_RemoveConnection).getConnection(IDNumberInput2_RemoveConnection) == null) {
                                System.out.println("That ID Number does not exist.");
                                continue;
                            }
                            allIDCards.get(IDNumberInput_RemoveConnection).getConnections().removeConnection(IDNumberInput2_RemoveConnection);
                            System.out.println("Success.");
                        }
                        // make it so it lists the possible connections to remove in the case of 2 arguments
                        break;

                    case "/createid":
                        if (argNum < 2 || argNum > 2) {
                            System.out.println("Invalid syntax.");
                            continue mainLoop;
                        }
                        String name = commandArgs[1]; // handle name
                        if (allNames.contains(name)) {
                            while (true) {
                                Scanner sc2 = new Scanner(System.in);
                                System.out.print("This name is already in use. Continue? (y/n): ");
                                String answer = sc2.next();
                                switch (answer) {
                                    case ("y"): break;
                                    case ("n"): continue mainLoop;
                                    default: System.out.println("Invalid input.");
                                }
                            }
                        }

                        // PROPERTIES
                        Map<String, Object> newProperties = new TreeMap<>();
                        while (true) {
                            Scanner sc2 = new Scanner(System.in);
                            System.out.print("Input the name of a property (input /end to end): ");
                            String propertyName = sc2.nextLine();
                            if (propertyName.toLowerCase().equals("/end")) {
                                break;
                            } else if (propertyName.equals("")) {
                                System.out.println("Invalid input.");
                                continue;
                            }
                            if (newProperties.containsKey(propertyName)) {
                                System.out.println("Property already exists. Invalid input.");
                                continue;
                            }

                            System.out.print("Input the value of this property: ");
                            // add support for objects later
                            String propertyValue = sc2.nextLine();
                            if (propertyValue == null) {
                                System.out.println("Invalid input.");
                                continue;
                            }
                            newProperties.put(propertyName, propertyValue);
                        }

                        // CONNECTIONS
                        System.out.print("Do you wish to enter connections to other ID Cards? (y/n): ");
                        String requestedConnections = sc.next();

                        Map<Integer, ConnectionsProperties> newConnections = new TreeMap<>();
                        if (requestedConnections.toLowerCase().equals("y")) {
                            while (true) { // encompasses all connections
                                Scanner sc2 = new Scanner(System.in);
                                System.out.print("Input the ID Number of the card to connect to (/end to end): ");
                                String IDNumberInputString_CreateID = sc2.nextLine();
                                int IDNumberInput_CreateID;
                                if (IDNumberInputString_CreateID.toLowerCase().equals("/end"))
                                    break;
                                try {
                                    IDNumberInput_CreateID = Integer.parseInt(IDNumberInputString_CreateID);
                                } catch (NumberFormatException ex) {
                                    System.out.println("You did not enter an ID Number.");
                                    continue;
                                }
                                if (!allIDCards.containsKey(IDNumberInput_CreateID)) {
                                    System.out.println("That ID Number does not exist.");
                                    continue;
                                }
                                Map<String, Object> newConnectionsProperties = new TreeMap<>();
                                while (true) { // encompasses all connectionsProperties
                                    Scanner sc3 = new Scanner(System.in);
                                    System.out.print("Input the name of a property of the connection with " + IDNumberInput_CreateID + " (input /end to end): ");
                                    String propertyName = sc3.nextLine();
                                    if (propertyName.equals("/end")) {
                                        break;
                                    } else if (propertyName.equals("")) {
                                        System.out.println("Invalid input.");
                                        continue;
                                    }
                                    if (newConnectionsProperties.containsKey(propertyName)) {
                                        System.out.println("Property already exists. Invalid input.");
                                        continue;
                                    }

                                    System.out.print("Input the value of this property: ");
                                    // add support for objects later
                                    String propertyValue = sc2.nextLine();
                                    if (propertyValue == null) {
                                        System.out.println("Invalid input.");
                                        continue;
                                    }

                                    newConnectionsProperties.put(propertyName, propertyValue);
                                }

                                System.out.print("Input initial connection level (/end for none): ");
                                String initialConnectionLevelString = sc.next();
                                int initialConnectionLevel;
                                if (!initialConnectionLevelString.toLowerCase().equals("/end")) {
                                    while (true) {
                                        try {
                                            initialConnectionLevel = Integer.parseInt(initialConnectionLevelString);
                                        } catch (NumberFormatException ex) {
                                            System.out.println("You did not enter a number.");
                                            continue;
                                        }
                                        if (initialConnectionLevel >= 0)
                                            break;
                                        else
                                            System.out.println("You cannot enter a negative value.");
                                        break;
                                    }
                                } else {
                                    initialConnectionLevel = 0;
                                }
                                newConnections.put(IDNumberInput_CreateID, new ConnectionsProperties(newConnectionsProperties, initialConnectionLevel));
                            }
                            createIDCard(name, newProperties, newConnections);
                            System.out.println("Success. IDCard Number = " + (nextIDNumber - 1)); // possible error?
                            break;
                        }
                        createIDCard(name, newProperties);
                        System.out.println("Success. IDCard Number = " + (nextIDNumber - 1)); // possible error?
                        break;

                    case "/removeid":
                        if (argNum < 2 || argNum > 2) {
                            System.out.println("Invalid syntax.");
                            continue mainLoop;
                        }
                        String IDNumberInputString_RemoveID = commandArgs[1];
                        int IDNumberInput_RemoveID;
                        try {
                            IDNumberInput_RemoveID = Integer.parseInt(IDNumberInputString_RemoveID);
                        } catch (NumberFormatException ex) {
                            System.out.println("You did not enter an ID Number.");
                            continue mainLoop;
                        }
                        if (!allIDCards.containsKey(IDNumberInput_RemoveID)) {
                            System.out.println("That ID Number does not exist.");
                            continue mainLoop;
                        }
                        removeIDCard(IDNumberInput_RemoveID);
                        allIDCards.remove(IDNumberInput_RemoveID);
                        System.out.println("Success.");
                        break;

                    case "/showallids": // shape this up
                        if (argNum > 1) {
                            System.out.println("Invalid syntax");
                            continue mainLoop;
                        }
                        for (Map.Entry<Integer, IdentityCard> e : allIDCards.entrySet()) {
                            System.out.println((e.getKey()) + " : " + (e.getValue().getName()));
                            if (e.getValue().getProperties() == null) {
                                System.out.println("No properties found for " + e.getValue().getName() + ".");
                                continue;
                            }
                            System.out.println("Properties: " + e.getValue().getProperties().propertiesList.keySet());
                            System.out.println("Property Values: " + e.getValue().getProperties().propertiesList.values());
                            if (e.getValue().getConnections() == null) {
                                System.out.println("No connections found for " + e.getValue().getName() + ".");
                                continue;
                            }
                            for (Map.Entry<Integer, ConnectionsProperties> f : e.getValue().getConnections().connectionsList.entrySet()) {
                                System.out.println("Connections between " + e.getKey() + " and " + f.getKey() + ":");
                                System.out.println("Connection Properties: " + f.getValue().propertiesList.keySet());
                                System.out.println("Connection Property Values: " + f.getValue().propertiesList.values());
                            }
                            System.out.println("-----");
                        }
                        break;

                    case "/nfid": // name from id number
                        if (argNum < 2 || argNum > 2) {
                            System.out.println("Invalid syntax.");
                            continue mainLoop;
                        }
                        String requestedIDNumberString = commandArgs[1];
                        int requestedIDNumber;

                        try {
                            requestedIDNumber = Integer.parseInt(requestedIDNumberString);
                        }
                        catch (NumberFormatException ex) {
                            System.out.println("You did not enter an ID Number.");
                            continue mainLoop;
                        }
                        if (!allIDCards.containsKey(requestedIDNumber)) {
                            System.out.println("That ID Number does not exist.");
                            continue mainLoop;
                        }
                        System.out.println("Name for ID #" + requestedIDNumber + ": " + allIDCards.get(requestedIDNumber).getName());
                        break;

                    default:
                        System.out.println("Invalid command.");
                }
            }
        }
    }
}