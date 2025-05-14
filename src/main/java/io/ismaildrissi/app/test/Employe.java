package io.ismaildrissi.app.test;

import io.ismaildrissi.app.annotations.Inject;
import io.ismaildrissi.app.annotations.InjectValue;

public class Employe {
    @InjectValue(val = "employeName")
    private String name;

    public String getName() {
        return name;
    }

}
