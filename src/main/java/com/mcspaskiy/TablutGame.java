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

import static com.mcspaskiy.utils.Constants.SCREEN_HEIGHT;
import static com.mcspaskiy.utils.Constants.SCREEN_WITH;

/**
 * @author Mikhail Spaskiy
 */

/**
 * * Точная начальная расстановка известна только для лапландской и саксонской версий (Таблут и «Alea Evangelii»). Все прочие, предложенные историками для досок других размеров, лишь предположительны и основаны на исландских, ирландских и валлийских литературных источниках.
 * * Игроки ходят по очереди. Первый ход делает атакующая сторона. Большинство исследователей сходятся в том, что в ранних вариантах Тафла игроки использовали кубики, бросок которых определял количество ходов и, может быть, саму возможность хода, но со временем поняли, что в Хнефатафл как игру логическую интереснее играть без костей.
 * * Все фигуры двигаются ортогонально, по вертикали или горизонтали на любое количество свободных клеток по типу шахматной ладьи (на малом поле – только на одну соседнюю клетку). Если есть возможность ходить, игрок обязан сделать ход. Фигуры не могут перепрыгивать друг через друга и вставать на центральную и угловые «крепости»: их занимать может только фигура Короля. При этом, сойдя с «трона», Король больше не может на него возвратиться.
 * * Рубка производится путём захвата фишки в клещи двумя фишками противника. По диагонали фишки не ходят и не зажимают. Допустим захват одним ходом двух и даже трёх фишек соперника. Также взятие можно проводить, зажимая вражескую фигуру меж своей фигурой и пустым «троном». «Трон» всегда враждебен к нападающим, но к защитникам – только, когда он пустой. Захват не обязателен. Срубленные фишки уходят с доски навсегда.
 * * Зажатие фишки противника между своей фишкой и угловой «крепостью» зависит от правил конкретной игры: в Таблуте так можно атаковать даже Короля, в валлийском Таблборде и норвежском Хнефатафле подобный захват короля запрещён, но можно атаковать простые фишки, в ирландском Брандубе – разрешён любой захват при любой атаке.
 * * Хотя взятие и осуществляется зажимом фишки с боков, проход фишки меж двумя стоящими фишками противника безопасен. На малых досках фишки могут проходить сквозь «трон», на больших это может делать только Король.
 * * Фишка Короля может быть срублена, только будучи зажатой с четырёх сторон (по уточнённой версии правил, в Таблуте – с двух; четыре фишки требуются, только если Король на «троне»). Король может быть пойман с помощью «трона» (окружённый с трёх сторон фигурами противника, и с 4-й – «троном»).
 * * Чёрные выигрывают, когда берут в плен Короля. Если Король окружён, но королевские фишки следующим ходом могут прорвать окружение, Король ещё не считается захваченным.
 * * Белые выигрывают, когда Король достигает любой угловой «крепости» (в варианте с большим полем – до края доски). Простые фишки не имеют права заходить в «крепость», иначе играющий чёрными может четырьмя ходами заблокировать их и выиграть. К тому же, «крепость» может использоваться в качестве стены для зажатия фишки, поэтому для полной блокады требуется 4 фишки (всего 16). Теоретически, конечно, белые могут построить отдельную цитадель, закрыв Короля от атак и повторяя ходы внутри неё, но такое построение означает проигрыш, поскольку в ней нет выхода.
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
