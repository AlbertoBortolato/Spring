package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by AlbertoBortolato on 26/04/2016.
 */
public class Items {

    public static class Item {

        String nome;

        String ricerca;

        Random rnd = new Random();


        public Item(){
            ricerca= ""+ rnd.nextInt();
        }

        public int getCounter() {
            return counter;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }

        static int counter;

        public String getNome() {
            return nome;
        }

        public String getRicerca(){return  ricerca;}

        public void setNome(String nome) {
            this.nome = nome;
        }



        @Override
        public boolean equals(Object obj) {
            return this.hashCode() == obj.hashCode();
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }

        @Override
        public String toString() {
            return this.getNome();
        }

    }
    List<Item> items=new ArrayList<Item>();


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public void addItem(Item item){
        items.add(item);
    }

}
