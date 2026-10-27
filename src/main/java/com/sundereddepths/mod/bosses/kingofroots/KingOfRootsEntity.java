package com.sundereddepths.mod.bosses.kingofroots;

import com.sundereddepths.mod.bosses.AbstractBossEntity;
import com.sundereddepths.mod.bosses.BossPhase;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * King of Roots — the first King-in-Ash (GAME_DESIGN.md 5). Stuck in the
 * Echo of the Rupture at the moment his kingdom's roots swallowed his
 * throne. Three phases matching the GDD minimum, full BOSSES.md entry to
 * follow once BOSSES.md itself is written (PHASE 6/7).
 * <p>
 * This class is deliberately thin — see AbstractBossEntity for the shared
 * machinery. All the fight-specific content lives in the {@code phases}
 * sub-package as separate small classes, per ARCHITECTURE.md's "no
 * God-class" rule.
 */
public class KingOfRootsEntity extends AbstractBossEntity {

    public KingOfRootsEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 220.0)
                .add(Attributes.MOVEMENT_SPEED, 0.22)
                .add(Attributes.ATTACK_DAMAGE, 9.0)
                .add(Attributes.ARMOR, 8.0)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    protected List<BossPhase> definePhases() {
        return List.of(
                new KingOfRootsPhase1(),
                new KingOfRootsPhase2(),
                new KingOfRootsPhase3()
        );
    }
}
