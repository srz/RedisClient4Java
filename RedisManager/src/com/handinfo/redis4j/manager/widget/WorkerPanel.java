package com.handinfo.redis4j.manager.widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.api.classification.IServer;
import com.handinfo.redis4j.manager.widget.command.ConfirmExecutePanel;
import com.handinfo.redis4j.manager.widget.command.LongConnectionPanel;
import com.handinfo.redis4j.manager.widget.command.NormalInfoPanel;
import com.handinfo.redis4j.manager.worker.IExecuteCommand;

public class WorkerPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JLayeredPane workerLayer;
	private MainWindow mainWindow;
	private JPopupMenu popupMenu;

	public static final Integer COMMAND_LAYER = 1;

	public WorkerPanel(MainWindow mainWindow)
	{
		super(new GridLayout(1, 0));
		this.setBackground(Color.white);

		this.mainWindow = mainWindow;
		this.workerLayer = new JLayeredPane();
		this.workerLayer.setBorder(BorderFactory.createTitledBorder("工作区"));
		this.setOpaque(true);

		this.add(workerLayer);
		this.initPopMenu();
		this.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				onMouseRightClicked(e);
			}
		});

		this.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if (evt.getNewValue() == null)
				{
					// 此控件被删除,销毁数据库连接
					workerLayer.removeAll();
					WorkerPanel.this.mainWindow.getClient().getConnection().quit();
				}
			}
		});
	}

	/**
	 * @return the mainWindow
	 */
	public MainWindow getMainWindow()
	{
		return mainWindow;
	}

	private void onMouseRightClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	private void initPopMenu()
	{
		popupMenu = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("关闭所有窗口");
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				workerLayer.removeAll();
				workerLayer.repaint();
			}
		});
		popupMenu.add(menuItem);
	}

	public void executeCommand(NavigationData command)
	{
		boolean isHaveCreate = false;
		for (Component comp : workerLayer.getComponents())
		{
			if (((CommandExecutePanel) comp).getTitle().equalsIgnoreCase(command.getTitle()))
			{
				isHaveCreate = true;
				break;
			}
		}

		if (!isHaveCreate)
		{
			CommandExecutePanel panel = null;
			if (command.getTitle().equalsIgnoreCase("info"))
			{
				panel = new NormalInfoPanel(command.getTitle(), workerLayer, mainWindow.getClient());
			} else if (command.getTitle().equalsIgnoreCase("monitor"))
			{
				panel = new LongConnectionPanel(command.getTitle(), workerLayer, mainWindow.getClient());
			} else
			{
				final String className = command.getClassFullName();
				final String methodName = command.getMethodName();

				try
				{
					final Method  executor = Class.forName(className).getMethod(methodName);
					
					panel = new ConfirmExecutePanel(command.getTitle(), workerLayer, mainWindow.getClient(), new IExecuteCommand()
					{
						@Override
						public String executeCommand(IRedis4j client)
						{
							String result = "";
							Object instance = null;

							if(executor.getDeclaringClass().equals(IServer.class))
							{
								instance = client.getServer();
							}
							
							if(instance != null)
							{
								try
								{
									//执行命令
									result = String.valueOf(executor.invoke(instance));
								}
								catch (SecurityException e)
								{
									e.printStackTrace();
								}
								catch (IllegalArgumentException e)
								{
									e.printStackTrace();
								}
								catch (IllegalAccessException e)
								{
									e.printStackTrace();
								}
								catch (InvocationTargetException e)
								{
									e.printStackTrace();
								}
							}

							return result;
						}
					});
				}
				catch (SecurityException e)
				{
					e.printStackTrace();
				}
				catch (NoSuchMethodException e)
				{
					e.printStackTrace();
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			} 

			if (panel != null)
			{
				workerLayer.add(panel, WorkerPanel.COMMAND_LAYER);
				workerLayer.moveToFront(panel);
			}
		}
	}
}
