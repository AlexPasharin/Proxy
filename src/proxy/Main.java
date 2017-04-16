/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author aleksandrpasharin
 */
public class Main {
    
    public static void main(String[] args) {
        // luokka määritelty alla
        PhotoFilesFolder valokuvaKansio = new PhotoFilesFolder();
        
        //lisätään valokuvat proxyinä
        for(int i = 0; i < 10; i++)
            valokuvaKansio.addImage(new ProxyImage("HiRes_10MB_Photo" + i));
        
        // tulostetaan valokuvakansion sisältö (kuvia ei ladata)
        System.out.println("Kansion sisältö: ");
        valokuvaKansio.showContents();
        
        System.out.println();
        System.out.println("\n---------------------------\n");
        
        
        // iteraatiivinen selaustoiminto (selataan eteenpäin tai taaksepäin. Käyttämällä nähdään, että kuva ladataan vain tarvittaessa eli ensim. kertaa.
        
        System.out.println("Tervetuloa selamaan kansion kuvat");
        System.out.println("Paina 1 jos haluat mennä seuraavaan kuvaan");
        System.out.println("Paina 2 jos haluat mennä edelliseen kuvaan");
        System.out.println("Paina 3 jos haluat aloittaa selaaminen kansion alusta");
        System.out.println("Paina 4 jos haluat lopettaa");
        
        Scanner lukija = new Scanner(System.in);
        char nextChar;
        
        do{
            System.out.print("Anna kommento: ");
            String kommento = lukija.nextLine();
            if(kommento.length() != 1) nextChar = '5';
            else nextChar = kommento.charAt(0);
            
            switch(nextChar){
                case '1': 
                    try{
                       valokuvaKansio.displayNext();
                    }catch(NoSuchElementException e){
                        System.out.println("Tämä on viimeinen kuva, seuraavan kuvan näyttäminen ei ole mahdollista.");
                    }
                    break;    
                    
                case '2': 
                    try{
                       valokuvaKansio.displayPrev();
                    }catch(NoSuchElementException e){
                        System.out.println("Tämä on ensimmäinen kuva, edellisen kuvan näyttäminen ei ole mahdollista.");
                    }
                    break;  
               
                case '3': 
                    valokuvaKansio.setFromTheBeginning();
                    
                    System.out.println("Selaaminen asetettu kansion alkuun.");
                    break;
                
                case '4':
                    
                    System.out.println("Näkemmin!");
                    break;
                
                    
                default: 
                    System.out.println("Kelvoton syöte!");
                    break;
            }
            
        }while(nextChar != '4');
    }
    
        
    public static class PhotoFilesFolder{
        
        private final List<Image> photos = new ArrayList<>();
        private ListIterator<Image> it = this.photos.listIterator();
        
        /*to accomadate for listIterator's behaviour: from javaDocs:
         (Note that alternating calls to next and previous will return the same element repeatedly.)     
        */

        private boolean nextWasDisplayed = false;
        private boolean prevWasDisplayed = false;
        
        public void addImage(Image image){
            this.photos.add(image);
        }
        
        public void showContents(){
            this.setFromTheBeginning();
            
            while(it.hasNext()){
                it.next().showData();
            }
            
            this.setFromTheBeginning();
            
        }
        
        public void displayNext(){
            if(this.prevWasDisplayed) this.it.next();
            
            if(this.it.hasNext()){             
                this.it.next().displayImage();
                this.nextWasDisplayed = true;
                this.prevWasDisplayed = false;
            }
            else
                throw new NoSuchElementException();
            
     
        }
        
        public void displayPrev(){
            if(this.nextWasDisplayed) this.it.previous();
            
            if(this.it.hasPrevious()){ 
                this.it.previous().displayImage();
                
                this.nextWasDisplayed = false;
                this.prevWasDisplayed = true;
            }
            else
                throw new NoSuchElementException();
        }
        
        // sets iterator from the beginning
        public void setFromTheBeginning(){
            this.it = this.photos.listIterator();
        }
        
    }

    
    
}

