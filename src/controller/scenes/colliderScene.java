package controller.scenes;

import UI.LabeledValue;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Particle;
import controller.ScenesController;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.util.Duration;
import model.database.ParticleDB;
import model.database.UserParticleDB;
import model.databaseControllers.DatabaseAccessor;
import model.databaseControllers.ParticleController;
import model.databaseControllers.UserController;
import obv.particle.ParticlesContainer;
import view.collider.Chain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Latawiec on 27/05/2017.
 */
public class colliderScene extends SceneTemplate {

    private FloatProperty energyIncomeProperty = new SimpleFloatProperty(0);
    private LabeledValue energyIncome = new LabeledValue("Energy income");
    private VBox Vcontainer = new VBox();
    private StackPane top = new StackPane();
    private StackPane bottom = new StackPane();
    Label outputLabel = new Label();
    ParticlesContainer pContainer;
    Label plusLabel = new Label();
    private List<ParticleController> chosenParticles = new ArrayList<ParticleController>();
    private List<ParticleController> userParticles = new ArrayList<ParticleController>();
    boolean isStable = false;

    private Chain chain = new Chain();
    private UserController userControll;

    public colliderScene(ScenesController controller) {
        super(controller);

        energyIncome.getValueProperty().bind(energyIncomeProperty.asString());
        energyIncome.translateXProperty().bind(widthProperty().divide(5));
        userControll = new UserController(controller.getUser());
        outputLabel.setOpacity(0);
        outputLabel.setTranslateY(-150);
        getExcitedParticles();
        plusLabel.setText("+");
        plusLabel.setFont(Font.font("Helvetica", 40));
        plusLabel.setTranslateY(-425);
        plusLabel.setTextFill(Color.WHITE);
        plusLabel.setOpacity(0);

        bottom.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0.5f, 0, 0, 0))));

        Vcontainer.getChildren().addAll(top, bottom);
        Vcontainer.setAlignment(Pos.BOTTOM_CENTER);
        top.getChildren().add(chain);
        pContainer = new ParticlesContainer(getWidth(), 200, userParticles);
        pContainer.disableElectrons();
        bottom.getChildren().add(pContainer);
        bottom.getChildren().add(outputLabel);
        bottom.getChildren().add(plusLabel);
        bottom.getChildren().add(energyIncome);
        bottom.setMinHeight(250);

        container.getChildren().add(Vcontainer);
        root.setAlignment(container, Pos.BOTTOM_CENTER);
        setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent arg0) {
                switch(arg0.getCode()){
                    case LEFT:
                        pContainer.setSelectedParticle(pContainer.getSelectedParticle() - 1);
                        break;
                    case RIGHT:
                        pContainer.setSelectedParticle(pContainer.getSelectedParticle() + 1);
                        break;
                    case UP:
                        if(pContainer.particlesCount()>0){
                            chooseParicle(userParticles.get(pContainer.getSelectedParticle()));
                            userParticles.remove(pContainer.getSelectedParticle());
                            pContainer.removeParticle(pContainer.getSelectedParticle());
                        }
                        break;
                    case ESCAPE:
                        cancel();
                        break;
                    case ENTER:
                        commit();
                        cancel();
                        break;
                    case BACK_SPACE:
                        try {
                            userControll.save();
                            controller.setScene(ScenesController.Scenes.Main);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }

    public void chooseParicle(ParticleController particle){
        chosenParticles.add(particle);
        chain.addParticle(particle);
        if(chosenParticles.size() >= 2){
            outputLabel.setOpacity(1);
            plusLabel.setOpacity(1);
            if(chosenParticles.size()%2 == 0){
                int nucleons = 0;
                int protons = 0;
                int neutrons = 0;
                nucleons += chosenParticles.get(chosenParticles.size()-1).getNucleons();
                nucleons += chosenParticles.get(chosenParticles.size()-2).getNucleons();

                protons += chosenParticles.get(chosenParticles.size()-1).getProtons();
                protons += chosenParticles.get(chosenParticles.size()-2).getProtons();

                neutrons += chosenParticles.get(chosenParticles.size()-1).getNeutrons();
                neutrons += chosenParticles.get(chosenParticles.size()-2).getNeutrons();

                List<ParticleDB> possibleParticles = null;
                ParticleController temp;
                try {
                    possibleParticles = DatabaseAccessor.getInstance().getParticlesWithNucleonsNumber(nucleons);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(possibleParticles.size() == 1){
                    temp = new ParticleController(new UserParticleDB(userControll.getSourceDB(), possibleParticles.get(0), 0));
                    chosenParticles.add(temp);
                    chain.addParticle(temp);
                    outputLabel.setTextFill(Color.GREEN);
                    outputLabel.setText("Stable");
                    chain.getLastParticleView().setStable();
                    isStable = true;
                }else{
                    temp = new ParticleController(new UserParticleDB(userControll.getSourceDB(), new ParticleDB(neutrons, protons, new int[]{0,0,0,0,0,0,0}), 0));
                    chosenParticles.add(temp);
                    chain.addParticle(temp);
                    outputLabel.setTextFill(Color.CRIMSON);
                    outputLabel.setText("Unstable");
                    chain.getLastParticleView().setUnstable();
                    isStable = false;
                }
                energyIncomeProperty.set( temp.getBindingEnergy() - energyIncomeProperty.getValue() );
            }
        }

    }

    public void cancel(){
        chosenParticles.clear();
        getExcitedParticles();
        bottom.getChildren().remove(pContainer);
        top.getChildren().remove(chain);
        pContainer = new ParticlesContainer(200, 200, userParticles);
        pContainer.disableElectrons();
        chain = new Chain();
        top.getChildren().add(chain);
        bottom.getChildren().add(pContainer);
        outputLabel.setOpacity(0);
        plusLabel.setOpacity(0);
        energyIncomeProperty.set(0);
    }

    public void commit(){
        if(chosenParticles.size()>0 && isStable) {
            for (ParticleController p : chosenParticles) {
                userControll.getParticlesArray().remove(p);
            }
            ParticleController addedParticle = chosenParticles.get(chosenParticles.size() - 1);
            userControll.getParticlesArray().add(addedParticle);
            userControll.saveParticles();
            animateBackgroundFlash(new Color(0.03, 0.53, 0.12, 1));
            userControll.unlockParticle((byte)addedParticle.getProtons());

            userControll.setEnergy( userControll.getEnergy() + energyIncomeProperty.get() );
        }else{
            animateBackgroundFlash(new Color(0.53, 0.03, 0.12, 1));
        }
    }


    private void animateBackgroundFlash(Color color){
        Color current = new Color(0.03, 0.03, 0.12, 1);
        FillTransition start = new FillTransition();
        start.setToValue(color);
        start.setShape(background);
        start.setFromValue(current);
        start.setDuration(Duration.seconds(0.2f));

        FillTransition end = new FillTransition();
        end.setToValue(current);
        end.setShape(background);
        end.setFromValue(color);
        end.setDuration(Duration.seconds(0.8f));

        start.setOnFinished(e->{
            end.play();
        });
        start.play();
    }

    public void getExcitedParticles(){
        userParticles.clear();
        List<ParticleController> temp = (List<ParticleController>) userControll.getParticlesArray().clone();
        for(ParticleController p : temp){
            if(p.isEnergized()){
                userParticles.add(p);
            }
        }
    }

}
