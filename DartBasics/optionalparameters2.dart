void main() {
  printbaby();
  printbaby(bname: "BJR");
  printbaby(bname: "princes", bnum: 2);
}

void printbaby({String bname = "Bobbys kid", int bnum = 2}) {
  if (bname.contains("Bobbys kid")) {
    print("This is not $bname");
  } else {
    print("This is $bname kid number $bnum of Bobby");
  }
}
