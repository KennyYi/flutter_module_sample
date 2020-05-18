import 'package:flutter/material.dart';

void main() => runApp(GreenModule());

class GreenModule extends StatelessWidget {

  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      home: Scaffold(
        backgroundColor: Colors.green,
        body: Center(
          child: Text("Flutter Green Screen", style: TextStyle(color: Colors.white),),
        ),
      ),
    );
  }
}