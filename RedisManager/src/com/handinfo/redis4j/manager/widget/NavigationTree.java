package com.handinfo.redis4j.manager.widget;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


public class NavigationTree extends JPanel implements TreeSelectionListener
{
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private MainWindow parent;
	private JPopupMenu popupMenu;
	private DefaultMutableTreeNode currentClickedNode;
	
	public NavigationTree(String serverName, MainWindow parent)
	{
		super(new GridLayout(1, 0));
		
		this.parent = parent;
		this.initPopMenu();
		
		//创建顶级结点
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(serverName);
		createNodes(top);

		//创建导航树
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e)
			{
				onMouseRightClicked(e);
			}
			
		});

		JScrollPane treeView = new JScrollPane(tree);
		treeView.setMinimumSize(new Dimension(100, 50));

		this.add(treeView);
	}
	
	private void onMouseRightClicked(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			TreePath path = tree.getPathForLocation(e.getX(),e.getY());
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
			if (node == null)
				return;

			NavigationNode navigationNode = (NavigationNode) node.getUserObject();
			if(navigationNode.getIsHavePopMenu())
			{
				tree.setSelectionPath(path);
				currentClickedNode = node;
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
	
	private void initPopMenu()
	{
		popupMenu = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("刷新");
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				currentClickedNode.removeAllChildren();
				createTreeForAllDB(currentClickedNode);
				tree.updateUI();
			}
		});
		popupMenu.add(menuItem);
	}
	
	private class RedisCommand
	{
		private String commandName;
		private String commandValue;

		public RedisCommand(String name, String value)
		{
			commandName = name;
			commandValue = value;
		}

		public String toString()
		{
			return commandName;
		}
	}
	
	private class NavigationNode
	{
		private String name;
		private NavigationData value;
		private boolean isHavePopMenu;
		private boolean isExecuteCommand;

		public NavigationNode(String name, NavigationData value, boolean isHavePopMenu, boolean isExecuteCommand)
		{
			this.name = name;
			this.value = value;
			this.isHavePopMenu = isHavePopMenu;
			this.isExecuteCommand = isExecuteCommand;
		}
		
		public void setName(String name)
		{
			this.name = name;
		}
		
		public boolean getIsHavePopMenu()
		{
			return isHavePopMenu;
		}
		
		public boolean getIsExecuteCommand()
		{
			return isExecuteCommand;
		}

		public String toString()
		{
			return name;
		}
		
		public NavigationData getValue()
		{
			return value;
		}
	}

	private void createNodes(DefaultMutableTreeNode top)
	{
		DefaultMutableTreeNode firstLevel = null;
		DefaultMutableTreeNode secondLevel = null;
		DefaultMutableTreeNode thirdLevel = null;
		
		//一级
		firstLevel = new DefaultMutableTreeNode(new NavigationNode("Administrator View", null, false, false));
		top.add(firstLevel);
		
		//二级
		secondLevel = new DefaultMutableTreeNode(new NavigationNode("服务器状态", null, false, false));
		firstLevel.add(secondLevel);
		
		//三级
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Get information", new NavigationData("INFO", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "info"), false, true));
		secondLevel.add(thirdLevel);
		
		//TODO monitor方法换地方了，这里无法执行，需要修改
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Listen for all requests",  new NavigationData("MONITOR", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "monitor"), false, true));
		secondLevel.add(thirdLevel);

		//二级
		secondLevel = new DefaultMutableTreeNode(new NavigationNode("管理", null, false, false));
		firstLevel.add(secondLevel);
		
		//三级
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Asynchronously rewrite the append-only file", new NavigationData("BGREWRITEAOF", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "backgroundRewriteAOF"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Asynchronously save the dataset to disk", new NavigationData("BGSAVE", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "backgroundSave"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Get the value of a configuration parameter", new NavigationData("CONFIG_GET", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "configGet"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Set a configuration parameter to the given value", new NavigationData("CONFIG_SET", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "configSet"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Reset the stats returned by INFO", new NavigationData("CONFIG RESETSTAT", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "configResetStat"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Get debugging information about a key", new NavigationData("DEBUG_OBJECT", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "debugObject"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Make the server crash", new NavigationData("DEBUG_SEGFAULT", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "debugSegfault"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Remove all keys from all databases", new NavigationData("FLUSHALL", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "flushAllDB"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Get the UNIX time stamp of the last successful save to disk", new NavigationData("LASTSAVE", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "lastSave"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Synchronously save the dataset to dis", new NavigationData("SAVE", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "save"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Synchronously save the dataset to disk and then shut down the server", new NavigationData("SHUTDOWN", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "shutdown"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Make the server a slave of another instance, or promote it as master", new NavigationData("SLAVEOF", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "slaveof"), false, true));
		secondLevel.add(thirdLevel);
		thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Internal command used for replicatio", new NavigationData("SYNC", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "sync"), false, true));
		secondLevel.add(thirdLevel);

		//一级
		firstLevel = new DefaultMutableTreeNode(new NavigationNode("Databases", null, true, false));
		top.add(firstLevel);
		
		createTreeForAllDB(firstLevel);
		
//		Iterator<Entry<String, List<String>>> it = RedisCommandlist.getAllCommand().entrySet().iterator();
//		while (it.hasNext())
//		{
//			Entry<String, List<String>> entry = it.next();
//			category = new DefaultMutableTreeNode(entry.getKey());
//			top.add(category);
//			
//			List<String> item = entry.getValue();
//
//			for(String commandValue : item)
//			{
//				command = new DefaultMutableTreeNode(new RedisCommand(commandValue, commandValue));
//				category.add(command);
//			}
//		}
	}
	
	private void createTreeForAllDB(DefaultMutableTreeNode node)
	{
		String[] info = parent.getClient().info().split("\r\n");

		DefaultMutableTreeNode secondLevel = null;
		DefaultMutableTreeNode thirdLevel = null;
		
		for(String item : info)
		{
			if(item.startsWith("db"))
			{
				//二级
				secondLevel = new DefaultMutableTreeNode(new NavigationNode(item, null, false, false));
				node.add(secondLevel);
				
				//三级
				thirdLevel = new DefaultMutableTreeNode(new NavigationNode("The number of keys", new NavigationData("DBSIZE", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "dbSize"), false, true));
				secondLevel.add(thirdLevel);
				thirdLevel = new DefaultMutableTreeNode(new NavigationNode("Remove all keys from the current database", new NavigationData("FLUSHDB", "com.handinfo.redis4j.api.database.IRedisDatabaseClient", "flushCurrentDB"), false, true));
				secondLevel.add(thirdLevel);
			}
		}
		
		NavigationNode navigationNode = (NavigationNode)node.getUserObject();
		if(node.isLeaf())
		{
			navigationNode.setName("Databases - no data");
		}
		else
		{
			navigationNode.setName("Databases");
		}
	}
	

	@Override
	public void valueChanged(TreeSelectionEvent e)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		if (node == null)
			return;

		NavigationNode navigationNode = (NavigationNode) node.getUserObject();
		if (navigationNode.getIsExecuteCommand())
		{
			parent.executeCommand(navigationNode.getValue());
		}
	}
}
