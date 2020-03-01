package laskin;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public abstract class Komento {
    protected TextField tuloskentta;
    protected TextField syotekentta;
    protected Button nollaa;
    protected Button undo;
    protected Sovelluslogiikka sovellus;
    protected int edellinenArvo;
     
    public Komento(TextField tuloskentta, TextField syotekentta, Button nollaa, Button undo, Sovelluslogiikka sovellus) {
        
        this.tuloskentta = tuloskentta;
        this.syotekentta = syotekentta;
        this.nollaa = nollaa;
        this.undo = undo;
        this.sovellus = sovellus;
        this.edellinenArvo = 0;
    }

    public abstract void suorita();
    
    public void tallennaEdellinen(){

        edellinenArvo = sovellus.tulos();

    }
    
    public int arvo(TextField syotekentta){
        
        int arvo = 0;
 
        try {
            arvo = Integer.parseInt(syotekentta.getText());
        } catch (Exception e) {
        }
        
        return arvo;
    }
    
    public void paivitaGUI(){
        
        asetteleGUIuuteenArvoon();
        
        undo.disableProperty().set(false);
                
    }
    
    private void asetteleGUIuuteenArvoon(){
        
        int laskunTulos = sovellus.tulos();
        
        syotekentta.setText("");
        tuloskentta.setText("" + laskunTulos);
        
        if ( laskunTulos==0) {
            nollaa.disableProperty().set(true);
        } else {
            nollaa.disableProperty().set(false);
        }
   
    }

    public void peru(){
        
        sovellus.nollaa();
        sovellus.plus(edellinenArvo);
        
        asetteleGUIuuteenArvoon();
        
        undo.disableProperty().set(true);
    }
    
}


