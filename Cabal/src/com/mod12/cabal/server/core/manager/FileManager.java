package com.mod12.cabal.server.core.manager;

import static com.mod12.cabal.server.core.ServerCabal.DEBUG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.util.Constants;
import com.mod12.cabal.common.util.Tuple;
import com.mod12.cabal.server.core.ServerCabal;
import com.mod12.cabal.server.core.faction.Faction;
import com.mod12.cabal.server.core.item.AssociatedSkill;
import com.mod12.cabal.server.core.item.Item;
import com.mod12.cabal.server.core.item.UniqueItem;
import com.mod12.cabal.server.core.location.FactionPresence;
import com.mod12.cabal.server.core.location.Location;
import com.mod12.cabal.server.core.location.Stage;
import com.mod12.cabal.server.core.mission.LogicKeyword;
import com.mod12.cabal.server.core.mission.MissionConcept;
import com.mod12.cabal.server.core.mission.MissionLogic;
import com.mod12.cabal.server.core.person.Name;
import com.mod12.cabal.server.core.person.agent.Ranking;

public class FileManager {

    private static FileManager manager;
    private BufferedReader bin;

    public static FileManager getFileManager() {
        if (manager == null) manager = new FileManager();
        return manager;
    }

    private FileManager() {

    }

    // TODO instead of throwing an exception, return boolean that file loading was unsuccessful
    public boolean initalizeScenario(File filename) throws Exception {
        FileReader fin = new FileReader(filename);
        bin = new BufferedReader(fin);

        this.establishConfigurations();
        this.generateSkillDescriptionTable();
        this.initNames();
        this.initFactions();
        this.generateWorld();
        this.establishFactionsHeadquarters();
        this.createItems();
        this.establishMissionsConcepts();


        return true;

    }

    private boolean establishConfigurations() throws Exception {
        String input = getLine();

        while (!input.equals(Constants.END_DELIM)) {
            String[] config = input.split(Constants.DELIM_KEY_VALUE_PAIR);

            if (config[0].equals(com.mod12.cabal.server.core.Message.SCENARIO)) {
                ServerCabal.getCabalInstance().setScenario(config[1]);
            }
            else if (config[0].equals(com.mod12.cabal.server.core.Message.LOCATION)) {
                ServerCabal.getCabalInstance().setLocation(config[1]);
            }
            else if (config[0].equals(com.mod12.cabal.server.core.Message.TIME)) {
                ServerCabal.getCabalInstance().setTimePeriod(config[1]);
            }
            else if (config[0].equals(com.mod12.cabal.server.core.Message.MONEY_NAME)) {
                FactionManager.getFactionManager().setMoneyName(config[1]);
            }
            else if (config[0].equals(com.mod12.cabal.server.core.Message.TURN_LENGTH)) {
                TurnManager.getTurnManager().setTurnLength(Integer.parseInt(config[1]));
            }
            else if (config[0].equals(com.mod12.cabal.server.core.Message.MAX_TURNS)) {
                TurnManager.getTurnManager().setMaxTurn(Integer.parseInt(config[1]));
            }
            input = getLine();
        }

        return true;
    }

    private boolean generateSkillDescriptionTable() throws IOException {
        Hashtable<String, String> skills = new Hashtable<String, String>();
        ArrayList<String> skillNames = new ArrayList<String>();
        String input = getLine();
        while (!input.equals(Constants.END_DELIM)) { //generates skill flavor text hash table
            String[] skillText = formatSkills(input);
            if (DEBUG) {
                System.out.println("Adding skill: " + skillText[0] + ": " + skillText[1]);
            }
            skillNames.add(skillText[0]);
            skills.put(skillText[0], skillText[1]);
            input = getLine();
        }
        SkillManager.getSkillManager().initializeSkills(skills, skillNames);
        return true;
    }

    private String[] formatSkills(String line) {
         String [] result = line.split(Constants.DELIM_KEY_VALUE_PAIR);
         return result;
    }

