package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class ToggleChangedEvent extends BaseEvent {

    public ToggleChangedEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
