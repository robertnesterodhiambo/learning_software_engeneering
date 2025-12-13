// here we will deal with something like json objects
import 'dart:convert';

void main(){
  Map<String, dynamic> data = {
    jsonEncode("Title"): json.encode("Star Wars"),
    jsonEncode("Year"): json.encode("1975")
  };

  // decode the json
  Map<String, dynamic> decoded = json.decode(data.toString());
  print("$decoded");
  print(decoded['Title']);

}