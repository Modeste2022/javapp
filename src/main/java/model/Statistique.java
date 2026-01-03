package model;
import model.dao.IStatistique;

import java.io.Serializable;

public class Statistique implements IStatistique, Serializable {
    private int _1PT;
    private int _2PTS;
    private int _3PTS;
    private int fautes;
    private int rebonds;
    private int assist;
    private int Contre;

    public Statistique() {
        _1PT = 0;
        _2PTS = 0;
        _3PTS = 0;
        fautes = 0;
        rebonds = 0;
        assist = 0;
        Contre=0;
    }

    public Statistique(int _1PT, int _2PTS, int _3PTS, int fautes, int rebonds, int assist, int Contre) {
        this._1PT = _1PT;
        this._2PTS = _2PTS;
        this._3PTS = _3PTS;
        this.fautes = fautes;
        this.rebonds = rebonds;
        this.assist = assist;
        this.Contre=Contre;
    }

    // Getters et Setters
    public int get_1PT() {
        return _1PT;
    }

    public void set_1PT(int _1PT) {
        this._1PT = _1PT;
    }

    public int get_2PTS() {
        return _2PTS;
    }

    public void set_2PTS(int _2PTS) {
        this._2PTS = _2PTS;
    }

    public int get_3PTS() {
        return _3PTS;
    }

    public void set_3PTS(int _3PTS) {
        this._3PTS = _3PTS;
    }

    public int getFautes() {
        return fautes;
    }

    public void setFautes(int fautes) {
        this.fautes = fautes;
    }

    public int getContre() {
        return Contre;
    }

    public void setContre(int contre) {
        this.Contre = contre;
    }

    public int getRebonds() {
        return rebonds;
    }

    public void setRebonds(int rebonds) {
        this.rebonds = rebonds;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }


    public int getTotalPoints() {
        return _1PT + _2PTS * 2 + _3PTS * 3;
    }

    @Override
    public String toString() {
        return "Statistique{" +
                "_1PT=" + _1PT +
                ", _2PTS=" + _2PTS +
                ", _3PTS=" + _3PTS +
                ", fautes=" + fautes +
                ", rebonds=" + rebonds +
                ", assist=" + assist +
                ", Contre=" + Contre +
                ", pointsTotal=" + getTotalPoints() +
                '}';
    }

    @Override
    public void incrementer(String typeStat) {
        switch (typeStat) {
            case "1PT" -> _1PT++;
            case "2PTS" -> _2PTS++;
            case "3PTS" -> _3PTS++;
            case "fautes" -> fautes++;
            case "rebonds" -> rebonds++;
            case "assists" -> assist++;
            case "contres" -> Contre++;
        }
    }

    @Override
    public void decrementer(String typeStat) {
        switch (typeStat) {
            case "1PT" -> _1PT--;
            case "2PTS" -> _2PTS--;
            case "3PTS" -> _3PTS--;
            case "fautes" -> fautes--;
            case "rebonds" -> rebonds--;
            case "assists" -> assist--;
            case "contres" -> Contre--;
        }
    }


    @Override
    public int getStat(String typeStat) {
        return switch (typeStat) {
            case "1PT" -> _1PT;
            case "2PTS" -> _2PTS;
            case "3PTS" -> _3PTS;
            case "fautes" -> fautes;
            case "rebonds" -> rebonds;
            case "assists" -> assist;
            case "contres" -> Contre;
            default -> 0;
        };
    }

    @Override
    public void reinitialiser() {
        _1PT = 0;
        _2PTS = 0;
        _3PTS = 0;
        fautes = 0;
        rebonds = 0;
        assist = 0;
        Contre = 0;
    }



}
