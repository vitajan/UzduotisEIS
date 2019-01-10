package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String ccy;
        String dt;
        String dtFrom;
        String dtTo;
        Scanner sc = new Scanner(System.in);
        do {
            do {
                System.out.println("\nIveskite valiutos koda:");
                ccy = sc.nextLine().toUpperCase();
            } while (!isCurrencyValid(ccy));

            System.out.println("\nPasirinkite ka norite ivesti:");
            System.out.println("1 - data ");
            System.out.println("2 - datu perioda ");
            try {
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        do {
                            System.out.println("\nIveskite data: ");
                            System.out.println("(Datos formatas yyyy-mm-dd)");
                            dt = sc.nextLine();
                        } while (!isDateValid(dt));
                        getFxRatesForCurrency(ccy, dt, dt);
                        break;
                    case 2:
                        do {
                            System.out.println("\nIveskite data NUO: ");
                            System.out.println("(Datos formatas yyyy-mm-dd) ");
                            dtFrom = sc.nextLine();
                        } while (!isDateValid(dtFrom));
                        do {
                            System.out.println("\nIveskite data IKI: ");
                            dtTo = sc.nextLine();
                        } while (!isDateValid(dtTo));
                        getFxRatesForCurrency(ccy, dtFrom, dtTo);
                        break;
                    default:
                        System.out.println("Tokio pasirinkimo nera.\n");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Tokio pasirinkimo nera.\n");
                sc.nextLine();
            }
            System.out.println("q - baigti");
            System.out.println("Enter - kartoti");
        } while (!sc.nextLine().equals("q"));
    }

    private static boolean isCurrencyValid(String tp) {
        if (!tp.matches("^(AED|AFN|ALL|AMD|ANG|AOA|ARS|AUD|AWG|AZN|BAM|BBD|BDT|BGN|BHD|BIF|BMD|BND|BOB|BOV|BRL|BSD|BTN|BWP|BYR|BZD|CAD|CDF|CHE|CHF|CHW|CLF|CLP|CNY|COP|COU|CRC|CUC|CUP|CVE|CZK|DJF|DKK|DOP|DZD|EGP|ERN|ETB|EUR|FJD|FKP|GBP|GEL|GHS|GIP|GMD|GNF|GTQ|GYD|HKD|HNL|HRK|HTG|HUF|IDR|ILS|INR|IQD|IRR|ISK|JMD|JOD|JPY|KES|KGS|KHR|KMF|KPW|KRW|KWD|KYD|KZT|LAK|LBP|LKR|LRD|LSL|LTL|LVL|LYD|MAD|MDL|MGA|MKD|MMK|MNT|MOP|MRO|MUR|MVR|MWK|MXN|MXV|MYR|MZN|NAD|NGN|NIO|NOK|NPR|NZD|OMR|PAB|PEN|PGK|PHP|PKR|PLN|PYG|QAR|RON|RSD|RUB|RWF|SAR|SBD|SCR|SDG|SEK|SGD|SHP|SLL|SOS|SRD|SSP|STD|SVC|SYP|SZL|THB|TJS|TMT|TND|TOP|TRY|TTD|TWD|TZS|UAH|UGX|USD|USN|USS|UYI|UYU|UZS|VEF|VND|VUV|WST|XAF|XAG|XAU|XBA|XBB|XBC|XBD|XCD|XDR|XFU|XOF|XPD|XPF|XPT|XSU|XTS|XUA|XXX|YER|ZAR|ZMW|ZWL)$")) {
            System.out.println("Netinkamas valiutos kodas.");
            return false;
        } else return true;
    }

    private static boolean isDateValid(String dt) {
        try {
            LocalDate date = LocalDate.parse(dt, DateTimeFormatter.ISO_DATE);
            if (date.isBefore(LocalDate.of(2014, 9, 29))) {
                System.out.println("Irasai duomenų bazeje yra nuo 2014 m. rugsejo 30 d.");
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Blogas datos formatas.");
            return false;
        }
    }

    private static void getFxRatesForCurrency(String ccy, String dtFrom, String dtTo) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("http://old.lb.lt/webservices/fxrates/FxRates.asmx/" +
                    "getFxRatesForCurrency?tp=Eu&ccy=" + ccy + "&dtFrom=" + dtFrom + "&dtTo=" + dtTo);

            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/FxRates/FxRate";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

            ArrayList<Float> reiksmes = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String data = eElement.getElementsByTagName("Dt").item(0).getTextContent();
                    if (i == 0 || i == nodeList.getLength() - 1) {
                        if (!data.equals(dtFrom) && !data.equals(dtTo)) {
                            System.out.println("\nUzklausoje nurodyta diena euro ir uzsienio valiutos santykis neskelbtas. Pateikiamas paskutinis iki uzklausos dienos paskelbtas euro ir uzsienio valiutos santykis.");
                        }
                    }
                    float reiksme = Float.parseFloat(eElement.getElementsByTagName("Amt").item(1).getTextContent());
                    reiksmes.add(reiksme);
                    System.out.println("Data : " + data);
                    System.out.println("Kursas : " + reiksme + "\n");
                }
            }
            float pokytis = reiksmes.get(0) - reiksmes.get(reiksmes.size() - 1);
            if (pokytis != 0) {
                System.out.println("Pokytis : " + pokytis + "\n");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }
}