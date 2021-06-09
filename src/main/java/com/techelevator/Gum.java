package com.techelevator;

import java.math.BigDecimal;

public class Gum extends Item {

	public Gum(String name, double price) {
		super(name, price);
	
	}


	@Override
	public String getNoise() {
		return "Chew Chew, Yum!";
	}

	
}
