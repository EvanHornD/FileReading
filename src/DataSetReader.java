import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class DataSetReader {
    
    public static Boolean isCSV(String dataSetFileLocation){
        BufferedReader dataSetReader = openReader(dataSetFileLocation);
        if(dataSetReader==null) return null;

        String line = readLine(dataSetReader);
        int numCommas = line.split(",").length - 1;

        if(numCommas==0) {
            System.out.println("Not parseable header without any commas");
            return false;
        }
        
        while ((line = readLine(dataSetReader))!=null) {
            if ((line.split(",").length - 1) != numCommas) return false;
        }

        closeReader(dataSetReader);
        return true;
    }

    private static BufferedReader openReader(String fileLocation){
        try {
			return new BufferedReader(new FileReader(fileLocation));
		} catch (FileNotFoundException e) {
			System.out.println("dataSet File not found at: " + fileLocation);
            return null;
		}
    }

    private static void closeReader(BufferedReader reader){
        try {
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] parseHeader(String fileLocation){
        BufferedReader dataSetReader = openReader(fileLocation);
        String[] headers = readLine(dataSetReader).split(",");

        String[] parameters = new String[headers.length];

        for (int i = 0; i < headers.length; i++) {
            try {
                parameters[i] = DebrisParameter.valueOf(headers[i].trim().toUpperCase()).toString();
            } catch (Exception e) {
                parameters[i] = "_";
            }
        }
        return parameters;
    }

    public static DebrisEntry[] parseDataEntries(String databaseFileLocation, int numEntries, DebrisParameter[] parameters){
        DebrisEntry[] debris = new DebrisEntry[numEntries];
        BufferedReader databaseReader = openReader(databaseFileLocation);
        String line = readLine(databaseReader);
        int currentLine = -1;
        while ((line = readLine(databaseReader)) != null && (currentLine += 1) < numEntries){
            String[] entry = line.split(",");
            if(entry.length != parameters.length) {
                System.out.println("Error reading the data set, " + line + " doesnt have the same number of parameters as the config");
                return null;
            }
            debris[currentLine] = createDebrisEntry(entry, parameters);
        }
        return debris;
    }

    private static DebrisEntry createDebrisEntry(String[] entry, DebrisParameter[] parameters){
        DebrisEntry debrisEntry = new DebrisEntry();
        for (int i = 0; i < entry.length; i++) {
            if(parameters[i] == null) continue;

            switch (parameters[i]) {
                case NAME:                  debrisEntry.NAME = entry[i]; break;
                case CATALOGNUMBER:         debrisEntry.CATALOGNUMBER = entry[i]; break;
                case OBJECTTYPE:            debrisEntry.OBJECTTYPE = entry[i]; break;
                case TLELINE0:              debrisEntry.TLELINE0 = entry[i]; break;
                case TLELINE1:              debrisEntry.TLELINE1 = entry[i]; break;
                case TLELINE2:              debrisEntry.TLELINE2 = entry[i]; break;
                case ORBITALCLASSIFICATION: debrisEntry.ORBITALCLASSIFICATION = entry[i]; break;
                case MEANANOMOLY:           debrisEntry.MEANANOMOLY = entry[i]; break;
                case EPOCHTIME:             debrisEntry.EPOCHTIME = entry[i]; break;
                case ARGUMENTOFPERIGEE:     debrisEntry.ARGUMENTOFPERIGEE = entry[i]; break;
                case INCLINATION:           debrisEntry.INCLINATION = entry[i]; break;
                case RAAN:                  debrisEntry.RAAN = entry[i]; break;
                case PERIOD:                debrisEntry.PERIOD = entry[i]; break;
                case SEMIMAJORAXIS:         debrisEntry.SEMIMAJORAXIS = entry[i]; break;
                case SEMIMINORAXIS:         debrisEntry.SEMIMINORAXIS = entry[i]; break;
                case PERIGEE:               debrisEntry.PERIGEE = entry[i]; break;
                case APOGEE:                debrisEntry.APOGEE = entry[i]; break;
                case ECCENTRICITY:          debrisEntry.ECCENTRICITY = entry[i]; break;
                case MEANMOTION:            debrisEntry.MEANMOTION = entry[i]; break;
                case REVOLUTIONNUMBER:      debrisEntry.REVOLUTIONNUMBER = entry[i]; break;
                case DRAGCOEFFICIENT:       debrisEntry.DRAGCOEFFICIENT = entry[i]; break;
                case AREA:                  debrisEntry.AREA = entry[i]; break;
                case MASS:                  debrisEntry.MASS = entry[i]; break;
                default: break;
            }
        }
        return debrisEntry;
    }

    private static String readLine(BufferedReader fileReader){
        try {
            return fileReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}