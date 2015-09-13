package com.example.hellolibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class Xingxing extends Image {

//	public float deltaY = 5f;//每一秒更新星星的位置
	public float deltaY = 3f;//每一秒更新星星的位置
	
	
	/** Creates an image with no region or patch, stretched, and aligned center. */
	public Xingxing () {
		this((Drawable)null);
	}

	/** Creates an image stretched, and aligned center.
	 * @param patch May be null. */
	public Xingxing (NinePatch patch) {
		this(new NinePatchDrawable(patch), Scaling.stretch, Align.center);
	}

	/** Creates an image stretched, and aligned center.
	 * @param region May be null. */
	public Xingxing (TextureRegion region) {
		this(new TextureRegionDrawable(region), Scaling.stretch, Align.center);
	}

	/** Creates an image stretched, and aligned center. */
	public Xingxing (Texture texture) {
		this(new TextureRegionDrawable(new TextureRegion(texture)));
	}

	/** Creates an image stretched, and aligned center. */
	public Xingxing (Skin skin, String drawableName) {
		this(skin.getDrawable(drawableName), Scaling.stretch, Align.center);
	}

	/** Creates an image stretched, and aligned center.
	 * @param drawable May be null. */
	public Xingxing (Drawable drawable) {
		this(drawable, Scaling.stretch, Align.center);
	}

	/** Creates an image aligned center.
	 * @param drawable May be null. */
	public Xingxing (Drawable drawable, Scaling scaling) {
		this(drawable, scaling, Align.center);
	}

	/** @param drawable May be null. */
	public Xingxing (Drawable drawable, Scaling scaling, int align) {
		setDrawable(drawable);
		setScaling(scaling);
		setAlign(align);
		setWidth(getPrefWidth());
		setHeight(getPrefHeight());
	}
	
	
	public float getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(float deltaY) {
		this.deltaY = deltaY;
	}

	/**
	 * 用于每帧更新星星的位置..
	 */
	public void update(){
		float positionY = getY();
		
		if(positionY < -150){//星星的位置跌落到一定的位置以后重新回到最高点
			positionY = 900;
		}else{
			positionY -= deltaY;
		}
		setY(positionY);
	}
	
}
