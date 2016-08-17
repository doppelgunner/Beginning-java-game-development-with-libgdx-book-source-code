package fiftytwopickup;

import base_B.BaseActor;
import base_B.BaseGame;
import base_B.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 05/08/2016.
 */
public class GameScreen extends BaseScreen {

    private BaseActor background;

    private ArrayList<Card> cardList;
    private ArrayList<Pile> pileList;

    private BaseActor glowEffect;
    private float hintTimer;

    //game world dimensions
    final int mapWidth = 800;
    final int mapHeight = 600;

    private boolean hint;

    public GameScreen(BaseGame g) {
        super(g);
    }

    @Override
    public void create() {

        //background
        background = new BaseActor();
        background.setTexture(new Texture(Gdx.files.internal("assets/chapter6/fiftytwopickup/felt.jpg")));
        mainStage.addActor(background);

        //piles
        pileList = new ArrayList<Pile>();
        Texture pileTex = new Texture(Gdx.files.internal("assets/chapter6/fiftytwopickup/cardBack.png"));
        for (int n = 0; n < 4; n++) {
            Pile pile = new Pile();
            pile.setTexture(pileTex);
            pile.setWidth(120);
            pile.setHeight(140);
            pile.setOriginCenter();
            pile.setPosition(70 + 180 * n, 400);
            pile.setRectangleBoundary();
            pileList.add(pile);
            mainStage.addActor(pile);
        }

        //cards
        cardList = new ArrayList<>();
        for (int r = 0; r < Card.rankNames.length; r++) {
            for (int s = 0; s < Card.suitNames.length; s++) {
                Card card = new Card(Card.rankNames[r], Card.suitNames[s]);
                String filename = "assets/chapter6/fiftytwopickup/card" + Card.suitNames[s] + Card.rankNames[r] + ".png";
                card.setTexture(new Texture(Gdx.files.internal(filename)));
                card.setWidth(80);
                card.setHeight(100);
                card.setOriginCenter();
                card.setRectangleBoundary();

                card.addListener(new InputListener() {

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.log("touchDown", "(x,y): (" + x + "," + y + ")");
                        Gdx.app.log("stageXY", "(x,y): (" + event.getStageX() + "," + event.getStageY() + ")");

                        if (!card.draggable) return false;

                        card.setZIndex(1000);
                        card.offsetX = x;
                        card.offsetY = y;
                        card.originalX = event.getStageX();
                        card.originalY = event.getStageY();

                        return true;
                    }

                    @Override
                    public void touchDragged(InputEvent event, float x, float y, int pointer) {
                        if (!card.draggable) return;

                        card.moveBy(x - card.offsetX, y - card.offsetY);
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        boolean overPile = false;

                        for (Pile pile : pileList) {
                            if (card.overlaps(pile, false)) {
                                overPile = true;
                                if (card.getRankIndex() == pile.getRankIndex() + 1 && card.getSuit().equals(pile.getSuit())) {
                                    float targetX = pile.getX() + pile.getOriginX() - card.getOriginX();
                                    float targetY = pile.getY() + pile.getOriginY() - card.getOriginY();
                                    card.draggable = false;
                                    card.addAction(Actions.moveTo(targetX,targetY,0.5f));
                                    pile.addCard(card);
                                    return;
                                }

                            }

                        }

                        if (overPile) {
                            card.addAction(Actions.moveTo(card.originalX - card.offsetX, card.originalY - card.offsetY, 0.5f));
                        }

                        if (card.getX() < 0 ) {
                            card.setX(0);
                        }

                        if (card.getX() + card.getWidth() > mapWidth) {
                            card.setX(mapWidth - card.getWidth());
                        }

                        if (card.getY() < 0 ) {
                            card.setY(0);
                        }

                        if (card.getY() + card.getWidth() > mapHeight) {
                            card.setY(mapHeight - card.getHeight());
                        }

                    }
                });

                cardList.add(card);
                mainStage.addActor(card);
                card.setZIndex(5);
            }
        }

        for (Card card : cardList) {
            if (card.getRank().equals("A")) {
                for (Pile pile : pileList) {
                    if (pile.isEmpty()) {
                        card.moveToOrigin(pile);
                        pile.addCard(card);
                        card.draggable = false;
                        break;
                    }
                }
            } else {

                //NEXT STEPS - animate the random position
                card.addAction(
                        Actions.moveTo(MathUtils.random(720), MathUtils.random(200), 1.5f)
                );

                //card.setPosition(MathUtils.random(720), MathUtils.random(200));
            }
        }

        glowEffect = new BaseActor();
        Texture glowTex = new Texture(Gdx.files.internal("assets/chapter6/fiftytwopickup/glowBlue.png"));
        glowEffect.setTexture(glowTex);
        glowEffect.setWidth(cardList.get(0).getWidth() * 1.5f);
        glowEffect.setHeight(cardList.get(0).getHeight() * 1.5f);
        glowEffect.setOriginCenter();
        glowEffect.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.fadeOut(0.5f),
                                Actions.fadeIn(0.5f)
                        )
                )
        );
        glowEffect.setVisible(false);
        mainStage.addActor(glowEffect);
        hintTimer = 0;
        hint = true;
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            hint = !hint;
            glowEffect.setVisible(false);
            hintTimer = 0;
        }

        if (hint) {
            hintTimer += dt;

            if (Gdx.input.isTouched()) {
                hintTimer = 0;
                glowEffect.setVisible(false);
            }

            //activate mechanic
            if (hintTimer > 3 && !glowEffect.isVisible()) {
                for (Card hintCard : cardList) {
                    if (hintCard.draggable) {
                        glowEffect.setVisible(true);
                        glowEffect.moveToOrigin(hintCard);
                        glowEffect.toFront();
                        hintCard.toFront();
                        break;
                    }
                }
            }
        }

    }
}
