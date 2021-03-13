package dev.sllcoding.pteromysql4j.ptero;

public enum Power {

    ON,
    OFF,
    STARTING,
    STOPPING;

    public static Power fromString(String string) {
        switch (string.toLowerCase()) {
            case "on":
                return ON;
            case "off":
                return OFF;
            case "starting":
                return STARTING;
            case "stopping":
                return STOPPING;
        }
        return null;
    }

}
