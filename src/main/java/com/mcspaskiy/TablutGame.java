package com.mcspaskiy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcspaskiy.io.AssetHolder;
import com.mcspaskiy.io.IOService;
import com.mcspaskiy.model.Board;
import com.mcspaskiy.model.Piece;
import com.mcspaskiy.model.PieceType;

import static com.mcspaskiy.model.PieceType.WHITE;
import static com.mcspaskiy.utils.Constants.SCREEN_HEIGHT;
import static com.mcspaskiy.utils.Constants.SCREEN_WITH;

/**
 * @author Mikhail Spaskiy
 */
public class TablutGame extends ApplicationAdapter {
    //scene 2d begin
    private Stage stage;
    private Viewport viewport;
    private AssetHolder assetHolder;


    private Camera camera;
    //scene 2d end

  /*  private Texture dropImage;
    private Texture bucketImage;


    private Sound dropSound;
    private Music rainMusic;
    private OrthographicCamera camera;
    private SpriteBatch batch;


    private Rectangle bucket;

    private Array<Rectangle> raindrops;*/

    private long lastDropTime;

    public TablutGame() {

    }

    @Override
    public void create() {
        //loadAssets();
        assetHolder = IOService.getInstance().getOrloadAssets();
        //scene 2d begin
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WITH, SCREEN_HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // MyActor myActor1 = new MyActor(manager.get(AssetDescriptors.skeleton) , AssetDescriptors.skeleton.fileName);

        Board board = new Board(stage);

      //  stage.addActor(new Piece(IOService.getInstance().getOrloadAssets().getBlackPieceImage(), PieceType.BLACK));

        //Piece piece = new Piece(assetHolder.getWhitePieceImage(), WHITE);

        //piece.spritePos(0, 0);

        //scene 2d end


        // Create camera


        // camera.setToOrtho(false, SCREEN_WITH, SCREEN_HEIGHT);


        // load the images for the droplet and the bucket, 64x64 pixels each
       /* dropImage = new Texture(Gdx.files.internal("drop.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        // load the drop sound effect and the rain background "music"
        //Gdx.files.internal();
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // start the playback of the background music immediately
        rainMusic.setLooping(true);
        rainMusic.play();

        batch = new SpriteBatch();

        bucket = new Rectangle();
        bucket.x = SCREEN_WITH / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        raindrops = new Array<Rectangle>();
        spawnRaindrop();*/
    }

   /* private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, SCREEN_WITH - 64);
        raindrop.y = SCREEN_HEIGHT;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }*/

    @Override
    public void render() {
        //scene 2d begin
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(assetHolder.getBoardImage(), 0, 0, SCREEN_WITH, SCREEN_HEIGHT);
        stage.getBatch().end();
        stage.draw();
        //scene 2d end

/*        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        //batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(boardImage, 0, 0);
       *//* for (Rectangle raindrop : raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        batch.draw(bucketImage, bucket.x, bucket.y);*//*
        batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > SCREEN_WITH - 64) bucket.x = SCREEN_WITH - 64;

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) iter.remove();

            if (raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
            }
        }*/
    }

    @Override
    public void dispose() {
        //scene 2d begin
        IOService.getInstance().unloadAssets();
        stage.dispose();
        //scene 2d end

        /**dropImage.dispose();
         bucketImage.dispose();
         dropSound.dispose();
         rainMusic.dispose();
         batch.dispose();*/
    }
}
