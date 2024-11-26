package Entities;

import Utils.Cell;
import Utils.GameMap;
import javafx.scene.control.Button;

import java.util.Collections;
import java.util.List;

public class Tree extends AbstractEntity {
    private double chance = 1; // Текущий шанс размножения
    private int age;

    public Tree(Cell cell, int age) {
        setCurrentCell(cell); // Используем сеттер суперкласса
        this.age = age;
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
        Button btn = getCurrentCell().getButton(); // Используем getCurrentCell()
        btn.setText(String.valueOf(age));

        if (age <= 0) {
            removeTree();
            return;
        }

        List<Cell> twoCellsNeighbors = gameMap.getCellsAround(getCurrentCell().getPosition(), 2);
        List<Cell> oneCellsNeighbors = gameMap.getCellsAround(getCurrentCell().getPosition(), 1);

        int treesAround = (int) twoCellsNeighbors.stream()
                .filter(cell -> cell.getEntity() instanceof Tree)
                .count();

        double calculatedChance = (treesAround > 0) ? chance * (1.0 / treesAround) : 1;
        calculatedChance /= 10;

        age--;

        if (Math.random() < calculatedChance) {

            Collections.shuffle(oneCellsNeighbors);

            for (Cell neighbor : oneCellsNeighbors) {
                if (neighbor.getEntity() == null) {
                    neighbor.setEntity(new Tree(neighbor, (int) (Math.random() * 60 + 1)));
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
        getCurrentCell().setEntity(null); // Используем getCurrentCell()
        Button button = getCurrentCell().getButton(); // Используем getCurrentCell()
        if (button != null) {
            button.setStyle("-fx-background-color: lightgray;");
            button.setText("");
        }
    }
}
