package com.handinfo.redis4j.manager.widget;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.RedisClientBuilder;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;

public class NewConnection extends JDialog implements ActionListener, PropertyChangeListener
{
	private String tabName = null;
	private JTextField ipAddress;
	private JTextField port;
	private JPanel parent;

	private JOptionPane optionPane;

	private String btnConfirm = "连接";
	private String btnCancle = "取消";

	private IRedisDatabaseClient client;

	/**
	 * Returns null if the typed string was invalid; otherwise, returns the
	 * string as the user entered it.
	 */
	public String getTabName()
	{
		return tabName;
	}
	
	public IRedisDatabaseClient getNewClient()
	{
		return client;
	}

	/** Creates the reusable dialog. */
	public NewConnection(Frame mainFrame, JPanel parent)
	{
		super(mainFrame, true);
		this.parent = parent;

		setTitle("建立连接");

		ipAddress = new JTextField(10);
		ipAddress.setText("192.2.9.223");
		port = new JTextField(10);
		port.setText("6379");

		String msgString1 = "服务器IP地址";
		String msgString2 = "服务器端口号";
		Object[] array =
		{ msgString1, ipAddress, msgString2, port };

		Object[] options =
		{ btnConfirm, btnCancle };

		optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, options[0]);

		setContentPane(optionPane);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
			}
		});


		addComponentListener(new ComponentAdapter()
		{
			public void componentShown(ComponentEvent ce)
			{
				ipAddress.requestFocusInWindow();
			}
		});


		ipAddress.addActionListener(this);

		optionPane.addPropertyChangeListener(this);
	}

	/** This method handles events for the text field. */
	public void actionPerformed(ActionEvent e)
	{
		optionPane.setValue(btnConfirm);
	}

	/** This method reacts to state changes in the option pane. */
	public void propertyChange(PropertyChangeEvent e)
	{
		String prop = e.getPropertyName();

		if (isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop)))
		{
			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE)
			{
				// ignore reset
				return;
			}

			// Reset the JOptionPane's value.
			// If you don't do this, then if the user
			// presses the same button next time, no
			// property change event will be fired.
			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			if (btnConfirm.equals(value))
			{
				if (!checkIpAndPort(this.ipAddress.getText(), this.port.getText()))
				{
					JOptionPane.showMessageDialog(NewConnection.this, "输入的IP或端口号无效", "Try again", JOptionPane.ERROR_MESSAGE);
				} else
				{
					client = RedisClientBuilder.buildDatabaseClient(this.ipAddress.getText().trim(), Integer.valueOf(this.port.getText().trim()), 0, "");
					if (client.totalOfConnected() == 1)
					{
						tabName = ipAddress.getText().trim() + ":" + port.getText().trim();
						clearAndHide();
					} else
					{
						// text was invalid
						// ipAddress.selectAll();
						JOptionPane.showMessageDialog(NewConnection.this, "无法建立连接,请检查网络或服务器状态", "Try again", JOptionPane.ERROR_MESSAGE);
						// typedText = null;
						// ipAddress.requestFocusInWindow();
					}
				}
			} else
			{
				// user closed dialog or clicked cancel
				//parent.setLabel("It's OK.  " + "We won't force you to type .");
				tabName = null;
				clearAndHide();
			}
		}
	}

	/** This method clears the dialog and hides it. */
	public void clearAndHide()
	{
		// ipAddress.setText(null);
		setVisible(false);
	}

	private boolean checkIpAndPort(String ip, String port)
	{
		boolean result = false;
		boolean isIP = false;
		boolean isPort = false;

		ip = ip.trim();
		if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"))
		{
			String s[] = ip.split("\\.");
			if (Integer.parseInt(s[0]) < 255)
				if (Integer.parseInt(s[1]) < 255)
					if (Integer.parseInt(s[2]) < 255)
						if (Integer.parseInt(s[3]) < 255)
							isIP = true;
		}

		int newPort = -1;
		try
		{
			newPort = Integer.parseInt(port.trim());
		}
		catch (Exception e)
		{
			return false;
		}

		if (newPort >= 0 && newPort <= 65535)
		{
			isPort = true;
		}

		if (isIP && isPort)
		{
			result = true;
		}

		return result;
	}
}