void PrintGreeting({String name = 'Robert', int id = 123}){
  if(name.contains("Robert")){
    print("Robert is the name of $name");
  } else {
    print("this is not the main user");
  }
}

void main(){
  PrintGreeting();
  PrintGreeting(name: 'Robert');
  PrintGreeting(name: "Robert", id: 1234);
  PrintGreeting(name: "Milk");
}