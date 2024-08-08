package net.talaatharb.jms.tibco.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CalculatorReq {

    private String operation;
    private long number1;
    private long number2;
}
