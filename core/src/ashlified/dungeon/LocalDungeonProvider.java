package ashlified.dungeon;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

public class LocalDungeonProvider implements DungeonProvider {

    @Override
    public Dungeon getNewDungeon(int seed, int size, int rooms) {
        Json json = new Json();
        json.setSerializer(Dungeon.class, new DungeonSerializer());
        json.setSerializer(DungeonSection.class, new DungeonSerializer.DungeonSectionSerializer());
        return json.fromJson(Dungeon.class, Gdx.files.getFileHandle("dungeon2.json", Files.FileType.Internal));
    }
}
