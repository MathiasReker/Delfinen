# Delfinen

## Indledning

Vi har prøvet at følge MCV modellen.

Der er lagt tanker i hvor en scanner skulle ligge i forhold til MVC - efter nogen forsøg er scanneren endt i en
controller. Argumentet for dette er, at når det bliver eventbaseret vil det være controller, der håndterer eventsne og
vi har lidt legede med den tanke i starten af opgaven.

Vi har haft GRASP i tankerne - især low coupling og high cohesion i form af bl.a. ansvarsfordeling mellem klasser og
hvilke klasser der kender hinanden. Ud fra vedlagte UML-diagram vil vi mene, at vi er kommet et langt stykke af vejen,
men der er klart plads til forbedringer. Vi har nogen klasser med meget ansvar, som godt kunne være mindre klasser. Se
evt. MemberController - selvom dens ansvar egentligt kun er medlemshåndtering kommer der lidt andet ind også.

## Afgrænsning

Vi vil mene, at vi har fået løst alle use-cases, som vi har fundet i opgaven samt lidt ekstra.

Vi har ikke kunne finde nogen åbenlyse problemer i programmet. Det er rimeligt robust i forhold til input fra brugeren.

Data bliver gemt løbende, så hvis programmet skulle crashe, er det forhåbentligt med minimalt tab af data.

### Medlemmer og Medlemskaber

I vores implementering er medlemmer og medlemskabet separeret. Et medlemskab indeholder data, om hvorvidt det er aktivt
eller passivt - og om det er betalt eller ej. Grundtanken er, at man i teorien således godt kan være medlem uden at have
et medlemskab - dog vil man altid have mindst et i vores implementering.

Når medlemskaber udløber, skal man aktivt sende et fornyelse anmodning, som fornyer og opretter en betalingsanmodning.
Dette vil oprette et nyt medlemskab på medlemmet af samme type (aktivt/passivt), som vil stå som ubetalt.

Medlemmer burde ikke skulle slettes, da de er bundet på resultater. Resultater ønsker vi at beholde, selvom medlemmet
ikke ønsker at være med i klubben længere. Vi har derfor tilføjet en anonymiseringsmetode for medlemmer, der ønsker at
blive udmeldt.

### Betaling

I forhold til betalings flowet benytter vi pt. den fil som vi opretter når vi laver fornyelser af medlemskab. Tanken er
her, at betaling foregår eksternt og at det eksterne system sender et svar tilbage til os med godkendte betalinger.
Herefter sætter vi medlemskabet til betalt.

### Træning og Stævne

Vi har valgt at lave en enkelt klasse til dette, da begge indeholder samme information. Klassen hedder SwimEvent. Flowet
for begge er at oprette et SwimEvent og derefter tilføje resultater til dem. Dette skyldes at vi vurderede at træninger
ligesom stævner også foregår på bestemte datoer og tidspunkter.

### Aldersgrænse

Alder skal være mellem 1 og 150. Dette er gjort for ikke at tilføje skøre datoer, hvor medlemmer kan være fx 1000 år
gamle.

### Abstract classes og interface

Vi har ikke benyttes os af selvimplementerede interface. Vi har implementeret Serializable og Comparable. Vi har ikke
fundet et sted hvor vi syntes, at vi har haft behov for et hjemmelavet interface, da abstrakt klasser løste vores
problem.