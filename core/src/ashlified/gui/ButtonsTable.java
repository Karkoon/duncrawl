package ashlified.gui;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.LookingDirectionComponent;
import ashlified.entitycomponentsystem.components.MovingDirectionComponent;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.signals.TurnEndSignal;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

class ButtonsTable extends Table {

    private TurnEndSignal turnEndSignal;
    private PositionComponent currentPosition;
    private Entity controlledEntity;

    ButtonsTable(final Skin skin, Engine engine, TurnEndSignal turnEndSignal) {
        this.controlledEntity = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
        final MovingDirectionComponent directionComponent = controlledEntity.getComponent(MovingDirectionComponent.class);
        final LookingDirectionComponent lookingDirectionComponent = controlledEntity.getComponent(LookingDirectionComponent.class);
        currentPosition = controlledEntity.getComponent(PositionComponent.class);
        this.turnEndSignal = turnEndSignal;
        Button forwardButton = new Button(skin);
        forwardButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                moveIn(directionComponent.getDirection());
                return false;
            }
        });
        Button backwardButton = new Button(skin);
        backwardButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                moveIn(CardinalDirection.oppositeOf(directionComponent.getDirection()));
                return false;
            }
        });
        Button rightButton = new Button(skin);
        rightButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                directionComponent.setDirection(CardinalDirection.rightOf(directionComponent.getDirection()));
                lookingDirectionComponent.setLookingDirection(directionComponent.getDirection().value.cpy());
                return false;
            }
        });
        Button leftButton = new Button(skin);
        leftButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                directionComponent.setDirection(CardinalDirection.leftOf(directionComponent.getDirection()));
                lookingDirectionComponent.setLookingDirection(directionComponent.getDirection().value.cpy());
                return false;
            }
        });
        Button inventoryButton = new Button(skin);
        inventoryButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getStage().addActor(new InventoryWindow(skin));
                return false;
            }
        });
        int buttonSize = 140;
        add();
        add(forwardButton).width(buttonSize).height(buttonSize);
        row();
        add(leftButton).width(buttonSize).height(buttonSize);
        add(backwardButton).width(buttonSize).height(buttonSize);
        add(rightButton).width(buttonSize).height(buttonSize);
        add().width(100);
        add(inventoryButton);
    }

    private void moveIn(CardinalDirection direction) {
        DungeonSection currentSection = currentPosition.getOccupiedSection();
        DungeonSection adjacentSection = currentSection;
        if (currentSection.getConnection(direction) != null) {
            adjacentSection = currentSection.getConnection(direction).getToNode();
        }
        if (adjacentSection.getOccupyingEntities().size() <= 0) {
            moveToSection(adjacentSection, currentPosition);
        }
    }

    private void moveToSection(DungeonSection section, PositionComponent currentPosition) {
        currentPosition.getOccupiedSection().getOccupyingEntities().remove(controlledEntity);
        currentPosition.setOccupiedSection(section);
        currentPosition.getPosition().set(section.getPosition().x, 6.5f, section.getPosition().z);
        section.addOccupyingObject(controlledEntity);
        turnEndSignal.dispatch(null);
    }
}