package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {
    
    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa k;
    
    @Before
    public void setUp() {
        // luodaan ensin mock-oliot
            pankki = mock(Pankki.class);
             viite = mock(Viitegeneraattori.class);
           varasto = mock(Varasto.class);

        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
    
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));          

        // määritellään että tuote numero 2 on kakku jonka hinta on 7 ja saldo 3
        when(varasto.saldo(2)).thenReturn(3); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kakku", 7));
        
        // määritellään että tuote numero 3 on 'loppunutTuote' jonka hinta on 6 ja saldo 0
        when(varasto.saldo(3)).thenReturn(0); 
        when(varasto.haeTuote(3)).thenReturn(new Tuote(2, "kakku", 6));
        
        
        // sitten testattava kauppa 
                k = new Kauppa(varasto, pankki, viite);              
        }

   
    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());   
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }
    
    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaArvoilla() {
        // oikealla asiakkaalla, tilinumeroilla ja summalla

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla arvoilla        
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(5));   
                
    }
    
    @Test
    public void ostetaanKaksiEriTuotettaJaTilisiirtoKutsutaanOikeillaArvoilla() {
        // oikealla asiakkaalla, tilinumeroilla ja summalla

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // ostetaan tuotetta numero 2 eli kakkua
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla arvoilla        
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(12));   
                
    }
    
    @Test
    public void ostetaanKaksiSamaaTuotettaJaTilisiirtoKutsutaanOikeillaArvoilla() {
        // oikealla asiakkaalla, tilinumeroilla ja summalla

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla arvoilla        
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(10));   
                
    }
    
    @Test
    public void ostetaanLoppunutTuoteJaNormaaliTuoteJaTilisiirtoKutsutaanOikeillaArvoilla() {
        // oikealla asiakkaalla, tilinumeroilla ja summalla

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(3);     // ostetaan tuotetta numero 3 eli loppunutTuote
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla arvoilla        
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(5));   
                
    }
    
    @Test
    public void aloitaAsiointiNollaaKaupanTiedot() {
        // oikealla asiakkaalla, tilinumeroilla ja summalla

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        
        // keskeytetaan - ja alotetaan alusta
        k.aloitaAsiointi();
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu nollatuilla arvoilla        
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(0));   
                
    }
    
    @Test
    public void uusiViitenumeroJokaMaksulle() {
        // oikealla asiakkaalla, tilinumeroilla ja summalla

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");
        
        // tarkistetaan että tässä vaiheessa viitegeneraattorin metodia seuraava()
        // on kutsuttu kerran
        verify(viite, times(1)).uusi();

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");
        
        // tarkistetaan että tässä vaiheessa viitegeneraattorin metodia seuraava()
        // on kutsuttu kaksi kertaa
        verify(viite, times(2)).uusi();
                
    }

    @Test
    public void poistetaanTuoteKorista() {

        // tehdään ostokset
        k.aloitaAsiointi();
        
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
               
        // tarkistetaan että tässä vaiheessa varaston metodia palautaVarastoon()
        // on kutsuttu kerran
        Tuote testituote=varasto.haeTuote(1);
        verify(varasto, times(0)).palautaVarastoon(testituote);

        k.poistaKorista(1);     // palautetaan varastoon tuotetta numero 1 eli maitoa
        
        // tarkistetaan että tässä vaiheessa varaston metodia palautaVarastoon()
        // on kutsuttu kerran
        verify(varasto, times(1)).palautaVarastoon(testituote);

    }

    
}
