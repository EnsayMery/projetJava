package model;

import java.util.GregorianCalendar;

public class Meal {
    private GregorianCalendar date;
    private String moment;
    private String dishName;

    public Meal(GregorianCalendar date, String moment, String dishName) {
        this.date = date;
        this.moment = moment;
        this.dishName = dishName;
    }

    public String getDishName() {
        return dishName;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getMoment() {
        return moment;
    }
}
