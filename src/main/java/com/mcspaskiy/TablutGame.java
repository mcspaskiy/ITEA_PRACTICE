package com.mcspaskiy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcspaskiy.db.DbConnectionParams;
import com.mcspaskiy.db.DbService;
import com.mcspaskiy.io.AssetHolder;
import com.mcspaskiy.io.IOService;
import com.mcspaskiy.model.Board;
import com.mcspaskiy.multiplayer.GameClient;

import java.sql.Connection;

import static com.mcspaskiy.utils.Constants.*;

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
    private Stage stage;
    private Viewport viewport;
    private AssetHolder assetHolder;
    private String playerName;
    private GameClient client;
    private Camera camera;
    private Board board;
    private int queue;
    private boolean myTurn;
    private Label.LabelStyle label1Style;
    private Label label1;
    private Color textColor = Color.BLACK;
    private int textPosX;
    private boolean gameStarted;
    private Connection connection;

    public TablutGame(String playerName, String ip) {
        this.playerName = playerName;
        this.client = new GameClient(ip, PORT);
        this.client.execute(this::onReceiveResponseFromServer);
        DbConnectionParams dbConnectionParams = IOService.getInstance().readDbConnectionParams();
        this.connection = DbService.getInstance().getConnection(dbConnectionParams);
    }

    private void updateTurnText(String msg) {
        DbService.getInstance().saveHistoryMovement(connection, msg);
        if (myTurn) {
            label1.setText(playerName + " your turn...");
        } else {
            label1.setText(playerName + " wait for opponent move...");
        }
    }

    public void onPlayerMovePieceCallback(String command, String playerName, int prevX, int prevY, int newX, int newY, boolean playerTurn) {
        this.myTurn = !this.myTurn;
        updateTurnText(prevX + " " + prevY + " " + newX + " " + newY);

        StringBuilder sb = new StringBuilder();
        sb.append(prevX)
                .append(" ")
                .append(prevY)
                .append(" ")
                .append(newX)
                .append(" ")
                .append(newY);
        client.processPlayerMovement(sb.toString());
    }

    public void onReceiveResponseFromServer(String response) {
        if ("start".equals(response)) {
            gameStarted = true;
            if (board != null) {
                board.beginGame(true);
                label1.setText(playerName + " your turn");
            }
            return;
        }

        String[] responseArray = response.split(" ");
        if (responseArray.length == 4) {
            board.moveItemByCoords(Integer.valueOf(responseArray[0]), Integer.valueOf(responseArray[1]),
                    Integer.valueOf(responseArray[2]), Integer.valueOf(responseArray[3]));
            myTurn = !myTurn;
            updateTurnText(response);
        } else {
            queue = Integer.valueOf(responseArray[0]);
            if (queue == 0) {
                textColor = Color.BLACK;
                textPosX = 8;
                myTurn = true;
            } else {
                textColor = Color.WHITE;
                textPosX = (SCREEN_WITH - CELL_SIZE * 11) / 2 + CELL_SIZE * 11;
                myTurn = false;
            }
        }
    }

    @Override
    public void create() {
        assetHolder = IOService.getInstance().getOrloadAssets();
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WITH, SCREEN_HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        board = new Board(stage, playerName, queue, this::onPlayerMovePieceCallback);
        board.beginGame(gameStarted);
        label1Style = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont();
        label1Style.font = myFont;
        label1Style.fontColor = textColor;
        label1 = new Label(playerName, label1Style);
        label1.setSize((SCREEN_WITH - CELL_SIZE * 11) / 2, 96);
        label1.setPosition(textPosX, Gdx.graphics.getHeight() - 96);
        label1.setAlignment(Align.center);

        if (!myTurn) {
            label1.setText(playerName + " waiting for opponent move...");
        } else {
            label1.setText(playerName + " waiting for opponent connect...");
        }
        stage.addActor(label1);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(assetHolder.getBoardImage(), 0, 0, SCREEN_WITH, SCREEN_HEIGHT);
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void dispose() {
        IOService.getInstance().unloadAssets();
        stage.dispose();
    }
}
