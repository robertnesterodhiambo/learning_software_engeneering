void main() {
  // printname();
  printname("BJR");
  printname("Robert", "nester");
}

void printname(String fname, [String? sname]) {
  print(fname);
  if (sname != null) {
    print(sname);
  }
}
