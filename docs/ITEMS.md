# ITEMS.md

Format per ТЗ section 55, for legendary/unique items; regular items get a
short entry too for now since the catalogue is still tiny.

---

## Chronoscope

**Rarity:** Uncommon
**Type:** Tool / key item
**Abilities:** Channel for 3s (60 ticks) to cycle the active Echo of the
layer around the player (see GAME_DESIGN.md 2.3). Cannot be used mid-combat-committed
(the channel is interrupted like any other `use` item if the player is hit —
inherited from vanilla `LivingEntity` behaviour, no extra code needed).
**Passive:** None.
**Special Effect:** N/A — utility item, not combat gear.
**Crafting:** *Not yet defined* — planned to require materials from the
Prologue quest chain (`prologue_chronoscope`), not purchasable/craftable
freely.
**Required Boss:** None (starting-area item).
**Lore:** Given by the Elder Ghost in the ruined surface village; a
fragment of the pre-Rupture court's own time-viewing instruments.

---

## Crown of Roots

**Name:** Crown of Roots
**Rarity:** Epic
**Type:** Regalia (accessory, see GAME_DESIGN.md 2.4)
**Abilities:** None active.
**Passive:** `ROOT_VITALITY` — regen + Abyssal Pressure resistance on
Layers 1-2 (exact numbers TBD once the Regalia slot-container from
ARCHITECTURE.md 3.4 is implemented — currently the item exists and carries
its effect enum, but nothing consumes it yet).
**Special Effect:** Unlocks the Void Echo on Layer 1 once worn (planned,
depends on EchoManager — PHASE 10).
**Set:** N/A (Regalias are not a traditional armor set).
**Crafting:** Not craftable — boss drop only.
**Required Boss:** King of Roots (guaranteed drop).
**Required Materials:** N/A.
**Upgrade Path:** Corrupted variant planned for Endgame Ancient Trials
(GAME_DESIGN.md 9) — not yet implemented.
**Lore:** The literal crown worn by the King of Roots at the moment of the
Rupture.

---

## Root-Ash Stone (block)

Tier-1 building block for the Layer 1 "Roots" region. Currently a plain
Block with no special behaviour — decorative/building-block variants
(stairs, slabs, walls) are a straightforward follow-up once the base
block's look is finalized with real art (current texture is a placeholder,
see docs/ARCHITECTURE.md note on assets).
