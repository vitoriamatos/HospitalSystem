package hospitalsystem.enums;


import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public enum Schedule {

    _2AB("2AB"), _2CD("2CD"), _2EF("2EF"), _2GH("2GH"), _2IJ("2IJ"), _2LM("2LM"), _2NO("2NO"), _2PQ("2PQ"),
    _3AB("3AB"), _3CD("3CD"), _3EF("3EF"), _3GH("3GH"), _3IJ("3IJ"), _3LM("3LM"), _3NO("3NO"), _3PQ("3PQ"),
    _4AB("4AB"), _4CD("4CD"), _4EF("4EF"), _4GH("4GH"), _4IJ("4IJ"), _4LM("4LM"), _4NO("4NO"), _4PQ("4PQ"),
    _5AB("5AB"), _5CD("5CD"), _5EF("5EF"), _5GH("5GH"), _5IJ("5IJ"), _5LM("5LM"), _5NO("5NO"), _5PQ("5PQ"),
    _6AB("6AB"), _6CD("6CD"), _6EF("6EF"), _6GH("6GH"), _6IJ("6IJ"), _6LM("6LM"), _6NO("6NO"), _6PQ("6PQ"),
    _7AB("7AB"), _7CD("7CD"), _7EF("7EF"), _7GH("7GH"), _7IJ("7IJ"), _7LM("7LM"), _7NO("7NO"), _7PQ("7PQ"),
    NONE("NONE"), HOLDER("HOLDER");


    private final String value;

    private final static Map<String, Schedule> map = stream(Schedule.values())
            .collect(
                    toMap(
                            schedule -> schedule.value,
                            schedule -> schedule));


    Schedule(final String schedule) {
        this.value = schedule;
    }



    @Override
    public String toString() {
        return getValue();
    }




//    public static Schedule enumOf(String value){
//        for(Schedule schedule : Schedule.values()){
//            if (schedule.value.equals(value)){
//                return schedule;
//            }
//        }
//        return null;
//    }

    public static Schedule enumOf(String value) {
        return map.get(value);
    }


    // Getters
    public String getValue() {
        return value;
    }

}
