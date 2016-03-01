package com.mlenkiewicz.model;

import java.util.Date;

import com.mlenkiewicz.db.SpendMoney;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SpendMoneyModel {

	private StringProperty idValue;
	private StringProperty categoryValue;
	private StringProperty descriptionValue;
	private DoubleProperty costValue;
	private StringProperty spendDate;

	public SpendMoneyModel(SpendMoney spendMondy) {
		idValue = new SimpleStringProperty("id");
		categoryValue = new SimpleStringProperty(spendMondy.getCategory().getName());
		descriptionValue = new SimpleStringProperty(spendMondy.getDescripton());
		costValue = new SimpleDoubleProperty(spendMondy.getCost());
		spendDate = new SimpleStringProperty(spendMondy.getSpendDate().toString());
	}

	public StringProperty idProperty() {
		return idValue;
	}

	public StringProperty categoryProperty() {
		return categoryValue;
	}

	public StringProperty descriptionProperty() {
		return descriptionValue;
	}

	public StringProperty spendProperty() {
		return spendDate;
	}

	public DoubleProperty costProperty() {
		return costValue;
	}

	public void setId(int id) {
		idValue.set(id + "");
	}

	public void setCategory(String category) {
		categoryValue.set(category);
	}

	public void setDescription(String description) {
		descriptionValue.set(description);
	}

	public void setSpendDate(Date date) {
		spendDate.set(date.toString());

	}

	public void setCost(double cost) {
		costValue.set(cost);
	}

	public String getId() {
		return idValue.get();
	}

	public String getCategory() {
		return categoryValue.get();
	}

	public String getDescription() {
		return descriptionValue.get();
	}

	public String getDate() {
		return spendDate.get();
	}

	public double getCost() {
		return costValue.get();
	}

}
