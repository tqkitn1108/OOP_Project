package model2;

import model.DBInteract;

import java.util.List;
import java.util.Map;

public class DataModel {
    private static DataModel instance;

    private Integer number;

    private Map<Integer, List<Integer>> userAnswer;

    private DBInteract dbInteract;

    private DataModel() {

    }

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setUserAnswer(Map<Integer, List<Integer>> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Map<Integer, List<Integer>> getUserAnswer() {
        return userAnswer;
    }

    public void setDbInteract(DBInteract dbInteract) {
        this.dbInteract = dbInteract;
    }

    public DBInteract getDbInteract() {
        return dbInteract;
    }
}