package com.mlenkiewicz.core;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;

import com.mlenkiewicz.db.Category;
import com.mlenkiewicz.db.SpendMoney;


public class DBManager {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("DataBase");
	private static EntityManager manager = factory.createEntityManager();

	public static List<SpendMoney> getLatestSpends(){
		return manager.createQuery("SELECT c FROM SpendMoney c").getResultList();
	}
	
	public static void addSpendMoney(SpendMoney spend){
		manager.getTransaction().begin();
		manager.persist(spend);
		manager.getTransaction().commit();
	}
	
	public static void addCategory(Category category){
		manager.getTransaction().begin();
		manager.persist(category);
		manager.getTransaction().commit();
		
	}
	public static List<Category> getCategories(){
		return manager.createQuery("SELECT c FROM Category c").getResultList();
	}
	
	public static List<SpendMoney> getAggregate(Date dateFrom,Date dateTo){
	return manager
				.createQuery("SELECT p FROM SpendMoney p where p.spendDate BETWEEN :dateFrom AND :dateFor")
				.setParameter("dateFrom", dateFrom, TemporalType.TIMESTAMP)
				.setParameter("dateFor", dateTo, TemporalType.TIMESTAMP).getResultList();
	}
}
