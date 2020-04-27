package sabbaken.laba2.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sabbaken.laba2.controller.ProductController;
import sabbaken.laba2.model.Product;

import java.util.ArrayList;
import java.util.List;

public class View {
    private Scene scene;
    private TableElement tableElement;
    private ProductController controller;
    private Stage stage;
    private VBox root;

    private enum WindowType {
        DELETE, SEARCH
    }

    public View() {
        final int STAGE_WIDTH = 1460,
                STAGE_HEIGHT = 781;
        final String STAGE_TITLE_TEXT = "Table editor";

        this.controller = new ProductController();
        initWindow();
        stage = new Stage();
        stage.setWidth(STAGE_WIDTH);
        stage.setHeight(STAGE_HEIGHT);
        stage.setTitle(STAGE_TITLE_TEXT);
        stage.setScene(scene);
    }

    private void initWindow() {
        final String FILE_MENU_LABEL_TEXT = "Файл",
                EDIT_MENU_LABEL_TEXT = "Редактировать",
                NEW_DOC_MENU_ITEM_LABEL_TEXT = "Новый документ",
                OPEN_DOC_MENU_ITEM_LABEL_TEXT = "Открыть документ",
                SAVE_DOC_MENU_ITEM_LABEL_TEXT = "Сохранить документ",
                ADD_ITEM_MENU_ITEM_LABEL_TEXT = "Добваить строчки",
                SEARCH_ITEMS_MENU_ITEM_LABEL_TEXT = "Поиск по строкам",
                DELETE_ITEMS_MENU_ITEM_LABEL_TEXT = "Удалить строки",
                CLOSE_APP_MENU_ITEM_LABEL_TEXT = "Выйти",
                NEW_DOC_BUTTON_LABEL_TEXT = "Новый документ",
                OPEN_DOC_BUTTON_LABEL_TEXT = "Открыть документ",
                SAVE_DOC_BUTTON_LABEL_TEXT = "Сохранить документ",
                ADD_ITEMS_BUTTON_LABEL_TEXT = "Добавить строчки",
                SEARCH_ITEMS_BUTTON_LABEL_TEXT = "Поиск по строкам",
                DELETE_ITEMS_BUTTON_LABEL_TEXT = "Удалить строки";
        MenuItem newDocMenuItem = new MenuItem(NEW_DOC_MENU_ITEM_LABEL_TEXT),
                openDocMenuItem = new MenuItem(OPEN_DOC_MENU_ITEM_LABEL_TEXT),
                saveMenuItem = new MenuItem(SAVE_DOC_MENU_ITEM_LABEL_TEXT),
                addItemsMenuItem = new MenuItem(ADD_ITEM_MENU_ITEM_LABEL_TEXT),
                searchItemsMenuItem = new MenuItem(SEARCH_ITEMS_MENU_ITEM_LABEL_TEXT),
                deleteItemsMenuItem = new MenuItem(DELETE_ITEMS_MENU_ITEM_LABEL_TEXT),
                closeAppMenuItem = new MenuItem(CLOSE_APP_MENU_ITEM_LABEL_TEXT);
        Menu fileMenu = new Menu(FILE_MENU_LABEL_TEXT),
                editMenu = new Menu(EDIT_MENU_LABEL_TEXT);
        MenuBar menuBar = new MenuBar();
        Button newDocButton = new Button(NEW_DOC_BUTTON_LABEL_TEXT),
                openDocButton = new Button(OPEN_DOC_BUTTON_LABEL_TEXT),
                saveDocButton = new Button(SAVE_DOC_BUTTON_LABEL_TEXT),
                addItemsButton = new Button(ADD_ITEMS_BUTTON_LABEL_TEXT),
                searchItemsButton = new Button(SEARCH_ITEMS_BUTTON_LABEL_TEXT),
                deleteItemsButton = new Button(DELETE_ITEMS_BUTTON_LABEL_TEXT);
        ToolBar instruments;

        fileMenu.getItems().addAll(
                newDocMenuItem,
                openDocMenuItem,
                saveMenuItem,
                new SeparatorMenuItem(),
                closeAppMenuItem);
        editMenu.getItems().addAll(
                addItemsMenuItem,
                new SeparatorMenuItem(),
                searchItemsMenuItem,
                deleteItemsMenuItem);
        menuBar.getMenus().addAll(
                fileMenu,
                editMenu);

        instruments = new ToolBar(
                newDocButton,
                openDocButton,
                saveDocButton,
                new Separator(),
                addItemsButton,
                searchItemsButton,
                deleteItemsButton);

        tableElement = new TableElement(controller.getProductList(), controller.getProductList().size());

        root = new VBox();
        root.getChildren().addAll(
                menuBar,
                instruments,
                tableElement.get());
        scene = new Scene(root);

        newDocButton.setOnAction(ae -> newDoc());
        newDocMenuItem.setOnAction(ae -> newDoc());
        openDocButton.setOnAction(ae -> openDoc());
        openDocMenuItem.setOnAction(ae -> openDoc());
        saveDocButton.setOnAction(ae -> saveDoc());
        saveMenuItem.setOnAction(ae -> saveDoc());
        addItemsButton.setOnAction(ae -> addItems());
        addItemsMenuItem.setOnAction(ae -> addItems());
        searchItemsButton.setOnAction(ae -> searchItems());
        searchItemsMenuItem.setOnAction(ae -> searchItems());
        deleteItemsButton.setOnAction(ae -> deleteItems());
        deleteItemsMenuItem.setOnAction(ae -> deleteItems());
        closeAppMenuItem.setOnAction(ae -> Platform.exit());
    }

