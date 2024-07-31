void main() async {
  int Delay = 5;

  print("Hello");
  var value = await _customDelay(Delay);
  var customText = Delay == 1 ? "Seconds Later " : "Seconds Later";
  print("its $value $customText");
}

Future<int> _customDelay(int Delay) async {
  try {
    await Future.delayed(Duration(seconds: Delay));
    return Delay;
  } catch (e) {
    print(e);
    return Delay;
  }
}
