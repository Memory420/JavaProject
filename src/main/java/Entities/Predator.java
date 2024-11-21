package Entities;

import Enums.Gender;
import Utils.IMovable;

public class Predator extends AbstractEntity implements IMovable {
    private final int viewRange; // подтянем из Simulation
    private Gender gender;
    private int lastMealCooldown;
    private int speed;

    public Predator(int viewRange, Gender gender, int lastMealCooldown, int speed) {
        this.viewRange = viewRange;
        this.gender = gender;
        this.lastMealCooldown = lastMealCooldown;
        this.speed = speed;
    }

    @Override
    public void update() {

    }

    @Override
    public void move() {

    }
}
