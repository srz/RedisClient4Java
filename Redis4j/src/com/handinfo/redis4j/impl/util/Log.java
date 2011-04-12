package com.handinfo.redis4j.impl.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Log
{
	private Logger logger;

	private class LogFormatter extends Formatter
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

	private class StdoutConsoleHandler extends ConsoleHandler
	{
		public StdoutConsoleHandler()
		{
			super();
			super.setOutputStream(System.out);
		}

	}

	public Log(String loggerName)
	{
		super();
		logger = Logger.getLogger(loggerName);
		StdoutConsoleHandler consoleHandler = new StdoutConsoleHandler();
		consoleHandler.setLevel(Level.INFO);
		// consoleHandler.setLevel(Level.OFF);
		LogFormatter MyLogHander = new LogFormatter();
		consoleHandler.setFormatter(MyLogHander);
		logger.setUseParentHandlers(false);
		logger.addHandler(consoleHandler);
	}

	public Logger getLogger()
	{
		return logger;
	}

	public static void main(String[] args)
	{
		Logger logger = (new Log("test")).getLogger();
		logger.log(Level.INFO, "xxxxxxxxxxx");
	}
}
