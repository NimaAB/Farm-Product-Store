package app;

import dataModels.data.Components;

import java.util.ArrayList;

public class PCConfigurations {
   /* private ArrayList<Items> configurationList;
    public PCConfiuration(ArrayList<Items> configurationList){
        this.configurationList=configurationList;
    }

    public void setConfigurationList(ArrayList<Items> configurationList){
        this.configurationList=configurationList;
    }
    public ArrayList<Items> getConfigurationList() {
        return configurationList;
    }*/
    public double totalPrice(ArrayList<Components> configurationList){
        double totalPris = 0;
        for(Components item:configurationList){
            totalPris += item.getComponentPrice();
        }
        return totalPris;
    }
}
