package model.databaseControllers;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import model.database.ExchangePositionDB;

/**
 * Created by Latawiec on 13/06/2017.
 */
public class ExchangePositionController implements DatabaseSource {

    private ExchangePositionDB sourceDB;
    public ExchangePositionDB getSource(){ return sourceDB; }

    private FloatProperty energyCost;
    public FloatProperty getEnergyCost(){ return energyCost; }

    public ExchangePositionController(ExchangePositionDB source){
        this.sourceDB = source;
        this.energyCost = new SimpleFloatProperty(source.getEnergyCost());
    }

    @Override
    public void save() {

    }
}
