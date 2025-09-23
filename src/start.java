import java.util.Arrays;

public class start {
    static String configPath = "resources\\Config.txt";
    static String TLETestPath = "resources\\DataSets\\TLETest.txt";

    public static void testConfig(){
        ConfigManager config = new ConfigManager(configPath);
        String[] items = {"2","7","8"};
        config.changeConfig("csv.txt", items);
        //config.removeConfig("csv.txt");
        for (Object string : config.getStrings()) {
            System.out.println((String)string);
        }
    }

    public static boolean testDataSetReader(String datasetFilePath){
        if(!DataSetReader.isCSV(datasetFilePath)) return false;

        return true;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(testDataSetReader(TLETestPath));
        if(!testDataSetReader(TLETestPath)) return;

        System.out.println(Arrays.deepToString(DataSetReader.parseHeader(TLETestPath)));
        ConfigManager config = new ConfigManager(configPath);
        DebrisParameter[] parameters = config.getConfigParameters(TLETestPath);
        for (DebrisParameter debrisParameter : parameters) {
            System.out.println(debrisParameter);
        }
        DebrisEntry[] debris = DataSetReader.parseDataEntries(TLETestPath, 2, parameters);
        for (DebrisEntry debrisEntry : debris) {
            System.out.println(debrisEntry);
        }
    }
}
