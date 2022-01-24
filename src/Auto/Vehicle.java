package Auto;

import Interface.*;
import Exceptions.*;
import java.io.*;

public class Vehicle {
    public static double getAverage(Transport transport) {
        double[] arrPricesOfModels = transport.getArrayPricesOfModels();
        double sumPrice = 0;
        for (double price : arrPricesOfModels) {
            sumPrice += price;
        }
        return sumPrice / transport.getLenModels();
    }

    public static void printNamesModel(Transport transport) {
        String[] arrNamesOfModels = transport.getArrayNamesOfModels();
        for (String name : arrNamesOfModels) {
            System.out.println("Имя модели: " + name);
        }
    }

    public static void printPricesModel(Transport transport) {
        double[] arrPricesOfModels = transport.getArrayPricesOfModels();
        for (double price : arrPricesOfModels) {
            System.out.println("Цена модели: " + price);
        }
    }
    //Запись в байтовый поток
    private static void recordingStream(String strData, DataOutputStream dataOutputStream) throws IOException{
        byte[] bytes = strData.getBytes();
        //чтобы при чтении знать сколько читать
        dataOutputStream.writeInt(bytes.length);//записывает в поток целочисленное значение int
        dataOutputStream.write(bytes);
    }
    public static void outputTransport(Transport v, OutputStream out) throws IOException, NoSuchModelNameException
    {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        recordingStream(v.getVehicle(),dataOutputStream);
        recordingStream(v.getMotoBrand(),dataOutputStream);

        //чтобы при чтении знать сколько элементолв в массиве
        int len = v.getLenModels();
        dataOutputStream.writeInt(len);
        String[] models = v.getArrayNamesOfModels();
        double[] prices = v.getArrayPricesOfModels();
        for (int i = 0; i < v.getLenModels(); i++) {
            dataOutputStream.writeUTF(models[i]);
            dataOutputStream.writeDouble(prices[i]);
        }
    }
    //Чтение из байтового потока
    private static String readingStream(DataInputStream dataInputStream)throws IOException{
        //длина модели
        int length = dataInputStream.readInt();//считывает из потока целочисленное значение int
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++){
            bytes[i] = dataInputStream.readByte();//считывает из потока 1 байт
        }
        String name = new String(bytes);
        return name;
    }
    public static Transport inputTransport(InputStream in) throws IOException, DuplicateModelNameException, NoSuchModelNameException {
        Transport transport = null;
        DataInputStream dataInputStream = new DataInputStream(in);

        switch (readingStream(dataInputStream)){
            case "Car":
                transport = new Car(readingStream(dataInputStream));
                break;
            case "Motorbike":
                transport = new Motorbike(readingStream(dataInputStream));
                break;
        }
        //сколько моделей и цен
        int numModels = dataInputStream.readInt();
        for(int i = 0; i < numModels; i++){
            String model = dataInputStream.readUTF();
            double price = dataInputStream.readDouble();
            transport.addNewModel(model,price);
        }
        return transport;
    }

    //Записи информации в символьный поток
    public static void writeTransport(Transport v, Writer out)
    {
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.println(v.getVehicle());
        printWriter.println(v.getMotoBrand());
        printWriter.println(v.getLenModels());
            String[] models = v.getArrayNamesOfModels();
            double[] prices = v.getArrayPricesOfModels();
            for (int i = 0; i < v.getLenModels(); i++) {
                printWriter.println(models[i]);
                printWriter.println(prices[i]);
            }
            printWriter.flush();//сброс буф потока и запись
    }

    //Чтение из символьного потока
    public static Transport readTransport(Reader in) throws IOException, DuplicateModelNameException, NoSuchModelNameException {
        Transport transport = null;
        BufferedReader bfReader = new BufferedReader(in);//Создает буферный поток ввода символов

        switch (bfReader.readLine()){
            case "Car":
                transport = new Car(bfReader.readLine());
                break;
            case "Motorbike":
                transport = new Motorbike(bfReader.readLine());
                break;
        }
        int numModels = Integer.parseInt(bfReader.readLine());
        for(int i = 0; i < numModels; i++){
            String model = bfReader.readLine();
            double price = Double.parseDouble(bfReader.readLine());
            transport.addNewModel(model, price);
        }
        return transport;
    }
}
