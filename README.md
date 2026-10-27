# Sundered Depths — Forge 1.21.1

Мод для Minecraft **1.21.1 на Forge** (работает с TLauncher). Дизайн: `docs/GAME_DESIGN.md`, архитектура: `docs/ARCHITECTURE.md`.

## Как получить .jar (без установки Java и Gradle)

1. Зарегистрируйся на github.com и создай новый репозиторий.
2. Загрузи в него ВСЁ содержимое этой папки, включая скрытую папку `.github`.
3. Открой вкладку **Actions** -> workflow **Build mod jar** -> дождись зелёной галочки (3-10 минут).
4. Открой завершённый запуск, внизу в разделе **Artifacts** скачай `sundereddepths-jar`. Внутри будет `sundereddepths-0.1.0.jar`.

Если сборка красная, открой её лог и пришли мне текст ошибки.

## Установка в TLauncher

1. В TLauncher выбери версию **1.21.1 с Forge** и один раз запусти игру, чтобы создались папки.
2. Положи `sundereddepths-0.1.0.jar` в папку `mods` (Win+R -> `%appdata%\.minecraft\mods`).
3. OptiFine для 1.21.1 клади туда же. Если игра падает, сначала убери OptiFine и проверь мод без него.
4. Запусти игру. В креативе будет вкладка **Sundered Depths**.

## Что есть в этой версии

King of Roots (босс, 3 фазы), Root Wraith (моб), Chronoscope, Crown of Roots, эффект Abyssal Pressure, блок Root-Ash Stone, загрузчик квестов, сохранение прогресса игрока. Остальное из GDD ещё не реализовано.

## Важно

Проект не компилировался в среде, где он написан (нет интернета), поэтому первая сборка может показать ошибки. Если Gradle не находит `forge_version=52.1.0`, возьми номер сборки 1.21.1 на files.minecraftforge.net и поменяй его в `gradle.properties`.
