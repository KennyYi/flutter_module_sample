import 'package:flutter/material.dart';

void main() => runApp(RedModule());

class RedModule extends StatelessWidget {

  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      home: Scaffold(
        backgroundColor: Colors.red,
        body: Center(
          child: Text("Flutter Red Screen", style: TextStyle(color: Colors.white),),
        ),
      ),
    );
  }
}