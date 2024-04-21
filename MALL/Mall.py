import tkinter as tk
from tkinter import ttk

class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

class Manager(Person):
    def __init__(self, name, age, monthly_salary):
        super().__init__(name, age)
        self.monthly_salary = monthly_salary

    def yearly_salary(self):
        return self.monthly_salary * 12

    def __str__(self):
        return f"Manager: {self.name}, Age: {self.age}, Monthly Salary: ${self.monthly_salary}"

class Employee(Person):
    def __init__(self, name, age, hourly_salary):
        super().__init__(name, age)
        self.hourly_salary = hourly_salary

    def yearly_salary(self):
        return self.hourly_salary * 40 * 50

    def __str__(self):
        return f"Employee: {self.name}, Age: {self.age}, Hourly Salary: ${self.hourly_salary}"

class Product:
    def __init__(self, name, price):
        self.name = name
        self.price = price

    def __str__(self):
        return f"Product: {self.name}, Price: ${self.price}"

class Store:
    def __init__(self, name, store_type):
        self.name = name
        self.store_type = store_type
        self.managers = []
        self.employees = []
        self.products = []

    def add_manager(self, manager):
        if isinstance(manager, Manager):
            self.managers.append(manager)
        else:
            print("Error: Only Manager instances can be added as managers.")

    def add_employee(self, employee):
        if isinstance(employee, Employee):
            self.employees.append(employee)
        else:
            print("Error: Only Employee instances can be added as employees.")

    def add_product(self, product):
        if isinstance(product, Product):
            self.products.append(product)
        else:
            print("Error: Only Product instances can be added as products.")

    def yearly_budget(self):
        total_salary = sum(employee.yearly_salary() for employee in self.employees)
        return total_salary + sum(manager.yearly_salary() for manager in self.managers)

    def add_option(self, option_name, option_price):
        new_option = Product(option_name, option_price)
        self.products.append(new_option)

    def summary(self):
        summary = f"Store: {self.name}, Type: {self.store_type}\nProducts:\n"
        for product in self.products:
            summary += f"\t{product}\n"
        return summary

    def __str__(self):
        return f"Store: {self.name}, Type: {self.store_type}"

class Restaurant:
    def __init__(self, name, seats):
        self.name = name
        self.seats = seats
        self.manager = None
        self.employees = []
        self.products = []

    def set_manager(self, manager):
        if isinstance(manager, Manager):
            self.manager = manager
        else:
            print("Error: Only Manager instances can be set as managers.")

    def add_employee(self, employee):
        if isinstance(employee, Employee):
            self.employees.append(employee)
        else:
            print("Error: Only Employee instances can be added as employees.")

    def add_product(self, product):
        if isinstance(product, Product):
            self.products.append(product)
        else:
            print("Error: Only Product instances can be added as products.")

    def __lt__(self, other):
        return self.seats < other.seats

    def __str__(self):
        return f"Restaurant: {self.name}, Seats: {self.seats}, Manager: {self.manager}"

class FoodCourt:
    def __init__(self, floor):
        self.floor = floor
        self.restaurants = []

    def add_restaurant(self, restaurant):
        if isinstance(restaurant, Restaurant):
            self.restaurants.append(restaurant)
        else:
            print("Error: Only Restaurant instances can be added to the food court.")

    def summary(self):
        summary = f"FoodCourt: Floor {self.floor}, Number of Restaurants: {len(self.restaurants)}\n"
        for restaurant in self.restaurants:
            summary += f"\t{restaurant}\n"
        return summary

    def __str__(self):
        return f"FoodCourt: Floor {self.floor}, Number of Restaurants: {len(self.restaurants)}"

