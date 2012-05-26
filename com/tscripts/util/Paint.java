package com.tscripts.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.bot.event.listener.PaintListener;

public class Paint extends Strategy implements Task, PaintListener {

	public static int chocolateBarPrice;
	public static int chocolateDustPrice;

	public static int chocolateBarsGrinded = 0;
	public static int chocolateBarsGrindedPerHour = 0;
	
	private static Timer timer;
	private static long lastTimeMsUpdate = 0;
	
	@Override
	public boolean validate() {
		return System.currentTimeMillis() - lastTimeMsUpdate >= 5000;
	}
	
	@Override
	public void run() {
		if (timer == null)
			timer = new Timer(0);
		
		lastTimeMsUpdate = System.currentTimeMillis();
		
		chocolateBarsGrindedPerHour = (int) ((1000L*60*60 / timer.getElapsed()) * chocolateBarsGrinded);
	}
	
	
	
	private static int noticeA = 200;
	private static boolean lower = true;
	
	private static Color mouseColor = new Color(47, 183, 237, 75);
	
	public static final Color mouseAntibanRedColor = new Color(173, 12, 12, 85);
	public static final Color mouseDefaultBlueColor = new Color(47, 183, 237, 75);
	public static Color noticeDefaultColor = new Color(71, 163 , 255, noticeA);
	
	@Override
	public void onRepaint(Graphics g) {
		int y = 50;
		
		if (timer != null) {
			g.drawString("Runtime: "+timer.toElapsedString(), 5, y);
			y += 10;
		}
		
		if (chocolateBarsGrinded > 0) {
			g.drawString("Chocolate bars grinded: "+chocolateBarsGrinded, 5, y);
			y+=10;
		}
		
		if (chocolateBarsGrindedPerHour > 0) {
			g.drawString("Per hour: "+chocolateBarsGrindedPerHour, 5, y);
		}
		
		if (!Game.isLoggedIn())
			paintNotice("Please login", g);
			
		if (Mouse.getLocation() != null)
			paintMouse(g);
	}
	
	public void paintMouse(Graphics g) {
		
		g.setColor(mouseColor);
		g.drawLine(Mouse.getX(), 0, Mouse.getX(), Game.getDimensions().height);
		g.drawLine(0, Mouse.getY(), Game.getDimensions().width, Mouse.getY());
	}
	
	public void paintNotice(String notice, Graphics g) {
		
		int offset = notice.length()*10;
		if (noticeA <= 75 && lower || noticeA >= 200 && !lower)
			lower = !lower;
		if (noticeA > 75 && lower)
			noticeA -= 4;
		if (!lower)
			noticeA += 4;
		noticeDefaultColor = new Color(71, 163 , 255, noticeA);
		g.setColor(noticeDefaultColor);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString(notice, Game.getDimensions().width/2 - offset, 50);
	}
	
	public static void setMouseColor(Color c) {
		mouseColor = c;
	}

}
