package net.talaatharb.jms.tibco.model;

import lombok.Data;

@Data
public class QueueRequest {
    private String configtype;
    private String configkey;
    private String stage;
    private long no;
    private long total;
    private String operation;

}
