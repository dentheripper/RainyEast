package com.dentheripper.trying.BuildElements.GameBaseElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class ExtraWindow extends Actor {

    protected Stage stage;

    private Image image;
    protected float w = Gdx.graphics.getWidth();
    protected float h = Gdx.graphics.getHeight();
    private float x;

    public ExtraWindow() {
        stage = new Stage(new StretchViewport(1000, 1000 * (h / w)));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        actFinal(parentAlpha);
    }

    public void setImage(Texture texture, float x, float y, float width, float height) {
        image = new Image(texture);
        image.setPosition(x, y * (h / w));
        this.x = x;
        image.setSize(width, height * (h / w));
        stage.addActor(image);
    }

    public void actFinal(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void close() {
        image.setPosition(1500, image.getY());
    }

    public void show() {
        image.setPosition(x, image.getY());
    }
}
