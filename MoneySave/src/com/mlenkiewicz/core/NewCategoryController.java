package com.mlenkiewicz.core;

import java.util.List;

import javax.annotation.PostConstruct;

import com.mlenkiewicz.db.Category;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class NewCategoryController {

	@FXML
	private TreeView<Category> treeView;
	@FXML
	private Label labelParent;
	@FXML
	private TextField tfCategoryName;

	private TreeItem<Category> lastSelected;
	private Stage stage;

	@PostConstruct
	public void initialize(Stage stage) {
		refreshCategoriesTree();
		this.stage = stage;

		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Category>>() {

			@Override

			public void changed(ObservableValue<? extends TreeItem<Category>> observable, TreeItem<Category> oldValue,
					TreeItem<Category> newValue) {
				System.out.println("Selected " + newValue);
				if (newValue != null) {
					lastSelected = newValue;
					labelParent.setText(newValue.getValue().getName());
				}
			}
		});
	}

	public TreeItem<Category> buildTree(List<Category> categories) {
		Category categoryRoot = new Category();
		categoryRoot.setName("ROOT");
		TreeItem<Category> root = new TreeItem<Category>(categoryRoot);
		lastSelected = root;
		for (Category category : categories) {

			if (category.getParentId() == 0) {
				TreeItem<Category> item = new TreeItem<Category>(category);
				root.getChildren().add(item);
				recurseAdd(item, categories);
			}
		}
		return root;
	}

	public void recurseAdd(TreeItem<Category> treeItem, List<Category> categories) {

		for (Category category : categories) {
			if (category.getParentId() == treeItem.getValue().getId()) {
				TreeItem<Category> item = new TreeItem<Category>(category);
				treeItem.getChildren().add(item);
				recurseAdd(item, categories);
			}
		}
	}

	@FXML
	public void btAddAction(ActionEvent e) {
		Category category = new Category();
		category.setName(tfCategoryName.getText());
		category.setParentId(0);
		TreeItem<Category> item = treeView.getSelectionModel().getSelectedItem();
		if (item != null) {

			if (item.getValue().getId() != 0) {
				category.setParentId(item.getValue().getId());
			}
		}

		DBManager.addCategory(category);
		addTreeCategory(lastSelected, category);
		//refreshCategoriesTree();

		treeView.getSelectionModel().select(lastSelected);
		lastSelected.setExpanded(true);
		tfCategoryName.setText("");
	}

	@FXML
	public void btCloseAction(ActionEvent e) {
		stage.close();
	}

	private void refreshCategoriesTree() {
		List<Category> categories = DBManager.getCategories();

		treeView.setRoot(buildTree(categories));
	}

	private void addTreeCategory(TreeItem<Category> root, Category cat) {
		TreeItem<Category> child = new TreeItem<Category>();
		child.setValue(cat);
		if (root == null)
			treeView.getRoot().getChildren().add(child);
		else
			root.getChildren().add(child);

	}

}
