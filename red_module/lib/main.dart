import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(RedModule());

class RedModule extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => _RedModule();
}

class _RedModule extends State<RedModule> {

  static const MethodChannel _channel = MethodChannel("palette/red");

  String _time = "";

  @override
  void initState() {
    super.initState();

    getTime();
  }

  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      home: Scaffold(
        backgroundColor: Colors.red,
        body: Center(
          child: Text("Flutter Red Screen: $_time", style: TextStyle(color: Colors.white),),
        ),
      ),
    );
  }

  Future<void> getTime() async {

    final String time = await _channel.invokeMethod("getTime");
    setState(() => _time = time);
  }
}