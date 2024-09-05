void main() {
  Map<int, String> mapmonth = {1: "Jan", 2: "Feb", 3: "March"};
  Map<int, String> moremonth = {4: "Apr", 5: "May", 6: "June"};

  mapmonth.addEntries(moremonth.entries);
  mapmonth.forEach((key, value) {
    print("$key, $value");
  });
}
