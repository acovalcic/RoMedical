# RoMedical

Aplicație economică cu baze de date în domeniul medical. Aplicație Android ce accesează o bază de date SQL prin intermediul unui server Node.js și efectuează anumite operații cu datele din cadrul acesteia.

## Căutarea Spitalelor

Aplicația pornește afișând activitatea principală, iar în cadrul acesteia se afișează fragmentul inițial de *Căutare spital*.

<image src="./md_res/cautare.png" width="358" height="370" alt="img">

Apăsarea butonului *Afișează Toate Spitalele* va afișa toate spitalele din baza de date.

<image src="./md_res/cautare_ex.jpg" width="201" height="413" alt="img">

Căutarea spitalelor se face introducând criterii de căutare precum: denumirea spitalului, categoria acestuia sau localitatea în care se află.

<image src="./md_res/cautare_ex2.png" width="554" height="380" alt="img">

Apăsarea pe un rezultat din listă duce la apariția unei ferestre de dialog, cu ajutorul căreia se va putea vizualiza spitalul selectat în Google Maps.

<image src="./md_res/detalii.jpg" width="190" height="390" alt="img">

## Date Personale

Atunci când este selectată opțiunea *Date personale*, în cadrul activității principale se va  afișa fragmentul intitulat *Date personale de sănătate* în care se pot introduce date cu privire la starea de sănătate, se poate alege afișarea într-o notificare cu ajutorul switch-ului dedicat, iar la apăsarea butonului de salvare, datele și preferința vor fi salvate în cadrul aplicației și restaurate de fiecare dată când se revine la acest fragment.

<image src="./md_res/date_san.png" width="352" height="363" alt="img">

Pentru un utilizator care suferă de trei afecțiuni: *astm bronșic, tensiune ridicată și aritmie*; o notificare creată cu acestea va arăta astfel:

<image src="./md_res/date_san2.png" width="610" height="420" alt="img">

## Feedback

Butonul *Feedback* va afișa fragmentul dedicat acestei activități. La apăsarea butonului de trimitere feedback va apărea o fereastră de dialog de confirmare a trimiterii formularului iar un răspuns pozitiv va insera formularul în baza de date.

<image src="./md_res/feedback.png" width="430" height="450" alt="img">

În cadrul acestui fragment se pot viziona clasamentele efectuate pe baza mediei realizate plecând de la notele acordate de fiecare utilizator prin intermediul formularului de feedback apăsând pe unul din butoanele de afișare pentru clasamentul doctorilor sau cel al spitalelor.

<image src="./md_res/clasamente.png" width="362" height="466" alt="img">

### Model de formular de feedback

<image src="./md_res/feedback_compl.png" width="390" height="410" alt="img">

### Verificarea existenței datelor introduse anterior

<image src="./md_res/dovada_insert.png" width="480" height="520" alt="img">

## Altele

### Sigla aplicației

<image src="./md_res/sigla.png" width="100" height="100" alt="img">

### Schema bazei de date

<image src="./md_res/schemaBDGen.png" width="680" height="430" alt="img">
