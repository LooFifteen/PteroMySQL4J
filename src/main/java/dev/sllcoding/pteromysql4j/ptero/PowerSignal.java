package dev.sllcoding.pteromysql4j.ptero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PowerSignal {

    START("start", Power.ON),
    STOP("stop", Power.OFF),
    RESTART("restart", Power.ON),
    KILL("kill", Power.OFF);

    private final String toString;
    private final List<Power> success = new ArrayList<>();

    PowerSignal(String toString, Power... success) {
        this.toString = toString;
        this.success.addAll(Arrays.asList(success));
    }

    @Override
    public String toString() {
        return toString;
    }

    public List<Power> getRequiredPower() {
        return success;
    }

}
