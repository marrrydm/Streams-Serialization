package com.company;
import Auto.*;
import Exceptions.*;
import Interface.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args)  {
        try {
            Transport car = new Car("BMW", 3);
            Transport motorbike = new Motorbike("Harley", 2);

            File fileName = new File("Document.txt");
            //Запись в байтовый поток
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            Vehicle.outputTransport(car, fileOutputStream);
            //Чтение из байтового потока
            FileInputStream fileInputStream = new FileInputStream(fileName);
            Transport transport1 = Vehicle.inputTransport(fileInputStream);
            //Запись в символьный поток
            FileWriter fileWriter = new FileWriter(fileName);
            Vehicle.writeTransport(motorbike, fileWriter);
            //Чтение из символьного потока
            FileReader fileReader = new FileReader(fileName);
            Transport transport2 = Vehicle.readTransport(fileReader);
            //сериализация
            FileOutputStream fileOutputStream1 = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream1);
            objectOutputStream.writeObject(motorbike);//записывает данные в поток
            //десериализация
            FileInputStream fileInputStream1 = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream1);
            Transport transport3 = (Transport) objectInputStream.readObject();

            System.out.println("Байтовый поток:");
            System.out.println(transport1.getMotoBrand());
            System.out.println(transport1.getLenModels());
            System.out.println(Arrays.toString(transport1.getArrayNamesOfModels()));
            System.out.println(Arrays.toString(transport1.getArrayPricesOfModels()));
            System.out.println();

            System.out.println("Символьный поток:");
            System.out.println(transport2.getMotoBrand());
            System.out.println(transport2.getLenModels());
            System.out.println(Arrays.toString(transport2.getArrayNamesOfModels()));
            System.out.println(Arrays.toString(transport2.getArrayPricesOfModels()));
            System.out.println();

            System.out.println("Сериализация:");
            System.out.println(transport3.getMotoBrand());
            System.out.println(transport3.getLenModels());
            System.out.println(Arrays.toString(transport3.getArrayNamesOfModels()));
            System.out.println(Arrays.toString(transport3.getArrayPricesOfModels()));

            //ввод с консоли
            Transport moto = Vehicle.readTransport(new InputStreamReader(System.in));
            Vehicle.writeTransport(moto,new OutputStreamWriter(System.out));

            fileOutputStream.close();
            fileInputStream.close();
            fileWriter.close();
            fileReader.close();
            objectOutputStream.close();
            objectInputStream.close();

        } catch (ModelPriceOutOfBoundsException | IOException |DuplicateModelNameException | NoSuchModelNameException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

