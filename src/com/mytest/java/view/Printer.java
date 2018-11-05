package com.mytest.java.view;

import com.mytest.java.Generator;
import com.mytest.java.model.Person;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class Printer {
    //private int numberOfColumn;
    private static ArrayList<Person> listOfPerson = Generator.getList();
    private Page page;
    private int heightSum;

    //private static ArrayList<Column> list =
    public Row getRow(Person person) {
        return new Row(person);
    }



    public class Row {


        private int rowHeight = 1;
        private int number;
        private Person person;

        public Row() {

        }

        public Row(Person person) {
            this.person = person;
            //this.page = page;
            this.number = person.getNumber();

        }


        public int getRowHeight() {
            calculateRowHeight();
            return rowHeight;
        }

        public void setRowHeight(int rowHeight) {
            this.rowHeight = rowHeight;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String toCorrectView(int width, String line) {
            //корректируем все линии


            StringBuilder sb = new StringBuilder();


            if ((line.length() > width)) {
//
                int count = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (count == width) {
                        sb.append("\n");
                        //rowHeight++;
                        count = 0;
                    }
                    sb.append(line.charAt(i));
                    count++;

                }


                return sb.toString();
            }


            return line;
        }

        public void calculateRowHeight() {
            calculateRowHeight(person, page.getColumn());
        }

        public void calculateRowHeight(Person person, ArrayList<Column> columns) {
            //корректируем все линии
            //как скопировать строку не изменяя оригинала
            ArrayList<String> personList = new ArrayList<>();
            personList.add(String.valueOf(person.getNumber()));

            personList.add(person.getDate());
            personList.add(person.getName());

            // toCorrectView(width, line);

            int count = 0;
            ArrayList<Integer> listOfHeight = new ArrayList<>();
            for (int i = 0; i < columns.size(); i++) {
                String line = toCorrectView(columns.get(i).getWidth(), personList.get(i));
                if (line.contains("\n")) {
                    String[] mass = line.split("\n");
                    listOfHeight.add(mass.length);
                } else {
                    listOfHeight.add(1);
                }
            }


            rowHeight = Collections.max(listOfHeight);


        }

        public void printRow() {

            //calculateRowHeight(person, page.getColumn());
            calculateRowHeight();
            StringBuilder sb = new StringBuilder();

            ArrayList<String> list = new ArrayList<>();
            list.add(String.valueOf(person.getNumber()));
            list.add(person.getDate());
            list.add(person.getName());
            for (int j = 0; j < rowHeight; j++) {
                sb.append("|");

                for (int i = 0; i < page.getColumn().size(); i++) {
                    String name = toItterative(toCorrectView(page.getColumn().get(i).getWidth(), list.get(i))).get(j);// берем имя на кждой итерации
                    int spaceRequired = page.getColumn().get(i).getWidth() - name.length();// считаем необходдимое количество пробелов
                    //вычитаем разницу добавляемых пробелов
                    // те получаем нужное количество пробелов после слова
                    sb.append(String.format(" %s", name));
                    // prints: |

                    sb.append(countElementToAdd(" ", spaceRequired)).append(" |");

                }

                sb.append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(sb.toString());
        }

        public ArrayList<String> toItterative(String line) {
            ArrayList<String> list = null;
            //заполняем нужным числом пробелов колонку для правильного отображения
            if (rowHeight == 1) {//суть ошибки заключается в том что строка без переноса все равно делится на \n
                list = new ArrayList<>();
                list.add(line);
                return list;
            } else {
                if (!line.contains("\n")) {
                    list = new ArrayList<>();
                    list.add(line);
                    for (int i = 0; i < rowHeight - 1; i++) {
                        list.add(" ");
                    }

                    return list;
                }

                String[] mass = line.split("\n");

                if (mass.length == rowHeight) {

                    list = new ArrayList<>(Arrays.asList(mass));
                    // list.add(line);

                    return list;
                }

                list = new ArrayList<>(Arrays.asList(mass));
                //list.add(line);
                for (int i = 0; i < Math.abs(rowHeight - mass.length); i++) {
                    list.add(" ");
                }

                return list;
            }

        }


    }


    public void setPage(Page page) {
        this.page = page;
    }


    public void printLineBtwTwoRows(StringBuilder sb) {

        System.out.println(sb.toString());

    }


    public StringBuilder lineBtwTwoRows() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < page.getWidth(); i++) {
            sb.append("-");
        }
        return sb;
    }

    public void printPage() {

        printFullTitle();

        for (int i = 0; i < listOfPerson.size(); i++) {
            Row row = getRow(listOfPerson.get(i));

            heightSum += row.getRowHeight();
            if (heightSum < page.getHeight()) {
                row.printRow();
                printLineBtwTwoRows(lineBtwTwoRows());
            } else {
                printSeparator();
                printFullTitle();
                heightSum = row.getRowHeight();
               row.printRow();
               printLineBtwTwoRows(lineBtwTwoRows());
            }

            //row.printRow();
           // heightSum += row.getRowHeight();
//            if (heightSum < page.getHeight())
//                printLineBtwTwoRows(lineBtwTwoRows());
//            else {
//
//            }

           // printLineBtwTwoRows(lineBtwTwoRows());

        }


    }

    public void printSeparator() {
        System.out.println("~");
    }

    public void printTitle() {
        StringBuilder sb = new StringBuilder();
        sb.append("|");

        for (int i = 0; i < page.getColumn().size(); i++) {
            int spaceRequired = page.getColumn().get(i).getWidth() - page.getColumn().get(i).getTitle().length();
            //вычитаем разницу добавляемых пробелов
            // те получаем нужное количество пробелов после слова
            sb.append(String.format(" %s", page.getColumn().get(i).getTitle()));
            // prints: |
            sb.append(countElementToAdd(" ", spaceRequired)).append(" |");
        }
        //sb.a
        System.out.println(sb.toString());


    }

    public void printFullTitle(){
        printTitle();
        printLineBtwTwoRows(lineBtwTwoRows());
    }
    /*
    Добавляет @символ @раз
     */
    public void printPageToFile(String name) throws FileNotFoundException {
        FileOutputStream fout = new FileOutputStream(name);

        PrintStream consoleStream = System.out;
        PrintStream fileStream = new PrintStream(fout);
        System.setOut(fileStream);
        printPage();
        System.setOut(consoleStream);

    }

    public StringBuilder countElementToAdd(String element, int count) {
        //Добавление элемента в любом количестве
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(element);
        }


        return sb;
    }


}
