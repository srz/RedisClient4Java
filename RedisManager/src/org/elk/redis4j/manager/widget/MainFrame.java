package org.elk.redis4j.manager.widget;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame
{
	public static final int WIDTH = 300;
	public static final int HEIGHT = 200;

	public MainFrame()
	{
		setTitle("Hello CSDN.NET");
		setSize(WIDTH, HEIGHT);
		MainPanel panel = new MainPanel();
		Container c = getContentPane();
		c.add(panel);
		
		
		
		javax.swing.SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run()
			{
				System.out.println("====" + SwingUtilities.isEventDispatchThread());
			}});
	}
}
