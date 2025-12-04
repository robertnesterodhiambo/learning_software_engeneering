void main(){
  TimeDiff(-7);
}

void TimeDiff(int hoursdiff){
  var time = DateTime.now();
  print("The time now is $time");
  var timel = time.add(Duration(hours: hoursdiff));
  print("The time difference is $timel");
}