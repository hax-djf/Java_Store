package cn.al.hax.store.utils;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
//一个进行类的属性封装操作（以及进行了日期格式的装换）
public class MyBeanUtils {

	public static void populate02(Object obj, Map<String, String> map) {
		try {
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			BeanUtils.populate(obj, map);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void populate(Object obj, Map<String, String[]> map) {
		try {
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			BeanUtils.populate(obj, map);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static<T> T  populate(Class<T> clazz, Map<String, String[]> map) {
		try {
			
			T obj=clazz.newInstance();
			
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			BeanUtils.populate(obj, map);
			
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	
	/**
	 * 
	 * @param <T> 返回的类型T
	 * @param clazz 传递某个类的反射
	 * @param map 将map集合中的数据赋值到对应的javabean中
	 * @return 返回一个T类型的javabaen
	 */
	public static<T> T  populateObject(Class<T> clazz, Map<String, Object> map) {
		try {
			//实例化对象
			T obj=clazz.getConstructor().newInstance();
			
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			//commons-beanUtil包下的 转换方式
			BeanUtils.populate(obj, map);
			
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	
	/**
	 * 
	 * @param obj 需要进行日期转型的javabean
	 * @param map map集合中的参数的赋值
	 */
	public static <T> T  populate_String(Class<T> clazz, Map<String, String> map) {
		try {
			//实例化对象
			T obj=clazz.getConstructor().newInstance();
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			BeanUtils.populate(obj, map);
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
