# laravel_flutter_pusher_plus_example

Demonstrates how to use the laravel_flutter_pusher_plus plugin.

```dart
import 'package:laravel_flutter_pusher_plus/laravel_flutter_pusher_plus.dart';

void main() {

  var options = PusherOptions(
          host: '10.0.2.2',
          port: 6001,
          encrypted: false,
          cluster: 'eu'
      );

      LaravelFlutterPusher pusher = LaravelFlutterPusher('app_key', options, enableLogging: true);
      pusher
          .subscribe('channel')
          .bind('event', (event) => log('event =>' + event.toString()));
}
```
