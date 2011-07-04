package org.elk.redis4j.impl.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LogUtil
{
	private static Level level;
	private static LogFormatter logFormatter;
	private static Handler handler;
	
	static
	{
		Properties pro = new Properties();
		try
		{
			pro.load(LogUtil.class.getClassLoader().getResourceAsStream("redis4j.log.properties"));
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		level = Level.parse(pro.getProperty("level"));
		logFormatter = new LogFormatter();
		if(pro.getProperty("handler").equalsIgnoreCase("java.util.logging.ConsoleHandler"))
		{
			handler = new ConsoleHandler();
		}
		else
		{
			try
			{
				String filePath = pro.getProperty("logFilePath");
				if(filePath.isEmpty() || filePath.replace(" ", "").equals("\"\""))
					filePath = LogUtil.class.getClassLoader().getResource("").toString().replace("file:/", "") + "redis4j.log";
				handler = new FileHandler(filePath, true);
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		handler.setFormatter(logFormatter);
		handler.setLevel(level);
	}

	private static class LogFormatter extends Formatter
	{
		@Override
		public String format(LogRecord record)
		{
			StringBuffer sb = new StringBuffer();
			Date date = new Date(record.getMillis());
			// 日期
			sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
			sb.append(" ");

			// 日志本身
			sb.append("LogInfo=【");
			sb.append(formatMessage(record));
			sb.append("】");

			// 日志级别
			sb.append(record.getLevel().getName());
			sb.append(" ");

			// 线程名
			sb.append("ThreadName=【");
			sb.append(Thread.currentThread().getName());
			sb.append("】");
			sb.append(" ");

			// 调用函数所在的类名
			sb.append("SourceClassName=【");
			sb.append(record.getSourceClassName());
			sb.append("】");
			sb.append(" ");

			// 函数名
			sb.append("SourceMethodName=【");
			sb.append(record.getSourceMethodName());
			sb.append("】");

			sb.append("\r\n");

			return sb.toString();
		}
	}
	
	public static Logger getLogger(String loggerName)
	{
		Logger logger = Logger.getLogger(loggerName);
		logger.setUseParentHandlers(false);
		logger.addHandler(handler);
		logger.setLevel(level);
		return logger;
	}

	public static void main(String[] args)
	{
		Logger logger = LogUtil.getLogger("test");
		logger.finest("aa");
		logger.log(Level.INFO, "xxxxxxxxxxx=");
		System.out.println("iiii");
		Logger logger1 =  LogUtil.getLogger("test1");
		logger1.log(Level.INFO, "xxxxxxxxxxx111");
		//int a = 1/0;
	}
}