    private boolean initNames() throws IOException {
        //Generates List of potential male firstNames
                ArrayList<String> firstNamesMale = new ArrayList<String>();
                String input = getLine();
                while (!input.equals(Constants.END_DELIM)) {
                    firstNamesMale.add(input);
                    input = getLine();
                }
                Name.initializeFirstMaleNames(firstNamesMale);
                if (DEBUG) {
                    for (String name : Name.getFirstMaleNames()) {
                        System.out.println("Male First Name: " + name);
                    }
                }

                //Generates List of potential female firstNames
                ArrayList<String> firstNamesFemale = new ArrayList<String>();
                input = getLine();
                while (!input.equals(Constants.END_DELIM)) {
                    firstNamesFemale.add(input);
                    input = getLine();
                }
                Name.initializeFirstFemaleNames(firstNamesFemale);
                if (DEBUG) {
                    for (String name : Name.getFirstFemaleNames()) {
                        System.out.println("Female First Name: " + name);
                    }

                }

                //Generates List of potential lastNames
                ArrayList<String> lastNames = new ArrayList<String>();
                input = getLine();
                while (!input.equals(Constants.END_DELIM)) {
                    lastNames.add(input);
                    input = getLine();
                }
                Name.initializeLastNames(lastNames);
                if (DEBUG) {
                    for (String name : Name.getLastNames()) {
                        System.out.println("Last Name: " + name);
                    }
                }
                return true;
    }

    private boolean initFactions() throws Exception {
        String input = getLine();
        List<String> factionNames = new ArrayList<String>();
        List<Integer> monies = new ArrayList<Integer>();
        List<String> ranks = new ArrayList<String>();
        List<Integer> sizes = new ArrayList<Integer>();
        while (!input.equals(Constants.END_DELIM)) {
            String[] parts = input.split(Constants.DELIM);
            factionNames.add(parts[0]);
            monies.add(Integer.parseInt(parts[1]));
            ranks.add(parts[2]);
            sizes.add(Integer.parseInt(parts[3]));
            input = getLine();
        }
        FactionManager.getFactionManager().initializeFactions(factionNames, monies, ranks, sizes);
        if (DEBUG) {
            for (Faction faction : FactionManager.getFactionManager().getFactions()) {
                System.out.println(faction.getName());
            }
        }
        return true;
    }

    //the world is generated, and then the presences will be established once they are met in the file.
    private boolean generateWorld() throws IOException {
        String input = getLine();
        Location parent = formatLocation(input);
        Location lastLocation = parent;
        LocationManager.getLocationManager().setTopLevel(parent);
        input = getLine();
        while (!input.equals(Constants.END_DELIM)) {
            if (input.equals(Constants.DELIM_NESTED_START)) {
                parent = lastLocation;
            }
            else if (input.equals(Constants.DELIM_NESTED_END)) {
                parent = parent.getParent();
            }
            else if (input.startsWith("Stage")) {
                Stage temp = formatStage(input);
                parent.addChild(temp);
                temp.setParent(parent);
                lastLocation = temp;
            }
            else{
                Location temp = formatLocation(input);
                parent.addChild(temp);
                temp.setParent(parent);
                lastLocation = temp;
            }
            input = getLine();
        }
        if (DEBUG) {
            for (Location location : LocationManager.getLocationManager().getLocationList()) {
                System.out.println(location.toString());
                System.out.println("    Parent: " + location.getParent());
                System.out.print("    Children: ");
                for (Location child : location.getChildren()) {
                    System.out.print(child.getName() + ",");
                }
                System.out.println("\n");
            }
        }
        return true;
    }

    private boolean establishFactionsHeadquarters() throws IOException {
        String input = getLine();
        List<Tuple<String, String>> factionHeadquarters = new LinkedList<Tuple<String, String>>();
        while (!input.equals(Constants.END_DELIM)) {
            String[] parts = input.split(Constants.DELIM_KEY_VALUE_PAIR);
            factionHeadquarters.add(new Tuple<String, String>(parts[0], parts[1]));
            input = getLine();
        }
        FactionManager.getFactionManager().setFactionHeadquaters(factionHeadquarters);
        return true;
    }

    private boolean createItems() throws IOException {
        String input = getLine();
        List<Item> items = new ArrayList<Item>();
        List<UniqueItem> uniqueItems = new ArrayList<UniqueItem>();
        while (!input.equals(Constants.END_DELIM)) {
            String[] parts = input.split(Constants.DELIM);

            String name = parts[0];
            if (parts.length == 2) {
                Item temp = newItem(name, parts[1]);
                items.add(temp);
            } else {
                UniqueItem temp = newUniqueItem(name, parts[1], parts[2], parts[3]);
                uniqueItems.add(temp);
            }

            input = getLine();
        }
        ItemManager.getItemManager().initializeItems(items, uniqueItems);
        if (DEBUG) {
            for (Item item : ItemManager.getItemManager().getItems()) {
                System.out.println(item);
            }
            for (UniqueItem item : ItemManager.getItemManager().getUniqueItems()) {
                System.out.println(item);
            }
        }
        return true;
    }

