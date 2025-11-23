package com.fancyinnovations.fancycore.api.events;

import com.fancyinnovations.fancycore.api.FancyCore;

public abstract class FancyEvent {

    private final long firedAt;
    private final boolean isCancellable;
    private boolean cancelled;

    public FancyEvent() {
        this.firedAt = System.currentTimeMillis();
        this.isCancellable = true;
        this.cancelled = false;
    }

    public FancyEvent(boolean isCancellable) {
        this.firedAt = System.currentTimeMillis();
        this.isCancellable = isCancellable;
        this.cancelled = false;
    }

    /**
     * Fires this event using the FancyCore EventService.
     *
     * @return true if the event was not cancelled, false otherwise
     */
    public boolean fire()  {
        return FancyCore.get().getEventService().fireEvent(this);
    }

    public boolean isCancellable() {
        return isCancellable;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public long firedAt() {
        return firedAt;
    }
}
