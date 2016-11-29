package com.desitum.zagg.library;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.desitum.library.widgets.LinearLayout;
import com.desitum.library.widgets.Widget;

/**
 * Created by kody on 12/19/15.
 * can be used by kody and people in [kody}]
 */
public class Label extends Widget {

    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private String text;
    private int alignment;

    public Label(Texture texture, String name, float width, float height, float X, float Y, BitmapFont font, Camera camera) {
        super(texture, name, width, height, X, Y, null);


        this.text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.font = font;
        glyphLayout = new GlyphLayout(font, text);
        setupFontSize();
        alignment = LinearLayout.ALIGNMENT_LEFT;
    }

    private void setupFontSize() {
        float x = 0;
        float z = 0;
        while (x < getHeight() * 0.6f) {
            z += 0.01f;
            font.getData().setScale(z);
            x = font.getCapHeight();
        }
        font.getData().setScale(z - 0.01f);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if (alignment == LinearLayout.ALIGNMENT_LEFT)
            font.draw(batch, text, getX() + getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        if (alignment == LinearLayout.ALIGNMENT_CENTER)
            font.draw(batch, text, getX() + getWidth() / 2 - getTextWidth() / 2, getY() + getHeight() * 0.8f);
        if (alignment == LinearLayout.ALIGNMENT_RIGHT)
            font.draw(batch, text, getX() + getWidth() - getTextWidth() - getHeight() * 0.2f, getY() + getHeight() * 0.8f);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(Color color) {
        this.font.setColor(color);
    }

    public float getTextWidth() {
        glyphLayout.setText(font, text);
        return glyphLayout.width;
    }

    public void setAlignment(int align) {
        this.alignment = align;
    }
}
