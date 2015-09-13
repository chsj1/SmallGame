package com.example.hellolibgdx;

import java.util.Random;

import android.os.SystemClock;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class MyGame implements ApplicationListener {

	
	Stage stage;
	
	
	Group gameGroup;
	Xingxing xingxing;
	Image bucketImage;
	
	
	
	Random random;
	
	Sound pengzhaungSound;
	Music gameMusic;
	
	
	int scoreNums;
	BitmapFont scoreFont;
	LabelStyle scoreLabelStyle;
	Label scoreNumLabel;//用来显示分数
	
	
	Label successLabel;
	Group successGroup;
	
	long endTime;//用来记录结束时间
	StringBuffer leftTimeSB;
	Label leftTimeLabel;
	
	Label loseLabel;
	Group loseGroup;
	
	int continuesTime = 60;
	int successTarget = 60;
	
	
	@Override
	public void create() {
		stage = new Stage(480, 800, false);
		
		xingxing = new Xingxing(new Texture(Gdx.files.internal("data/droplet.png")));
		xingxing.setPosition(240, 700);
		
		
		bucketImage = new Image(new Texture(Gdx.files.internal("data/bucket.png")));
		
		scoreFont = new BitmapFont(Gdx.files.internal("font/default.fnt"),false);
		scoreLabelStyle = new LabelStyle(scoreFont, Color.BLUE);
		scoreNumLabel = new Label("the numbers of star you have collected is: " + scoreNums, scoreLabelStyle);
		scoreNumLabel.setPosition(0, 600);
		
		
		endTime = SystemClock.elapsedRealtime() + 1000*continuesTime;//结束时间位5秒后
		leftTimeSB = new StringBuffer("5");
		leftTimeLabel = new Label("the left time is: " + leftTimeSB.toString(), scoreLabelStyle);
		leftTimeLabel.setPosition(0, 500);
		
		gameGroup = new Group();
		gameGroup.addActor(xingxing);
		gameGroup.addActor(bucketImage);
		gameGroup.addActor(scoreNumLabel);
		gameGroup.addActor(leftTimeLabel);
		
		
		successLabel = new Label("you have success !\n tap tp continue...", scoreLabelStyle);
		successLabel.setPosition(150, 400);
		successGroup = new Group();
		successGroup.setSize(480, 800);
		successGroup.addActor(successLabel);
		successGroup.setVisible(false);
		successGroup.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				successGroup.setVisible(false);
				
				endTime = SystemClock.elapsedRealtime() + 1000*continuesTime;//更新游戏的结束时间
				
				//重新开始游戏
				scoreNums = 0;
				scoreNumLabel.setText("the numbers of star you have collected is: " + scoreNums);
				
				gameGroup.setVisible(true);
				
				return true;
			}
		});
		
		
		loseLabel = new Label("sorry ,you have lose the game.\n tap to continue", scoreLabelStyle);
		loseLabel.setPosition(150, 400);
		loseGroup = new Group();
		loseGroup.setSize(480, 800);
		loseGroup.setVisible(false);
		loseGroup.addActor(loseLabel);
		loseGroup.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("--------->你单击了losegroup");
				
				loseGroup.setVisible(false);
				endTime = SystemClock.elapsedRealtime() + 1000*continuesTime;
				
				//重新开始游戏
				scoreNums = 0;
				scoreNumLabel.setText("the numbers of star you have collected is: " + scoreNums);
				
				gameGroup.setVisible(true);
				
				return true;
			}
		});
		
		
		stage.addActor(gameGroup);
		stage.addActor(successGroup);
		stage.addActor(loseGroup);
		
		
		addListenerOnStageToHandleBucket();
		
		random = new Random();//用来产生星星被捡到后的随机数
		pengzhaungSound = Gdx.audio.newSound(Gdx.files.internal("music/drop.wav"));
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/rain.mp3"));
		gameMusic.setLooping(true);
		gameMusic.play();
		
		
		
		Gdx.input.setInputProcessor(stage);
		
	}
	
	public void updateLeftTime(){
		long diff = endTime - SystemClock.elapsedRealtime();

		if (diff < 0) {
//			isDoneds[i] = true;
//			leftTimeLabels[i].setVisible(false);
//			jindutiaoImages[i].setVisible(false);
//			workingImages[i].setVisible(false);
//			getMoneyImages[i].setVisible(true);

			// 这时候依然让它先不能显示
			// singleWork = false;// 表示可以按下其他按钮了...
			// removeOtherWorkOnImagesAnimation(i);
			
			gameGroup.setVisible(false);
			loseGroup.setVisible(true);
			
		}


		leftTimeSB.delete(0, leftTimeSB.length());// 清空之前的内容

		long current = diff / 1000;
		int hour = (int) (current / (3600));
		int minute = (int) ((current - hour * 3600) / 60);
		int second = (int) (current - hour * 3600 - minute * 60);

		if (hour < 10) {
			leftTimeSB.append('0');
		}

		leftTimeSB.append(hour);
		leftTimeSB.append(':');
		if (minute < 10) {
			leftTimeSB.append('0');
		}
		leftTimeSB.append(minute);
		leftTimeSB.append(':');
		if (second < 10) {
			leftTimeSB.append('0');
		}
		leftTimeSB.append(second);

		// System.out.println("------------->dailyTaskTimesb: " +
		// dailyTaskTimesb);
		leftTimeLabel.setText("the left time is: " + leftTimeSB.toString());

	}
	
	public void pengzhuangJiance(){
		if(Utils.overlapDetector(xingxing, bucketImage) == true){
			System.out.println("------------->发生了碰撞");
			
			xingxing.setPosition(random.nextFloat() * 480, 900);//发生碰撞后星星在另外一个位置上再出现
			pengzhaungSound.play();
			
			scoreNums++;
			
			if(scoreNums >= successTarget){
				gameGroup.setVisible(false);
				successGroup.setVisible(true);
			}
			scoreNumLabel.setText("the numbers of star you have collected is: " + scoreNums);
			
		}
	}
	
	
	
	public void addListenerOnStageToHandleBucket(){
		stage.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				
				return true;
			}
			
			
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				
				bucketImage.setX(x);
				
			}
		});
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
	
	
	
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);// 设置背景为白色
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);// 清屏
		
		xingxing.update();
		pengzhuangJiance();
		
		updateLeftTime();
		
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
