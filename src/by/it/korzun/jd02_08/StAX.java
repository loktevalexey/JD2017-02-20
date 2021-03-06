package by.it.korzun.jd02_08;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StAX {

    private XMLStreamReader reader;

    public StAX(XMLStreamReader reader) {
        this.reader = reader;
    }

    String run() throws XMLStreamException {
        StringBuilder text = new StringBuilder();
        StringBuilder txtBuff = new StringBuilder();
        String tab = "";
        while(reader.hasNext()) {
            int operation = reader.next();
            switch (operation) {
                case XMLStreamConstants.START_ELEMENT:{
                    String qName = reader.getLocalName();
                    int attCount = reader.getAttributeCount();

                    text.append(tab).append("<").append(qName);
                    for (int i = 0; i < attCount; i++) {
                        String name = reader.getAttributeLocalName(i);
                        String value = reader.getAttributeValue(i);
                        text.append(" ").append(name).append("=\"").append(value).append("\"");
                    }
                    text.append(">\n");
                    tab += "\t";
                    break;
                }
                case XMLStreamConstants.END_ELEMENT:{
                    String qName = reader.getLocalName();
                    String tx = txtBuff.toString().trim();
                    if(tx.length() > 0) {
                        text.append(tab).append(tx).append("\n");
                    }
                    txtBuff.setLength(0);
                    tab = tab.substring(1);
                    text.append(tab).append("</").append(qName).append(">\n");
                    break;
                }
                case XMLStreamConstants.CHARACTERS:{
                    String part = reader.getText();
                    txtBuff.append(part);
                    break;
                }
            }
        }
        return text.toString();
    }
}
