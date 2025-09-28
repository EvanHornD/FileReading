import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exceptions.InvalidFileTypeException;
import Exceptions.InvalidParameterException;

class ConfigManager {

	private String configFilePath;
	private Map<String, Integer> indexMap;
    private List<Config> configs;

    ConfigManager(String configFilePath){
        this.configFilePath = configFilePath;
        initConfig();
    }
    //#region getters

    public Object[] getStrings(){
        return configs.toArray();
    }

    //#endregion

    //#region initialization
	public void initConfig() {
        
		try {
			BufferedReader configReader = new BufferedReader(new FileReader(configFilePath));
			indexFile(configReader);
            try {
                configReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
		} catch (FileNotFoundException e) {
			System.out.println("Config File not found at: " + configFilePath);
			createFile();
			indexMap = new HashMap<>();
		}
	}

	private void indexFile(BufferedReader fileReader) {
        boolean invalidConfigFile = false;
        String line;
        String line2;
        indexMap = new HashMap<>();
        configs = new ArrayList<>();
		while((line = readLine(fileReader)) != null && (line2 = readLine(fileReader)) != null){
            try {
                Config newConfig = new Config(line.strip(), line2.strip());
                configs.add(newConfig);
                indexMap.put(line, configs.size()-1);
            } catch (InvalidFileTypeException | InvalidParameterException e) {
                invalidConfigFile = true;
                System.out.println("Invalid Config:" + line);
                e.printStackTrace();
                continue;
            }
		}
        if (invalidConfigFile) updateFile();
	}

    private String readLine(BufferedReader fileReader){
        try {
            return fileReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	private void createFile(){
		File configFile = new File(configFilePath);
		try{
			configFile.createNewFile();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

    private void updateFile(){
        String fileString = "";
        for (Config config : configs) {
            fileString+=config.toString()+"\n";
        }
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(configFilePath));
            fileWriter.write(fileString);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //#endregion

    //#region  changing config file
    public void changeConfig(String fileName, String[] headerNames) throws InvalidFileTypeException, InvalidParameterException{

        String configString = "";
        for (int i = 0; i < headerNames.length; i++) {
            if(i == (headerNames.length-1)){
                configString += (headerNames[i]);
                continue;
            }
            configString += (headerNames[i] + ',');
        }

        Config configChange = new Config(fileName, configString);

        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(configFilePath));
            if (!indexMap.containsKey(fileName)){
                fileWriter.write(addConfig(configChange));
            }else{
                fileWriter.write(replaceConfig(fileName, configChange));
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String addConfig(Config config){
        String stringBeforeConfig = getStringBeforeConfig(configs.size());
        configs.add(config);
        return stringBeforeConfig + config.toString();
    }

    private String replaceConfig(String fileName, Config config){
        String stringBeforeConfig = getStringBeforeConfig(indexMap.get(fileName));
        String stringAfterConfig = getStringAfterConfig(indexMap.get(fileName));

        configs.set(indexMap.get(fileName), config);

        return stringBeforeConfig + (config.toString() + "\n") + stringAfterConfig;
    }

    public void removeConfig(String fileName){
        if(!indexMap.containsKey(fileName)) return;
        String stringBeforeLine = getStringBeforeConfig(indexMap.get(fileName));
        String stringAfterLine = getStringAfterConfig(indexMap.get(fileName));
        String configString = (stringBeforeLine + stringAfterLine);
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(configFilePath));
            if (indexMap.get(fileName.strip())!=null){
                fileWriter.write(configString);
            } else {
                System.out.println("Couldn't fine the file: " + fileName);
            }
            fileWriter.flush();
            fileWriter.close();

            configs.remove((int)indexMap.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStringBeforeConfig(int configNumber){
        String configString = "";
        for (int i = 0; i < configNumber; i++) {
            configString += (configs.get(i).toString() + '\n');
        }
        return configString;
    }

    private String getStringAfterConfig(int configNumber){
        String configString = "";
        for (int i = (configNumber+1); i < configs.size(); i++) {
            configString += (configs.get(i).toString() + '\n');
        }
        return configString;
    }

    //#endregion

    public DebrisParameter[] getConfigParameters(String filePath){

        String[] stringParameters = configs.get(indexMap.get(filePath)).configuration.split(",");

        DebrisParameter[] parameters = new DebrisParameter[stringParameters.length];

        for (int i = 0; i < stringParameters.length; i++) {
            try {
                parameters[i] = DebrisParameter.valueOf(stringParameters[i]);
            } catch (Exception e) {
                parameters[i] = null;
            }
        }
        return parameters;
    }
}
