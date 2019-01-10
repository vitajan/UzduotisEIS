
<h1>Valiutų kursai</h1>

Lietuvos Bankas suteikia galimybę gauti oficialius valiutų kursus. Daugiau informacijos galima
rasti lb.lt puslapyje:
https://www.lb.lt/lt/kasdien-skelbiami-euro-ir-uzsienio-valiutu-santykiai-skelbia-europos-centrinis-bankas

Valiutų kursus galima parsisiųsti XML ir CSV formatais, o duomenų užklausos parametrus (pvz.
datą) galima kontroliuoti pagal URL parametrus. Atkreipkite dėmesį į kursų skelbimo procedūras
ir dienas, kai kursai neskelbiami.

<h2>Užduotis: </h2>
<ul>
  <li>Išanalizuoti kokią informaciją apie valiutų kurs galima gauti iš LBank.lt ir kaip veikia užklausos parametrai.</li>
  <li>Sukurti programą, kuri galėtų parsiųsti valiutų kursus pagal:</li>
      <ul>
      <li>nurodytas datas arba periodą (nuo, iki)</li>
      <li>nurodytų valiutų kodus.</li>
    </ul>
  <li>Programa turi pateikti nurodytų valiutų kursus ir suskaičiuoti valiutos kurso pokytį nuoperiodo pradžios iki periodo pabaigos.</li>
  <li>Duomenų saugojimas nebūtinas.</li>
</ul>

<h2>Įdiegimas</h2>
UzduotisEIS.jar failas - Java programa paleidžiama komandinėje eilutėje.

<h2>Naudojimas</h2>
Paleidus programą suvedame valiutos, kurios informaciją norime pamatyti, kodą.
<ul>
  <li>Norint pamatyti <b>vienos dienos</b> informaciją: 
  </br>Spausti "1" -> "Enter" -> suvesti datą -> "Enter" </li>
  <li>Norint pamatyti informaciją apie valiutos kursą ir jo pokytį tam tikram <b>periode</b>:
   </br>Spausti "2" -> "Enter" -> įvesti periodo pradžios datą -> "Enter" -> įvesti periodo pabaigos datą -> "Enter"</li>
</ul>

 <h4>Datos ribojimai</h4>
Jei pasirinktą dieną valiutos kursas buvo nepaskelbtas parodoma informacinė žinutė ir pateikiama paskutinis iki užklausos dienos skelbtas kursas.

Negalimas ankstesnių nei 2014 m. rugsėjo 30 d datų įvedimas.
 <h4>Formatai</h4>
 <ul><li>Data - ISO 8601 formatas, pvz. 2015-01-02 </li>
  <li>Valiutos kodas - ISO 4217 formatas, pvz. USD</li></ul>
