void main() {
  int value = 10;

  //anonymous functions 1
  int fxnsquared(num1) => num1 * num1;
  int fxncubed(num1) => num1 * num1 * num1;

  //anonymous functions 2
  int fxnsquared2(num1) {
    return num1 * num1;
  }

  int fxncubed2(num1) {
    return num1 * num1 * num1;
  }

  print("the fxnsquared $value squared is  ${fxnsquared(value)}");
  print("the fxn cubed $value cubed is ${fxncubed(value)}");
  print("the fxnsquared2 $value squared is ${fxnsquared2(value)}");
  print("the fxncubed2 $value cubed is ${fxncubed2(value)}");
}
