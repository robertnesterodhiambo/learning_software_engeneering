void main() {
  printname();
  printname(fname: "Rich");
  printname(fname: "Robert", clientId: 123);
}

void printname({String fname = "Stranger", int clientId = 999}) {
  if (fname.contains("Stranger")) {
    print("Employee $clientId Strnger Danger");
  } else {
    print("Employee $fname  $clientId ");
  }
}
