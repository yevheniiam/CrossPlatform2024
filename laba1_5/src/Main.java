import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Evaluatable {
    double evalf(double x);
}

class Function1 implements Evaluatable {
    @Override
    public double evalf(double x) {
        double a = 2.5;
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }
}

class Function2 implements Evaluatable {
    @Override
    public double evalf(double x) {
        return x * x;
    }
}

class ProfilingHandler implements InvocationHandler {
    private final Object target;

    public ProfilingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        long endTime = System.nanoTime();
        System.out.println(method.getName() + " took " + (endTime - startTime) + " ns");
        return result;
    }
}

class TracingHandler implements InvocationHandler {
    private final Object target;

    public TracingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print(method.getName() + ": ");
        Object result = method.invoke(target, args);
        if (args != null && args.length > 0) {
            System.out.println(method.getName() + "(" + args[0] + ") = " + result);
        } else {
            System.out.println(result);
        }
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        Evaluatable f1 = new Function1();
        Evaluatable f2 = new Function2();

        Evaluatable f1Proxy = (Evaluatable) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class[]{Evaluatable.class},
                new ProfilingHandler(f1));

        Evaluatable f2Proxy = (Evaluatable) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class[]{Evaluatable.class},
                new TracingHandler(f2));

        double x = 1.0;
        System.out.println("F1: " + f1Proxy.evalf(x));
        System.out.println("F2: " + f2Proxy.evalf(x));
        System.out.println("F1: " + f1Proxy.evalf(x));
        System.out.println("F2: " + f2Proxy.evalf(x));
    }
}
