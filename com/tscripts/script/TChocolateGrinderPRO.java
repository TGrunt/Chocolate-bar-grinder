package com.tscripts.script;

import java.awt.ItemSelectable;

import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

import com.tscripts.strategy.BankTask;
import com.tscripts.strategy.GrindTask;
import com.tscripts.util.Antiban;
import com.tscripts.util.Paint;


@Manifest(name = "TChocolateBarGrinderPRO", description = "Grinds all chocolate bars in your bank with a great antiban. One click run.", version = 1.0, authors = { "TGrunt" })
public class TChocolateGrinderPRO  extends ActiveScript{

	public static final int CHOCOLATE_BAR = 1973;
	public static final int CHOCOLATE_DUST = 1975;

	@Override
	protected void setup() {
		
		final BankTask bankTask = new BankTask();
		bankTask.setTask(bankTask);
		provide(bankTask);
		
		final GrindTask grindTask = new GrindTask();
		grindTask.setTask(grindTask);
		provide(grindTask);
		
		final Antiban antiban = new Antiban();
		antiban.setTask(antiban);
		provide(antiban);
		
		final Paint paint = new Paint();
		paint.setTask(paint);
		provide(paint);
		
		
		
	}

}
