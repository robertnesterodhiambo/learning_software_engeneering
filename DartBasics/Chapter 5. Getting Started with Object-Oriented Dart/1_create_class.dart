const NumDays = 7;

class NumDaysLeft {
  int today = 0;
  NumDaysLeft(){
    today =  DateTime.now().weekday.toInt();
  }
  int HowManyDaysLeft () {
    return NumDays - today;
  }
}
// Error 1 My folder has spaces rectifying this 

void main(){
  final daysleft = NumDaysLeft();
  print("The number of days left is  $daysleft");
}