void main(){
  String name =  "Robert Nester Odhiambo";
  try{
    print("My name is $name");
    name.indexOf(name[0], name.length - (name.length + 2));
  } on RangeError catch(exception) {
    print("$exception");
  } catch(exception){
    print("This $exception was caught");
  } finally{
    print("Final Boss caught");
  }
}