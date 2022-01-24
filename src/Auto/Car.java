package Auto;

import java.io.Serializable;
import java.util.Arrays;
import Exceptions.*;
import Interface.Transport;

public class Car implements Transport {
    private static final long serialVersionUID = 1;
    private String brand;
    private Model[] models;
    private String vehicle = "Car";

    public String getVehicle() {return vehicle;}

    public Car(String brand, int length) throws DuplicateModelNameException{
        this.brand = brand;
        this.models = new Model[length];

        for (int i=0; i < length; i++) {
            models[i] = new Model("Машина" + i, 100* (i+1));
        }
    }
    private class Model implements Serializable {

        private String name;
        private double price;

        public Model(String name, double price){
            this.name = name;
            this.price = price;
        }

        public String getNameModel(){
            return name;
        }

        public void setNameModel(String name){
            this.name = name;
        }

        public double getPriceModel(){
            return price;
        }

        public void setPriceModel(double price){
            if (price < 0)
                throw new ModelPriceOutOfBoundsException("Цена модели должна быть положительным числом");
            this.price = price;
        }
    }
    public String getMotoBrand(){
        return brand;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public void setNewModelName(String oldName, String newName)
            throws NoSuchModelNameException, DuplicateModelNameException {
        int index = 0;
        while (index < getLenModels()){
            if (models[index].getNameModel().equals(newName))
                throw new DuplicateModelNameException("Такая модель уже есть");
            index++;
        }
        index = 0;
        while (index < getLenModels() && !models[index].getNameModel().equals(oldName)){
            index++;
        }
        if (index == getLenModels()) {
            throw new NoSuchModelNameException("Модель не найдена");}
        models[index].setNameModel(newName);
    }

    public String[] getArrayNamesOfModels() {
        String[] names = new String[models.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = models[i].getNameModel();
        }
        return names;
    }

    public double getPriceModelByName(String nameModel) throws NoSuchModelNameException {
        int index = 0;
        while (index < getLenModels() && !models[index].getNameModel().equals(nameModel)) {
            index++;
        }
        if (index == getLenModels()) {
            throw new NoSuchModelNameException("Модель не найдена");}
        return models[index].getPriceModel();
    }

    public void setPriceModelByName(String nameModel, double priceModel) throws NoSuchModelNameException {
        if (priceModel < 0) {
            throw new ModelPriceOutOfBoundsException("Цена модели не может быть отрицательной");}
        int index = 0;
        while (index < getLenModels() && !models[index].getNameModel().equals(nameModel)) {
            index++;
        }
        if (index == getLenModels()) throw new NoSuchModelNameException("Модели с таким именем нет");
        models[index].setPriceModel(priceModel);
    }

    public double[] getArrayPricesOfModels() {
        double[] price = new double[models.length];
        for (int i = 0; i < price.length; i++) {
            price[i] = models[i].getPriceModel();
        }
        return price;
    }

    public void addNewModel(String modelName, double modelPrice) throws DuplicateModelNameException {
        if (modelPrice < 0) {
            throw new ModelPriceOutOfBoundsException("Цена модели не может быть отрицательной");}
        else
        {
            int i = 0;
            while (i < models.length && models[i] != null)
            {
                if(models[i].getNameModel().equals(modelName))
                {
                    throw new DuplicateModelNameException("Такая модель уже есть");
                }
                i++;
            }
            if( i == models.length)
            {
                Model[] addModel = Arrays.copyOf(models, models.length + 1);
                addModel[models.length] = new Model(modelName, modelPrice);
                models = addModel;
            }
            else
            {
                models[i] = new Model(modelName, modelPrice);
            }
        }
    }

    public void deleteModelByName(String modelName) throws NoSuchModelNameException {

        int index = findModel(modelName);
        if (findModel(modelName) != -1) {
            System.arraycopy(models, index + 1, models, index,getLenModels() - index - 1);
            models = Arrays.copyOf(models, getLenModels() - 1);
        }
        else {
            throw new NoSuchModelNameException("Модель не найдена");
        }
    }

    public int findModel(String modelName){
        int length = getLenModels();
        int index = 0;
        while(index < length){
            if(models[index].getNameModel().equals(modelName)){
                return index;
            }
            index++;
        }
        return -1;
    }

    public int getLenModels(){
        return models.length;
    }

    public Car(String brand){
        this.brand = brand;
        models = new Model[0];
    }
}
