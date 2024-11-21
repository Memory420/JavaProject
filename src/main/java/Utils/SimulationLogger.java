package Utils;

import Entities.Tree;

public class SimulationLogger {
    private int totalTicks; // Общее количество тиков
    private int totalCells; // Общее количество клеток
    private int extinctionTick = -1; // Тик массового вымирания (-1, если не произошло)
    private double[] fillPercentages; // Уровень заполненности на каждом тике
    private boolean extinctionLogged = false; // Флаг, чтобы логировать вымирание только один раз

    public SimulationLogger(int totalCells, int maxTicks) {
        this.totalCells = totalCells;
        this.fillPercentages = new double[maxTicks];
    }

    public void update(GameMap gameMap, int tick) {
        int totalTreesNow = 0;

        // Считаем текущее количество деревьев
        for (Cell cell : gameMap.getCells()) {
            if (cell.getEntity() instanceof Tree) {
                totalTreesNow++;
            }
        }

        // Сохраняем уровень заполненности
        fillPercentages[tick] = (double) totalTreesNow / totalCells * 100;

        // Проверяем массовое вымирание
        if (totalTreesNow == 0 && !extinctionLogged) {
            extinctionTick = tick; // Фиксируем тик, на котором произошло вымирание
            extinctionLogged = true;
        }

        totalTicks = tick;
    }

    public void printFinalStatistics(double percentage) {
        System.out.println("Current Percent: " + percentage);
        System.out.println("Final Simulation Statistics:");
        System.out.println("Total Ticks: " + totalTicks);
        System.out.println("Extinction Tick: " + (extinctionTick == -1 ? "No extinction" : extinctionTick));
        System.out.println("Average Fill Percentage: " + round(calculateAverageFillPercentage(), 2) + "%");
        System.out.println("Maximum Fill Percentage: " + round(calculateMaxFillPercentage(), 2) + "%");
        System.out.println("-----------------------------------------");
    }

    private double calculateAverageFillPercentage() {
        if (totalTicks == 0) return 0;
        double sum = 0;
        for (int i = 0; i < totalTicks; i++) {
            sum += fillPercentages[i];
        }
        return sum / totalTicks;
    }

    private double calculateMaxFillPercentage() {
        double max = 0;
        for (int i = 0; i < totalTicks; i++) {
            if (fillPercentages[i] > max) {
                max = fillPercentages[i];
            }
        }
        return max;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
