package com.techelevator;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

	public class Menu {
		
			private boolean validInput = false;
		
			private Scanner userInput = new Scanner(System.in);
			private String userSelection;
			private Inventory newInventory;
			private List<String> validMenuInputs = new ArrayList<>(Arrays.asList("1", "2", "3"));
			private Map<String, Item> itemMap;
		
		// To make a new map, we need to be given a new map and given a new inventory
		public Menu(Map<String, Item> itemMap, Inventory newInventory) {
			this.newInventory = newInventory;
			this.itemMap = itemMap;
		}
		
		public String getUserInput() {
			userSelection = userInput.nextLine(); 
			return userSelection;
		}
		
		public void getMainMenu() {
						
			while (!validInput) {
				System.out.println("\nPURCHASE MENU\n");
				System.out.println("[1] Display Vending Machine Items");
				System.out.println("[2] Purchase");
				System.out.println("[3] Exit");
				System.out.println("\nPlease enter your selection: ");
					getUserInput();
					if(validMenuInputs.contains(userSelection)) {
						
						if(userSelection.equals("1")) {
	
							for (Map.Entry <String, Item> entry : itemMap.entrySet()) {
							 	 System.out.println(entry.getKey() + " " + entry.getValue());
							}
							
								System.out.println("\nType 'R' to return to the main menu.");
										getUserInput();
								
								if (userSelection.equalsIgnoreCase("R")) {
									continue;
								} else {
									continue;
									}
							
						} else if(userSelection.equals("2")) {
							getPurchaseMenu();
								
						} else if(userSelection.equals("3")) {
							System.out.println("Thank you for your business.");
							System.out.println("Have a great day!");
							System.exit(0); // Exit the program
						
						} else {
							System.out.println("Invalid selection, please choose a valid option.");
							continue;
						}	
					}
				}
			}
		
		public void getPurchaseMenu() {
			
			List<String> validBillInput = new ArrayList<>();
				validBillInput.add("1");
				validBillInput.add("2");
				validBillInput.add("5");
				validBillInput.add("10");
				
			while (!validInput) {
				System.out.println("\nPURCHASE MENU\n");
				System.out.println("[1] Feed Money");
				System.out.println("[2] Select Product");
				System.out.println("[3] Finish Transaction\n");
				System.out.println("Current Money Provided: " + "$" + newInventory.getBalance()+""); 
				System.out.println("\nPlease enter your selection: ");
				
				getUserInput(); // returns userSelection -- String
				
				if(validMenuInputs.contains(userSelection)) {
					
					if(userSelection.equals("1")) {
						
						boolean validMoney = false;
						while(!validMoney) {
							System.out.println("Please enter amount in whole dollars (1, 2, 5, & 10 is accepted): "); // Display items
							getUserInput();// returns userSelection -- String
							
							try {
								if(newInventory.feedMoney(Double.parseDouble(userSelection))) {
									System.out.println("\n$" + userSelection + " was added to your balance.");
									validMoney = true;
								} else {
									System.out.println("\nInvalid Currency Amount, Only $1s, $2s, $5s, and $10s accepted.");
									// could also create a Exception and throw it here too
								}
							} catch(NumberFormatException e) {
								System.out.println("\nInvalid Currency Amount, Only $1s, $2s, $5s, and $10s accepted.\n");
								
							}
						}
							
					} else if(userSelection.equals("2")) {
						System.out.println("Display of items\n"); // -- same as list from main menu
						
							for (Map.Entry <String, Item> entry : itemMap.entrySet()) {
						 		 System.out.println(entry.getKey() + " " + entry.getValue());
							}
						
						System.out.println("Please enter Product Code selection");
						
						getUserInput(); // returns userSelection -- String
						
						newInventory.purchase(userSelection);
						
						continue;
						
					} else if(userSelection.equals("3")) {
						
						double oldBalance = newInventory.getBalance();
						System.out.println(newInventory.coinChange(newInventory.depositCashAsPennies(newInventory.getBalance())));
						
						newInventory.createNewLogEntry("GIVE CHANGE: ", oldBalance, newInventory.getBalance());
						newInventory.balance = 0;// balance re-set to 0 AKA balance
							
							System.out.println("Transaction Complete\n");
							return;// return to Main Menu
							
				} else {
					System.out.println("Invalid selection, please choose a valid option.");
					continue;
				}		
			}
		}
	}
	}
