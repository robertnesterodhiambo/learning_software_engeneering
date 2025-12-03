void main(){
  String name = "Dart";
  try{
    print("Hello I am learning $name");
    // this will cause a rnage error
    name.indexOf(name[0],name.length - (name.length + 2));
  } on RangeError catch(exception){
    print("On exception: $exception");
  } catch(exception){
    print("CAught Exceptions : $exception");
  } finally {
    print("Mission completed");
  }
}