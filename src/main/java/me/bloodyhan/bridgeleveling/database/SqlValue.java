package me.bloodyhan.bridgeleveling.database;

import lombok.Getter;

/**
 * @author Bloody_Han
 */
@Getter
public class SqlValue{

    private String data;
    private Object value;

    public SqlValue(String data, Object value) {
        this.data = data;
        this.value = value;
    }

}