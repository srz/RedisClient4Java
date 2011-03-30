package com.handinfo.redis4j.manager;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.manager.widget.MainWindow;
import com.handinfo.redis4j.manager.widget.Menu;
import com.handinfo.redis4j.manager.widget.NewConnection;
import com.handinfo.redis4j.manager.widget.TabLabel;

public class RedisManager 
{
	private static boolean useSystemLookAndFeel = true;
	private static JFrame mainFrame = new JFrame("RedisManager");
	private static NewConnection newConnection = new NewConnection(mainFrame, null);
	private static JTabbedPane tabbedPane = new JTabbedPane();
	
	public static JFrame getMainFrame()
	{
		return mainFrame;
	}
	
	public static NewConnection getNewConnection()
	{
		return newConnection;
	}
	
	public static void addNewTab(String tabName, IRedisDatabaseClient client)
	{
		MainWindow mainWindow = new MainWindow(tabName, client);
		tabbedPane.addTab(null, mainWindow);
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(mainWindow), new TabLabel(tabName, tabbedPane));
		//getMainFrame().pack();
	}
	private static void createAndShowGUI()
	{
		if (useSystemLookAndFeel)
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (Exception e)
			{
				System.err.println("Couldn't use system look and feel.");
			}
		}
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.setJMenuBar(new Menu().createMenuBar());
		mainFrame.add(tabbedPane);

		//mainFrame.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setSize((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);
		//mainFrame.setLocation(0,0); 
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);
	}

	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGUI();
			}
		});
	}
}