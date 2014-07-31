/*
This file is part of the Seletest by Papadakis Giannis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Papadakis Giannis <gpapadakis84@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

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
public class ReflectionUtils {


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

