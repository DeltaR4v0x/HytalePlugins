package com.fancyinnovations.fancycore.player.storage.json;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerStorage;
import com.fancyinnovations.fancycore.main.FancyCorePlugin;
import com.fancyinnovations.fancycore.player.FancyPlayerImpl;
import de.oliver.fancyanalytics.logger.properties.StringProperty;
import de.oliver.fancyanalytics.logger.properties.ThrowableProperty;
import de.oliver.jdb.JDB;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FancyPlayerJsonStorage implements FancyPlayerStorage {

    private static final String DATA_DIR_PATH = "plugins/FancyCore/data/players";
    private static final File DATA_DIR = new File(DATA_DIR_PATH);
    private final JDB jdb;

    public FancyPlayerJsonStorage() {
        this.jdb = new JDB(DATA_DIR_PATH);
    }

    @Override
    public void savePlayer(FancyPlayer player) {
        if (!(player instanceof FancyPlayerImpl fpImpl)) {
            FancyCorePlugin.get().getFancyLogger().warn("Only real player objects can be saved");
            return;
        }

        JsonFancyPlayer jsonFancyPlayer = JsonFancyPlayer.from(fpImpl);
        try {
            jdb.set(fpImpl.getUUID().toString(), jsonFancyPlayer);
        } catch (IOException e) {
            FancyCorePlugin.get().getFancyLogger().error(
                    "Failed to save FancyPlayer",
                    StringProperty.of("uuid", jsonFancyPlayer.uuid()),
                    ThrowableProperty.of(e)
            );
        }
    }

    @Override
    public FancyPlayer loadPlayer(UUID uuid) {
        try {
            JsonFancyPlayer jsonFancyPlayer = jdb.get(uuid.toString(), JsonFancyPlayer.class);
            return jsonFancyPlayer.toFancyPlayer();
        } catch (IOException e) {
            FancyCorePlugin.get().getFancyLogger().error(
                    "Failed to load FancyPlayer",
                    StringProperty.of("uuid", uuid.toString()),
                    ThrowableProperty.of(e)
            );
        }
        return null;
    }

    @Override
    public void deletePlayer(UUID uuid) {
        jdb.delete(uuid.toString());
    }

    @Override
    public int countPlayers() {
        return jdb.countDocuments("");
    }
}
