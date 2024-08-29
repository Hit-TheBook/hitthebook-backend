package dreamteam.hitthebook.common.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PlannerAspect {
    //    @Before("execution(* com.example.service.*.*(..))")
//    public void beforeMethod() {
//        System.out.println("A method is being called");
//    }
    @After("execution()")
    public void scheduleAlertAspect(){

    }
}
