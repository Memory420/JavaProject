package Entities;

import Utils.Cell;
import Utils.GameMap;
import javafx.scene.control.Button;

import java.util.Collections;
import java.util.List;

public class Tree extends AbstractEntity {
    private Cell cell;

    public int getAge() {
        return age;
    }

    private int age;

    public Tree(Cell cell, int age) {
        this.cell = cell;
        this.age = age;
    }

    @Override
    public void update() {
        Button btn = cell.getButton();
        btn.setText(String.valueOf(age));

        if (age <= 0) {
            cell.setEntity(null);
            Button button = cell.getButton();
            if (button != null) {
                button.setStyle("-fx-background-color: lightgray;");
                button.setText("");
            }
            return;
        }

        age--;

        if (Math.random() < 0.2) {
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


}
