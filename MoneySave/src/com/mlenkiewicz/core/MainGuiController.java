package com.mlenkiewicz.core;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import com.mlenkiewicz.db.Category;
import com.mlenkiewicz.db.SpendMoney;
import com.mlenkiewicz.model.Helper;
import com.mlenkiewicz.model.SpendMoneyModel;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class MainGuiController {

	@FXML
	private Button btClear;
	@FXML
	private Button btAccept;

	@FXML
	private ComboBox<Category> cbCategory;
	@FXML
	private TextField tbDescription;
	@FXML
	private TextField tbPrice;
	@FXML
	private DatePicker datePicker;

	@FXML
	private DatePicker dateTo;
	@FXML
	private DatePicker dateFrom;

	@FXML
	private TableView<SpendMoneyModel> tableHistory;
	@FXML
	private TableColumn<SpendMoneyModel, String> columnSpendDate;
	@FXML
	private TableColumn<SpendMoneyModel, String> columnCategory;
	@FXML
	private TableColumn<SpendMoneyModel, String> columnDescription;
	@FXML
	private TableColumn<SpendMoneyModel, Number> columnPrice;
	@FXML
	private TableColumn<SpendMoneyModel, String> columnNr;

	@FXML
	private TableView<SpendMoneyModel> tableHistoryReport;
	@FXML
	private TableColumn<SpendMoneyModel, String> columnSpendDateReport;
	@FXML
	private TableColumn<SpendMoneyModel, String> columnCategoryReport;
	@FXML
	private TableColumn<SpendMoneyModel, String> columnDescriptionReport;
	@FXML
	private TableColumn<SpendMoneyModel, Number> columnPriceReport;
	@FXML
	private TableColumn<SpendMoneyModel, String> columnNrReport;

	@FXML
	private Label lbTotal;

	// Testowe Drzewo
	@FXML
	private TreeTableView<SpendMoney> treeTableView;
	@FXML
	private TreeTableColumn<SpendMoney, String> treeColumnPrice;
	@FXML
	private TreeTableColumn<SpendMoney, String> treeColumnCategory;

	private void initializeTree() {
		SpendMoney spend = new SpendMoney();
		Category category = new Category();
		category.setId(1L);
		category.setName("Test");
		category.setParentId(0);

		spend.setCategory(category);
		spend.setCost(156D);
		spend.setDescripton("Desc");
		spend.setSpendDate(new Date());
		TreeItem<SpendMoneyModel> root = new TreeItem<>(new SpendMoneyModel(spend));
		// treeTableView.setShowRoot(false);
		// treeTableView.setRoot(root);
		// treeColumnPrice.setCellValueFactory(new
		// TreeItemPropertyValueFactory("categoryValue"));
		// column.setCellValueFactory(new
		// TreeItemPropertyValueFactory("descriptionValue"));
		treeColumnCategory.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<SpendMoney, String> p) -> new ReadOnlyStringWrapper(
						p.getValue().getValue().getCategory().getName()));

		TreeTableColumn<String, String> column = new TreeTableColumn<>("Column");
		column.setPrefWidth(150);

		// Defining cell content

		treeColumnPrice.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<SpendMoney, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getCost() + ""));

	}

	@PostConstruct
	public void initialize() {
		initializeReportTable();
		initializeHistoryTable();
		initializeDatePickers();
		initializeTree();
	}

	private void initializeDatePickers() {
		datePicker.setValue(LocalDate.now());
		initializeDateForm();
		initializeDateTo();
		initializeComobBox();
	}

	private void initializeComobBox() {
		List<Category> categories = DBManager.getCategories();
		cbCategory.getItems().addAll(categories);
	}

	private void initializeDateForm() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
	//	calendar.add(Calendar.MONTH, 1);
		dateFrom.setValue(Helper.toLocalDate(calendar.getTime()));
	}

	private void initializeDateTo() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		dateTo.setValue(Helper.toLocalDate(calendar.getTime()));
	}

	@FXML
	public void btAcceptAction(ActionEvent e) {
		SpendMoney spendMoney = new SpendMoney();
		spendMoney.setCost(Double.parseDouble(tbPrice.getText()));
		spendMoney.setDescripton(tbDescription.getText());
		spendMoney.setSpendDate(Helper.toDate(datePicker.getValue()));
		spendMoney.setCategory(cbCategory.getValue());
		DBManager.addSpendMoney(spendMoney);
		updateHistoryTable();
	}

	@FXML
	public void btClearAction(ActionEvent e) {
		tbDescription.clear();
		tbPrice.clear();
		datePicker.setValue(LocalDate.now());
	}

	private void initializeReportTable() {
		columnNrReport.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		columnCategoryReport.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
		columnSpendDateReport.setCellValueFactory(cellData -> cellData.getValue().spendProperty());
		columnDescriptionReport.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		columnPriceReport.setCellValueFactory(cellData -> cellData.getValue().costProperty());

		columnNrReport.setCellFactory(TextFieldTableCell.forTableColumn());
		columnCategoryReport.setCellFactory(TextFieldTableCell.forTableColumn());
		columnSpendDateReport.setCellFactory(TextFieldTableCell.forTableColumn());
		columnDescriptionReport.setCellFactory(TextFieldTableCell.forTableColumn());
		columnPriceReport.setCellFactory(
				TextFieldTableCell.<SpendMoneyModel, Number> forTableColumn(new NumberStringConverter()));
	}

	private void initializeHistoryTable() {
		// Historia w tabeli
		columnNr.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		columnCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
		columnSpendDate.setCellValueFactory(cellData -> cellData.getValue().spendProperty());
		columnDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		columnPrice.setCellValueFactory(cellData -> cellData.getValue().costProperty());

		columnNr.setCellFactory(TextFieldTableCell.forTableColumn());
		columnCategory.setCellFactory(TextFieldTableCell.forTableColumn());
		columnSpendDate.setCellFactory(TextFieldTableCell.forTableColumn());
		columnDescription.setCellFactory(TextFieldTableCell.forTableColumn());
		columnPrice.setCellFactory(
				TextFieldTableCell.<SpendMoneyModel, Number> forTableColumn(new NumberStringConverter()));
		updateHistoryTable();
	}

	@FXML
	public void btCategoriesManagerAction(ActionEvent e) {
		showCategoriesManager();
	}

	@FXML
	public void btFilterAction(ActionEvent e) {
		Date dateForm = Helper.toDate(dateFrom.getValue());
		Date dateTo1 = Helper.toDate(dateTo.getValue());

		List<SpendMoney> spends = DBManager.getAggregate(dateForm, dateTo1);

		tableHistoryReport.getItems().clear();
		spends.stream().forEach(x -> tableHistoryReport.getItems().add(new SpendMoneyModel(x)));
		double total = 0.0;
		for (SpendMoney spendMoney : spends) {
			total += spendMoney.getCost();
		}
		lbTotal.setText(total + " zł");
		TreeItem<SpendMoney> mon = buildTree(spends);
		treeTableView.setRoot(mon);
	}

	private void showCategoriesManager() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCategory.fxml"));
		Stage stage = new Stage();
		try {
			AnchorPane pane = (AnchorPane) loader.load();
			stage.setScene(new Scene(pane, 600, 600));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stage.show();

		NewCategoryController controller = loader.<NewCategoryController> getController();
		controller.initialize(stage);
	}

	private void updateHistoryTable() {
		// DOdanie do tabeli danych
		List<SpendMoney> actions = DBManager.getLatestSpends();
		tableHistory.getItems().clear();
		actions.stream().forEach(x -> tableHistory.getItems().add(new SpendMoneyModel(x)));
	}

	private TreeItem<SpendMoney> buildTree(List<SpendMoney> spends) {
		SpendMoney rootMoney = new SpendMoney();
		Category rootCat = new Category();
		rootCat.setParentId(0);
		rootCat.setName("RootCategory");
		rootMoney.setCategory(rootCat);
		rootMoney.setCost(0.0);
		rootMoney.setDescripton("RootDescription");
		rootMoney.setSpendDate(new Date());
		TreeItem<SpendMoney> root = new TreeItem<SpendMoney>(rootMoney);

		for (SpendMoney spendMoney : spends) {
			if (spendMoney.getCategory().getId() == 0) {
				TreeItem<SpendMoney> item = new TreeItem<SpendMoney>(spendMoney);
				root.getChildren().add(item);
				recurseAdd(item, spends);
			}
		}
		return root;
	}

	public void recurseAdd(TreeItem<SpendMoney> treeItem, List<SpendMoney> spends) {

		for (SpendMoney spendMoney : spends) {
			if (spendMoney.getCategory().getParentId() == treeItem.getValue().getId()) {
				TreeItem<SpendMoney> item = new TreeItem<SpendMoney>(spendMoney);
				treeItem.getChildren().add(item);
				recurseAdd(item, spends);
			}
		}

	}

	// Category categoryRoot = new Category();
	// categoryRoot.setName("ROOT");
	// TreeItem<Category> root = new TreeItem<Category>(categoryRoot);
	// for (Category category : categories) {
	// if (category.getParentId() == 0) {
	// TreeItem<Category> item = new TreeItem<Category>(category);
	// root.getChildren().add(item);
	// recurseAdd(item,categories);
	// }
	// }
	// return root;

}
