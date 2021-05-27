# Delfinen
## Indledning

Vi har prvet at følge mvc modellen.

Der er lagt tanker i hvor en scanner skulle ligge i MVC - efter nogen forsøg er det  endt i en controller.
Argumentet for dette er at når det bliver eventbaseret vil det controller der håndterer eventsne og vi lidt legede med den tanke i starten af opgaven.

Vi har haft GRASP i tankerne - især low coupling og high cohesion i form at ansvarsfordeling mellem klasser og hvilke klasser der kender hinanden.
Udfra vedlagte uml diagram vil vi mene er stykke vejen men der er klart plads til forbedringer.
Vi har nogen klasser med meget ansvar, som godt kunne være blive mindre klasser se evt MemberController - selvom dens ansvar egentligt kun er medlemshåndtering.

## Afgrænsning
Vi vil mene at vi har fået løst alle uses cases fundet i opgaven og lidt ekstra.

Vi har kunne finde nogen åbenlyse problemer i programmet.
Det er rimeligt robust i forhold til input fra brugeren.

Data bliver gemt løbende så hvis det skulle crashe er det forhåbenligt med minimanlt tab af data.

### Medlemmer og Medlemskaber
I vores er medlemmer og medlemskabet separeret.
Et medlemskab indeholder data om det er aktivt eller passivt og om det er betalt eller ej.
Grund tanken er at man i teorien således godt kan være medlem uden at have et medlemskab - dog vil man altid have mindst et i vores implementering.

Når medlemskaber udløber skal man aktivt sende et forsyelseanmodning, som fornyer og laver en betalingsanmodning. Dette vil give lave et nyt medlemskab på medlemmet af samme type some tidligere som er ubetalt.

Medlemmer burde ikke skulle slettes da de er bundet på resultater, som vi ønsker at beholder selvom medlemmet ikke ønsker at være med i klubben længere.
Vi har derfor tilføjet en anonymiserings metode for medlemmer der ikke ønsker at være med mere.

### Betaling
I forhold til betalingsflowet benytter vi pt den fil som vi opretter når vi laver fornyelser af medlemskab.
Tanken er her at betaling foregår eksternt og at det eksterne system så sender et svar tilbage til os med godkendte betalinger. Hvorefter vi så sætter medlemskabet til betalt.

### Træning og Stævne
Vi har valgt at lave en enkelt klasse til dette, da begge indeholder samme information.
Denne hedder SwimEvent.
Så flowet for begge er at oprette et SwimEvent og derefter tilføje resultater til dem.
Dette skyldes vi vuderede at træninger ligesom stævner også foregår på bestemte datoer og tidspunkter.

### Aldersgrænse
Alder skal være mellem 1 og 150 det er sat for ikke tilføje skøre datoer.

###  Abstract classes og interface
Vi har ikke benyttes os af selvimplemterede interface, vi har impleteret Serializable og Comparable.
Vi har ikke fundet et sted hvor vi syntes vi have behov for et hjemmelavet interface, da abstact klasser løste vores problem.