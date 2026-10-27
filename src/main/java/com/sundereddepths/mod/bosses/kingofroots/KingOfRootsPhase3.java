package com.sundereddepths.mod.bosses.kingofroots;

import com.sundereddepths.mod.bosses.AbstractBossEntity;
import com.sundereddepths.mod.bosses.BossAttack;
import com.sundereddepths.mod.bosses.BossPhase;
import com.sundereddepths.mod.bosses.kingofroots.attacks.RootSlamAttack;
import com.sundereddepths.mod.bosses.kingofroots.attacks.RootSpikeVolleyAttack;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;

/**
 * Phase 3 (below 25% HP): "Last Root" desperation phase. Matches ТЗ
 * section 6's "новая форма; усиление атак; уникальная механика" — here
 * the King's attack speed and damage both scale up as a simple first-pass
 * implementation of "усиление атак"; a true "new form" (visual/model swap)
 * is a client-side addition slated for PHASE 6 once boss models exist.
 */
public class KingOfRootsPhase3 implements BossPhase {

    private final List<BossAttack> attacks = List.of(new RootSlamAttack(), new RootSpikeVolleyAttack());
    private boolean buffApplied = false;

    @Override
    public double hpThreshold() {
        return 0.25;
    }

    @Override
    public void onEnter(AbstractBossEntity boss) {
        if (!buffApplied) {
            var speedAttr = boss.getAttribute(Attributes.MOVEMENT_SPEED);
            var dmgAttr = boss.getAttribute(Attributes.ATTACK_DAMAGE);
            if (speedAttr != null) speedAttr.setBaseValue(speedAttr.getBaseValue() * 1.3);
            if (dmgAttr != null) dmgAttr.setBaseValue(dmgAttr.getBaseValue() * 1.25);
            buffApplied = true;
        }
    }

    @Override
    public void tick(AbstractBossEntity boss) {
        // Placeholder for arena collapse hazard (ТЗ п.6: "разрушение арены"),
        // added once BossArena exists (PHASE 6).
    }

    @Override
    public List<BossAttack> attacks() {
        return attacks;
    }

    @Override
    public String id() {
        return "phase_3_last_root";
    }
}
