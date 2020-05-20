---
theme: "Solarized"
transition: "zoom"
highlightTheme: "railscasts"
---

# How to integrate Flutter modules with an existing Native Android app

---

## Plugin vs Module

| Plugin | Module |
|---|---|
| Making native funtionality available to Flutter | Integrating Flutter with an existing native application |

---

### Step 1. Android native app

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickGreen(view: View) {
        // TODO call flutter screen
    }
}
```

---

### Step 2. Create a Flutter module

```dart
void main() => runApp(GreenModule());

class GreenModule extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: Colors.green,
        body: Center(
          child: Text("Flutter Green Screen",
            style: TextStyle(color: Colors.white)),
        ),
      ));
  }
}
```

---

### Step 3. Import a Flutter module

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickGreen(view: View) {
        startActivity(
            FlutterActivity.createDefaultIntent(this)
        )
    }
}
```

---

![arch_1](./arch_1.png)

---

### Step 4. Increase speed

pre-warm `FlutterEngine` and caching

```kotlin
class NativeApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val flutterEngine = FlutterEngine(this)

        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        FlutterEngineCache.getInstance()
            .put("green", flutterEngine)
    }
}
```

---

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickGreen(view: View) {
        startActivity(
            FlutterActivity
                .withCachedEngine("green")
                .build(this)
        )
    }
}
```

---

### Step 5. Add another Flutter module

![arch_2](./arch_2.png)

---

![arch_2_no](./arch_2_no.png)

---

![arch_3](./arch_3.png)

---

```yaml
#pubspec.yaml
dependencies:
  greenmodule:
    path: ../green_module
  redmodule:
    path: ../red_module
```

```dart
void main() => runApp(palette(route: window.defaultRouteName));

class palette extends StatelessWidget {
  final String route;
  palette({this.route});

  @override
  Widget build(BuildContext context) {
    switch(route) {
      case "green": return GreenModule();
      case "red": return RedModule();
      default: return GreenModule();
    }
  }
}
```

---

NativeApp

Check module name

```groovy
// settings.gradle
rootProject.name='NativeApp'
include ':app'
setBinding(new Binding([gradle: this]))
evaluate(new File(
  settingsDir,
  '../palette/.android/include_flutter.groovy'
))

include ':palette'
project(':palette').projectDir = new File('../palette')
```

---

NativeApplication.onCreate()

```kotlin
val greenEngine = FlutterEngine(this)
greenEngine.navigationChannel.setInitialRoute("green")
greenEngine.dartExecutor.executeDartEntrypoint(
    DartExecutor.DartEntrypoint.createDefault()
)
FlutterEngineCache.getInstance().put("green", greenEngine)

val redEngine = FlutterEngine(this)
redEngine.navigationChannel.setInitialRoute("red")
redEngine.dartExecutor.executeDartEntrypoint(
    DartExecutor.DartEntrypoint.createDefault()
)
FlutterEngineCache.getInstance().put("red", redEngine)
```

---

```kotlin
class MainActivity : AppCompatActivity() {

    fun onClickGreen(view: View) {
        startActivity(
            FlutterActivity
                .withCachedEngine("green").build(this))
    }

    fun onClickRed(view: View) {
        startActivity(
            FlutterActivity
                .withCachedEngine("red").build(this))
    }
}
```

---

### Step 6. Passing arguments

---

## 🤷‍♂️

There is no way to pass parameters while starting the Flutter module.

(I couldn't find it.)

---

Bypass using the **`PlatformChannel`** (`MethodChannel`)

* MethodChannel
  1. Call Native method from Flutter 👈
  2. Call Flutter method from Flutter

* In Android, `channel.invokeMethod("methodName")` should be called through **MainThread**.

---

```kotlin
redChannel = MethodChannel(
  redEngine.dartExecutor.binaryMessenger,
  "palette/red" // MethodChannel name
)

redChannel?.setMethodCallHandler { call, result ->

    if(call.method == "getParam") {
        result.success(getParam())
    }
}
```

```kotlin
fun getParam(): String {
    return "parameter"
}
```

---

```dart
static const MethodChannel _channel = const MethodChannel(
  "palette/red" // MethodChannel name
);

// To call from initState, change widget to StatefulWidget
@override
void initState() {
  super.initState();
  getInitParam();
}

Future<void> getInitParam() async {
  final String result = await _channel.invokeMethod("getParam");
  setState(() => _result = result);
}
```

---

## Uncovered contents

* Android basics
* Flutter basics
* Add a Flutter Fragment module
* Add a Flutter module to an existing iOS app

---

## Links

https://flutter.dev/docs/development/add-to-app

https://github.com/flutter/flutter/issues/39707#issuecomment-569120877

https://github.com/flutter-moum/flutter-moum/wiki/3.-%EB%84%A4%EC%9D%B4%ED%8B%B0%EB%B8%8C-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84-%5BMethodChannel%5D

Sample project: https://github.com/KennyYi/flutter_module_sample
