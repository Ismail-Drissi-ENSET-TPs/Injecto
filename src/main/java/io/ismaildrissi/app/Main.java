package io.ismaildrissi.app;

import io.ismaildrissi.app.test.Work;

import java.io.IOException;

public class Main {

    // Testing the injection
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InjectoClassPathsFinder classPathsFinder = new InjectoClassPathsFinder();
        InjectoClassLoader injectoClassLoader = new InjectoClassLoader(classPathsFinder.getClassPaths());
        InjectoInjectDependency injectoInjectDependency = new InjectoInjectDependency(injectoClassLoader.getClasses());
        Work work = (Work) injectoInjectDependency.getBean("io.ismaildrissi.app.test.Work");
        System.out.println(work.getEmploye().getName());
        System.out.println(work.getName());
        System.out.println(work.getAge());
    }
}