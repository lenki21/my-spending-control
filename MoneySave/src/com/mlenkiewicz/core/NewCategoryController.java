package com.mlenkiewicz.core;

import java.util.List;

import javax.annotation.PostConstruct;

import com.mlenkiewicz.db.Category;

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

	private Stage stage;
	@PostConstruct
	public void initialize(Stage stage) {
		refreshCategoriesTree();
		this.stage=stage;
	}

	public TreeItem<Category> buildTree(List<Category> categories) {
		Category categoryRoot = new Category();
		categoryRoot.setName("ROOT");
		TreeItem<Category> root = new TreeItem<Category>(categoryRoot);
		for (Category category : categories) {
			if (category.getParentId() == 0) {
				TreeItem<Category> item = new TreeItem<Category>(category);
				root.getChildren().add(item);
				recurseAdd(item,categories);
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
		if (item != null){
			
			if(item.getValue().getId()!=0){
				category.setParentId(item.getValue().getId());	
			}
		}
			
		DBManager.addCategory(category);
		 refreshCategoriesTree();
	}
	@FXML
	public void btCloseAction(ActionEvent e) {
		stage.close();
	}
	
	private void refreshCategoriesTree(){
		List<Category> categories = DBManager.getCategories();
		treeView.setRoot(buildTree(categories));
	}

}
