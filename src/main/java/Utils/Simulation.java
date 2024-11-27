package Utils;

import Entities.*;
import Enums.Gender;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class Simulation extends Application {
    private GameMap gameMap;
    private SimulationLogger logger;
    private int tickCount;
    private static final int MAX_TICKS = 1000;
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    private static final int INITIAL_TREES = 50;
    private static final int INITIAL_HERBIVORES = 10;
    private static final int INITIAL_PREDATORS = 5;

    @Override
    public void start(Stage primaryStage) {
        gameMap = new GameMap(WIDTH, HEIGHT);
        logger = new SimulationLogger(WIDTH * HEIGHT, MAX_TICKS);
        tickCount = 0;

        GridPane grid = new GridPane();

        for (Cell cell : gameMap.getCells()) {
            Button button = new Button();
            button.setMinSize(30, 30);
            button.setStyle(CellColors.EMPTY);
            cell.setButton(button);
            grid.add(button, cell.getPosition().getX(), cell.getPosition().getY());
        }

        placeInitialEntities();

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ecosystem Simulation");
        primaryStage.show();

        runSimulation();
    }

    private void placeInitialEntities() {
        Random random = new Random();

        for (int i = 0; i < INITIAL_TREES; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            Cell cell = gameMap.getCell(new Position(x, y));
            if (cell.getEntity() == null) {
                Tree tree = new Tree(cell, random.nextInt(60) + 20);
                cell.setEntity(tree);
                Button button = cell.getButton();
                if (button != null) {
                    button.setStyle(CellColors.TREE);
                }
            } else {
                i--;
            }
        }

        for (int i = 0; i < INITIAL_HERBIVORES; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            Cell cell = gameMap.getCell(new Position(x, y));
            if (cell.getEntity() == null) {
                Herbivore herbivore = new Herbivore(cell);
                cell.setEntity(herbivore);
                Button button = cell.getButton();
                if (button != null) {
                    button.setStyle(CellColors.HERBIVORE);
                }
            } else {
                i--;
            }
        }

        for (int i = 0; i < INITIAL_PREDATORS; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            Cell cell = gameMap.getCell(new Position(x, y));
            if (cell.getEntity() == null) {
                Gender gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
                Predator predator = new Predator(cell, gender);
                cell.setEntity(predator);
                Button button = cell.getButton();
                if (button != null) {
                    button.setStyle(CellColors.PREDATOR);
                }
            } else {
                i--;
            }
        }
    }

    private void runSimulation() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (tickCount < MAX_TICKS) {
                List<AbstractEntity> entities = new ArrayList<>();

                for (Cell cell : gameMap.getCells()) {
                    if (cell.getEntity() != null) {
                        entities.add(cell.getEntity());
                    }
                }

                for (AbstractEntity entity : entities) {
                    entity.update();
                }

                logger.update(gameMap, tickCount);
                tickCount++;
            } else {
                logger.printFinalStatistics();
                ((Timeline) event.getSource()).stop();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
