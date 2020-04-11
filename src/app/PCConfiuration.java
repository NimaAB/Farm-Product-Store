package app;

import dataModels.data.Items;

import java.util.ArrayList;

public class PCConfiuration {
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
    public double totalPrice(ArrayList<Items> configurationList){
        double totalPris = 0;
        for(Items item:configurationList){
            totalPris += item.getPris();
        }
        return totalPris;
    }
}
