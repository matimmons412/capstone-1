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
    	
	double balance;
	
	List <String> inventoryList = new ArrayList<>();

	Map<String, Item> itemMap = new LinkedHashMap<>();
	
	public Map<String, Item> stockVendingMachine(File inputFile) {
 		try (Scanner fileScanner = new Scanner(inputFile)){
			while (fileScanner.hasNextLine()) { 
				String singleLine = fileScanner.nextLine(); 
				String[] itemDetails = null;
				itemDetails = singleLine.split("\\|");
				if(itemDetails[3].equals("Chip")) {
					Item newItem = new Chip(itemDetails[1], Double.parseDouble(itemDetails[2]));
					itemMap.put(itemDetails[0], newItem);
				} else if (itemDetails[3].equals("Candy")) {
					Item newItem = new Candy(itemDetails[1], Double.parseDouble(itemDetails[2]));
					itemMap.put(itemDetails[0], newItem);
				} else if (itemDetails[3].equals("Drink")) {
					Item newItem = new Drink(itemDetails[1], Double.parseDouble(itemDetails[2]));
					itemMap.put(itemDetails[0], newItem);
				}else if (itemDetails[3].equals("Gum")) {
					Item newItem = new Gum(itemDetails[1], Double.parseDouble(itemDetails[2]));
					itemMap.put(itemDetails[0], newItem);
				}		
			}
		} catch (FileNotFoundException e) {
			System.out.println("There is an error with the file.");
			e.printStackTrace();
			}
		return itemMap;
	}
	
	public String purchase(String productCode) {
		double oldBalance = balance;
		if(!itemMap.containsKey(productCode)) {
			System.out.println("The selected product code doesn't exist.");
			return "The selected product code doesn't exist."; // return to Purchase Menu
		}
		if(itemMap.containsKey(productCode)) {
		 	Item currentItem = itemMap.get(productCode);
			if(currentItem.getQuantity() == 0) {
			System.out.print("Product is SOLD OUT\n\n"); ; // newMenu.getPurchaseMenu(); <<-- need to return to purchase menu
			return "Product is SOLD OUT"; // return to Purchase Menu
			} else if(balance >= currentItem.getPrice()) {										
			currentItem.reduceQuantity();
			balance -= currentItem.getPrice();
			System.out.println(currentItem.getName() + " $" + currentItem.getPrice() + " $" + balance + " " + "\"" +currentItem.getNoise() + "\"" + "\n");	
			createNewLogEntry(currentItem.getName() + " " + productCode, oldBalance, balance);
			return ""; // return to Purchase Menu	
			} else {
				System.out.println("Please enter more money."); // price exceeds balance 
				return ""; // return to Purchase Menu
			}
		}
		return null;
	}
	
	public boolean feedMoney(double addMoney) {

		double oldBalance = getBalance();
	
		Set<Double> values = new HashSet<Double>(Arrays.asList(new Double[] {1.00, 2.00, 5.00, 10.00}));
			if(values.contains(addMoney)) {
				balance += addMoney;
				createNewLogEntry("FEED MONEY: ", oldBalance, getBalance());
				return true;
			} 
		
		return false;
		
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
	
	public String coinChange (double balance) {
		
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
}


	

	
	
	
	
