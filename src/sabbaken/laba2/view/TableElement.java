package sabbaken.laba2.view;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sabbaken.laba2.model.Product;


import java.util.ArrayList;
import java.util.List;

public class TableElement {
    private int rowsOnPage,
            currentPage = 1,
            numberOfPages;
    private Label paginationLabel,
            itemsCountLabel;
    private Button resetSearchButton;
    private TextField rowsOnPageField;
    private TableView<Product> table;
    private ToolBar navigator,
            pagination;
    private Pane tableElement;
    private List<Product> defaultProductsList;
    private ObservableList<Product> studentObsList,
            curStudentObsList;

    private int TABLE_HEIGHT = 600,
            TABLE_WIDTH = 1460,
            DEFAULT_ROWS_ON_PAGE_NUMBER = 17;
    private final String NAME_COLUMN_LABEL_TEXT = "Название товара",
            M_NAME_COLUMN_LABEL_TEXT = "Название производителя",
            UPN_COLUMN_LABEL_TEXT = "УПН производителя",
            STOCK_NAME_COLUMN_LABEL_TEXT = "Количество на складе",
            ADDRESS_SCORE_COLUMN_LABEL_TEXT = "Адресс склада",
            ROWS_ON_PAGE_LABEL_TEXT = "Строк на странице: ",
            TO_BEGIN_BUTTON_LABEL_TEXT = "<<",
            TO_LEFT_BUTTON_LABEL_TEXT = "<",
            TO_RIGHT_BUTTON_LABEL_TEXT = ">",
            TO_END_BUTTON_LABEL_TEXT = ">>";


    public TableElement(ArrayList<Product> productsList, int examNumber) {
        Property sProperty = new SimpleStringProperty();
        Button toBeginButton = new Button(TO_BEGIN_BUTTON_LABEL_TEXT),
                toLeftButton = new Button(TO_LEFT_BUTTON_LABEL_TEXT),
                toRightButton = new Button(TO_RIGHT_BUTTON_LABEL_TEXT),
                toEndButton = new Button(TO_END_BUTTON_LABEL_TEXT);
        TableColumn<Product, String> nameCol = new TableColumn<>(NAME_COLUMN_LABEL_TEXT),
                mNameCol = new TableColumn<>(M_NAME_COLUMN_LABEL_TEXT),
                upnCol = new TableColumn<>(UPN_COLUMN_LABEL_TEXT),
                stockCol = new TableColumn<>(STOCK_NAME_COLUMN_LABEL_TEXT),
                addressCol = new TableColumn<>(ADDRESS_SCORE_COLUMN_LABEL_TEXT);


        defaultProductsList = productsList;
        studentObsList = FXCollections.observableArrayList(defaultProductsList);
        curStudentObsList = FXCollections.observableArrayList();

        nameCol.setMinWidth(300);
        mNameCol.setMinWidth(300);
        upnCol.setMinWidth(100);
        stockCol.setMinWidth(100);
        addressCol.setMinWidth(300);

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mNameCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        upnCol.setCellValueFactory(new PropertyValueFactory<>("manufacturerID"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("warehouseAddress"));

        paginationLabel = new Label();
        navigator = new ToolBar(
                toBeginButton,
                toLeftButton,
                paginationLabel,
                toRightButton,
                toEndButton
        );

        itemsCountLabel = new Label("/" + studentObsList.size() + "/");
        rowsOnPageField = new TextField();
        rowsOnPageField.setText(String.valueOf(DEFAULT_ROWS_ON_PAGE_NUMBER));
        resetSearchButton = new Button("Сбросить");
        resetSearchButton.setVisible(false);
        pagination = new ToolBar(
                itemsCountLabel,
                new Separator(),
                new Label(ROWS_ON_PAGE_LABEL_TEXT),
                rowsOnPageField,
                new Separator(),
                navigator,
                resetSearchButton
        );

        table = new TableView<>();
        table.setMinHeight(TABLE_HEIGHT);
        table.setMaxWidth(TABLE_WIDTH);
        table.getColumns().addAll(
                nameCol,
                mNameCol,
                upnCol,
                stockCol,
                addressCol
        );
        table.setItems(curStudentObsList);
        setRowsOnPage();

        tableElement = new VBox();
        tableElement.getChildren().addAll(table,
                pagination);

        rowsOnPageField.setOnAction(ae -> setRowsOnPage());
        toBeginButton.setOnAction(ae -> goBegin());
        toLeftButton.setOnAction(ae -> goLeft());
        toRightButton.setOnAction(ae -> goRight());
        toEndButton.setOnAction(ae -> goEnd());
        resetSearchButton.setOnAction(ae -> {
            resetToDefaultItems();
            resetSearchButton.setVisible(false);
        });
    }

    public Pane get() {
        return tableElement;
    }

    public void rewriteDefaultList(List<Product> list) {
        defaultProductsList = list;
    }

    public void resetToDefaultItems() {
        setObservableList(defaultProductsList);
    }

    public void setObservableList(List<Product> list) {
        studentObsList = FXCollections.observableArrayList(list);
        resetSearchButton.setVisible(false);

        setRowsOnPage();
    }

    private void setRowsOnPage() {
        rowsOnPage = Integer.valueOf(rowsOnPageField.getText());
        currentPage = 1;

        refreshPage();
    }

    private void goBegin() {
        currentPage = 1;
        refreshPage();
    }

    private void goLeft() {
        if (currentPage > 1) {
            currentPage--;
        }
        refreshPage();
    }

    private void goRight() {
        if (currentPage < numberOfPages) {
            currentPage++;
        }
        refreshPage();
    }

    private void goEnd() {
        currentPage = numberOfPages;
        refreshPage();
    }

    private void refreshPage() {
        int fromIndex = (currentPage - 1) * rowsOnPage,
                toIndex = currentPage * rowsOnPage;

        if (toIndex > studentObsList.size()) {
            toIndex = studentObsList.size();
        }

        curStudentObsList.clear();
        curStudentObsList.addAll(
                studentObsList.subList(
                        fromIndex,
                        toIndex
                )
        );

        refreshPaginationLabel();
    }

    private void refreshPaginationLabel() {
        numberOfPages = (studentObsList.size() - 1) / rowsOnPage + 1;
        paginationLabel.setText(currentPage + "/" + numberOfPages);
        itemsCountLabel.setText("/" + studentObsList.size() + "/");
    }
}
