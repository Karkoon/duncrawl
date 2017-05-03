package ashlified.dungeon;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by @Karkoon on 18.08.2016.
 * Provides dungeon in a form of a json from a third-party server (REST).
 * https://aorioli.github.io/procedural/
 * Resulted from a session of blind programming and googling.
 */
public class HTTPDungeonProvider implements DungeonProvider {

    public Dungeon getNewDungeon(int seed, int size, int rooms) {
        return getNewDungeonWithHTTP(seed, size, rooms);
    }

    private static Dungeon getNewDungeonWithHTTP(int seed, int size, int rooms) {
        Dungeon dungeon = null;
        try {
            URL url = new URL("https://procedural-service.herokuapp.com/dungeon/generate?size="
                    + size + "&rooms=" + rooms + "&seed=" + seed);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            Json json = new Json();
            dungeon = json.fromJson(Dungeon.class, br);
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dungeon == null) throw new NullPointerException("wtf");
        return dungeon;
    }
}
