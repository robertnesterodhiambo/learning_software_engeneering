void main() {
  printbobby("BJR");
  printbobby("Bobby", "Junior");
}

void printbobby(String fname, [String? sname]) {
  print("Bobsys kid is called $fname");
  if (sname != null) {
    print("Bobbsy kids name is also $sname");
  }
}
