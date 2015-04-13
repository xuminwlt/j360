package me.j360.base.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

public class LogUtil {

  private static Logger objLog;
  private static Logger getLogger() {
    if(objLog==null){
      objLog = LoggerFactory.getLogger(LogUtil.class);
    }
    return objLog;
  }

  // Start Info
  public static void info(String message, Exception exception) {
    try {
      log("INFO", message, exception);
    } catch (Exception ex) {

    }
  }

  // /
  // / 记录信息
  public static void info(Object message) {
    log("INFO", message);
  }
  // end Info

  // Start Error 
  public static void trace(String message) {
    try {
      log("TRACE", message);
    } catch (Exception ex) {
    }
  }

  public static void trace(String message, Exception exception) {
    try {
      log("TRACE", message, exception);
    } catch (Exception ex) {
    }
  }

  // /
  // / 记录一个错误信息
  // /
  // / 信息内容
  // / 异常对象
  public static void error(String message, Exception exception) {
    try {
      log("ERROR", message, exception);
    } catch (Exception ex) {

    }
  }

  // /
  // / 记录一个错误信息
  // /
  // / 信息内容
  public static void error(String message) {
    try {
      log("ERROR", message);
    } catch (Exception ex) {

    }
  }

  // end Error

  // Start Warning

  // /
  // / 记录一个警告信息
  // /
  // / 信息内容
  // / 异常对象
  public static void warning(String message, Exception exception) {
    try {
      log("WARN", message, exception);
    } catch (Exception ex) {

    }
  }

  // /
  // / 记录一个警告信息
  // /
  // / 信息内容
  public static void warning(String message) {
    try {
      log("WARN", message);
    } catch (Exception ex) {

    }
  }

  // /
  // / 记录调试信息
  // /
  // / 信息内容
  // / 异常对象
  public static void debug(String message, Exception exception) {
    try {
      log("DEBUG", message, exception);
    } catch (Exception ex) {

    }
  }

  // /
  // / 记录调试信息
  // /
  // / 信息内容
  public static void debug(String message) {
    try {
      log("DEBUG", message);
    } catch (Exception ex) {

    }
  }
  // end Debug

  public static void log(String level, Object msg)
  {
    log(level, msg, null);
  }

  public static void log(String level, Throwable e)
  {
    log(level, null, e);
  }

  public static void log(String level, Object msg, Throwable e)
  {
    try{
      StringBuilder sb = new StringBuilder();
      Throwable t = new Throwable();
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      t.printStackTrace(pw);
      String input = sw.getBuffer().toString();
      StringReader sr = new StringReader(input);
      BufferedReader br = new BufferedReader(sr);
      for(int i=0;i<4;i++)
        br.readLine(); 
      String line = br.readLine();
      int paren = line.indexOf("at ");
      line = line.substring(paren+3);
      paren = line.indexOf('(');
      String invokeInfo = line.substring(0, paren);
      int period = invokeInfo.lastIndexOf('.');
      sb.append('[');
      sb.append(invokeInfo.substring(0,period));
      sb.append(':');
      sb.append(invokeInfo.substring(period+1));
      sb.append("():");
      paren = line.indexOf(':');
      period = line.lastIndexOf(')');
      sb.append(line.substring(paren+1,period));
      sb.append(']');
      sb.append(" - ");
      sb.append(msg);

      if(StringUtils.equals(level,"TRACE")){
        getLogger().trace(sb.toString(), e);
      }else  if(StringUtils.equals(level,"DEBUG")){
        getLogger().debug(sb.toString(),e);
      }else if(StringUtils.equals(level,"INFO")){
        getLogger().info(sb.toString(),e);
      }else if(StringUtils.equals(level,"WARN")){
        getLogger().warn(sb.toString(),e);
      }else if(StringUtils.equals(level,"ERROR")){
        getLogger().error(sb.toString(),e);
      }
    }catch(Exception ex){
      LogUtil.info(ex.getLocalizedMessage());
    }
  }
}
