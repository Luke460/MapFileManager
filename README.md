# MapFileManager
*Gestore di file .txm*

### MapFileManager(String fileName):
Crea un oggetto MapFileManager, che opera sul file specificato. La lettura e la scrittura vanno richieste esplicitamente.

### readFile():
Aggiorna la mappa (Map<String,String) con i valori contenuti nel file.

### updateFile()
Aggiorna il file sulla base dei valori contenuti nella mappa.

#### getMap()
Restituisce la mappa permettendo la sua modifica.

---

## esempio di un file *.txm*

```
#testFile.txm#

#comment one#
<key01>
  <value01>

#comment two#
<key02>
  <value02>

<key03>
  <value03>

<key X>
  <XXX>

# some comments:
  bla bla
  bla bla
  bla bla bla#
<final key>
  <final value>

$

another comment (1)
another comment (2)

another comment (3)

another comment (...)

```
