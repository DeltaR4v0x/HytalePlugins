package com.fancyinnovations.fancycore.api;

import com.fancyinnovations.fancycore.api.events.service.EventService;
import com.fancyinnovations.fancycore.api.moderation.PunishmentService;
import com.fancyinnovations.fancycore.api.moderation.PunishmentStorage;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.api.player.FancyPlayerStorage;
import de.oliver.fancyanalytics.logger.ExtendedFancyLogger;
import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.ScheduledExecutorService;

public interface FancyCore {

    static FancyCore get() {
        return InstanceHolder.getInstance();
    }

    ExtendedFancyLogger getFancyLogger();

    FancyCoreConfig getConfig();

    ScheduledExecutorService getThreadPool();

    EventService getEventService();

    @ApiStatus.Internal
    FancyPlayerStorage getPlayerStorage();
    FancyPlayerService getPlayerService();

    @ApiStatus.Internal
    PunishmentStorage getPunishmentStorage();
    PunishmentService getPunishmentService();


    @ApiStatus.Internal
    class InstanceHolder {
        private static FancyCore instance;

        @ApiStatus.Internal
        public static FancyCore getInstance() {
            return instance;
        }

        @ApiStatus.Internal
        public static void setInstance(FancyCore fancyCore) {
            if (instance != null) {
                throw new IllegalStateException("FancyCore instance is already set!");
            }

            instance = fancyCore;
        }
    }
}
