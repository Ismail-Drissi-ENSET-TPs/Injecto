package io.ismaildrissi.app.test;


import io.ismaildrissi.app.annotations.Inject;
import io.ismaildrissi.app.annotations.InjectValue;

public class Work {

    @Inject
    private Employe employe;

    @InjectValue(val = "workName")
    private String name;

    @InjectValue(val= "42")
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Employe getEmploye() {
        return employe;
    }
}
