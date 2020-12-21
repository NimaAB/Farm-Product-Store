package dataModels.data;

import javafx.collections.ObservableList;

public class ConfigItem extends Component {
    public ConfigItem(Component obj){
        super(""+obj.getComponentNr(),obj.getComponentName(),obj.getComponentCategory(),
                obj.getComponentSpecs(),""+obj.getComponentPrice() , obj.getCheckBox());
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s",this.getComponentNr(),this.getComponentName(),this.getComponentPrice());
    }

    public static double totalPrice(ObservableList<ConfigItem> configurationItemList){
        double totalPris = 0;
        for(Component item: configurationItemList){
            totalPris += item.getComponentPrice();
        }
        return totalPris;
    }
}
