class DebrisEntry {
    public String NAME;
    public String CATALOGNUMBER;
    public String OBJECTTYPE;
    public String TLELINE0;
    public String TLELINE1;
    public String TLELINE2;
    public String ORBITALCLASSIFICATION;
    public String MEANANOMOLY;
    public String EPOCHTIME;
    public String ARGUMENTOFPERIGEE;
    public String INCLINATION;
    public String RAAN;
    public String PERIOD;
    public String SEMIMAJORAXIS;
    public String SEMIMINORAXIS;
    public String PERIGEE;
    public String APOGEE;
    public String ECCENTRICITY;
    public String MEANMOTION;
    public String REVOLUTIONNUMBER;
    public String DRAGCOEFFICIENT;
    public String AREA;
    public String MASS;

    @Override
    public String toString() {
        return NAME+", \n"+
        CATALOGNUMBER+", \n"+
        OBJECTTYPE+", \n"+
        TLELINE0+", \n"+
        TLELINE1+", \n"+
        TLELINE2+", \n"+
        ORBITALCLASSIFICATION+", \n"+
        MEANANOMOLY+", \n"+
        EPOCHTIME+", \n"+
        ARGUMENTOFPERIGEE+", \n"+
        INCLINATION+", \n"+
        RAAN+", \n"+
        PERIOD+", \n"+
        SEMIMAJORAXIS+", \n"+
        SEMIMINORAXIS+", \n"+
        PERIGEE+", \n"+
        APOGEE+", \n"+
        ECCENTRICITY+", \n"+
        MEANMOTION+", \n"+
        REVOLUTIONNUMBER+", \n"+
        DRAGCOEFFICIENT+", \n"+
        AREA+", \n"+
        MASS;
        
    }
}
