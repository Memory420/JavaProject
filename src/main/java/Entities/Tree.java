package Entities;

import Entities.AbstractEntity;
import Utils.Cell;
import Utils.GameMap;
import javafx.scene.control.Button;

import java.util.Collections;
import java.util.List;

public class Tree extends AbstractEntity {
    private static double chance = 0.27; // Текущий шанс размножения
    private Cell cell;
    private int age;

    public Tree(Cell cell, int age) {
        this.cell = cell;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public static void setChance(double newChance) {
        chance = newChance;
    }

    public static double getChance() {
        return chance;
    }

    @Override
    public void update() {
        Button btn = cell.getButton();
        btn.setText(String.valueOf(age));

        if (age <= 0) {
            removeTree();
            return;
        }

        age--;

        if (Math.random() < chance) {
            GameMap gameMap = cell.getGameMap();
            List<Cell> neighbors = gameMap.getCellsAround(cell.getPosition(), 1);

            Collections.shuffle(neighbors);

            for (Cell neighbor : neighbors) {
                if (neighbor.getEntity() == null) {
                    neighbor.setEntity(new Tree(neighbor, (int) (Math.random() * 10 + 1)));
                    Button button = neighbor.getButton();
                    if (button != null) {
                        button.setStyle("-fx-background-color: green;");
                    }
                    break;
                }
            }
        }
    }

    private void removeTree() {
        cell.setEntity(null);
        Button button = cell.getButton();
        if (button != null) {
            button.setStyle("-fx-background-color: lightgray;");
            button.setText("");
        }
    }
}
