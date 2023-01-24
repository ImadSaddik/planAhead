package com.example.mytimetable;

public class Utils {
    // Element type
    public static final String TYPE_ONE = "COURS";
    public static final String TYPE_TWO = "TP";
    public static final String[] TYPES = {TYPE_ONE, TYPE_TWO};

    // Days
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY = "TUESDAY";
    public static final String WEDNESDAY = "WEDNESDAY";
    public static final String THURSDAY = "THURSDAY";
    public static final String FRIDAY = "FRIDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final String[] DAYS_OF_THE_WEEK = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
        SATURDAY};

    // Elements
    public static final String VM = "Mécanique vibratoire";
    public static final String IA = "Simulation en IA";
    public static final String COMPT = "Comptabilite";
    public static final String TT = "Transferts thermiques";
    public static final String INFO = "Informatique industrielle";
    public static final String MAINT = "Maintenance et fiabilité";
    public static final String FR = "Communication et dév...";
    public static final String EN = "English for engineering";
    public static final String SP = "Système de production";
    public static final String GP = "Gestion de production";
    public static final String ECO = "Eco conception";
    public static final String TURBO = "Turbomachines";
    public static final String BD = "Base de données";
    public static final String MACHINES = "Machines thermiques";
    public static final String[] ELEMENTS = {VM, COMPT, TT, INFO, MAINT, FR, IA,
        EN, SP, GP, ECO, TURBO, BD, MACHINES};

    // Weeks
    public static final String[] WEEKS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
            "12", "13", "14"};

    // KEYS
    public static final String DAY_STRING_KEY = "DAY";
    public static final String WEEK_STRING_KEY = "WEEK";

    public static int dayToInt(String day) {
        switch (day) {
            case MONDAY: return 1;
            case TUESDAY: return 2;
            case WEDNESDAY: return 3;
            case THURSDAY: return 4;
            case FRIDAY: return 5;
            case SATURDAY: return 6;
            default: return -1;
        }
    }
}
