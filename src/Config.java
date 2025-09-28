import Exceptions.InvalidParameterException;
import Exceptions.InvalidFileTypeException;

public class Config {
    
    String configFilePath;
    String configuration;

    Config(String filePath, String configuration) throws InvalidFileTypeException, InvalidParameterException {
        this.configFilePath = filePath;
        this.configuration = configuration;

        if(!(filePath.endsWith(".txt") || filePath.endsWith(".csv"))){
            int index = filePath.split("\\.").length -1;
            String fileType = filePath.split("\\.")[index];
            throw new InvalidFileTypeException("DataSets can only be of FileType: .txt, and .csv The filetype:" + fileType + " is an invalid filetype");
        }

        String[] headers = configuration.split(",");
        for (int i = 0; i < headers.length; i++) {
            try {
                DebrisParameter.valueOf(headers[i]);
            } catch (Exception e) {
                throw new InvalidParameterException("The Parameter " + headers[i] + " is not a valid Debris Parameter");
            }
        }
    }

    @Override
    public String toString() {
        return this.configFilePath + "\n" + this.configuration;
    }
}
