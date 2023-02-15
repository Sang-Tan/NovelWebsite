package service;

import repository.IObjectRepository1;
import repository.ObjectRepository1;

public class ObjectService implements IObjectService{
    private final IObjectRepository1 objectRepository1 = new ObjectRepository1();

}
