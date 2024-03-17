package laba1_3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CallMethod {
    public CallMethod() {
    }

    public static Object invokeMethod(Object obj, String methodName, Object... args) throws FunctionNotFoundException {
        Class<?> cls = obj.getClass();

        Method method;
        try {
            method = cls.getMethod(methodName, getParameterTypes(args));
        } catch (NoSuchMethodException var7) {
            throw new FunctionNotFoundException("Метод " + methodName + " не знайдено");
        }

        if (!Modifier.isPublic(method.getModifiers())) {
            throw new FunctionNotFoundException("Метод " + methodName + " не є публічним");
        } else {
            try {
                return method.invoke(obj, args);
            } catch (InvocationTargetException | IllegalAccessException var6) {
                throw new FunctionNotFoundException("Помилка при виклику методу " + methodName + "!", var6);
            }
        }
    }

    private static Class<?>[] getParameterTypes(Object... args) {
        Class<?>[] types = new Class[args.length];

        for(int i = 0; i < args.length; ++i) {
            types[i] = args[i].getClass();
        }

        return types;
    }

    public static void main(String[] args) {
        Object objString = new String("Hello world!");
        TestClass tc = new TestClass(1.5);

        try {
            int length = (Integer)invokeMethod(objString, "length");
            System.out.println("Довжина рядка: " + length);
            String upperCase = (String)invokeMethod(objString, "toUpperCase");
            System.out.println("Рядок у верхньому регістрі: " + upperCase);
            double sinSquare = (Double)invokeMethod(tc, "sinSquare");
            System.out.println("Синус квадрату числа: " + sinSquare);
        } catch (FunctionNotFoundException var7) {
            System.out.println("Помилка: " + var7.getMessage());
        }

    }
}
