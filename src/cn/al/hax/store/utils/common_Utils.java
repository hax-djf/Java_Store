package cn.al.hax.store.utils;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.beanutils.BeanUtils;
public class common_Utils {
	//获取一个随机的UUID
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	//将指定类型的map存到指定类型的javabean里面
	public static<T> T SetMapToBean(Class<T> clazz,Map map) {
	try {
		//指定指定类型的javabean
		T bean=clazz.getConstructor().newInstance();
		//将map封装到这个javanean中
		BeanUtils.populate(bean, map);
		//返回javabean对象
		return bean;
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
	
	}
}

