void main() {
  Map<String, dynamic> FilmStarwars = {"title": "StarWars", "Year": 1977};
  Map<String, dynamic> FilmEmpire = {"title": "Empire Strikes", "Year": 1980};
  Map<String, dynamic> FilmJedi = {"title": "The last Jedi", "Year": 1983};
  List films = [FilmStarwars, FilmEmpire, FilmJedi];
  Map<String, dynamic> CurrentFilm = films[0];
  print(CurrentFilm);
  print(CurrentFilm["title"]);
}
