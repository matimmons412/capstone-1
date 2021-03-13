package com.techelevator;

import java.io.File;

import java.util.Map;

public class VendingMachine {
	
	
	public static void main(String[] args) {
		File inputFile = new File("./vendingmachine.csv");
	
		
		Inventory vm800 = new Inventory();
		Map<String, Item> itemMap = vm800.runVendingMachine(inputFile);
		
		Menu testMenu = new Menu(itemMap, vm800);
		
		testMenu.getMainMenu();
	 	 		   
	}
}
		
