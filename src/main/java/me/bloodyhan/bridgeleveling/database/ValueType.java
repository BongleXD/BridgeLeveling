package me.bloodyhan.bridgeleveling.database;

/**
 * @author Bloody_Han
 */

public enum ValueType{

    ID,
    SQLITE_ID,
    STRING,
    SQLITE_STRING,
    DECIMAL,
    INTEGER;

    public String getStatement(){
        switch (this){
            case ID:
                return "INT NOT NULL AUTO_INCREMENT PRIMARY KEY";
            case SQLITE_ID:
                return "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT";
            case STRING:
                return "VARCHAR(200) CHARACTER SET utf8";
            case SQLITE_STRING:
                return "VARCHAR(200)";
            case DECIMAL:
                return "DOUBLE";
            case INTEGER:
                return "INTEGER";
            default:
                return null;
        }
    }
}
