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
	private List<String> validBillAmounts = new ArrayList<>();
	private List<String> validMenuInputs = new ArrayList<>();
	private Map<String, Item> itemMap;
	private boolean validMoney;

	public Menu(Map<String, Item> itemMap, Inventory newInventory) {
		this.newInventory = newInventory;
		this.itemMap = itemMap;
	}

	public void showMainMenu() {
		createValidMenuOptions();
		while (!validInput) {
			printMainMenu();

			if (validMenuInputs.contains(userSelection)) {
				if (userSelection.equals("1")) {
					displayVendingMachineInventory();
					System.out.println("\nType 'R' to return to the main menu.");
					getUserInput();
					if (userSelection.equalsIgnoreCase("R")) {
						continue;
						
					} else {
						continue;
					}
					
				} else if (userSelection.equals("2")) {
					showPurchaseMenu();
					
				} else if (userSelection.equals("3")) {
					exitVendingMachine();
					
				} else {
					System.out.println("Invalid selection, please choose a valid option.");
					continue;
				}
			}
		}
	}

	public void showPurchaseMenu() {
		createValidBillAmounts();
		while (!validInput) {
			printPurchaseMenu();
			if (validMenuInputs.contains(userSelection)) {
				if (userSelection.equals("1")) {
					showPurchaseOptionOne();

				} else if (userSelection.equals("2")) {
					showPurchaseOptionTwo();
					continue;

				} else if (userSelection.equals("3")) {
					showPurchaseOptionThree();
					return;

			} else {
				System.out.println("Invalid selection, please choose a valid option.");
				continue;
					
				}
			}
		}
	}

	private String getUserInput() {
		userSelection = userInput.nextLine();
		return userSelection;
	}

	private void printMainMenu() {
		System.out.println("\nMAIN MENU\n");
		System.out.println("[1] Display Vending Machine Items");
		System.out.println("[2] Purchase");
		System.out.println("[3] Exit");
		System.out.println("\nPlease enter your selection: ");
		getUserInput();
	}

	private void printPurchaseMenu() {
		System.out.println("\nPURCHASE MENU\n");
		System.out.println("[1] Feed Money");
		System.out.println("[2] Select Product");
		System.out.println("[3] Finish Transaction\n");
		System.out.println("Current Money Provided: " + "$" + newInventory.getBalance() + "");
		System.out.println("\nPlease enter your selection: ");
		getUserInput();
	}

	private void createValidMenuOptions() {
		validMenuInputs.add("1");
		validMenuInputs.add("2");
		validMenuInputs.add("3");
	}

	private void createValidBillAmounts() {
		validBillAmounts.add("1");
		validBillAmounts.add("2");
		validBillAmounts.add("5");
		validBillAmounts.add("10");
	}
	private void displayVendingMachineInventory() {
		for (Map.Entry<String, Item> entry : itemMap.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
	
	private void showPurchaseOptionOne() {
		while (!validMoney) {
			System.out.println("Please enter amount in whole dollars (1, 2, 5, & 10 is accepted): "); 																							
			getUserInput();
			addMoneyToUserBalance();
		}
	}
	
	private void showPurchaseOptionTwo() {
		displayVendingMachineInventory();
		System.out.println("Please enter Product Code selection");
		getUserInput(); 
		newInventory.purchase(userSelection);
	}
	
	private void showPurchaseOptionThree() {
		double oldBalance = newInventory.getBalance();
		System.out.println(
				newInventory.coinChange(newInventory.depositCashAsPennies(newInventory.getBalance())));
		newInventory.createNewLogEntry("GIVE CHANGE: ", oldBalance, newInventory.getBalance());
		newInventory.setBalance(0);
		System.out.println("Transaction Complete\n");
	}
	
	private void addMoneyToUserBalance(){
		try {
			if (newInventory.feedMoney(Double.parseDouble(userSelection))) {
				System.out.println("\n$" + userSelection + " was added to your balance.");
				validMoney = true;
			} else {
				System.out.println("\nInvalid Currency Amount, Only $1s, $2s, $5s, and $10s accepted.");
				// could also create a Exception and throw it here too
			}
		} catch (NumberFormatException e) {
			System.out.println("\nInvalid Currency Amount, Only $1s, $2s, $5s, and $10s accepted.\n");

		}
	}
	
	private void exitVendingMachine() {
		System.out.println("Thank you for your business.");
		System.out.println("Have a great day!");
		System.exit(0);
	}
}
