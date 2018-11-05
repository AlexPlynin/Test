package com.mytest.java;

import com.mytest.java.model.Person;
import com.mytest.java.view.Column;
import com.mytest.java.view.Page;
import com.mytest.java.view.Printer;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class Generator {
    private static ArrayList<Person> list = new ArrayList<>();

    public static ArrayList<Person> getList() {
        return list;
    }
    public static void main(String[] args) throws IOException, SAXException {
        String result = args[2];
        String settingName = args[0];
        String dataName = args[1];
        try (FileInputStream fis = new FileInputStream(dataName)) {

            Generator.fillFromFile(fis);

            System.out.println(Generator.getList());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




//
        // Заполнение объектов из XML
        try {
            ArrayList<Column> columns = fillCollumns(settingName);
            Page page = fillPage(settingName);
            page.setColumn(columns);
            System.out.println(page);
            columns.forEach(System.out::println);

            Printer printer = new Printer();
            printer.setPage(page);
            //printer.printTitle();//для записи в файл использовать перенаправление ввода
            printer.printPage();
            printer.printPageToFile(result);
          //  System.out.print(sb+"\t"+sb);

            //System.out.println(String.format());
            //System.out.print(sb);
           // System.out.println(mass.length);
           // System.out.println(String.format(" %s    %s", sb.toString(),sb.toString()));




        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


    }

    public static ArrayList<Column> fillCollumns(String settingFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(settingFile);


        NodeList columnNodeList = document.getElementsByTagName("column");

        ArrayList<Column> columnsList = new ArrayList<>();

        for (int i = 0; i < columnNodeList.getLength(); i++) {

            if (columnNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {// проверка на элемент
                Element columnElement = (Element) columnNodeList.item(i);

                Column column = new Column();
                NodeList childNodes = columnElement.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {


                        Element childElement = (Element) childNodes.item(j);

                        switch (childElement.getNodeName()) {
                            case "title":
                                column.setTitle(childElement.getTextContent());
                                break;
                            case "width":
                                column.setWidth(Integer.valueOf(childElement.getTextContent()));
                                break;
                        }
                    }


                }
                columnsList.add(column);

            }

        }

        return columnsList;
    }

    public static Page fillPage(String settingFile) throws ParserConfigurationException, IOException, SAXException {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(settingFile);

        //Element settingElement = (Element) document.getElementsByTagName("page").item(0);

        NodeList columnNodeList = document.getElementsByTagName("page");

        //ArrayList<Column> columnsList = new ArrayList<>();
        Page page = new Page();

        for (int i = 0; i < columnNodeList.getLength(); i++) {

            if (columnNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {// проверка на элемент
                Element columnElement = (Element) columnNodeList.item(i);


                NodeList childNodes = columnElement.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {


                        Element childElement = (Element) childNodes.item(j);

                        switch (childElement.getNodeName()) {
                            case "width":
                                page.setWidth(Integer.valueOf(childElement.getTextContent()));

                                break;
                            case "height":
                                page.setHeight(Integer.valueOf(childElement.getTextContent()));
                                break;
                        }
                    }


                }


            }

        }


        return page;
    }

    public static void fillFromFile(FileInputStream fis) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis))) {


            String s;
            while ((s = bufferedReader.readLine()) != null) {

                String[] mass = s.trim().split("#");
                list.add(new Person(mass[2], mass[1], Integer.valueOf(mass[0])));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//
