package view;

import controller.Function1;
import controller.Function2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.concurrent.ConcurrentLinkedQueue;


public class Plotter {

    private final Group group = new Group();

    private Integer x = 0;
    private Integer limit;
    private Integer DoubleNumberN;
    private Integer n = 1;

    private final ConcurrentLinkedQueue<Integer> numberQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Integer> arrQueue = new ConcurrentLinkedQueue<>();

    private Thread numberCalculationThread = new Thread();
    private Thread arrCalculationThread = new Thread();


    public Plotter(ChartGroup graphicGroup, Table table) {
        setNumberTempLine(graphicGroup);
        setArrTempLine(graphicGroup);

        Label inputRangeXLabel = new Label("      x: ");
        TextField maxLimitForX = new TextField();
        HBox parameterBox = new HBox(inputRangeXLabel, maxLimitForX);
        Label spaceLabel = new Label("      ");
        Label inputKLabel = new Label("      k: ");
        TextField NumberN = new TextField();
        HBox parameterBox2 = new HBox(inputKLabel, NumberN);
        Button startBuildButton = new Button("    Start   ");
        Button stopBuildButton = new Button("    Stop    ");
        HBox startStopButtonBox = new HBox(spaceLabel, startBuildButton, stopBuildButton);

        VBox buttonsGroup = new VBox(parameterBox, parameterBox2, startStopButtonBox);
        buttonsGroup.setSpacing(5);

        startBuildButton.setOnAction(actionEvent -> {
            table.clearTable();
            if (!numberCalculationThread.isAlive()) {
                if (inputCheck(NumberN.getText()) && inputCheck(maxLimitForX.getText())) {
                    DoubleNumberN = Integer.parseInt(NumberN.getText());
                    limit = Integer.parseInt(maxLimitForX.getText());
                    numberQueue.clear();

                    table.clearTable();
                    graphicGroup.createNewNumberSeries("(2*x)-5", x, limit);

                    numberCalculationThread = new Thread(new Function1(x, limit, numberQueue));
                    numberCalculationThread.start();

                    if (!arrCalculationThread.isAlive()) {
                        arrQueue.clear();
                        graphicGroup.createNewNumberSeriesForCoplexFunction(limit);
                        arrCalculationThread = new Thread(new Function2(x, limit, arrQueue, DoubleNumberN, table));
                        arrCalculationThread.start();
                    }
                } else {
                    errorAlert();
                }
            }
        });

        stopBuildButton.setOnAction(actionEvent -> {
            if (!numberCalculationThread.isInterrupted()) {

                numberCalculationThread.interrupt();
                arrCalculationThread.interrupt();
                numberQueue.clear();
                arrQueue.clear();
            }
        });

        group.getChildren().addAll(buttonsGroup);
    }

    public Group getGroup() {
        return group;
    }

    private void setNumberTempLine(ChartGroup chartGroup) {
        Timeline numberTempLine = new Timeline();
        numberTempLine.setCycleCount(Timeline.INDEFINITE);
        numberTempLine.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                actionEvent -> {
                    if (!numberQueue.isEmpty()) {
                        chartGroup.updateNumberSeriesList(x, numberQueue.poll());
                        x += 1;
                    }
                }));
        numberTempLine.play();
    }

    private void errorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("Impossible to draw a graphic");
        alert.setContentText("Enter the correct data");

        alert.showAndWait();
    }

    private boolean inputCheck(String text) {
        boolean isInteger = false;

        String numberMatcher = "^-?[0-9]*$";
        if (!text.isEmpty()) {
            if (!text.matches(numberMatcher)) {
                errorAlert();
            } else {
                isInteger = true;
            }
        }

        return isInteger;
    }

    private void setArrTempLine(ChartGroup chartGroup) {
        Timeline wordTempLine = new Timeline();
        wordTempLine.setCycleCount(Timeline.INDEFINITE);
        wordTempLine.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                actionEvent -> {
                    if (!arrQueue.isEmpty()) {
                        chartGroup.updateWordSeriesList(n, arrQueue.poll());
                        n += 1;
                    }
                }));
        wordTempLine.play();
    }
}
