import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:greenmodule/main.dart';
import 'package:redmodule/main.dart';

void main() => runApp(Pallet(route: window.defaultRouteName,));

class Pallet extends StatelessWidget {

  final String route;

  Pallet({this.route});

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
