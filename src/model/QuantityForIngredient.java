package model;

public class QuantityForIngredient {

    private String name;
    private Double calories;
    private Double volume;
    private String unit;

    public QuantityForIngredient(String name, Double calories, Double volume, String unit) {
        this.name = name;
        this.calories = calories;
        this.volume = volume;
        this.unit = unit;
    }

    public Double getVolume() {
        return volume;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    public Double getCalories() {
        return calories;
    }
}
