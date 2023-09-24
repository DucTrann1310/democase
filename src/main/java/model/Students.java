package model;



import java.sql.Date;
import java.time.LocalDate;

public class Students {
    private  int id;
    private  String name;
    private  Date DOB;
    private  EGender gender;

    public Students(Integer id, String name, Date DOB, EGender gender){
        this.id = id;
        this.name = name;
        this.DOB = DOB;
        this.gender = gender;
    }
    public Students(){

    }
    private boolean deleted = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public boolean getDeleted(){
        return deleted;
    }
}
