package dev.micazi.laravel_flutter_pusher_plus.platform_messages;

public class BindInstanceMessage extends InstanceMessage {
    private String eventName;
    private String channelName;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
