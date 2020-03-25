package test;

import java.util.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="Messages")
@XmlAccessorType(XmlAccessType.FIELD)
public class Messages {

    @XmlElement(name="Message")
    private List<Message> messages = new ArrayList<Message>();

    public List<Message> getMessages() {
        return messages;
    }
}