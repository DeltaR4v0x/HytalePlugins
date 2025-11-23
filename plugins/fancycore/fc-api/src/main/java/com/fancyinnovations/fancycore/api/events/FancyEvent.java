package com.fancyinnovations.fancycore.api.events;

import com.fancyinnovations.fancycore.api.FancyCore;

/**
 * The base class for all events in the FancyCore system.
 */
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

    /**
     * Checks if this event is cancellable.
     *
     * @return true if the event is cancellable, false otherwise
     */
    public boolean isCancellable() {
        return isCancellable;
    }

    /**
     * Checks if this event has been cancelled.
     *
     * @return true if the event is cancelled, false otherwise
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Cancels this event if it is cancellable.
     */
    public void cancel() {
        if (!isCancellable) {
            throw new UnsupportedOperationException("This event cannot be cancelled.");
        }

        this.cancelled = true;
    }

    /**
     * Gets the timestamp when this event was fired.
     *
     * @return the timestamp in milliseconds
     */
    public long firedAt() {
        return firedAt;
    }
}
