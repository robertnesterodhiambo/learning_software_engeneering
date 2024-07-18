void main() {
  londontime(-7);
}

void londontime(int diff) {
  var currentim_lodon = DateTime.now();
  var timediff = currentim_lodon.add(Duration(hours: diff));
  print("The current tiime in london is  $currentim_lodon");
  print("The curent time in Kenya 7hours behind is $timediff");
}
