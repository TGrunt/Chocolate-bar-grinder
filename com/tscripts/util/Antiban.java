package com.tscripts.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.bot.event.listener.PaintListener;

public class Antiban extends Strategy implements Task {
	
	private static long lastAction = System.currentTimeMillis();
	private static long delay = 200000;
	
	public boolean validate() {
		return System.currentTimeMillis() - lastAction >= delay && Game.isLoggedIn() && !Bank.isOpen(); 
	}

	@Override
	public void run() {
		
		lastAction = System.currentTimeMillis();
		delay = Random.nextInt(100000, 300000);
		
		int x = Random.nextInt(0, 6);
		Paint.setMouseColor(Paint.mouseAntibanRedColor);
		
		switch (x) {
		case 1:
			Camera.setAngle(Random.nextInt(0, 360));
			break;
		case 2:
			Camera.setPitch(true);
			break;
		case 3:
			Tabs.values()[Random.nextInt(0, Tabs.values().length)].open();
			break;
		case 4:
			Time.sleep(Random.nextInt(15000, 100000));
			break;
		case 5:
			Dimension game = Game.getDimensions();
			Mouse.move(new Point(Random.nextInt(0, game.width), Random.nextInt(0, game.height)));
			break;
		}
		
		Paint.setMouseColor(Paint.mouseDefaultBlueColor);
	}

}
