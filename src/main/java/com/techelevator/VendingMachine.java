package com.techelevator;

import java.io.File;

import java.util.Map;

public class VendingMachine {
	
	
	public static void main(String[] args) {
		File inputFile = new File("./vendingmachine.csv");
	
		
		Inventory inventory = new Inventory();
		Map<String, Item> itemMap = inventory.stockVendingMachine(inputFile);
		
		Menu menu = new Menu(itemMap, inventory);
		
		menu.getMainMenu();
	 	 		   
	}
}
		
