package model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String mail;
    private int javaScore;
    @Setter
    private boolean isJavaComp;
    private int dsaScore;
    @Setter
    private boolean isDsaComp;
    private int dataBasesScore;
    @Setter
    private boolean isDbComp;
    private int springScore;
    @Setter
    private boolean isSpringComp;

    public void setJavaScore(int javaScore) {
        this.javaScore += javaScore;
    }

    public void setDsaScore(int dsaScore) {
        this.dsaScore += dsaScore;
    }

    public void setDataBasesScore(int dataBasesScore) {
        this.dataBasesScore += dataBasesScore;
    }

    public void setSpringScore(int springScore) {
        this.springScore += springScore;
    }

    public User(int id, String firstName, String lastName, String mail, int javaScore, int dsaScore, int dataBasesScore, int springScore) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.javaScore = javaScore;
        this.dsaScore = dsaScore;
        this.dataBasesScore = dataBasesScore;
        this.springScore = springScore;
        isJavaComp = false;
        isDsaComp = false;
        isDbComp = false;
        isSpringComp = false;
    }
}
