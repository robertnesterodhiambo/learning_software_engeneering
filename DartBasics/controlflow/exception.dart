void main() {
  String name = 'Dart';
  try {
    print('Name: $name');
    name.indexOf(name[0], name.length - (name.length + 2));
  } on RangeError catch (exception) {
    print('On exception $exception');
  } catch (exception) {
    print('catch exception $exception');
  } finally {
    print("Mission completed");
  }
}
