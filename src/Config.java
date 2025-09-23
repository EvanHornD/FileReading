public class Config {
    
    String configFilePath;
    String configuration;

    Config(String filePath, String configuration){
        this.configFilePath = filePath;
        this.configuration = configuration;
    }

    public boolean isConfig(){
        if(!(configFilePath.endsWith(".txt") || configFilePath.endsWith(".csv"))){
            return false;
        }
        
        String[] headers = configuration.split(",");

        for (int i = 0; i < headers.length; i++) {
            try {
                DebrisParameter.valueOf(headers[i]);
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return this.configFilePath + "\n" + this.configuration;
    }
}
