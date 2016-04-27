package hello;

import model.Items;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
@EnableAutoConfiguration
public class SampleController {

    Items items = new Items();
    int i = 0;

    @RequestMapping(value = "/{nome}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Items.Item home(@PathVariable("nome") String nome) {

        Items.Item item1 = new Items.Item();

        item1.setNome(nome);
        boolean doppio = false;
        for (int j = 0; j < items.getItems().size(); j++) {
            if (items.getItems().get(j).getNome().equals(nome)) {
                doppio = true;
            }
        }
        if (!doppio) {
            this.items.addItem(item1);
            i++;
        }
        //this.items.addItem(item1);
        //i++;
        item1.setCounter(i);

        return item1;//importante this

    }

    @RequestMapping("/")
    @ResponseBody
    Items allItems(@RequestParam(value = "query",required = false) String query) {
        Items items2 = new Items();
        if(query!=null){
            for (int j = 0; j < items.getItems().size(); j++) {
                if(items.getItems().get(j).getRicerca().contains(query)){
                    items2.addItem(items.getItems().get(j));
                }
            }
        }
        else{
            items2=items;
        }
        return items2;
    }

    @RequestMapping(value = "/{nome}", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String elimina(@PathVariable("nome") String nome) {
        String esito = "Nessun dato eliminato.";
        for (int j = 0; j < items.getItems().size(); j++) {
            if (items.getItems().get(j).getNome().equals(nome)) {
                esito = "" + this.items.getItems().get(j).getNome() + " eliminato!";
                this.items.getItems().remove(j);
                break;
            }
        }
        return esito;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String create(@RequestBody Items.Item item2) {
        String esito = "";
        boolean doppio = false;
        for (int j = 0; j < items.getItems().size(); j++) {
            if (items.getItems().get(j).getNome().equals(item2.getNome())) {
                esito = "Item già inserito!";
                doppio = true;
                break;
            }
        }
        if (!doppio) {
            i++;
            item2.setCounter(i);
            this.items.addItem(item2);
            esito = "Item inserito!";
        }
        return esito;
    }


    @RequestMapping(value = "/{nome}", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String update(@RequestBody Items.Item item1, @PathVariable("nome") String nome) {
        String esito = "Nessun item modificato";
        if (items.getItems().contains(item1)) {
            System.out.println("Has Item: " + item1);
        }else{
            Items.Item prev = new Items.Item();
            prev.setNome(nome);
            if (items.getItems().contains(prev)) {
                int idx = items.getItems().indexOf(prev);
                Items.Item previousItem = items.getItems().get(idx);
                items.getItems().remove(prev);
                items.getItems().add(idx, item1);

                esito = "Item modificato da " + previousItem.getNome() + " a " + nome;
            }
        }
        return esito;
    }


    /*
    @RequestMapping(value = "/{nome}", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String update(@RequestBody Items.Item item1, @PathVariable("nome") String nome) {
        String esito="Nessun item modificato";
        boolean conflitto=false;
        for (int j = 0; j < items.getItems().size(); j++) {
            if (items.getItems().get(j).getNome().equals(item1.getNome())) {
                for (int g = 0; g < items.getItems().size(); g++){
                    if (items.getItems().get(g).getNome().equals(nome)){
                        conflitto=true;
                        break;
                    }
                }
                if(!conflitto){
                    esito="L'item col nome "+items.getItems().get(j).getNome()+" è stato modificato in "+nome;
                    items.getItems().get(j).setNome(nome);
                    break;
                }
            }
        }
        return esito;
    }
    */

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
