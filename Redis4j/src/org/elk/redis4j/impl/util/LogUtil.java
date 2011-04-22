package org.elk.redis4j.impl.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LogUtil
{
	private static Level level = Level.ALL;
	private static LogFormatter logFormatter = new LogUtil.LogFormatter();
	private static ConsoleHandler ch = new ConsoleHandler()
	{
		@Override
		public void setFormatter(Formatter newFormatter) throws SecurityException
		{
			super.setFormatter(logFormatter);
		}

		@Override
		public synchronized void setLevel(Level newLevel) throws SecurityException
		{
			super.setLevel(level);
		}
		
	};

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

			sb.append("\n");

			return sb.toString();
		}
	}
	
	public static Logger getLogger(String loggerName)
	{
		Logger logger = Logger.getLogger(loggerName);
		logger.setUseParentHandlers(false);
		logger.addHandler(ch);
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
