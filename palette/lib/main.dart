import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:greenmodule/main.dart';
import 'package:redmodule/main.dart';

void main() => runApp(Palette(route: window.defaultRouteName,));

class Palette extends StatelessWidget {

  final String route;

  Palette({this.route});

  @override
  Widget build(BuildContext context) {

    print("$route");

    switch(route) {

      case "green": return GreenModule();
      case "red": return RedModule();
      default: return GreenModule();
    }
  }
}
