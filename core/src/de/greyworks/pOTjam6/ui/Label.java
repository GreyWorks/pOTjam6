package de.greyworks.pOTjam6.ui;

import java.awt.Font;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Label implements Screen {
	Texture bg;
	BitmapFont font;
	String text = "";
	Vector2 pos, size;
	ClickAction clk;

	public Label(Texture background, BitmapFont font, Vector2 pos, Vector2 size) {
		this.bg = background;
		this.font = font;
		this.pos = pos;
		this.size = size;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(bg, pos.x, pos.y, size.x, size.y);
		font.drawWrapped(batch, text, pos.x + 5, pos.y + 19, size.x - 10);
	}

	@Override
	public void click(Vector2 pos) {


	}

}
