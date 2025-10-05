
import Exceptions.InvalidFileTypeException;
import Exceptions.InvalidParameterException;

public class start {
    static String configPath = "resources\\Config.txt";
    static String TLETestPath = "resources\\DataSets\\TLETest.txt";

    public static void testConfig(){
        ConfigManager config = new ConfigManager(configPath);
        String[] items = {"2","6","8"};
        
        try {
            config.changeConfig("csv.txt", items);
        } catch (InvalidFileTypeException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
        //config.removeConfig("csv.txt");
        for (Object string : config.getStrings()) {
            System.out.println(string);
        }
    }

    public static boolean testDataSetReader(String datasetFilePath){
        if(!DataSetReader.isCSV(datasetFilePath)) return false;

        return true;
    }

    public static void main(String[] args) throws Exception {
        testConfig();
    }
}
