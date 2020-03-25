package test;

import javax.xml.bind.annotation.XmlAttribute;

public class Message {
	private Integer id = 0;

	@XmlAttribute
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
