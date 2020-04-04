package htools.utils;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;

@XStreamAlias("isomsg")
public class IsoMsgXml {

	private List<Field> fields;

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public static Object fromXML(byte[] xml) {
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.alias("isomsg", IsoMsgXml.class);
		xstream.alias("field", Field.class);
		xstream.addImplicitCollection(IsoMsgXml.class, "fields");

		xstream.useAttributeFor(Field.class, "id");
		xstream.useAttributeFor(Field.class, "value");
		xstream.useAttributeFor(Field.class, "type");

		return xstream.fromXML(new ByteArrayInputStream(xml));

	}

}
