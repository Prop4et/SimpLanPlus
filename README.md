# SimpLanPlus
Progetto di compilatori e interpreti, A. A. 2021

#TODO: 
   -[ ] Ci sono dei problemi con le label, non capisco come funziona il costruttore di SVMvisitorImpl - cosa sono labelRef e labelAdd? e come vengono inizializzate? non trovo corrispondenza tra l'intero assegnato e le righe del codice - ma credo che il problema potrebbe essere li, ovvero che l'istruzione non venga modificata in modo corretto e quindi la label non viene tradotta in numero.  
   
#IN PROGRESS: 
   
    
#DONE: 
   - [X] variabili/funzioni non dichiarate
   - [x] variabili dichiarate piu' volte nello stesso ambiente 
   - [x] parametri attuali non conformi ai parametri formali 
   - [x] uso di variabili non inizializzate
   - [x] corretto uso dei puntatori
   - [x] (inclusa la verifica sui parametri passati per var)
   - [x] la delete non deve funzionare su id non puntatori
   - [x] variabili globali non devono essere visibili dalle funzioni
   - [x] la correttezza dei tipi (da finire di testare) 

Al momento è così, manca ancora l'utilizzo di RA 
|Record di attivazione        |
|-----------------------------|
| Dec M                       |
| ...                         |
| Dec 1                       | 
| Par N                       | 
| ...                         |
| Par 1                       |
| AL (Access Link)            | 
| Old FP (Frame Pointer)      |
