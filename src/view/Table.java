package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Point2D;


public class Table {

    private final ObservableList<Point2D> tableData = FXCollections.observableArrayList();
    private final Group group = new Group();

    public Table() {

        TableColumn<Point2D, Integer> xColumn = new TableColumn<>("x");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        xColumn.setPrefWidth(140);
        TableView<Point2D> table = new TableView<>(tableData);
        table.getColumns().add(xColumn);

        TableColumn<Point2D, Integer> yColumn = new TableColumn<>("f1");
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        yColumn.setPrefWidth(140);
        table.getColumns().add(yColumn);

        table.setPrefWidth(300);
        table.setPrefHeight(400);

        group.getChildren().add(table);

    }

    public Group getGroup() {
        return group;
    }

    public void updateTable(Point2D tableRecord) {
        tableData.add(tableRecord);
    }

    public void clearTable() {
        tableData.clear();
    }

}
