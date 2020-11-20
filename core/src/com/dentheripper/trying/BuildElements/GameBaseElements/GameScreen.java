package com.dentheripper.trying.BuildElements.GameBaseElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.dentheripper.trying.BuildElements.ButtonBase;
import com.dentheripper.trying.BuildElements.ScreenBase;
import com.dentheripper.trying.GameCore.Engine;
import com.dentheripper.trying.GameCore.Entity;
import com.dentheripper.trying.View.OnScreen.Controller;
import com.dentheripper.trying.View.OnScreen.HealthBar;
import com.dentheripper.trying.View.OnScreen.SmartRender;
import com.dentheripper.trying.View.OnScreen.StrengthBar;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenBase {

    public InputMultiplexer multiplexer;

    protected static ButtonBase useButton = new ButtonBase("Atlas/buttons.txt", "useButton", 600, 350, 60, 120);
    protected static ButtonBase smartButton = new ButtonBase("Atlas/buttons.txt", "smartButton", 950, 600, 50, 200);
    protected static HealthBar healthBar = new HealthBar();
    protected static StrengthBar strengthBar = new StrengthBar();
    protected SmartRender smartRender = new SmartRender();

    private Entity player;
    protected List<Entity> npc = new ArrayList<>();
    protected Controller controller = new Controller();
    private int useID;

    public GameScreen(Engine engine) {
        super(engine);
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(useButton.stage);
        multiplexer.addProcessor(smartButton.stage);
        smartRender.multiplexSmartScreens(multiplexer);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(multiplexer);

        stage.addActor(player);
        for (int i = 0; i < npc.size(); i ++) {
            stage.addActor(npc.get(i));
        }
        stage.addActor(controller);
        stage.addActor(smartButton);
        stage.addActor(strengthBar);
        stage.addActor(useButton);
        stage.addActor(healthBar);
        smartRender.addToStage(stage);

        smartRender.close();
        useButton.addToClose();

        healthBar.setRealHealth(100);
        strengthBar.setRealStrength(100);

        data.setPrefSpeed(200);
        player.setMaxHp(100);
        player.setMaxSp(100);
        player.setHp(data.getPrefHp());
        player.setSp(data.getPrefSp());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        super.actFinal(delta);
        healthBar.setRealHealth(player.getHp());
        strengthBar.setRealStrength(player.getSp());
        data.putString("realHP", Float.toString(player.getHp()));
        data.putString("realSP", Float.toString(player.getSp()));
        smartRender.renderThis(multiplexer, engine);
        System.out.println(player.getX() + "    " + player.getY()*2);

        if (player.getX() >= 2012 && player.getX() <= 2280 && player.getY() >= 5666*(h/w) && player.getY() <= 5828*(h/w)) {
            useButton.open();
            setUseID(0);// Aug Shop
        } else if (player.getX() >= 2492 && player.getX() <= 2565 && player.getY() >= 5666*(h/w) && player.getY() <= 5828*(h/w)) {
            useButton.open();
            setUseID(1);// Vending machine near augShop
        } else if (player.getX() >= 6300 && player.getY() >= 7990*(h/w)) {
            useButton.open();
            setUseID(2);// C-Tech building
        } else if (player.getY() >= 10800*(h/w)) {
            useButton.open();
            setUseID(3);// Apartments
        } else {
            useButton.addToClose();
            setUseID(-1);
        }

        if (smartButton.isClicked()) {
            Gdx.input.setInputProcessor(SmartRender.home.multiplexer);
            SmartRender.home.show();
            smartButton.setClicked(false);
        }

        player.HPControl();

        if (player.getSp() == 0) {
            long time = System.currentTimeMillis();
            if ((System.currentTimeMillis() - time) / 1000 <= 30) {
                controller.setCanMove(false);
            }
            else {
                controller.setCanMove(true);
            }
        }
        if (player.getSp() >= 10) {
            controller.setCanMove(true);
        }
        if (player.isRunning()) {
            player.setSp(player.getSp() - 5*Gdx.graphics.getDeltaTime());
        } else {
            player.SPControl();
        }
    }

    protected void setPlayer(Entity player) {
        this.player = player;
    }

    protected void addNPC(Entity npcP) {
        npc.add(npcP);
    }

    public int getUseID() {
        return useID;
    }

    public void setUseID(int useID) {
        this.useID = useID;
    }
}
