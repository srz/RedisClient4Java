package com.handinfo.redis4j.manager.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.handinfo.redis4j.manager.RedisCommandlist;
import com.handinfo.redis4j.manager.RedisManager;
import com.handinfo.redis4j.manager.worker.RedisInfo;
import com.handinfo.redis4j.manager.worker.RedisMonitor;

public class Menu implements ItemListener
{
	private ActionListener menuActionListener;

	/**
	 * @param menuActionListener
	 */
	public Menu()
	{
		super();
		this.menuActionListener = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Menu.this.actionPerformed(e);
			}
		};
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JMenuItem source = (JMenuItem) (e.getSource());
//		String s = "Action event detected." + newline + "    Event source: " + source.getText() + " (an instance of " + getClassName(source) + ")";
//		info.append(s + newline);
//		
//		if(source.getActionCommand().equalsIgnoreCase("INFO"))
//		{
//			if(isStartInfo)
//			{
//				redisInfo.cancel(true);
//			}
//			else
//			{
//				redisInfo = new RedisInfo(info);
//				redisInfo.execute();
//			}
//			isStartInfo = !isStartInfo;
//		}
//		else if(source.getActionCommand().equalsIgnoreCase("MONITOR"))
//		{
//			if(isStartMonitor)
//			{
//				redisMonitor.cancel(true);
//			}
//			else
//			{
//				redisMonitor = new RedisMonitor(monitor);
//				redisMonitor.execute();
//			}
//			isStartMonitor = !isStartMonitor;
//		}
//		
//		monitor.setCaretPosition(monitor.getDocument().getLength());
	}

	public void itemStateChanged(ItemEvent e)
	{
		JMenuItem source = (JMenuItem) (e.getSource());
//		String s = "Item event detected." + newline + "    Event source: " + source.getText() + " (an instance of " + getClassName(source) + ")" + newline + "    New state: " + ((e.getStateChange() == ItemEvent.SELECTED) ? "selected" : "unselected");
//		info.append(s + newline);
//		info.setCaretPosition(info.getDocument().getLength());
	}
	
	// Returns just the class name -- no package info.
	protected String getClassName(Object o)
	{
		String classString = o.getClass().getName();
		int dotIndex = classString.lastIndexOf(".");
		return classString.substring(dotIndex + 1);
	}

	
	public JMenuBar createMenuBar()
	{
		Map<String, List<String>> allCommand = RedisCommandlist.getAllCommand();

		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;


		// Create the menu bar.
		menuBar = new JMenuBar();
		
		menu = new JMenu("系统");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("新建连接");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
			{
				RedisManager.getNewConnection().setLocationRelativeTo(RedisManager.getMainFrame());
				RedisManager.getNewConnection().pack();
				RedisManager.getNewConnection().setVisible(true);

				String tabName = RedisManager.getNewConnection().getTabName();
				if(tabName != null)
					RedisManager.addNewTab(tabName, RedisManager.getNewConnection().getNewClient());
			}});
		menu.add(menuItem);
		
		menuItem = new JMenuItem("退出");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}});
		menu.add(menuItem);

//		Iterator<Entry<String, List<String>>> it = allCommand.entrySet().iterator();
//		while (it.hasNext())
//		{
//			Entry<String, List<String>> entry = it.next();
//			menu = new JMenu(entry.getKey());
//			menuBar.add(menu);
//			
//			List<String> item = entry.getValue();
//
//			for(String s : item)
//			{
//				menuItem = new JMenuItem(s);
//				menuItem.setActionCommand(s);
//				menuItem.addActionListener(menuActionListener);
//				menu.add(menuItem);
//			}
//		}

		return menuBar;
	}

}
