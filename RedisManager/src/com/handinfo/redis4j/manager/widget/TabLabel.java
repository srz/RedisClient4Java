package com.handinfo.redis4j.manager.widget;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

public class TabLabel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JLabel title;
	private final JTabbedPane parentPane;
	private JPopupMenu popupMenu;

	public TabLabel(String titleText, JTabbedPane pane)
	{
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.title = new JLabel(titleText);
		this.parentPane = pane;
		this.add(title);

		this.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		this.setOpaque(false);

		this.initPopMenu();
		this.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				parentPane.setSelectedIndex(parentPane.indexOfTabComponent(TabLabel.this));
				
				if(e.getButton() == MouseEvent.BUTTON3)
				{
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		this.validate();
	}
	
	private void initPopMenu()
	{
		popupMenu = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("断开连接并关闭");
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parentPane.remove(parentPane.indexOfTabComponent(TabLabel.this));
				
			}});
		popupMenu.add(menuItem);
	}
}