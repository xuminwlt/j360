package me.j360.base.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BaseHelper{
    public static final String PARAM_EQUAL = "=";
    public static final String PARAM_AND   = "&";

    /**
     * 流转字符串方法
     * 
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 字符串转json对象
     * 
     * @param str
     * @param split
     * @return
     */
/*    public static JSONObject string2JSON(String str, String split)
    {
        JSONObject json = new JSONObject();
        try
        {
            String[] arrStr = str.split(split);
            for (int i = 0; i < arrStr.length; i++)
            {
                String[] arrKeyValue = arrStr[i].split("=");
                json.put(arrKeyValue[0],
                        arrStr[i].substring(arrKeyValue[0].length() + 1));
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return json;
    }

    public static JSONObject string2JSON(String str)
    {
        try
        {
            return new JSONObject(str);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static String toJSONString(Object obj)
    {
        JSONObject json = new JSONObject();
        try
        {
            List<NameValuePair> list = bean2Parameters(obj);
            for (NameValuePair nv : list)
            {
                json.put(nv.getName(), nv.getValue());
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return json.toString();
    }*/

    /**
     * 将bean转换成键值对列表
     * 
     * @param bean
     * @return
     */
    public static List<NameValuePair> bean2Parameters(Object bean)
    {
        if (bean == null)
        {
            return null;
        }
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        // 取得bean所有public 方法
        Method[] Methods = bean.getClass().getMethods();
        for (Method method : Methods)
        {
            if (method != null && method.getName().startsWith("get")
                    && !method.getName().startsWith("getClass"))
            {
                // 得到属性的类名
                String value = "";
                // 得到属性值
                try
                {
                    String className = method.getReturnType().getSimpleName();
                    if (className.equalsIgnoreCase("int"))
                    {
                        int val = 0;
                        try
                        {
                            val = (Integer) method.invoke(bean);
                        } catch (InvocationTargetException e)
                        {
                            
                        }
                        value = String.valueOf(val);
                    } else if (className.equalsIgnoreCase("String"))
                    {
                        try
                        {
                            value = (String) method.invoke(bean);
                        } catch (InvocationTargetException e)
                        {
                            
                        }
                    }
                    if (value != null && value != "")
                    {
                        // 添加参数
                        // 将方法名称转化为id，去除get，将方法首字母改为小写
                        String param = method.getName().replaceFirst("get", "");
                        if (param.length() > 0)
                        {
                            String first = String.valueOf(param.charAt(0))
                                    .toLowerCase();
                            param = first + param.substring(1);
                        }
                        parameters.add(new BasicNameValuePair(param, value));
                    }
                } catch (IllegalArgumentException e)
                {
                    
                } catch (IllegalAccessException e)
                {
                   
                }
            }
        }
        return parameters;
    }

    /**
     * 对Object进行List<NameValuePair>转换后按key进行升序排序，以key=value&...形式返回
     * 
     * @param list
     * @return
     */
    public static String sortParam(Object order)
    {
        List<NameValuePair> list = bean2Parameters(order);
        return sortParam(list);
    }

    /**
     * 对List<NameValuePair>按key进行升序排序，以key=value&...形式返回
     * 
     * @param list
     * @return
     */
    public static String sortParam(List<NameValuePair> list)
    {
        if (list == null)
        {
            return null;
        }
        Collections.sort(list, new Comparator<NameValuePair>(){
            @Override
            public int compare(NameValuePair lhs, NameValuePair rhs)
            {
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });
        StringBuffer sb = new StringBuffer();
        for (NameValuePair nameVal : list)
        {
            if (null != nameVal.getValue() && !"".equals(nameVal.getValue()))
            {
                sb.append(nameVal.getName());
                sb.append(PARAM_EQUAL);
                sb.append(nameVal.getValue());
                sb.append(PARAM_AND);
            }
        }
        String params = sb.toString();
        if (sb.toString().endsWith(PARAM_AND))
        {
            params = sb.substring(0, sb.length() - 1);
        }
        
        return params;
    }
}