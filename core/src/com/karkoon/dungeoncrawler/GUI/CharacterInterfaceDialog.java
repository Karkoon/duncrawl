package com.karkoon.dungeoncrawler.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.karkoon.dungeoncrawler.Characters.Character;
import com.karkoon.dungeoncrawler.Characters.Player;
import com.karkoon.dungeoncrawler.Items.Item;
import com.karkoon.dungeoncrawler.Items.Items;
import com.karkoon.dungeoncrawler.Statistics;
import com.karkoon.dungeoncrawler.Statistics.AttributeType;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Pc on 2016-09-09.
 */
class CharacterInterfaceDialog extends Dialog {

    private final static String TITLE = "Inventory";
    private final static NinePatchDrawable slotBg = new NinePatchDrawable(new NinePatch(new Texture(Gdx.files.internal("slotbg.9.png")))); //temporary
    private final static TextureRegionDrawable trashSlotBg = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("trash.png")))); //temporary
    private final float SLOT_SIZE;
    private final float RETURN_BUTTON_WIDTH;
    private final float RETURN_BUTTON_HEIGHT;
    private final Skin skin;
    private Character player;
    private Table slots;
    private Table usedSlots;
    private StatsTable statsTable;

    private ArrayList<Item> oldItems = new ArrayList<>();
    private DragAndDrop dnd;

    CharacterInterfaceDialog(Character player, final Skin skin, float viewportWidth, float viewportHeight) {
        super(TITLE, skin);
        SLOT_SIZE = viewportWidth / 20f;
        RETURN_BUTTON_HEIGHT = SLOT_SIZE / 2f;
        RETURN_BUTTON_WIDTH = SLOT_SIZE * 2f;
        setUpDragAndDrop();
        this.skin = skin;
        this.player = player;
        usedSlots = createUsedItemsTable();
        statsTable = new StatsTable(player.getStatistics(), skin);
        slots = createInventoryTable(skin, dnd);
        getContentTable().add(usedSlots);
        final Image image = new Image(new Texture(Gdx.files.internal("skelly.png")));
        getContentTable().add(image).size(viewportWidth / 4f, viewportHeight / 3f);
        getContentTable().add(statsTable);
        getContentTable().add(createTrashSlot()).size(SLOT_SIZE, SLOT_SIZE).pad(10f);
        getContentTable().row();
        getContentTable().add(slots).colspan(3);
        getButtonTable().add(createButton(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                remove();
            }
        })).size(RETURN_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT);
    }

    private void setUpDragAndDrop() {
        dnd = new DragAndDrop();
        dnd.setDragTime(0);
    }

    void update() {
        Stack<Item> newItems = new Stack<>();
        for (Item item : player.getItems()) {
            if (!oldItems.contains(item)) newItems.add(item);
        }
        addNewItemsToInventory(newItems);
    }

    private void addNewItemsToInventory(Stack<Item> newItems) {
        if (!newItems.isEmpty()) {
           for (Cell cell : slots.getCells()) {
               Container<Image> slot = (Container<Image>) cell.getActor();
               boolean slotIsOccupied = slot.hasChildren();
               if (!slotIsOccupied && !newItems.isEmpty()) {
                   Item item = newItems.pop();
                   oldItems.add(item);
                   slot.setActor(createImage(item.getDecal().getTextureRegion(), item));
               }
           }
        }
    }

    private Table createUsedItemsTable() {
        Table table = new Table(skin);
        for (int i = 0; i < Player.MAX_USED_ITEMS; i++) {
            Slot slot = new Slot(Items.baseTypes[i]);
            dnd.addSource(new UsedItemSlotSource(slot));
            dnd.addTarget(new UsedItemSlotTarget(slot));
            table.add(slot).size(SLOT_SIZE, SLOT_SIZE).pad(5f);
            table.row();
        }
        table.pack();
        return table;
    }

    private Table createInventoryTable(final Skin skin, DragAndDrop dnd) {
        Table table = new Table(skin);
        for (int i = 1; i <= Player.MAX_ITEMS; i++) {
            Slot slot = new Slot(Item.class);
            dnd.addSource(new ItemSlotSource(slot));
            dnd.addTarget(new ItemSlotTarget(slot));
            table.add(slot).size(SLOT_SIZE, SLOT_SIZE).pad(5f);
            if (i % 8 == 0) {
                table.row();
            }
        }
        table.pack();
        return table;
    }

    private Container<Image> createTrashSlot() {
        final Container<Image> slot = new Container<>();
        dnd.addTarget(new DragAndDrop.Target(slot) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                slot.setColor(Color.RED);
                return true;
            }

            @Override
            public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
                slot.setColor(Color.WHITE);
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Image payloadObject = (Image) payload.getObject();
                player.dropItem((Item) payloadObject.getUserObject(), player.getPositionSection());
                oldItems.remove(payloadObject.getUserObject());
            }
        });
        slot.background(trashSlotBg);
        slot.setTouchable(Touchable.enabled);
        return slot;
    }

    private Button createButton(EventListener listener) {
        Button button = new Button(skin);
        button.addListener(listener);
        return button;
    }

    private Image createImage(TextureRegion textureRegion, Object userObject) {
        Image image = new Image(textureRegion);
        image.setUserObject(userObject);
        return image;
    }

    private class UsedItemSlotTarget extends ItemSlotTarget {

        UsedItemSlotTarget(Slot slot) {
            super(slot);
        }

        @Override
        public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            super.drop(source, payload, x, y, pointer);
            player.putToUsedItems((Item) ((Image) payload.getObject()).getUserObject());
            statsTable.update();
        }
    }

    private class UsedItemSlotSource extends ItemSlotSource {

        UsedItemSlotSource(Slot slot) {
            super(slot);
        }

        @Override
        public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
            DragAndDrop.Payload payload = super.dragStart(event, x, y, pointer);
            if (payload != null) {
                player.removeFromUsedItems((Item) ((Image) payload.getObject()).getUserObject());
                statsTable.update();
            }
            return payload;
        }

        @Override
        public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
            super.dragStop(event, x, y, pointer, payload, target);
            if (target == null) {
                player.putToUsedItems((Item) ((Image) payload.getObject()).getUserObject());
                statsTable.update();
            }
        }
    }

    private class ItemSlotTarget extends DragAndDrop.Target {

        private Slot slot;

        ItemSlotTarget(Slot slot) {
            super(slot);
            this.slot = (Slot) getActor();
        }

        @Override
        public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            boolean slotAcceptsThisItemsType = slot.slotType.isInstance(((Image) payload.getObject()).getUserObject());
            boolean slotIsEmpty = slot.getActor() == null;
            if (!slotIsEmpty || !slotAcceptsThisItemsType) {
                return false;
            } else {
                getActor().setColor(Color.GOLD);
                return true;
            }
        }

        @Override
        public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
            getActor().setColor(Color.WHITE);
        }

        @Override
        public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            Image payloadsObject = (Image) payload.getObject();
            slot.setActor(payloadsObject);
            slot.getActor().setVisible(true);
        }

    }

    private class ItemSlotSource extends DragAndDrop.Source {

        final DragAndDrop.Payload payload = new DragAndDrop.Payload();
        private Slot slot;

        ItemSlotSource(Slot slot) {
            super(slot);
            this.slot = slot;
        }

        @Override
        public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
            if (slot.getActor() != null) {
                Image image = slot.getActor();
                image.setVisible(false);
                payload.setObject(image);
                payload.setDragActor(createDragActorFrom(image));
                dnd.setDragActorPosition(-payload.getDragActor().getWidth() / 2f, payload.getDragActor().getHeight() / 2f);
                return payload;
            } else return null;
        }

        @Override
        public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
            if (target != null && target.getActor() != slot) {
                slot.setActor(null);
            } else {
                slot.getActor().setVisible(true);
            }
        }

        private Actor createDragActorFrom(Image image) {
            Image dragActor = new Image(image.getDrawable());
            dragActor.setSize(image.getWidth(), image.getHeight());
            return dragActor;
        }

    }

    private static class Slot extends Container<Image> {

        Class<Item> slotType;

        Slot(Class<Item> slotType) {
            this.slotType = slotType;
            background(slotBg);
            setTouchable(Touchable.enabled);
        }
    }

    private static class StatsTable extends Table {

        StatsTable(Statistics statistics, Skin skin) {
            super(skin);
            for (AttributeType type : AttributeType.values()) {
                add(new StatLabel(skin, statistics.get(type)));
                row();
            }
            pack();
        }

        void update() {
            updateStatLabels();
        }

        private void updateStatLabels() {
            for (Cell cell : getCells()) {
                ((StatLabel) cell.getActor()).update();
            }
        }

        private static class StatLabel extends Label {

            private Statistics.Attribute attribute;

            StatLabel(Skin skin, Statistics.Attribute attribute) {
                super("", skin, "default-font", Color.LIGHT_GRAY);
                super.setFontScale(0.5f);
                this.attribute = attribute;
                update();
            }

            void update() {
                super.setText(attribute.getName() + ": " + Integer.toString(attribute.getValue()));
            }
        }
    }
}
