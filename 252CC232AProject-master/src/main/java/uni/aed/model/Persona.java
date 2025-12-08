package uni.aed.model;

import java.util.Comparator;

public class Persona implements Comparable<Persona>{
    public static final int NAME=0;
    public static final int AGE=1;
    private static int compareAttribute=NAME;
    
    private static final int LESS=-1;//menor
    private static final int EQUAL=0;//igual
    private static final int MORE=1;//mayor
    
    private String name;//nombre
    private int age;    //edad
    private char gender;//genero

    public Persona(){
        this("No ingresado",0,'U');
    }
    public Persona(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Persona(String name, int age, char gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public static void setCompareAttribute(int attribute) {
        compareAttribute = attribute;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }    

    @Override
    public boolean equals(Object o) {       
        return name.equals(((Persona)o).name);
    }    

    @Override
    public String toString() {
        return this.name+"||"+
               this.age+"||"+
               this.gender;
    }
    public int compareTo(Persona persona,int attribute){
        int comparisonResult;
        if(attribute==AGE){
            int p2age=persona.getAge();
            if(this.age<p2age)
                comparisonResult=LESS;                
            else if(this.age==p2age)
                comparisonResult=EQUAL;
            else
                comparisonResult=MORE;
        }else{
            String p2name=persona.getName();
            comparisonResult=this.name.compareTo(p2name);
        }
        return comparisonResult;
    }    

    @Override
    public int compareTo(Persona o) {
        return compareTo(o,compareAttribute);
    }
    
    //Comparador de edad
    public static final Comparator<Persona> POR_EDAD=new Comparator<>(){
        @Override
        public int compare(Persona o1, Persona o2) {
            return Integer.compare(o1.age, o2.age);
        }
    };
    //Comparador por nombre
    public static final Comparator<Persona> POR_NOMBRE=new Comparator<>(){
        @Override
        public int compare(Persona o1, Persona o2) {
            return o1.name.compareTo(o2.name);
        }
    };
}
