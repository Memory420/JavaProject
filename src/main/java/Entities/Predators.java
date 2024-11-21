package Entities;

import Enums.Gender;

public class Predators extends AbstractEntity {
    private final int viewRange; // подтянем из Simulation
    private Gender gender;
    private int lastMealCooldown;
    private int speed;

    public Predators(int viewRange, Gender gender, int lastMealCooldown, int speed) {
        this.viewRange = viewRange;
        this.gender = gender;
        this.lastMealCooldown = lastMealCooldown;
        this.speed = speed;
    }

    @Override
    public void update() {

    }
}
