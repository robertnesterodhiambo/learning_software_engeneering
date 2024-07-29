void main() {
  int value = 5;

  //anonymous functions style1
  int ex1Squared(num1) => num1 * num1;
  int ex1cubed(num1) => num1 * num1 * num1;

  //ananonymous function style 3
  int ex2squared(num1) {
    return num1 * num1;
  }

  int ex2cubed(num1) {
    return num1 * num1 * num1;
  }

  print("Ex1  $value squared is $ex1Squared(value)");
  print("Ex1  $value cubed is $ex1cubed(value)");
  print("Ex2  $value squared is $ex2squared(value)");
  print("Ex2  $value cubed is $ex2cubed(value)");
}
