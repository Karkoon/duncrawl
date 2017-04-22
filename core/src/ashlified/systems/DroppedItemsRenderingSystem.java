package ashlified.systems;

import ashlified.components.DecalComponent;
import ashlified.components.PositionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by karkoon on 22.04.17.
 */
public class DroppedItemsRenderingSystem extends IteratingSystem {

    private ComponentMapper<DecalComponent> decalMapper = ComponentMapper.getFor(DecalComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

    private DecalBatch batch;

    public DroppedItemsRenderingSystem(Family family, DecalBatch batch) {
        super(family);
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DecalComponent decalComponent = decalMapper.get(entity);
        Decal decal = decalComponent.getDecal();
        Vector3 position = positionMapper.get(entity).getPosition();
        updateAnimationPosition(decal, position);
        batch.add(decal);
    }

    private void updateAnimationPosition(Decal decal, Vector3 position) {
        decal.setPosition(position);
    }

}
