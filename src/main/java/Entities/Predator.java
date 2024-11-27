package Entities;

import Enums.Gender;
import Utils.*;
import javafx.scene.control.Button;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Predator extends AbstractEntity implements IMovable, IEatable {
    private final int viewRange = 4;
    private Gender gender;
    private int energy;
    private int eatenHerbivoresCount;

    public int getEnergy() {
        return energy;
    }

    public Predator(Cell cell, Gender gender) {
        setCurrentCell(cell);
        this.gender = gender;
        this.energy = 30;
        this.eatenHerbivoresCount = 0;
    }

    @Override
    public void update() {
        eat();
        move();
        energy--;
        if (energy <= 0) {
            die();
            return;
        }

        checkForReproduction();
    }

    @Override
    public void move() {
        GameMap gameMap = getCurrentCell().getGameMap();

        List<Cell> visibleCells = gameMap.getCellsInLines(getCurrentCell().getPosition(), viewRange);

        List<Cell> herbivoreCells = visibleCells.stream()
                .filter(cell -> cell.getEntity() instanceof Herbivore)
                .collect(Collectors.toList());

        if (!herbivoreCells.isEmpty()) {
            // Двигаемся к травоядному
            Cell targetCell = herbivoreCells.get(0);
            moveToCell(targetCell);
        } else {
            // Случайное движение
            List<Cell> neighbors = gameMap.getCellsAround(getCurrentCell().getPosition(), 1);
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
        if (getCurrentCell().getEntity() instanceof Herbivore) {
            getCurrentCell().setEntity(this);
            energy += 15;
            eatenHerbivoresCount++;

            Button button = getCurrentCell().getButton();
            if (button != null) {
                button.setStyle(CellColors.PREDATOR);
            }
        }
    }

    private void moveToCell(Cell targetCell) {
        if (targetCell.getEntity() instanceof Predator && targetCell.getEntity() != this) {
            return;
        }

        getCurrentCell().setEntity(null);
        Button currentButton = getCurrentCell().getButton();
        if (currentButton != null) {
            currentButton.setStyle(CellColors.EMPTY);
        }

        if (targetCell.getEntity() instanceof Herbivore) {
            energy += 15;
            eatenHerbivoresCount++;
        }

        setCurrentCell(targetCell);
        getCurrentCell().setEntity(this);

        Button button = getCurrentCell().getButton();
        if (button != null) {
            button.setStyle(CellColors.PREDATOR);
        }
    }

    private void checkForReproduction() {
        List<Cell> neighbors = getCurrentCell().getGameMap().getCellsAround(getCurrentCell().getPosition(), 1);
        for (Cell cell : neighbors) {
            if (cell.getEntity() instanceof Predator && cell.getEntity() != this) {
                Predator otherPredator = (Predator) cell.getEntity();
                if (otherPredator.eatenHerbivoresCount > 0  && this.eatenHerbivoresCount > 0) {
                    reproduceWith(otherPredator);
                    break;
                }
            }
        }
    }

    private void reproduceWith(Predator otherPredator) {
        List<Cell> freeCells = getCurrentCell().getGameMap().getCellsAround(getCurrentCell().getPosition(), 1).stream()
                .filter(cell -> cell.getEntity() == null)
                .collect(Collectors.toList());

        if (!freeCells.isEmpty()) {
            Cell offspringCell = freeCells.get(new Random().nextInt(freeCells.size()));
            Gender offspringGender = new Random().nextBoolean() ? Gender.MALE : Gender.FEMALE;
            Predator offspring = new Predator(offspringCell, offspringGender);
            offspringCell.setEntity(offspring);

            Button button = offspringCell.getButton();
            if (button != null) {
                button.setStyle(CellColors.PREDATOR);
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
