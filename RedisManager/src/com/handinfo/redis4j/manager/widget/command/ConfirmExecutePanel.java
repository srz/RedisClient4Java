package com.handinfo.redis4j.manager.widget.command;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.manager.widget.CommandExecutePanel;
import com.handinfo.redis4j.manager.worker.IExecuteCommand;
import com.handinfo.redis4j.manager.worker.IResult;
import com.handinfo.redis4j.manager.worker.OnlyExecuteOnceWorker;

public class ConfirmExecutePanel extends CommandExecutePanel implements IResult
{
	private static final long serialVersionUID = 1L;
	private OnlyExecuteOnceWorker worker;


	public ConfirmExecutePanel(String cmd, JLayeredPane parent, IRedisDatabaseClient client, IExecuteCommand executor)
	{
		super(cmd, parent, client);

		this.worker = new OnlyExecuteOnceWorker(this.getRedisClient(), this, executor);

		worker.execute();
	}
	

	@Override
	public void onRemoved()
	{
		worker.cancel(true);
		System.out.println("==" + this.getTitle() + "±»ÒÆ³ýÁË");
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
