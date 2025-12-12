void main(){
  Map <int, String> months = {1: "Jan", 2: "Feb", 3: "March"};
  if(months[1] != null){
    print("Test 1 exists");
  }
  if(months.containsKey(2)){
    print("Test Key exists");
  }
}