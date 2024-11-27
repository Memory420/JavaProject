package Entities;

import Utils.Cell;
import Utils.CellColors;
import Utils.GameMap;
import javafx.scene.control.Button;

import java.util.Collections;
import java.util.List;

public class Tree extends AbstractEntity {
    private double chance = 1; // Текущий шанс размножения
    private int age;
    private int reproductionCooldown; // Период между размножениями

    public Tree(Cell cell, int age) {
        setCurrentCell(cell);
        this.age = age;
        this.reproductionCooldown = 5; // Дерево может размножаться каждые 5 тактов
    }

    public int getAge() {
        return age;
    }

    public void setChance(double newChance) {
        this.chance = newChance;
    }

    public double getChance() {
        return this.chance;
    }

    @Override
    public void update() {
        GameMap gameMap = getCurrentCell().getGameMap();
        Button btn = getCurrentCell().getButton();

        age--;

        if (age <= 0) {
            removeTree();
            return;
        }

        reproductionCooldown--;
        if (reproductionCooldown <= 0) {
            reproduce();
            reproductionCooldown = 5; // Сбросим кулдаун
        }
    }

    private void reproduce() {
        GameMap gameMap = getCurrentCell().getGameMap();
        List<Cell> oneCellsNeighbors = gameMap.getCellsAround(getCurrentCell().getPosition(), 1);

        Collections.shuffle(oneCellsNeighbors);

        for (Cell neighbor : oneCellsNeighbors) {
            if (neighbor.getEntity() == null) {
                Tree newTree = new Tree(neighbor, (int) (Math.random() * 60 + 20));
                neighbor.setEntity(newTree);
                Button button = neighbor.getButton();
                if (button != null) {
                    button.setStyle(CellColors.TREE);
                }
                break;
            }
        }
    }

    private void removeTree() {
        getCurrentCell().setEntity(null);
        Button button = getCurrentCell().getButton();
        if (button != null) {
            button.setStyle(CellColors.EMPTY);
        }
    }
}
