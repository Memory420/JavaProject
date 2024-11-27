package Entities;

import Utils.*;
import javafx.scene.control.Button;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Herbivore extends AbstractEntity implements IMovable, IEatable {
    private int energy;
    private int viewRange = 1;
    private int reproductionCooldown;
    private static final int MAX_ENERGY = 50;

    public Herbivore(Cell cell) {
        setCurrentCell(cell);
        this.energy = 20;
        this.reproductionCooldown = 5;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public void update() {
        eat();
        move();
        energy--;
        reproductionCooldown--;

        if (energy <= 0) {
            die();
            return;
        }

        if (reproductionCooldown <= 0 && energy >= 30) {
            checkForReproduction();
            reproductionCooldown = 5;
        }


        if (energy > MAX_ENERGY) {
            energy = MAX_ENERGY;
        }
    }

    @Override
    public void move() {
        GameMap gameMap = getCurrentCell().getGameMap();
        List<Cell> neighbors = gameMap.getCellsAround(getCurrentCell().getPosition(), viewRange);

        List<Cell> treeCells = neighbors.stream()
                .filter(cell -> cell.getEntity() instanceof Tree)
                .collect(Collectors.toList());

        if (!treeCells.isEmpty()) {
            Cell targetCell = treeCells.get(0);
            moveToCell(targetCell);
        } else {
            List<Cell> freeCells = neighbors.stream()
                    .filter(cell -> cell.getEntity() == null)
                    .collect(Collectors.toList());
            if (!freeCells.isEmpty()) {
                Cell targetCell = freeCells.get(new Random().nextInt(freeCells.size()));
                moveToCell(targetCell);
            }
        }
    }

    @Override
    public void eat() {
        List<Cell> neighbors = getCurrentCell().getGameMap().getCellsAround(getCurrentCell().getPosition(), viewRange);
        for (Cell cell : neighbors) {
            if (cell.getEntity() instanceof Tree) {
                cell.setEntity(null);
                Button button = cell.getButton();
                if (button != null) {
                    button.setStyle(CellColors.EMPTY);
                }
                energy += 5;
                break;
            }
        }
    }

    private void moveToCell(Cell targetCell) {
        getCurrentCell().setEntity(null);
        Button currentButton = getCurrentCell().getButton();
        if (currentButton != null) {
            currentButton.setStyle(CellColors.EMPTY);
        }

        if (targetCell.getEntity() instanceof Tree) {
            energy += 5;
            targetCell.setEntity(null);
            Button targetButton = targetCell.getButton();
            if (targetButton != null) {
                targetButton.setStyle(CellColors.EMPTY);
            }
        }

        setCurrentCell(targetCell);
        getCurrentCell().setEntity(this);

        Button button = getCurrentCell().getButton();
        if (button != null) {
            button.setStyle(CellColors.HERBIVORE);
        }
    }

    private void checkForReproduction() {
        if (energy >= 30) {
            List<Cell> neighbors = getCurrentCell().getGameMap().getCellsAround(getCurrentCell().getPosition(), 1);

            boolean hasNeighborHerbivore = neighbors.stream()
                    .anyMatch(cell -> cell.getEntity() instanceof Herbivore && cell.getEntity() != this);

            if (hasNeighborHerbivore) {
                List<Cell> freeCells = neighbors.stream()
                        .filter(cell -> cell.getEntity() == null)
                        .collect(Collectors.toList());

                if (!freeCells.isEmpty()) {
                    Cell offspringCell = freeCells.get(new Random().nextInt(freeCells.size()));
                    Herbivore offspring = new Herbivore(offspringCell);
                    offspringCell.setEntity(offspring);

                    Button button = offspringCell.getButton();
                    if (button != null) {
                        button.setStyle(CellColors.HERBIVORE);
                    }
                    energy -= 20;
                }
            }
        }
    }

    private void die() {
        getCurrentCell().setEntity(null);
        Button button = getCurrentCell().getButton();
        if (button != null) {
            button.setStyle(CellColors.EMPTY);
        }
    }
}
