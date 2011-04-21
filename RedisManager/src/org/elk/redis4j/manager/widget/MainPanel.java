package org.elk.redis4j.manager.widget;

import java.awt.Graphics;

import javax.swing.JPanel;

public class MainPanel extends JPanel
{
	public static final int MESSAGE_X = 100;
	public static final int MESSAGE_Y = 100;

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawString("Hello CSDN.NET", MESSAGE_X, MESSAGE_Y);
	}
}