package Auto;

import Exceptions.DuplicateModelNameException;
import Exceptions.ModelPriceOutOfBoundsException;
import Exceptions.NoSuchModelNameException;
import Interface.Transport;

import java.io.Serializable;

public class Motorbike implements Transport {
    private static final long serialVersionUID = 1;
    private String motoBrand;
    private int size = 0;
    private String vehicle = "Motorbike";

    public String getVehicle() {return vehicle;}

    public int getLenModels() {
        return size;
    }

    public String getMotoBrand() {
        return motoBrand;
    }

    public void setMotoBrand(String motoBrand) {
        this.motoBrand = motoBrand;
    }

    private class Model implements Serializable{
        String name = null;
        double price = Double.NaN;
        Model prev = null;
        Model next = null;

        public String getNameModel() {
            return name;
        }

        public void setNameModel(String name) {
            this.name = name;
        }

        public double getPriceModel() {
            return price;
        }

        public void setPriceModel(double price) {
            this.price = price;
        }

        public Model() {}

        public Model(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    private Model head = new Model();
    {
        head.prev = head;
        head.next = head;
    }

    public Motorbike(String motoBrand, int length) throws DuplicateModelNameException {
        setMotoBrand(motoBrand);
        for (int i = 0; i < length; i++) {
            addNewModel("Мотоцикл" + (i + 1), 100 * (i + 1));
        }
    }

    public void addNewModel(String modelName, double modelPrice) throws DuplicateModelNameException {
        if (modelPrice < 0)
            throw new ModelPriceOutOfBoundsException("Цена модели не может быть отрицательной");
        Model model = head.next;
        while (model != head) {
            if (model.getNameModel().equals(modelName))
                throw new DuplicateModelNameException("Такая модель уже есть");
            model = model.next;
        }
        Model newModel = new Model(modelName, modelPrice);
        newModel.next = head;
        newModel.prev = head.prev;
        head.prev.next = newModel;
        head.prev = newModel;
        size++;
    }

    public void setNewModelName(String oldName, String newName)
            throws DuplicateModelNameException, NoSuchModelNameException {
        Model p = head.next;
        while (p.next != head) {
            if (p.getNameModel().equals(newName))
                throw new DuplicateModelNameException("Такая модель уже есть");
            p = p.next;
        }
        Model q = head.next;
        while (q.next != head && !q.getNameModel().equals(oldName)) {
            q = q.next;
        }
        if (q.equals(head))
            throw new NoSuchModelNameException("Такого имени нет");
        q.setNameModel(newName);
    }


    public String[] getArrayNamesOfModels() {
        int i = 0;
        String[] names = new String[getLenModels()];
        Model model = head.next;
        while (!model.equals(head)) {
            names[i] = model.name;
            i++;
            model = model.next;
        }
        return names ;
    }

    public double[] getArrayPricesOfModels() {
        double[] arrPricesOfModels = new double[getLenModels()];
        int i = 0;
        Model model = head.next;
        while (!model.equals(head)) {
            arrPricesOfModels[i] = model.getPriceModel();
            model = model.next;
            i++;
        }
        return arrPricesOfModels;
    }

    public double getPriceModelByName(String modelName)
    throws NoSuchModelNameException
    {
        Model model = head.next;
        while (model != head && !model.getNameModel().equals(modelName)) {
            model = model.next;
        }
        if (model.equals(head)) throw new NoSuchModelNameException ("Модели с таким именем нет");
        return model.getPriceModel();
    }

    public void setPriceModelByName(String modelName, double modelPrice) throws NoSuchModelNameException {
        if (modelPrice < 0)
            throw new ModelPriceOutOfBoundsException("Цена модели должна быть положительным числом");
        Model model = head.next;
        while (model != head && !model.getNameModel().equals(modelName)) {
            model = model.next;
        }
        if (model.equals(head)) throw new NoSuchModelNameException("Такой модели нет");
        model.setPriceModel(modelPrice);
    }

    public void deleteModelByName(String modelName) throws NoSuchModelNameException {
        Model model = head.next;
        while (model != head && !model.getNameModel().equals(modelName)) {
            model = model.next;
        }
        if (model.equals(head)) throw new NoSuchModelNameException("Такой модели нет");
        model.next.prev = model.prev;
        model.prev.next = model.next;
        size--;
    }
    public Motorbike(String brand) {
        this.motoBrand = brand;
    }
}
