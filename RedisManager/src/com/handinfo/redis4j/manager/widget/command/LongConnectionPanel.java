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
import com.handinfo.redis4j.manager.worker.RedisMonitor;

public class LongConnectionPanel extends CommandExecutePanel implements IResult
{
	private static final long serialVersionUID = 1L;
	private RedisMonitor redisMonitor;
	private JMenuItem menuItem;

	public LongConnectionPanel(String cmd, JLayeredPane parent, IRedisDatabaseClient client)
	{
		super(cmd, parent, client);
		this.redisMonitor = new RedisMonitor(this.getRedisClient(), this);

		JPopupMenu popupMenu = this.getPopupMenu();
		menuItem = new JMenuItem();
		menuItem.setText("Çå¿Õ");
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				LongConnectionPanel.this.getTextArea().setText("");
			}
		});
		popupMenu.add(menuItem);
		
		redisMonitor.execute();
	}

	@Override
	public void onRemoved()
	{
		redisMonitor.cancel(true);
		System.out.println("==" + this.getTitle() + "±»ÒÆ³ýÁË");
	}

	@Override
	public void setResult(String result)
	{
		JTextArea textArea = this.getTextArea();
		textArea.append(result + "\n");

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

		int newHeight = (int) dimension.getHeight() + 30;
		if (newHeight > this.getParent().getHeight()-this.getY())
		{
			newHeight = this.getParent().getHeight()-this.getY();
		}
		this.setSize(this.getWidth(), newHeight);
		this.revalidate();
	}
}
