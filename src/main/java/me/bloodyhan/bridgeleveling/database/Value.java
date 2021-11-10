package me.bloodyhan.bridgeleveling.database;

import lombok.Getter;

/**
 * @author Bloody_Han
 */
@Getter
public class Value{

    private ValueType type;
    private String name;

    public Value(ValueType type, String name) {
        this.type = type;
        this.name = name;
    }

}