    public Stage getStage() {
        return stage;
    }

    private void newDoc() {
        controller.newDoc();

        this.root.getChildren().remove(tableElement.get());
        tableElement = new TableElement(controller.getProducts(), 0);
        this.root.getChildren().addAll(
                tableElement.get()
        );
    }

    private void openDoc() {
        FileChooser openDocChooser = new FileChooser();

        openDocChooser.setTitle("Открыть документ");
        openDocChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Все файлы", "*.*"),
                new FileChooser.ExtensionFilter("XML-документы", "*.xml")
        );

        try {
            controller.openFile(openDocChooser.showOpenDialog(stage));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        tableElement.rewriteDefaultList(controller.getProducts());
        tableElement.resetToDefaultItems();
    }

    private void saveDoc() {
        FileChooser saveDocChooser = new FileChooser();

        saveDocChooser.setTitle("Сохранить документ");
        saveDocChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Все файлы", "*.*"),
                new FileChooser.ExtensionFilter("XML-документ", "*.xml")
        );

        controller.saveFile(saveDocChooser.showSaveDialog(stage));
    }

    private void addItems() {
        final String WINDOW_TITLE_TEXT = "Добавить товар: ",
                NAME_LABEL_TEXT = "Название: ",
                M_NAME_LABEL_TEXT = "Название производителя: ",
                UPN_LABEL_TEXT = "УПН: ",
                STOCK_LABEL_TEXT = "Количество на складе: ",
                ADDRESS_NUMBER_LABEL_TEXT = "Адрес склада: ";

//        List<Exam>   examList       = new ArrayList<>();
        TextField surnameField = new TextField(),
                nameField = new TextField(),
                mNameField = new TextField(),
                upnField = new TextField(),
                stockField = new TextField(),
                addressField = new TextField();


        GridPane root = new GridPane();
        Alert addItemWindow;

        root.addRow(0,
                new Label(NAME_LABEL_TEXT),
                nameField
        );

        root.addRow(1,
                new Label(M_NAME_LABEL_TEXT),
                mNameField
        );

        root.addRow(2,
                new Label(UPN_LABEL_TEXT),
                upnField
        );

        root.addRow(3,
                new Label(STOCK_LABEL_TEXT),
                stockField
        );

        root.addRow(4,
                new Label(ADDRESS_NUMBER_LABEL_TEXT),
                addressField
        );


        addItemWindow = createEmptyCloseableDialog();
        addItemWindow.setTitle(WINDOW_TITLE_TEXT);
        addItemWindow.getDialogPane().setContent(root);
        addItemWindow.show();

        ((Button) addItemWindow.getDialogPane().lookupButton(addItemWindow.getButtonTypes().get(0))).setOnAction(ae -> {
            controller.add(
                    nameField.getText(),
                    mNameField.getText(),
                    upnField.getText(),
                    Integer.parseInt(stockField.getText()),
                    addressField.getText()
            );

            tableElement.resetToDefaultItems();
            addItemWindow.close();
        });
    }

    private class RequestElement {

        final String CRITERIA_1 = "По названию товара или количеству на складе",
                CRITERIA_2 = "По названию производителя или УНП производителя",
                CRITERIA_3 = "По адресу склада";

        private String selectedItem;
        private ComboBox criteriaComBox;
        private Button searchButton;
        private TableElement tableElement;
        private GridPane grid;
        private Pane criteriaChooser,
                root;
        private List<Label> criteria1LabelList,
                criteria2LabelList,
                criteria3LabelList;
        private List<TextField> criteria1FieldList,
                criteria2FieldList,
                criteria3FieldList;

        public RequestElement(WindowType windowType) {
            criteriaComBox = new ComboBox();
            criteriaComBox.getItems().addAll(
                    CRITERIA_1,
                    CRITERIA_2,
                    CRITERIA_3
            );
            criteriaComBox.setValue(CRITERIA_1);
            searchButton = new Button("Поиск");
            criteriaChooser = new HBox();

            criteria1LabelList = new ArrayList<>();
            criteria1FieldList = new ArrayList<>();
            criteria2LabelList = new ArrayList<>();
            criteria2FieldList = new ArrayList<>();
            criteria3LabelList = new ArrayList<>();
            criteria3FieldList = new ArrayList<>();
            initCriteriaLists();
            grid = new GridPane();
            switchPreset();

            tableElement = new TableElement(new ArrayList<>(), controller.getProducts().size());

            this.root = new VBox();

            if (windowType == WindowType.SEARCH) {
                criteriaChooser.getChildren().addAll(
                        new Label("Критерий поиска: "),
                        criteriaComBox,
                        searchButton
                );

                this.root.getChildren().addAll(
                        new Separator(),
                        new Separator(),
                        criteriaChooser,
                        grid,
                        new Separator(),
                        new Separator(),
                        tableElement.get(),
                        new Separator(),
                        new Separator(),
                        new Separator()
                );
            }

            if (windowType == WindowType.DELETE) {
                criteriaChooser.getChildren().addAll(
                        new Label("Кртерий поиска: "),
                        criteriaComBox
                );

                this.root.getChildren().addAll(
                        new Separator(),
                        new Separator(),
                        criteriaChooser,
                        grid
                );
            }

            criteriaComBox.setOnAction(ae -> switchPreset());
            searchButton.setOnAction(ae -> {
                List<Product> studentList = search();

                tableElement.setObservableList(studentList);
            });
        }

        private void switchPreset() {
            final int CRITERIA_1_FIELD_NUMBER = 2,
                    CRITERIA_2_FIELD_NUMBER = 2,
                    CRITERIA_3_FIELD_NUMBER = 1;

            grid.getChildren().clear();
            selectedItem = criteriaComBox.getSelectionModel().getSelectedItem().toString();
            switch (selectedItem) {
                case CRITERIA_1:
                    for (int i = 0; i < CRITERIA_1_FIELD_NUMBER; i++) {
                        grid.addRow(i,
                                criteria1LabelList.get(i),
                                criteria1FieldList.get(i)
                        );
                    }
                    break;
                case CRITERIA_2:
                    for (int i = 0; i < CRITERIA_2_FIELD_NUMBER; i++) {
                        grid.addRow(i,
                                criteria2LabelList.get(i),
                                criteria2FieldList.get(i)
                        );
                    }
                    break;
                case CRITERIA_3:
                    for (int i = 0; i < CRITERIA_3_FIELD_NUMBER; i++) {
                        grid.addRow(i,
                                criteria3LabelList.get(i),
                                criteria3FieldList.get(i)
                        );
                    }
                    break;
            }
        }

        private void initCriteriaLists() {
            final String NAME_LABEL_TEXT = "Название товара: ",
                    STOCK_LABEL_TEXT = "Количество на складе: ",
                    M_NAME_LABEL_TEXT = "Название производителя: ",
                    UPN_LABEL_TEXT = "УПН производителя: ",
                    ADDRESS_LABEL_TEXT = "По адресу склада: ";

            criteria1LabelList.add(new Label(NAME_LABEL_TEXT));
            criteria1LabelList.add(new Label(STOCK_LABEL_TEXT));
            criteria1FieldList.add(new TextField());
            criteria1FieldList.add(new TextField());

            criteria2LabelList.add(new Label(M_NAME_LABEL_TEXT));
            criteria2LabelList.add(new Label(UPN_LABEL_TEXT));
            criteria2FieldList.add(new TextField());
            criteria2FieldList.add(new TextField());

            criteria3LabelList.add(new Label(ADDRESS_LABEL_TEXT));
            criteria3FieldList.add(new TextField());
        }

        public Pane get() {
            return this.root;
        }

        public List search() {
            String name, mName, stock, upn, address;
            List criteriaList;

            try {
                name = criteria1FieldList.get(0).getText();
            } catch (NumberFormatException e) {
                name = "";
            }
            try {
                stock = criteria1FieldList.get(1).getText();
            } catch (NumberFormatException e) {
                stock = "0";
            }
            try {
                mName = criteria2FieldList.get(0).getText();
            } catch (NumberFormatException e) {
                mName = "";
            }
            try {
                upn = criteria2FieldList.get(1).getText();
            } catch (NumberFormatException e) {
                upn = "";
            }
            try {
                address = criteria3FieldList.get(0).getText();
            } catch (NumberFormatException e) {
                address = "";
            }

            criteriaList = new ArrayList<String>();
            criteriaList.add(name);
            criteriaList.add(mName);
            criteriaList.add(upn);
            criteriaList.add(stock);
            criteriaList.add(address);

            return controller.search(selectedItem, criteriaList);
        }
    }

    private void searchItems() {
        final String WINDOW_TITLE_TEXT = "Искать строки";
        Alert searchItemsWindow;
        RequestElement requestElement = new RequestElement(WindowType.SEARCH);

        searchItemsWindow = createEmptyCloseableDialog();
        searchItemsWindow.setTitle(WINDOW_TITLE_TEXT);
        searchItemsWindow.getDialogPane().setContent(requestElement.get());
        searchItemsWindow.show();

        ((Button) searchItemsWindow.getDialogPane().lookupButton(searchItemsWindow.getButtonTypes().get(0))).setOnAction(
                ae -> searchItemsWindow.close()
        );
    }

    private void deleteItems() {
        final String WINDOW_TITLE_TEXT = "Удалить строки";
        Alert deleteItemsWindow;
        RequestElement requestElement = new RequestElement(WindowType.DELETE);

        deleteItemsWindow = createEmptyCloseableDialog();
        deleteItemsWindow.setTitle(WINDOW_TITLE_TEXT);
        deleteItemsWindow.getDialogPane().setContent(requestElement.get());
        deleteItemsWindow.show();

        ((Button) deleteItemsWindow.getDialogPane().lookupButton(deleteItemsWindow.getButtonTypes().get(0))).setOnAction(ae -> {
            createDeleteInfoWindow(String.valueOf(requestElement.search().size()));
            controller.delete(requestElement.search());
            tableElement.resetToDefaultItems();
            deleteItemsWindow.close();
        });
    }

    private void createDeleteInfoWindow(String deleteInfo) {
        final String CLOSE_BUTTON_LABEL_TEXT = "ОК";
        ButtonType closeButton = new ButtonType(CLOSE_BUTTON_LABEL_TEXT);
        Alert window = new Alert(Alert.AlertType.NONE);
        VBox vertice = new VBox();

        vertice.getChildren().add(new Label("Удалено " + deleteInfo + " строк."));
        window.getDialogPane().setContent(vertice);
        window.getButtonTypes().addAll(closeButton);
        window.show();
    }

    private Alert createEmptyCloseableDialog() {
        final String CLOSE_BUTTON_LABEL_TEXT = "Далее";
        ButtonType closeButton = new ButtonType(CLOSE_BUTTON_LABEL_TEXT);
        Alert window = new Alert(Alert.AlertType.NONE);

        window.getButtonTypes().addAll(closeButton);
        return window;
    }
}