class Mall:
    def __init__(self, name):
        self.name = name
        self.manager = None
        self.stores = []
        self.food_court = None

    def set_manager(self, manager):
        if isinstance(manager, Manager):
            self.manager = manager
        else:
            print("Error: Only Manager instances can be set as mall manager.")

    def add_store(self, store):
        if isinstance(store, Store):
            self.stores.append(store)
        else:
            print("Error: Only Store instances can be added to the mall.")

    def set_food_court(self, food_court):
        if isinstance(food_court, FoodCourt):
            self.food_court = food_court
        else:
            print("Error: Only FoodCourt instances can be set as mall food court.")

    def yearly_budget(self):
        return sum(store.yearly_budget() for store in self.stores)

    def summary(self):
        summary = f"Mall: {self.name}, Number of Stores: {len(self.stores)}, Has Food Court: {self.food_court is not None}\n"
        for store in self.stores:
            summary += store.summary() + "\n"
        if self.food_court:
            summary += self.food_court.summary() + "\n"
        return summary

    def __str__(self):
        return f"Mall: {self.name}, Number of Stores: {len(self.stores)}, Has Food Court: {self.food_court is not None}"


class MallApp:
    def __init__(self, master):
        self.master = master
        self.master.title("Mall Management App")

        # Create test data
        self.create_test_data()

        # Create a frame for mall information
        self.mall_frame = tk.Frame(self.master, bd=2, relief=tk.GROOVE)
        self.mall_frame.pack(padx=10, pady=10, fill=tk.BOTH, expand=True)

        # Mall information label
        self.mall_label = tk.Label(self.mall_frame, text="Select Mall:")
        self.mall_label.grid(row=0, column=0, padx=5, pady=5, sticky="w")

        # Mall combobox
        self.mall_combobox = ttk.Combobox(self.mall_frame, values=[mall.name for mall in self.malls])
        self.mall_combobox.grid(row=0, column=1, padx=5, pady=5)
        self.mall_combobox.current(0)
        self.mall_combobox.bind("<<ComboboxSelected>>", self.update_mall_summary)

        # Mall summary label
        self.mall_summary_label = tk.Label(self.mall_frame, text="Mall Summary:")
        self.mall_summary_label.grid(row=1, column=0, columnspan=2, padx=5, pady=5, sticky="w")

        # Mall summary text
        self.mall_summary_text = tk.Text(self.mall_frame, height=10, width=50)
        self.mall_summary_text.grid(row=2, column=0, columnspan=2, padx=5, pady=5)
        self.mall_summary_text.insert(tk.END, self.malls[0].summary())

    def create_test_data(self):
        self.malls = []

        for i in range(1, 7):
            mall_name = f"Mall {i}"
            manager = Manager(f"Manager {i}", 40, 8000)
            mall = Mall(mall_name)
            mall.set_manager(manager)

            store1 = Store(f"Store {i}-1", "Electronics")
            store1.add_manager(manager)
            store1.add_product(Product(f"Laptop {i}-1", 1000))
            store1.add_product(Product(f"Smartphone {i}-1", 500))
            mall.add_store(store1)

            store2 = Store(f"Store {i}-2", "Fashion")
            store2.add_manager(manager)
            store2.add_product(Product(f"Dress {i}-2", 50))
            store2.add_product(Product(f"Shoes {i}-2", 80))
            mall.add_store(store2)

            food_court = FoodCourt(i)
            restaurant1 = Restaurant(f"Restaurant {i}-1", 50)
            restaurant1.set_manager(manager)
            restaurant1.add_product(Product(f"Food {i}-1", 10))
            restaurant1.add_product(Product(f"Drink {i}-1", 5))
            restaurant2 = Restaurant(f"Restaurant {i}-2", 30)
            restaurant2.set_manager(manager)
            restaurant2.add_product(Product(f"Food {i}-2", 15))
            restaurant2.add_product(Product(f"Drink {i}-2", 6))
            food_court.add_restaurant(restaurant1)
            food_court.add_restaurant(restaurant2)
            mall.set_food_court(food_court)

            self.malls.append(mall)

    def update_mall_summary(self, event=None):
        selected_mall_name = self.mall_combobox.get()
        for mall in self.malls:
            if mall.name == selected_mall_name:
                self.mall_summary_text.delete("1.0", tk.END)
                self.mall_summary_text.insert(tk.END, mall.summary())
                break

def main():
    root = tk.Tk()
    app = MallApp(root)
    root.mainloop()

if __name__ == "__main__":
    main()
