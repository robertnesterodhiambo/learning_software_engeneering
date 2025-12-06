void main(){
  int value  =3;
  
  //style1
  int squared1(num1) => num1 * num1;
  int cubed1(num1)  => num1 * num1 * num1;

  //style2 

  int squared2(num1){return num1 * num1;}
  int cubed2(num1){return num1 * num1 *num1; }

  print("The number $value Squared is ${squared1(value)}");
  print("The number $value cubed is ${cubed1(value)}");

  print("Style 2 ");

  print("The number $value squared is  ${squared2(value)}");
  print("The number $value cubed is ${cubed2(value)}");
}