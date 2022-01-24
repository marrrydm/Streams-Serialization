package Interface;

import Exceptions.DuplicateModelNameException;
import Exceptions.NoSuchModelNameException;

import java.io.Serializable;

public interface Transport extends Serializable {

    String getVehicle();

    void addNewModel(String modelName, double modelPrice) throws DuplicateModelNameException;

    void setNewModelName(String oldName, String newName) throws DuplicateModelNameException, NoSuchModelNameException;

    void deleteModelByName(String modelName) throws NoSuchModelNameException;

    double getPriceModelByName(String modelName) throws NoSuchModelNameException;

    String getMotoBrand();

    int getLenModels();

    String[] getArrayNamesOfModels();

    double[] getArrayPricesOfModels();

    void setPriceModelByName(String modelName, double modelPrice) throws NoSuchModelNameException, DuplicateModelNameException;
}