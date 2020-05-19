package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ChartGroup {

    private double mouseX;
    private double mouseY;
    private int scale = 0;

    private ObservableList<XYChart.Data<Integer, Integer>> numberSeriesData = FXCollections.observableArrayList();
    private ObservableList<XYChart.Data<Integer, Integer>> arrSeriesData = FXCollections.observableArrayList();

    private final ObservableList<XYChart.Series<Integer, Integer>> seriesList = FXCollections.observableArrayList();

    private final NumberAxis xAxis = new NumberAxis("x", 0, 50, 2);
    private final NumberAxis yAxis = new NumberAxis("y", 0, 100, 5);

    private final LineChart lineChart = new LineChart(xAxis, yAxis, seriesList);
    private final Group graphicGroup = new Group();

    public ChartGroup() {

        lineChart.setLayoutY(20);

        Button higherScaleBtn = new Button("+");
        Button lowerScaleBtn = new Button("-");
        higherScaleBtn.setMinSize(50, 20);
        lowerScaleBtn.setMinSize(50, 20);

        higherScaleBtn.setOnAction(actionEvent -> higherScale());
        lowerScaleBtn.setOnAction(actionEvent -> lowerScale());
        HBox buttonsBox = new HBox(higherScaleBtn, lowerScaleBtn);

        buttonsBox.setLayoutX(40);
        buttonsBox.setLayoutY(500);
        buttonsBox.setSpacing(20);
        setChartPressAndDragEvents();

        graphicGroup.getChildren().addAll(lineChart, buttonsBox);
    }

    private void setChartPressAndDragEvents() {
        lineChart.setOnMousePressed(mouseEvent -> {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        });

        lineChart.setOnMouseDragged(mouseEvent -> {
            xAxis.setLowerBound(xAxis.getLowerBound() + (mouseX - mouseEvent.getX()) / 1);
            xAxis.setUpperBound(xAxis.getUpperBound() + (mouseX - mouseEvent.getX()) / 1);
            mouseX = mouseEvent.getX();
            yAxis.setLowerBound(yAxis.getLowerBound() + (mouseEvent.getY() - mouseY) * 1);
            yAxis.setUpperBound(yAxis.getUpperBound() + (mouseEvent.getY() - mouseY) * 1);
            mouseY = mouseEvent.getY();
        });
    }

    public void setChartScroll() {
        lineChart.setOnScroll(scrollEvent -> {
            if (scrollEvent.getDeltaY() > 0) {
                higherScale();
            }
            if (scrollEvent.getDeltaY() < 0) {
                lowerScale();
            }
        });
    }

    private void higherScale() {
        if (scale < 4) {
            xAxis.setLowerBound(xAxis.getLowerBound() + xAxis.getTickUnit());
            xAxis.setUpperBound(xAxis.getUpperBound() - xAxis.getTickUnit());
            yAxis.setLowerBound(yAxis.getLowerBound() + yAxis.getTickUnit());
            yAxis.setUpperBound(yAxis.getUpperBound() - yAxis.getTickUnit());
            scale += 1;
        }
    }

    private void lowerScale() {
        xAxis.setLowerBound(xAxis.getLowerBound() - xAxis.getTickUnit());
        xAxis.setUpperBound(xAxis.getUpperBound() + xAxis.getTickUnit());
        yAxis.setLowerBound(yAxis.getLowerBound() - yAxis.getTickUnit());
        yAxis.setUpperBound(yAxis.getUpperBound() + yAxis.getTickUnit());
        scale -= 1;
    }

    public void clearChartScroll() {
        lineChart.setOnScroll(scrollEvent -> {
        });
    }

    public Group getChartGroup() {
        return graphicGroup;
    }

    public void updateWordSeriesList(Integer x, Integer y) {
        arrSeriesData.add(new XYChart.Data<>(x, y));
    }

    public void updateNumberSeriesList(Integer x, Integer y) {
        numberSeriesData.add(new XYChart.Data<>(x, y));
    }

    public void createNewNumberSeries(String fx, Integer leftThreshold, Integer rightThreshold) {
        String seriesName = "f(x) = " + fx + ", x ∈ [" + leftThreshold + "; " + rightThreshold + "]";

        numberSeriesData = FXCollections.observableArrayList();
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>(seriesName, numberSeriesData);
        seriesList.add(series);
    }

    public void createNewNumberSeriesForCoplexFunction(Integer number) {
        String seriesName = "f(x) =x ∈ [0; " + number + "]";

        arrSeriesData = FXCollections.observableArrayList();
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>(seriesName, arrSeriesData);
        seriesList.add(series);
    }
}
