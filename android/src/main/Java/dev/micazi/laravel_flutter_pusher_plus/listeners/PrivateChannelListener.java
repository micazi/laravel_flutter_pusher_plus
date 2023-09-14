package dev.micazi.laravel_flutter_pusher_plus.listeners;

import com.pusher.client.channel.PrivateChannelEventListener;

public class PrivateChannelListener extends EventChannelListener implements PrivateChannelEventListener {

    public PrivateChannelListener(String instanceId, boolean isLoggingEnabled) {
        super(instanceId, isLoggingEnabled);
    }

    @Override
    public void onSubscriptionSucceeded(String channelName) {
        this.onEvent(toPusherEvent(channelName, SUBSCRIPTION_SUCCESS_EVENT, null, null));
    }

    @Override
    public void onAuthenticationFailure(String message, Exception e) {
        onError(e);
    }
}
