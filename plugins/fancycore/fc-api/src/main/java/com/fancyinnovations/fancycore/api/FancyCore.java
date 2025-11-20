package com.fancyinnovations.fancycore.api;

import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.api.player.FancyPlayerStorage;
import de.oliver.fancyanalytics.logger.ExtendedFancyLogger;

import java.util.concurrent.ScheduledExecutorService;

public interface FancyCore {

    ExtendedFancyLogger getFancyLogger();
    ScheduledExecutorService getThreadPool();

    FancyPlayerStorage getPlayerStorage();
    FancyPlayerService getPlayerService();

}
