package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Inventory {
    	
	private double balance;

	private Map<String, Item> itemInventory = new LinkedHashMap<>();
	
	public Map<String, Item> stockVendingMachine(File inputFile) {
 		try (Scanner fileScanner = new Scanner(inputFile)){
			while (fileScanner.hasNextLine()) { 
				String lineFromFile = fileScanner.nextLine(); 
				String[] itemDetails = lineFromFile.split("\\|");
				
				if (itemDetails[3].equals("Chip")) {
					makeNewChipItemFrom(itemDetails);
					
				} else if (itemDetails[3].equals("Candy")) {
					makeNewCandyItemFrom(itemDetails);
					
				} else if (itemDetails[3].equals("Drink")) {
					makeNewDrinkItemFrom(itemDetails);
					
				} else if (itemDetails[3].equals("Gum")) {
					makeNewGumItemFrom(itemDetails);
					
				}		
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("There is an error with the file. File Not Found.");
			e.printStackTrace();
			}
		return itemInventory;
	}
	
	public void makeNewChipItemFrom(String[] itemDetails) {
		Item newItem = new Chip(itemDetails[1], Double.parseDouble(itemDetails[2]));
		itemInventory.put(itemDetails[0], newItem);
	}
	
	public void makeNewCandyItemFrom(String[] itemDetails) {
		Item newItem = new Candy(itemDetails[1], Double.parseDouble(itemDetails[2]));
		itemInventory.put(itemDetails[0], newItem);
	}
	
	public void makeNewDrinkItemFrom(String[] itemDetails) {
		Item newItem = new Drink(itemDetails[1], Double.parseDouble(itemDetails[2]));
		itemInventory.put(itemDetails[0], newItem);
	}
	
	public void makeNewGumItemFrom(String[] itemDetails) {
		Item newItem = new Gum(itemDetails[1], Double.parseDouble(itemDetails[2]));
		itemInventory.put(itemDetails[0], newItem);
	}
	
	public boolean purchaseItem(String productCode) {
		double oldBalance = balance;
		boolean productExists = checkIfItemExistsUsing(productCode);
		boolean purchaseOutcome = false;
		
		if(productExists) {
		 	Item selectedItem = itemInventory.get(productCode);
		 	boolean itemInStock = checkInventoryQuantity(selectedItem);
		 	if (itemInStock) {
		 		if(balance >= selectedItem.getPrice()) {										
		 			printPurchseDetails(selectedItem, productCode, oldBalance);
		 			purchaseOutcome = true;
		 		} else {
		 			System.out.println("Please enter more money."); 
		 		}
		 	}
		}
		return purchaseOutcome;
		
	}
	
	private boolean checkIfItemExistsUsing(String productCode) {
		if(!itemInventory.containsKey(productCode)) {
			System.out.println("The selected product code doesn't exist.");
			return false;
		} else {
			return true;
		}
	}
	
	private void printPurchseDetails(Item selectedItem, String productCode, double oldBalance) {
		selectedItem.reduceQuantity();
		balance -= selectedItem.getPrice();
		System.out.println(selectedItem.getName() + " $" + selectedItem.getPrice() + " $" + balance + " " + "\"" +selectedItem.getNoise() + "\"" + "\n");	
		createNewLogEntry(selectedItem.getName() + " " + productCode, oldBalance, balance);
	}
	
	private boolean checkInventoryQuantity(Item selectedItem) {
		if(selectedItem.getQuantity() == 0) {
			System.out.print("Product is SOLD OUT\n\n"); ;
			return false; 
		} else {
			return true;
		}
	}
	
	public boolean feedMoney(String userSelection) {
		double moneyToAdd = Double.parseDouble(userSelection);
		double oldBalance = getBalance();
		balance += moneyToAdd;
		createNewLogEntry("FEED MONEY: ", oldBalance, balance);
		return true;
	}
	
	public boolean createNewLogEntry(String step, double oldBalance, double newBalance) {
		String fileName = "log.txt";
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");
		String formattedDate = myDateObj.format(myFormatObj);		
		File filePath = new File(".");
		fileName = "log.txt";
		File logFile = new File(filePath, fileName);
		if (filePath.exists()) {
			if (!logFile.exists()) {
				try {
					logFile.createNewFile();
					PrintWriter fileWriter = new PrintWriter(new FileWriter(fileName, true));
					fileWriter.printf(formattedDate + " " + step + " " + "$" + oldBalance + " " + "$" + newBalance + "\n");
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (logFile.exists()) {
			try(PrintWriter fileWriter = new PrintWriter(new FileWriter(fileName, true))) {		
				fileWriter.printf(formattedDate + " "+ step + " " + "$" + oldBalance + " " + "$" + newBalance + "\n");			
			} catch (IOException e) {			
				e.printStackTrace();
				}
			}
			return true;
		}
		return false;	
	}
	
	public double depositCashAsPennies (double deposit) {
		return  (deposit * 100);
	}
	
	public String makeCoinChange (double balance) {		
		double quartersDue = 0;
		double dimesDue = 0;                                                      
		double nickelsDue = 0;                                                    	                                                                       
		// Balance counter moves through 25s, to 10s, to 5s                    
			if (balance % 25 == 0) {                                         
				quartersDue = balance / 25;                                  
				return "Here are your coins: " + quartersDue + " Quarters.";   
			} else if (balance % 25 != 0) {                                  
				quartersDue = balance / 25; 
				balance -= (int)quartersDue * 25;
				dimesDue = balance / 10; 
				balance -= (int)dimesDue * 10;
				nickelsDue = balance / 5;                              
			}                                                                  
		                                                                      
		return "Here are your coins: " + (int)quartersDue + " Quarter(s), " 
				+ (int)dimesDue + " Dime(s), and " + (int)nickelsDue + " Nickel(s).";		
	}
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double newBalance) {
		this.balance = newBalance;
	}
}


	

	
	
	
	
