void main(){
  Map<String, dynamic> film1 = {"Title": "Star wars","Year": "1977"};
  Map<String, dynamic> film2 = {"Title": "The empire Strikes back", "Year": "1980"};
  Map<String, dynamic> film3 = {"Title": "Tom and Jerry", "Year": "1965"};

  List film_list = [film1, film2, film3];
  film_list.forEach(print);
}