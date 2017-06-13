package model.databaseControllers;

import UI.Text.ActionLabel;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.database.ActionDB;

/**
 * Created by Latawiec on 12/06/2017.
 */
public class ActionController implements DatabaseSource {

    private ActionDB sourceDB;
    public ActionDB getSourceDB(){ return sourceDB; }

    private FloatProperty energyCost;
    public float getEnergyCost(){ return energyCost.get(); }
    public FloatProperty getEnergyCostProperty(){ return energyCost; }

    private StringProperty name;
    public String getName(){ return name.get(); }
    public StringProperty getNameProperty(){ return name; }

    private FloatProperty energyIncome;
    public float getEnergyIncome(){ return energyIncome.get(); }
    public FloatProperty getEnergyIncomeProperty(){ return energyIncome; }

    public ActionController(ActionDB source){
        sourceDB = source;
        energyCost = new SimpleFloatProperty(source.getEnergyCost());
        name = new SimpleStringProperty(source.getName());
        energyIncome = new SimpleFloatProperty(source.getEnergyIncome());
    }

    @Override
    public void save() {

    }
}
