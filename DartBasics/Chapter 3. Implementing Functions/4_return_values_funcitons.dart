DateTime CurremtTimef(int hour){
  DateTime timenow = DateTime.now();
  DateTime timediff = timenow.add(Duration(hours: hour));

  return timediff;
}

void main(){
  DateTime now = CurremtTimef(0);
  DateTime diff = CurremtTimef(-7);

  print("the time now is $now");
  print("the time diff is $diff");
}