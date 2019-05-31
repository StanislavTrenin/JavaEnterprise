package hello;

import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.openstreetmap.osm._0.Osm;
import org.openstreetmap.osm._0.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.xml.sax.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.HashMap;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(NodeRepository repository) {
        return (args) -> {
            File file = new File("RU-NVS.osm.bz2");

            FileInputStream fin = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fin);
            CompressorInputStream compressorInputStream = new CompressorStreamFactory()
                    .createCompressorInputStream(bis);

            // create a new XML parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XMLReader reader = factory.newSAXParser().getXMLReader();
            //XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(compressorInputStream);

            reader.setContentHandler(new ContentHandler() {

                private Node lastNode;
                private String lastElement;

                @Override
                public void setDocumentLocator(Locator locator) {

                }

                @Override
                public void startDocument() throws SAXException {

                }

                @Override
                public void endDocument() throws SAXException {

                }

                @Override
                public void startPrefixMapping(String prefix, String uri) throws SAXException {

                }

                @Override
                public void endPrefixMapping(String prefix) throws SAXException {

                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
                    if (qName.equals("node")) {
                        lastNode = new Node();

                        lastNode.setId(Long.valueOf(atts.getValue("id")));
                        lastNode.setLat(Double.parseDouble(atts.getValue("lat")));
                        lastNode.setLon(Double.parseDouble(atts.getValue("lon")));
                        lastNode.setUser(atts.getValue("user"));
                    }
                    if (qName.equals("tag")) {
                        String k = atts.getValue("k");
                        String v = atts.getValue("v");
                        lastNode.getTags().put(k, v);
                    }

                    lastElement = qName;
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equals("node")) {
                        //System.out.println(lastNode);
                        repository.save(lastNode);
                        lastNode = null;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    String value = new String(ch, start, length);

                }

                @Override
                public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

                }

                @Override
                public void processingInstruction(String target, String data) throws SAXException {

                }

                @Override
                public void skippedEntity(String name) throws SAXException {

                }
            });
            reader.parse(new InputSource(compressorInputStream));

            log.info("Nodes found with findAll():");
            log.info("-------------------------------");
            for (Node node : repository.findAll()) {
                log.info(node.toString());
            }
            log.info("");

            if (true) return;

            // fetch an individual customer by ID
            /*repository.findById(1L)
                    .ifPresent(customer -> {
                        log.info("Node found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });*/

            // fetch customers by last name
            log.info("Node found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            //repository.findByLastName("Bauer").forEach(bauer -> {
//                log.info(bauer.toString());
//            });
            // for (Node bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");
        };
    }
}
