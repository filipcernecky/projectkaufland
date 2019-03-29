package Main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import Bill.Bill;
import Items.Item;
import Items.Piece;
import Items.DraftInterface;
import Items.Fruit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Date;


public class Xml {

    public void createXML(Bill bill) throws ParserConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("Bill");
        Node nd = doc.appendChild(rootElement);

        Date date = new java.sql.Date(bill.getDate().getTime());
        Date time = new java.sql.Time(bill.getDate().getTime());

        Element dateEl = doc.createElement("DateOfPurchase");
        rootElement.appendChild(dateEl);

        Element date2 = doc.createElement("Date");
        date2.appendChild(doc.createTextNode(String.valueOf(date)));
        dateEl.appendChild(date2);

        Element time2 = doc.createElement("Time");
        time2.appendChild(doc.createTextNode(String.valueOf(time)));
        dateEl.appendChild(time2);



        for (Item item : bill.getList()) {
            Element billItem = doc.createElement("Item");
            rootElement.appendChild(billItem);

            Element name = doc.createElement("Name");
            name.appendChild(doc.createTextNode(item.getName()));
            billItem.appendChild(name);

            Element price = doc.createElement("Price");
            price.appendChild(doc.createTextNode(String.valueOf(item.getPrice())));
            billItem.appendChild(price);

            Element amount = doc.createElement("Amount");
            if (item instanceof DraftInterface){
                amount.appendChild(doc.createTextNode(String.valueOf(((DraftInterface) item).getVolume())));
                billItem.appendChild(amount);
            }
            else if (item instanceof Fruit){
                amount.appendChild(doc.createTextNode(String.valueOf(((Fruit) item).getWeight())));
                billItem.appendChild(amount);
            }
            else if (item instanceof Piece){
                amount.appendChild(doc.createTextNode(String.valueOf(((Piece) item).getAmount())));
                billItem.appendChild(amount);
            }

            Element unit = doc.createElement("Unit");
            if (item instanceof DraftInterface){
                unit.appendChild(doc.createTextNode("l"));
                billItem.appendChild(unit);
            }
            else if (item instanceof Fruit){
                unit.appendChild(doc.createTextNode("kg"));
                billItem.appendChild(unit);
            }
            else if (item instanceof Piece){
                unit.appendChild(doc.createTextNode("pcs"));
                billItem.appendChild(unit);
            }

        }

        Element totalPrice = doc.createElement("Price");
        rootElement.appendChild(totalPrice);

        Element eurPrice = doc.createElement("EUR");
        eurPrice.appendChild(doc.createTextNode(String.valueOf(bill.getFinalPrice())));
        totalPrice.appendChild(eurPrice);

        Element dolPrice = doc.createElement("USD");
        dolPrice.appendChild(doc.createTextNode(String.valueOf(bill.getTotalPriceUSD())));
        totalPrice.appendChild(dolPrice);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("bill.xml"));
        transformer.transform(source, result);

    }

}