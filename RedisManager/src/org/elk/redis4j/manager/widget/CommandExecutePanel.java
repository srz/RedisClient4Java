package org.elk.redis4j.manager.widget;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.elk.redis4j.api.database.IRedisDatabaseClient;


public abstract class CommandExecutePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JTextArea redisResult;
	private JScrollPane resultView;
	private JLayeredPane parent;
	private int mouseX = 0;
	private int mouseY = 0;
	private boolean isDragModel = false;
	private int mousePressLocation = 0;
	private int labelWidth = 0;
	private int labelHeight = 0;
	private int labelX = 0;
	private int labelY = 0;
	private String title;
	private IRedisDatabaseClient client;
	private JPopupMenu popupMenu;

	public CommandExecutePanel(String cmd, final JLayeredPane parent, IRedisDatabaseClient client)
	{
		GridBagLayout gridbag = new GridBagLayout();
		this.setLayout(gridbag);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), cmd));
		this.setOpaque(true);
		this.setBackground(new Color(133, 180, 4));

		this.title = cmd;
		this.parent = parent;
		this.client = client;

		int numberOfAllComp = parent.getComponentCountInLayer(WorkerPanel.COMMAND_LAYER);
		if (numberOfAllComp == 0)
		{
			this.setBounds(20, 20, 300, 300);
		} else
		{
			this.setBounds(20 + 50 * numberOfAllComp, 20 + 50 * numberOfAllComp, 300, 300);
		}

		MouseAdapter rightClicked = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				onMouseRightClicked(e);
			}
		};

		redisResult = new JTextArea();
		redisResult.setOpaque(true);
		redisResult.addMouseListener(rightClicked);

		resultView = new JScrollPane(redisResult);
		resultView.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		resultView.addMouseListener(rightClicked);

		gridbag.setConstraints(resultView, constraints);
		this.add(resultView);

		this.initPopupMenu();
		this.initMouseEvent();

		this.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if (evt.getNewValue() == null)
				{
					// 此控件被删除
					onRemoved();
				}
			}
		});
	}

	protected abstract void onRemoved();

	public String getTitle()
	{
		return title;
	}

	protected JTextArea getTextArea()
	{
		return redisResult;
	}

	protected IRedisDatabaseClient getRedisClient()
	{
		return client;
	}

	private void initMouseEvent()
	{
		this.addMouseMotionListener(new MouseAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				if (isDragModel)
				{
					e.translatePoint(CommandExecutePanel.this.getX() - mouseX, CommandExecutePanel.this.getY() - mouseY);
					CommandExecutePanel.this.setLocation(e.getX(), e.getY());
				} else
				{
					switch (mousePressLocation)
					{
					case 1:
					{
						int mouseMove = CommandExecutePanel.this.getX() + e.getX() - labelX - mouseX;
						CommandExecutePanel.this.setSize(labelWidth - mouseMove, labelHeight);
						CommandExecutePanel.this.setLocation(labelX + mouseMove, labelY);
					}
						break;
					case 2:
						CommandExecutePanel.this.setSize(labelWidth + e.getX() - mouseX, CommandExecutePanel.this.getHeight());
						break;
					case 3:
					{
						int mouseMove = CommandExecutePanel.this.getY() + e.getY() - labelY - mouseY;
						CommandExecutePanel.this.setSize(labelWidth, labelHeight - mouseMove);
						CommandExecutePanel.this.setLocation(labelX, labelY + mouseMove);
					}
						break;
					case 4:
						CommandExecutePanel.this.setSize(CommandExecutePanel.this.getWidth(), labelHeight + e.getY() - mouseY);
						break;
					default:
						break;
					}
					CommandExecutePanel.this.validate();
				}
			}

			public void mouseMoved(MouseEvent e)
			{
				if (e.getX() <= 5)
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
				} else if (e.getX() >= CommandExecutePanel.this.getWidth() - 5)
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				} else if (e.getY() <= 5)
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
				} else if (e.getY() >= CommandExecutePanel.this.getHeight() - 5)
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
				} else
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		this.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if (e.getX() <= 5)
				{
					mousePressLocation = 1;// 左
					isDragModel = false;
				} else if (e.getX() >= CommandExecutePanel.this.getWidth() - 5)
				{
					mousePressLocation = 2;// 右
					isDragModel = false;
				} else if (e.getY() <= 5)
				{
					mousePressLocation = 3;// 上
					isDragModel = false;
				} else if (e.getY() >= CommandExecutePanel.this.getHeight() - 5)
				{
					mousePressLocation = 4;// 下
					isDragModel = false;
				} else
				{
					mousePressLocation = 0;
					isDragModel = true;
				}
				mouseX = e.getX();
				mouseY = e.getY();
				labelWidth = CommandExecutePanel.this.getWidth();
				labelHeight = CommandExecutePanel.this.getHeight();
				labelX = CommandExecutePanel.this.getX();
				labelY = CommandExecutePanel.this.getY();
			}

			public void mouseEntered(MouseEvent e)
			{
				parent.moveToFront(CommandExecutePanel.this);
			}

			public void mouseClicked(MouseEvent e)
			{
				onMouseRightClicked(e);
			}

			public void mouseExited(MouseEvent e)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	private void onMouseRightClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	protected JPopupMenu getPopupMenu()
	{
		return popupMenu;
	}
	private void initPopupMenu()
	{
		popupMenu = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("关闭【" + title + "】");
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.remove(CommandExecutePanel.this);
				parent.repaint();
				System.out.println("关闭");

			}
		});
		popupMenu.add(menuItem);
	}
}
