package dev.micazi.laravel_flutter_pusher_plus;

import android.util.Log;

import androidx.annotation.NonNull;

import dev.micazi.laravel_flutter_pusher_plus.platform_messages.InstanceMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * LaravelFlutterPusherPlugin
 */
public class LaravelFlutterPusherPlugin implements FlutterPlugin, MethodCallHandler {

    public static String TAG = "FlutterPusherPlugin";
    public static EventChannel.EventSink eventSink;
    private final Map<String, PusherInstance> pusherInstanceMap = new HashMap<>();
    MethodChannel channel;
    EventChannel eventStream;

    public LaravelFlutterPusherPlugin() {
    }


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        channel = new MethodChannel(binding.getBinaryMessenger(), "dev.micazi/pusher");
        eventStream = new EventChannel(binding.getBinaryMessenger(), "dev.micazi/pusherStream");

        channel.setMethodCallHandler(new LaravelFlutterPusherPlugin());
        eventStream.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object args, final EventChannel.EventSink eventSink) {
                LaravelFlutterPusherPlugin.eventSink = eventSink;
            }

            @Override
            public void onCancel(Object args) {
                Log.d(TAG, String.format("onCancel args: %s", args != null ? args.toString() : "null"));
            }
        });
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
        eventStream.setStreamHandler(null);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        Type type = new TypeToken<InstanceMessage>(){}.getType();
        InstanceMessage instanceMessage = new Gson().fromJson(call.arguments.toString(), type);
        String instanceId = instanceMessage.getInstanceId();
        PusherInstance instance = getPusherInstance(instanceId);
        if (instance == null) {
            String message = String.format("Instance with id %s not found", instanceId);
            throw new IllegalArgumentException(message);
        }

        instance.onMethodCall(call, result);
    }

    private PusherInstance getPusherInstance(String instanceId) {
        if (instanceId != null && !pusherInstanceMap.containsKey(instanceId)) {
            pusherInstanceMap.put(instanceId, new PusherInstance(instanceId));
        }
        return pusherInstanceMap.get(instanceId);
    }
}
