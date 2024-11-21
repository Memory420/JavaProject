package Utils;

import Entities.Tree;
import javafx.scene.control.Button;

import java.util.List;

public class SimulationLogger {
    private int totalTrees;
    private int cellsFilled;
    private int treesDied;
    private int treesSurvived;
    private int totalAge;
    private int totalTicks;
    private int maxAge;

    // Считываем количество заполненных клеток
    public void update(GameMap gameMap) {
        totalTrees = 0;
        cellsFilled = 0;
        treesDied = 0;
        treesSurvived = 0;
        totalAge = 0;
        maxAge = 0;

        for (Cell cell : gameMap.getCells()) {
            if (cell.getEntity() instanceof Tree) {
                totalTrees++;
                Tree tree = (Tree) cell.getEntity();
                totalAge += tree.getAge();  // Суммируем возраст для средней величины
                if (tree.getAge() > maxAge) {
                    maxAge = tree.getAge();
                }
            }

            // Проверка на заполненные клетки
            if (cell.getEntity() != null) {
                cellsFilled++;
            }

            // Проверяем вымирание деревьев
            if (cell.getEntity() == null && cell.getButton().getText().isEmpty()) {
                treesDied++;
            }
        }

        treesSurvived = totalTrees - treesDied; // Деревья, которые не умерли
    }

    // Вывод статистики
    public void printStatistics() {
        System.out.println("Simulation Statistics:");
        System.out.println("Total Trees: " + totalTrees);
        System.out.println("Cells Filled: " + cellsFilled);
        System.out.println("Trees Died: " + treesDied);
        System.out.println("Trees Survived: " + treesSurvived);
        System.out.println("Average Tree Age: " + (totalTrees > 0 ? (totalAge / totalTrees) : 0));
        System.out.println("Max Tree Age: " + maxAge);
        System.out.println("Percentage of Filled Cells: " + (double) cellsFilled / totalTrees * 100 + "%");
        System.out.println("Was there any extinction event? " + (treesDied > 0 ? "Yes" : "No"));
    }
}

