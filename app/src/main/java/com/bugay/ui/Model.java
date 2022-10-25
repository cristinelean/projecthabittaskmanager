package com.bugay.ui;

public class Model {

    String taskm, timem, datem, id;
    public Model(){

    }
    public Model(String id, String task, String time, String date){
        this.id = id;
        this.taskm = task;
        this.timem = time;
        this.datem = date;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getTaskm() {

        return taskm;
    }

    public void setTaskm(String taskm) {

        this.taskm = taskm;
    }

    public String getTimem() {

        return timem;
    }

    public void setTimem(String timem) {

        this.timem = timem;
    }

    public String getDatem() {

        return datem;
    }

    public void setDatem(String datem) {

        this.timem = datem;
    }
}
