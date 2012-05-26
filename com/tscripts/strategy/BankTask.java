package com.tscripts.strategy;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Time;

import com.tscripts.script.TChocolateGrinderPRO;

public class BankTask extends Strategy implements Task {
	
	public boolean validate() {
		return Inventory.getCount(TChocolateGrinderPRO.CHOCOLATE_BAR) == 0
		|| Inventory.getCount(TChocolateGrinderPRO.CHOCOLATE_DUST) == Inventory.getCount();
	}
	
	@Override
	public void run() {
		
		if (Bank.isOpen()) { 
			if (Inventory.getCount(TChocolateGrinderPRO.CHOCOLATE_DUST) > 0)
			Bank.depositInventory();
			else if (Bank.getItemCount(TChocolateGrinderPRO.CHOCOLATE_BAR) > 0)
				Bank.withdraw(TChocolateGrinderPRO.CHOCOLATE_BAR, 0);
			else if (Bank.getItems().length > 0) {
				Bank.close();
				Time.sleep(300);
				Game.logout(true);
			}
		}
		else 
			Bank.open();
	}

}
