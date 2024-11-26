import Entities.Tree;
import Utils.Cell;
import Utils.GameMap;
import Utils.Position;
import Utils.SimulationLogger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Simulation extends Application {
    private static final int row = 15;
    private static final int col = 15;
    private static final int hGap = 2;
    private static final int vGap = 2;
    private static final int sideGap = 10;
    private static final int cellSize = 60;

    private final GameMap gameMap = new GameMap();
    private SimulationLogger logger = new SimulationLogger(row * col, 301);
    private int totalTicks;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Simulation");
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane, 200, 200);
        stage.setScene(scene);

        generateField(pane, row, col);

        // Инициализируем логгер с общим количеством клеток и максимумом тиков
        logger = new SimulationLogger(row * col, 500);

        startTicks();
        stage.show();
    }

    void generateField(StackPane pane, int row, int col) {
        Stage stage = (Stage) pane.getScene().getWindow();
        pane.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setHgap(hGap);
        grid.setVgap(vGap);
        grid.setTranslateX(sideGap);
        grid.setTranslateY(sideGap);
        grid.setPadding(new Insets(0));
        pane.getChildren().add(grid);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Position position = new Position(i, j);
                Cell cell = new Cell(position);
                cell.setGameMap(gameMap); // Связываем клетку с картой

                Button button = new Button();
                button.setPrefSize(cellSize, cellSize);
                button.setStyle("-fx-padding: 0; -fx-font-size: 12px; -fx-background-color: lightgray;");

                // Обработчик нажатия кнопки
                button.setOnAction(e -> {
                    if (cell.getEntity() == null) {
                        Tree tree = new Tree(cell, (int) (Math.random() * 10 + 1));
                        cell.setEntity(tree); // Устанавливаем дерево в клетке
                        button.setStyle("-fx-background-color: green;"); // Окрашиваем кнопку
                    }
                });

                cell.setButton(button);
                gameMap.addCell(cell);
                grid.add(button, j, i);
            }
        }

        int sizeX = sideGap * 2 + hGap * (col - 1) + cellSize * col + 16;
        int sizeY = sideGap * 2 + vGap * (row - 1) + cellSize * row + 39;
        stage.setWidth(sizeX);
        stage.setHeight(sizeY);
    }

    void startTicks() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> tick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    void tick() {
        int totalTrees = 0;

        // Обновляем деревья и считаем их количество
        for (Cell cell : gameMap.getCells()) {
            if (cell.getEntity() instanceof Tree) {
                Tree tree = (Tree) cell.getEntity();
                tree.update();
                totalTrees++;
            }
        }

        int totalCells = gameMap.getCells().size();
        double fillPercentage = (double) totalTrees / totalCells;

        logger.update(gameMap, totalTicks);

        totalTicks++;

        // Завершаем симуляцию после 300 тиков
        if (totalTicks == 300) {
            System.exit(0);
        }
    }
}
