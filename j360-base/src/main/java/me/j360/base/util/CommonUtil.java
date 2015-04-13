package me.j360.base.util;

import me.j360.base.bean.BaseEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 工具类 - 公用
 * ============================================================================
 */

public class CommonUtil {

	private static boolean match(String paramString1, String paramString2) {
		boolean bool;
		if (paramString1 == null || paramString1.trim().length()==0) {
			bool = false;
		} else {
			if (paramString2 == null || paramString2.trim().length()==0) {
				bool = false;
			} else {
				bool = Pattern.compile(paramString1).matcher(paramString2).matches();
			}
		}
		return bool;
	}

    /**
     * 手机号验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        if(StringUtils.isEmpty(str))
            return false;
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    public static boolean isEmail(String email)   {
        if(StringUtils.isEmpty(email))
            return false;
        boolean tag = true;
        //final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final String pattern1 =  "[a-z0-9A-Z_.-]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}" ;
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

    /**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	@Deprecated
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
	}

	/**
	 * 随机获取字符串
	 * 
	 * @param length
	 *            随机字符串长度
	 * 
	 * @return 随机字符串
	 */
	@Deprecated
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's',
				'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b',
				'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt())
					% randomChar.length]);
		}
		return stringBuffer.toString();
	}

	/**
	 * 根据指定长度 分隔字符串
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param length
	 *            分隔长度
	 * 
	 * @return 字符串集合
	 */
	public static List<String> splitString(String str, int length) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length(); i += length) {
			int endIndex = i + length;
			if (endIndex <= str.length()) {
				list.add(str.substring(i, i + length));
			} else {
				list.add(str.substring(i, str.length() - 1));
			}
		}
		return list;
	}

	/**
	 * 将字符串List转化为字符串，以分隔符间隔.
	 * 
	 * @param list
	 *            需要处理的List.
	 * 
	 * @param separator
	 *            分隔符.
	 * 
	 * @return 转化后的字符串
	 */
	public static String toString(List<String> list, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(separator + str);
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}

	/**
	 * 两个集合进行比较取相同的
	 * 
	 * @param list_pid
	 *            传入集合
     * @param list_id
     *            传入集合
	 * 
	 * @return list 返回一个集合
	 */

	public List<Object> listCompare(List<Object> list_pid, List<Object> list_id) {

		List<Object> list = new ArrayList<Object>();
		
		return list;
	}
	
	/**
	 * 字符串集合转化字符串
	 * @param obj
	 * @param tag 分隔符
	 * @return String
	 */
	public static String toString(String[] obj, String tag) {
		String str = "";
		if(obj!=null&&obj.length>0){
			for(int i=0;i<obj.length;i++){
				str = str + obj[i];
				if(i<obj.length-1){
					str = str + tag;
				}
			}
		}
		return str;
	}
	
	public static String getAliasPath(String[] str, int index, int begin){
		String result = "";
		if(index < 0){
			return result;
		}
		if(index > str.length){
			index = str.length-1;
		}
		for(int i=begin;i<=index;i++){
			if(i==begin){
				result = str[i];
			}else{
				result = result + "." + str[i];
			}
		}
		return result;
	}
	public static String getAliasPath(String[] str, int index){
		return getAliasPath(str, index, 0);
	}

	public static Object getData(String field, String data){
		Object result = "%"+data+"%";
		if(StringUtils.endsWith(field, "Sex")){
			result = BaseEnum.SexEnum.valueOf(data);
		}if(StringUtils.endsWith(field, "If")){
			//result = IfEnum.valueOf(data);
		}if(StringUtils.endsWith(field, "State")){
			result = BaseEnum.StateEnum.valueOf(data);
		}else if(StringUtils.endsWith(field, "Integer")){
			result = Integer.valueOf(data);
		}
		return result;
	}
	
	public static String getPath(String[] str, int index){
		return getPath(str, index, 0);
	}
	public static String getPath(String[] str, int index, int begin){
		String result = "";
		if(index < 0){
			return result;
		}
		if(index > str.length){
			index = str.length-1;
		}
		for(int i=begin;i<=index;i++){
			if(i==begin){
				result = str[i];
			}else{
				if(i==index){
					result = result + "." + str[i];
				}else{
					result = result + "_" + str[i];
				}
			}
		}
		return result;
	}
	public static String getAlias(String[] str, int index){
		return getAlias(str, index, 0);
	}
	public static String getAlias(String[] str, int index, int begin){
		String result = "";
		if(index < 0){
			return result;
		}
		if(index > str.length){
			index = str.length-1;
		}
		for(int i=begin;i<=index;i++){
			if(i==begin){
				result = str[i];
			}else{
				result = result + "_" + str[i];
			}
		}
		return result;
	}
	
	private static Pattern referer_pattern = Pattern.compile("@([^@^\\s^:]{1,})([\\s\\:\\,\\;]{0,1})");//@.+?[\\s:]


	public static List<String> getReferers(String msg){
		List<String> referers = new ArrayList<String>();
		Matcher matchr = referer_pattern.matcher(msg);
		while (matchr.find()) {
			String origion_str = matchr.group();
			String str = origion_str.substring(1, origion_str.length()).trim();
			referers.add(str);
		}
		return referers;
	}
	
	public static void main(String[] args) {
		/*String str = "今天天气好@啦啦 @哈哈 你么你 啦啦";
		List<String> referers = getReferers(str);
//		_GenerateRefererLinks(str, referers);
		for(String s:referers){
			System.out.println(s);
		}
//		String property = "business.client.name";
//		String[] fields = StringUtils.split(property, ".");
//		for (int j = 0; j < fields.length - 1; j++) {
//			System.out.println("Name:"+CommonUtil.getPath(fields, j));
//			System.out.println("Alias:"+fields[j]);
//		}
//		System.out.println("add:"+CommonUtil.getPath(fields, fields.length - 1, fields.length - 2));*/
        System.out.println(UUID.randomUUID().toString());
	}

}
