package com.handinfo.redis4j.manager.widget.command;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.manager.widget.CommandExecutePanel;
import com.handinfo.redis4j.manager.worker.IResult;
import com.handinfo.redis4j.manager.worker.RedisInfo;

public class NormalInfoPanel extends CommandExecutePanel implements IResult
{
	private static final long serialVersionUID = 1L;
	private RedisInfo redisInfo;
	private boolean isAutomaticUpdate;
	private JMenuItem menuItem;

	public NormalInfoPanel(String cmd, JLayeredPane parent, IRedisDatabaseClient client)
	{
		super(cmd, parent, client);
		this.isAutomaticUpdate = false;
		this.redisInfo = new RedisInfo(this.getRedisClient(), this, isAutomaticUpdate);

		JPopupMenu popupMenu = this.getPopupMenu();
		menuItem = new JMenuItem();
		
		initPopupMenu();
		
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				isAutomaticUpdate = !isAutomaticUpdate;
				redisInfo.setAutomaticUpdate(isAutomaticUpdate);
				initPopupMenu();
			}
		});
		popupMenu.add(menuItem);

		
		redisInfo.execute();
	}
	
	private void initPopupMenu()
	{
		if (isAutomaticUpdate)
		{
			menuItem.setText("停止自动刷新");
		} else
		{
			menuItem.setText("每5秒自动刷新");
		}
	}

	@Override
	public void onRemoved()
	{
		redisInfo.cancel(true);
		System.out.println("==" + this.getTitle() + "被移除了");
	}

	@Override
	public void setResult(String result)
	{
		JTextArea textArea = this.getTextArea();
		textArea.setText(result);

		Rectangle rect = null;
		try
		{
			rect = textArea.modelToView(textArea.getDocument().getLength());
		}
		catch (BadLocationException e)
		{
			System.out.println(e.getMessage());
		}

		Dimension dimension = new Dimension(0, rect.y + rect.height);
		textArea.setPreferredSize(dimension);

		this.setSize(this.getWidth(), (int) dimension.getHeight() + 30);
		this.revalidate();
	}
}
