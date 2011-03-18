package com.handinfo.redis4j.manager.widget;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.handinfo.redis4j.api.IRedis4j;

public class MainWindow extends JPanel
{
	private static final long serialVersionUID = 1L;
	private WorkerPanel workerPanel;
	private NavigationTree tree;
	private IRedis4j client;

	public MainWindow(String serverName, IRedis4j client)
	{
		super(new GridLayout(1, 0));
		this.client = client;
		
		//创建导航树
		tree = new NavigationTree(serverName, this);

		//创建工作区
		workerPanel = new WorkerPanel(this);
		//initHelp();
		JScrollPane workerView = new JScrollPane(workerPanel);

		//创建分割条
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(tree);
		splitPane.setRightComponent(workerView);

		workerView.setMinimumSize(new Dimension(100, 50));
		
		//设置位置
		splitPane.setDividerLocation(300);

		this.add(splitPane);
	}

	/**
	 * @return the client
	 */
	public IRedis4j getClient()
	{
		return client;
	}

	public void executeCommand(NavigationData value)
	{
		workerPanel.executeCommand(value);
		workerPanel.revalidate();
	}
}