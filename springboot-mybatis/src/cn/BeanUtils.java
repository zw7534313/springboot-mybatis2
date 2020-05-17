package cn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanUtils {
	
	public static <T> T copyObject(Object src, Class<T> t){
		T target = null;
		try {
			target = t.newInstance();
			if(src != null)
			org.springframework.beans.BeanUtils.copyProperties(src, target);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return target;
	}

	public static <T> List<T> copyList(List list, Class<T> t){
		List<T> result = new ArrayList<T>();
		if(list == null || list.size() == 0){
			return result;
		}
		T target = null;
		Object src = null;
		try {
			for(Iterator it=list.iterator();it.hasNext();){
				src = it.next();
				target = t.newInstance();
				org.springframework.beans.BeanUtils.copyProperties(src, target);
				result.add(target);
			}
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
}
