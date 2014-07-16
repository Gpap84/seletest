package com.automation.seletest.core.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.springframework.stereotype.Service;

/**
 * Functions using reflection API
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Service
public class Reflection {


	/**Invoke method of inner class
	 * @param className
	 * @param parameters
	 * @param innerclass
	 * @param declaredMethod
	 * @throws Exception
	 */
	public void invokeMethodofInnerClass(String className, Object[] parameters, int innerclass, int declaredMethod) throws Exception{
		Method method;
		Class<?> parentClass=Class.forName(className);
		Class<?> child=parentClass.getDeclaredClasses()[innerclass];

		//we need the constructor to pass the outer object info and change its accessibility
		Constructor<?> constructor = child.getDeclaredConstructors()[0];

		//the default constructor of the private class is also private, so we need to make it accessible
		constructor.setAccessible(true);

		//now we are ready to create an inner class object
		Object innerObject = constructor.newInstance();
		method = child.getDeclaredMethods()[declaredMethod];
		method.setAccessible(true);
		method.invoke(innerObject,parameters);

	}


	/**Invoke method of a class
	 *
	 * @param className
	 * @param parameters
	 * @param declaredMethod
	 * @throws Exception
	 */
	public void invokeMethod(Class<?> className, Object[] parameters, Method declaredMethod) throws Exception{
		Method[] method;

		//we need the constructor to pass the outer object info and change its accessibility
		Constructor<?> constructor = className.getDeclaredConstructors()[0];

		//the default constructor of the private class is also private, so we need to make it accessible
		constructor.setAccessible(true);

		//now we are ready to create an inner class object
		Object innerObject = constructor.newInstance();
		method = className.getDeclaredMethods();
		for(Method methods:method){
			if(methods.equals(declaredMethod)){
				methods.setAccessible(true);
				methods.invoke(innerObject,parameters);
			}
		}

	}


	/**
	 * Initialize static variables of a class
	 * @param classReflection
	 */
	public void initializeVariablesClass(Class<?> classReflection)throws Exception{
		//Initialize static variables for outer class
		Field[] fieldsDisplay = classReflection.getDeclaredFields();

		for (Field f : fieldsDisplay) {
			if (Modifier.isStatic(f.getModifiers())) {
				f.setAccessible(true);
				Object obj = f.getGenericType();
				if(obj.equals(String.class)){
					f.set(f, null);
				}
				else if(obj.equals(boolean.class)){
					f.setBoolean(f, false);
				}

			}
		}
	}
}

