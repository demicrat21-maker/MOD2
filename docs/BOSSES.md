# BOSSES.md

Format per ТЗ section 53. Filled in as each boss is implemented; only
King of Roots exists in code so far (see PhaseController/AbstractBossEntity
in ARCHITECTURE.md 3.2 for the shared framework every entry below uses).

---

## Boss Name: King of Roots

**Lore:** The first monarch of the Root Kingdom, frozen in the Echo of the
Rupture at the exact moment his throne root swallowed his court. He does
not know he is dead.

**Region:** Layer 1-2 ("Корни" / "Шахты Забвения")

**Difficulty:** Tier 1 (first major boss)

**Recommended Gear:** Tier 1-2 (Странник/Корни), any starting weapon

**Arena:** *Not yet implemented (BossArena system is a PHASE 6 deliverable).*
Planned: a collapsed throne room whose root-pillars grow denser each phase,
narrowing the fightable space.

**Phase 1 — Awakening (100%-60% HP):**
- `RootSlamAttack` only: a telegraphed (1.2s) overhead slam, 14 dmg + knockback,
  4-block range.

**Phase 2 — Full Awakening (60%-25% HP):**
- Adds `RootSpikeVolleyAttack`: a ranged line of ground spikes (8 dmg),
  telegraphed 1s, forces lateral dodging instead of tanking.
- Roar + particle burst on entry.

**Phase 3 — Last Root (below 25% HP):**
- Same attack pool as Phase 2, but +30% movement speed and +25% attack
  damage (desperation buff).
- *Planned, not yet implemented:* arena root-growth hazard, visual "last
  form" — both depend on the BossArena/model systems (PHASE 6).

**Special Mechanics:** None yet beyond phase-scaling attacks — Echo-switching
interaction (fighting him across Echoes) is planned per GAME_DESIGN.md 2.3
but requires EchoManager (PHASE 10) first.

**Weaknesses:** None yet defined (no elemental system wired to bosses yet —
PHASE 4/Combat).

**Resistances:** None yet defined.

**Summons:** None yet (planned for Phase 2 per the GDD's phase template —
requires minion mob roster for Layer 1 beyond Root Wraith).

**Drops:** `sundereddepths:crown_of_roots` (guaranteed), 2-4x
`sundereddepths:root_ash_stone`. See `loot_table/entities/king_of_roots.json`.

**Unique Weapon:** *Not yet designed.*

**Unique Armor:** *Not yet designed.*

**Secret Drop:** *Not yet designed.*

**Achievements:** *Not yet implemented (advancements system — PHASE 13/docs pass).*

**Rematch:** Planned for Endgame (GAME_DESIGN.md 9) as an Echo of the Void
remake — not yet implemented.

---

*(Remaining ~19 bosses per GAME_DESIGN.md section 5 to be added as each is
implemented — Ash-Court King, Crystal King, Sky King, Frost King, 4 regional
mini-bosses, 6 hidden bosses, 4 endgame remakes, and the Original King final
boss.)*
