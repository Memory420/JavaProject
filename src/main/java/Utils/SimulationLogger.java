package Utils;

import Entities.*;

public class SimulationLogger {
    private int totalTicks;
    private int totalCells;
    private int extinctionTick = -1;
    private double[] fillPercentages;
    private boolean extinctionLogged = false;

    public SimulationLogger(int totalCells, int maxTicks) {
        this.totalCells = totalCells;
        this.fillPercentages = new double[maxTicks];
    }

    public void update(GameMap gameMap, int tick) {
        int totalTreesNow = 0;
        int totalHerbivoresNow = 0;
        int totalPredatorsNow = 0;
        int totalPredatorOffsprings = 0;

        for (Cell cell : gameMap.getCells()) {
            if (cell.getEntity() instanceof Tree) {
                totalTreesNow++;
            } else if (cell.getEntity() instanceof Herbivore) {
                totalHerbivoresNow++;
            }
        }

        totalTicks = tick;

        System.out.println("Tick: " + tick);
        System.out.println("Trees: " + totalTreesNow);
        System.out.println("Herbivores: " + totalHerbivoresNow);
        System.out.println("Predators: " + totalPredatorsNow);
        System.out.println("Predator Offsprings: " + totalPredatorOffsprings);
        System.out.println("-------------------------");
    }

    public void printFinalStatistics() {
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
