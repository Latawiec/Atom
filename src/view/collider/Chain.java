package view.collider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import model.databaseControllers.ParticleController;
import obv.particle.ParticleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Latawiec on 28/05/2017.
 */
public class Chain extends StackPane {

    private DoubleProperty particleViewSize = new SimpleDoubleProperty(200);

    private List<ParticleController> particles = new ArrayList<ParticleController>();
    private FlowPane container = new FlowPane();

    public Chain(){
        super();
        container.setAlignment(Pos.CENTER);
        container.maxWidthProperty().bind(particleViewSize.multiply(2));
        getChildren().add(container);
    }

    public void addParticle(ParticleController particle){
        particles.add(particle);
        ParticleView view = new ChainParticleView(particle, particleViewSize, particleViewSize);
        view.disableElectronShells();
        container.getChildren().add(view);
    }

    public ChainParticleView getLastParticleView(){
        return (ChainParticleView) container.getChildren().get(container.getChildren().size()-1);
    }

}
