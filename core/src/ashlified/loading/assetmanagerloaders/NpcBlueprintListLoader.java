package ashlified.loading.assetmanagerloaders;

import ashlified.entitycomponentsystem.entityinitializers.EnemyNPCEntitiesInitializer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

/**
 * Loads blueprints of NPCs.
 */
public class NpcBlueprintListLoader extends AsynchronousAssetLoader<NpcBlueprintListLoader.EnemyNPCBlueprintList, NpcBlueprintListLoader.EnemyNPCBlueprintListLoaderParameter> {

    private EnemyNPCBlueprintList blueprints;

    public NpcBlueprintListLoader(FileHandleResolver resolver) {
        super(resolver);
        blueprints = new EnemyNPCBlueprintList();
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, EnemyNPCBlueprintListLoaderParameter parameter) {
        blueprints = null;
        blueprints = new EnemyNPCBlueprintList();
        FileHandle[] files = getBlueprintFiles(file);
        if (files != null) {
            Json json = new Json();
            for (FileHandle blueprintFileHandle : files) {
                EnemyNPCEntitiesInitializer.EnemyNPCBlueprint blueprint = json.fromJson(EnemyNPCEntitiesInitializer.EnemyNPCBlueprint.class, blueprintFileHandle);
                blueprints.getEnemyNPCBlueprints().add(blueprint);
            }
        }
    }

    private FileHandle[] getBlueprintFiles(FileHandle dir) {
        String blueprintFileSuffix = "bp";
        return dir.list(blueprintFileSuffix);
    }

    @Override
    public EnemyNPCBlueprintList loadSync(AssetManager manager, String fileName, FileHandle file, EnemyNPCBlueprintListLoaderParameter parameter) {
        EnemyNPCBlueprintList blueprints = this.blueprints;
        this.blueprints = null;
        return blueprints;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, EnemyNPCBlueprintListLoaderParameter parameter) {
        return null;
    }

    static class EnemyNPCBlueprintListLoaderParameter extends AssetLoaderParameters<EnemyNPCBlueprintList> {

    }

    public static class EnemyNPCBlueprintList {

        private ArrayList<EnemyNPCEntitiesInitializer.EnemyNPCBlueprint> enemyNPCBlueprints = new ArrayList<>();

        public ArrayList<EnemyNPCEntitiesInitializer.EnemyNPCBlueprint> getEnemyNPCBlueprints() {
            return enemyNPCBlueprints;
        }
    }
}