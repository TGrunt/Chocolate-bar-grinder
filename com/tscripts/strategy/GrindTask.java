package com.tscripts.strategy;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Time;

import com.tscripts.script.TChocolateGrinderPRO;
import com.tscripts.util.Paint;

public class GrindTask extends Strategy implements Task {
	
	private final static int CHAT_WIDGET = 905;
	private final static int MAKE_ALL_BUTTON_WIDGET = 14;

	private static long timeStamp = 0;
	private static int dustStamp = 0;
	
	public boolean validate() {
		if (Tabs.getCurrent() != Tabs.INVENTORY) {
			Tabs.INVENTORY.open();
		}
		return (Inventory.getCount(TChocolateGrinderPRO.CHOCOLATE_BAR) > 0 && Bank.isOpen() && Bank.getItem(TChocolateGrinderPRO.CHOCOLATE_BAR).getWidgetChild() == null)
		|| Inventory.getCount(TChocolateGrinderPRO.CHOCOLATE_BAR) > 0;
	}
	
	@Override
	public void run() {
		
		if (!Bank.isOpen()) {
			if (Tabs.getCurrent() == Tabs.INVENTORY) {
			if (Inventory.getCount(TChocolateGrinderPRO.CHOCOLATE_DUST) == dustStamp 
					&& System.currentTimeMillis() - timeStamp >= 1500) {
			Inventory.getItem(TChocolateGrinderPRO.CHOCOLATE_BAR).getWidgetChild().interact("Powder");
			if (waitForCondition(widgetChildIsVisible(CHAT_WIDGET, MAKE_ALL_BUTTON_WIDGET), 1500))
				Widgets.get(CHAT_WIDGET, MAKE_ALL_BUTTON_WIDGET).interact("Make All");

			}
			
			if (System.currentTimeMillis() - timeStamp >= 2000 || dustStamp != Inventory.getCount(TChocolateGrinderPRO.CHOCOLATE_DUST)) {
				if (Inventory.getCount(TChocolateGrinderPRO.CHOCOLATE_DUST) - dustStamp > 0)
			Paint.chocolateBarsGrinded += 
				(Inventory.getCount(TChocolateGrinderPRO.CHOCOLATE_DUST) - dustStamp);
			dustStamp = Inventory.getCount(TChocolateGrinderPRO.CHOCOLATE_DUST);
			timeStamp = System.currentTimeMillis();

			}
			
		}
		else Tabs.INVENTORY.open();
		}
		else Bank.close();
	}
	
	public static Condition widgetChildIsVisible(final int parent, final int child) {
		return new Condition() {
			public boolean validate() {
				return Widgets.get(parent, child).visible();
			}
		};
	}
	
	public static boolean waitForCondition(final Condition c, final long timeout) {
		final long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < timeout && !c.validate()) {
			Time.sleep(50);
		}
		return c.validate();
	}

}
