# SUNDERED DEPTHS — Technical Design Document

## 1. Стек

| Компонент | Выбор | Почему |
|---|---|---|
| Minecraft | 1.21.1 | Самая зрелая экосистема модов/доков на сегодня (16000+ модов на NeoForge для этой версии); последняя патч-версия 1.21.11 существует, но инструментарий и мод-совместимость для неё ещё не так обкатаны — для проекта такого масштаба это критично. |
| Mod loader | Forge 52.x (1.21.1) | Выбран по запросу пользователя: TLauncher + Forge + OptiFine. NeoForge-моды на Forge не работают, поэтому проект перенесён на Forge. |
| Java | 21 | Обязательный минимум для 1.21.x/NeoForge. |
| Build | Gradle 8.8 + ForgeGradle 6 | Стандарт для Forge 1.21.1. |
| Data | Datapack-driven где возможно (loot tables, recipes, worldgen) + кастомные Codec-сериализуемые системы для боссов/квестов | Меньше хардкода, легче балансировать без перекомпиляции. |
| Networking | Forge SimpleImpl / Channel | Родной сетевой слой Forge. |

## 2. Структура пакетов (com.sundereddepths.mod)

```
core        — точка входа, общая конфигурация мода, константы
registry    — все DeferredRegister'ы (единая точка регистрации)
blocks      — кастомные блоки + block entities
items       — оружие, броня, аксессуары, расходники, артефакты
entities    — базовые классы существ (не боссы), AI goals
bosses      — фреймворк боссов: BossEntity, PhaseController, BossArena
world       — dimension/chunk generator для Ярусов Бездны
biomes      — биомы и их фичи (features, placed features)
structures  — jigsaw-структуры (руины, данжи как структуры верхнего уровня)
dungeons    — логика внутреннего наполнения данжей (комнаты, ловушки, лут-циклы)
quests      — движок квестов (данные + прогресс игрока)
npc         — NPC-сущности, диалоги, торговля
combat      — урон, статус-эффекты, элементальные взаимодействия
abilities   — активные/пассивные способности (Хроновизор, Регалии, классы)
loot        — кастомные loot conditions/functions (редкость, эхо-зависимость)
recipes     — кастомные типы крафта (многокомпонентные, кузница, алтарь)
effects     — MobEffect'ы (Давление Бездны, Порча Пустоты, эхо-баффы)
events      — обработчики NeoForge событий (мировые события, эхо-переключение)
gui         — экраны (здоровье босса, характеристики, квесты, коллекции)
network     — Payload'ы клиент<->сервер
data        — Codec'и, SavedData (прогресс игрока/мира), data generation
config      — ModConfigSpec (баланс, тогглы сложности)
client      — рендереры, модели, звук, HUD-оверлеи
server      — серверная конфигурация, command'ы для отладки/GM
```

Правило: ни один класс не должен превышать ~300 строк. Boss-логика режется
на PhaseController + отдельные Attack-классы (Strategy pattern), а не один
God-class на 2000 строк.

## 3. Ключевые системы и их API-контракты

### 3.1 Registry (core/registry)
Единый `ModRegistries` держит все `DeferredRegister<T>`. Каждый под-модуль
(items/blocks/entities/bosses) регистрирует туда свои объекты через
статические `DeferredHolder`, регистрация вызывается из `SundeteredDepthsMod`
конструктора через `IEventBus`.

### 3.2 Boss Framework (bosses/)
```java
public abstract class AbstractBossEntity extends Monster {
    protected final PhaseController phases;
    protected BossArena arena; // может быть null для мини-боссов
    ...
    protected abstract List<BossPhase> definePhases();
}

public interface BossPhase {
    double hpThreshold(); // при каком % HP активна
    void onEnter(AbstractBossEntity boss);
    void tick(AbstractBossEntity boss);
    List<BossAttack> attacks();
}

public interface BossAttack {
    boolean canUse(AbstractBossEntity boss, LivingEntity target);
    void telegraph(AbstractBossEntity boss); // визуальное предупреждение
    void execute(AbstractBossEntity boss, LivingEntity target);
    int cooldownTicks();
}
```
Это даёт: любой новый босс = класс + список фаз/атак, без копипасты
инфраструктуры (health bar, телеграфирование, смена арены).

### 3.3 Эхо-система (world/ + effects/)
Эхо яруса хранится не как отдельное измерение (это взорвало бы генерацию
и производительность), а как **per-chunk blockstate/entity swap слой**:
`EchoManager` (data/) хранит текущее активное эхо на регион (SavedData),
при переключении подменяет предрегистрированные структуры блоков в радиусе
и деспавнит/спавнит соответствующий набор мобов. Подробности — при
реализации PHASE 10.

### 3.4 Регалии (abilities/)
Аксессуарные слоты реализуются через Curios API как soft-dependency, либо
собственный минимальный slot-container, если хотим ноль внешних зависимостей
(решение по умолчанию — свой контейнер, чтобы не тащить чужой мод в
зависимости; обсуждаемо).

### 3.5 Квесты (quests/)
Data-driven через JSON (datapack), Codec на `QuestDefinition`, прогресс —
per-player `SavedData` capability.

## 4. Производительность

- Боссы: AI-тики через собственный `Brain`-подобный не тяжелее ванильных
  мобов; тяжёлые VFX только клиент-сайд, партиклы через `ParticleOptions`
  с ограничением количества на тик.
- World-gen: Ярусы Бездны — НЕ бесконечные измерения по X/Z, а ограниченная
  область (border) на dimension, чтобы не тратить чанки впустую — игрок
  прогрессирует вертикально, не горизонтально.
- Entity count: жёсткий кап мобов на chunk через `LevelEvent`/спавн-условия.

## 5. Multiplayer-масштабирование

`BossScalingHandler` (bosses/) слушает вход/выход игроков в радиус арены,
пересчитывает HP-множитель и включает доп.атаки при 3-4 игроках согласно
таблице в BALANCE.md.

## 6. Ограничение на этот чат / порядок работы

Согласно плану PHASE 1-17 из исходного ТЗ, в этом ответе реализуется:
**PHASE 1 (архитектура/сборка проекта)** + минимальный рабочий срез
**PHASE 2 (core systems)** — регистрация, один блок, один предмет, одна
базовая сущность и один упрощённый Boss (proof of concept на фреймворке
из 3.2), чтобы проект реально собирался и запускался. Дальнейшие PHASE
добавляются в следующих итерациях этого же проекта (я продолжу без
дополнительных вопросов, просто говори "продолжай" / "дальше").
