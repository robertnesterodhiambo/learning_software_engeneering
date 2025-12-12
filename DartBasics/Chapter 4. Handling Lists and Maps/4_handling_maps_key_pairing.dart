void main(){
  Map<int, String> Months = {0: "Jan", 1: "Feb", 2: "March"};
  Map<int, String> MoreMonths = {3: "April"};

  Months.addEntries(MoreMonths.entries);
  Months.forEach((key, value){
    print("$key, $value");
  });
 }