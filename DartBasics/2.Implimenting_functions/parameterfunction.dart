void main() {
  KenyatimeDiff(-7);
}

void KenyatimeDiff(int hourdiff) {
  var timenow = DateTime.now();
  var timediff = timenow.add(Duration(hours: hourdiff));
  print("The time in Kenya now is $timenow");
  print("The time difference from Kenya is $timediff");
}