    private Item newItem(String name, String skillsAffected) {
        return new Item(name, parseAssociatedSkills(skillsAffected));
    }

    private UniqueItem newUniqueItem(String name, String id, String description, String skillsAffected) {
        return new UniqueItem(name, Integer.parseInt(id), description, parseAssociatedSkills(skillsAffected));
    }

    private List<AssociatedSkill> parseAssociatedSkills(String skillsAffected) {
        String[] values = skillsAffected.split(Constants.DELIM_LIST); //each string represents one tuple pair of skill and impact
        List<AssociatedSkill> affectedSkills = new ArrayList<AssociatedSkill>();
        for (String associatedSkill : values) {
            String[] attributes = associatedSkill.split(Constants.DELIM_KEY_VALUE_PAIR);
            AssociatedSkill skill = new AssociatedSkill(attributes[0], Integer.parseInt(attributes[1]));
            affectedSkills.add(skill);
        }
        return affectedSkills;
    }

    private boolean establishMissionsConcepts() throws IOException {
        String input = getLine();
        MissionManager mgmt = MissionManager.getMissionManager();
        LocationManager lm = LocationManager.getLocationManager();
        while (!input.equals(Constants.END_DELIM)) {
            String[] details = input.split(Constants.DELIM);
            MissionLogic passLogic = parseLogic();
            MissionLogic failLogic = parseLogic();
            int turnsToComplete = Integer.parseInt(details[3]);
            MissionConcept newMission = new MissionConcept(details[0], details[1], Ranking.valueOf(details[2]),
                    passLogic, failLogic, turnsToComplete);
            input = getLine();
            String[] locations = input.split(Constants.DELIM_LIST);
            for (String location : locations) {
                Stage temp = (Stage) lm.getLocation(location);
                temp.addMission(newMission);
            }
            mgmt.addMissionConcept(newMission);

            if (DEBUG) {
                System.out.println("mission concept: " + newMission.getName() + ": " + newMission.getDescriptionText() +
                        " " + newMission.getDifficulty() + ". " + newMission.getSuccessLogic().getMessage());
            }

            input = getLine();
        }
        return true;
    }

    private MissionLogic parseLogic() throws IOException {
        getLine();
        String[] skills = getLine().split(Constants.DELIM);
        String[] logic = getLine().split(Constants.DELIM);
        String message = getLine();
        getLine();
        MissionLogic result = new MissionLogic(LogicKeyword.valueOf(logic[0]), skills[0],
                Integer.parseInt(skills[1]), Double.parseDouble(logic[1]), message);
        return result;
    }

    private Location formatLocation(String line) {
        Location result;
        String[] parts = line.split(Constants.DELIM);
        result = LocationManager.getLocationManager().createLocation(parts[0], parts[1],
                Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), parts[4]);
        return result;
    }

    private Stage formatStage(String line) {
        Stage result;
        String[] parts = line.split(Constants.DELIM);

        if (parts.length > 6) {
            List<Tuple<String, Double>> factionPresences = createFactionPresences(parts[6]);
            List<FactionPresence> presences = LocationManager.getLocationManager().createFactionPresences(factionPresences);

            result = LocationManager.getLocationManager().createStage(parts[1], parts[2],
                    Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), parts[5], presences);
        } else {
            result = LocationManager.getLocationManager().createStage(parts[1], parts[2],
                    Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), parts[5]);
        }

        return result;
    }

    private List<Tuple<String, Double>> createFactionPresences(String line) {
        List<Tuple<String, Double>> presences = new LinkedList<Tuple<String, Double>>();
        String[] parts = line.split(Constants.DELIM_LIST);
        for (String part : parts) {
            String[] pair = part.split(Constants.DELIM_KEY_VALUE_PAIR);
            presences.add(new Tuple<String, Double>(pair[0], Double.parseDouble(pair[1])));
        }
        return presences;
    }

    private String getLine() throws IOException {
        String line = bin.readLine().trim();
        if (line.contains(Constants.COMMENT)) {
            if (DEBUG) System.out.println("comment: " + line.substring(line.indexOf(Constants.COMMENT)));
            line = line.substring(0, line.indexOf(Constants.COMMENT)).trim();
        }

        if (line.isEmpty()) {
            return getLine();
        }

        return line;
    }

}
