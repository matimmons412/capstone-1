package com.techelevator;

import java.math.BigDecimal;

public class Drink extends Item {

	public Drink(String name, double price) {
		super(name, price);
	
	}

	@Override
	public String getNoise() {
		return "Glug Glug, Yum!";
	}

	
}