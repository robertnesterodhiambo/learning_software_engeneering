void main() {
int value = 5;
// Anonymous Function - Style 1
int ex1Squared(num1) => num1 * num1;
int ex1Cubed(num1)
=> num1 * num1 * num1;
// Anonymous Function - Style 2
int ex2Squared(num1){ return num1 * num1; }
int ex2Cubed(num1){ return num1 * num1 * num1; }
print('EX1: $value squared is ${ex1Squared(value)}');
print('EX1: $value cubed is ${ex1Cubed(value)}');
print('EX2: $value squared is ${ex2Squared(value)}');
print('EX2: $value cubed is ${ex2Cubed(value)}');
}