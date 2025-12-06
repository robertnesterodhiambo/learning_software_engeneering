void PrintName(String fname,[String? Sirname]){
    print(fname);
    if(Sirname != Null){
        print(Sirname);
    }
}

void main(){
    PrintName("Rober");
    PrintName("Robert","Odhiambo");
}