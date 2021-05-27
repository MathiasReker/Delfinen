# Delfinen

## Indledning

Vi har prøvet at følge MCV modellen.

Der er lagt tanker i hvor en scanner skulle ligge i forhold til MVC - efter nogen forsøg er det endt i en controller. 
Argumentet for dette er at når det bliver eventbaseret vil det være controller der håndterer eventsne og vi har lidt legede med den tanke i
starten af opgaven.

Vi har haft GRASP i tankerne - især low coupling og high cohesion i form af bl.a. ansvarsfordeling mellem klasser og hvilke
klasser der kender hinanden. Udfra vedlagte UML diagram vil vi mene at vi er kommet et langt stykke vejen, men der er klart plads til
forbedringer. Vi har nogen klasser med meget ansvar som godt kunne være mindre klasser, se evt MemberController -
selvom dens ansvar egentligt kun er medlemshåndtering, kommer der lidt andet ind også.

## Afgrænsning

Vi vil mene at vi har fået løst alle use cases vi har fundet i opgaven og lidt ekstra.

Vi har ikke kunne finde nogen åbenlyse problemer i programmet. Det er rimeligt robust i forhold til input fra brugeren.

Data bliver gemt løbende så hvis det skulle crashe er det forhåbentligt med minimanlt tab af data.

### Medlemmer og Medlemskaber

I vores implementering er medlemmer og medlemskabet separeret. Et medlemskab indeholder data om det er aktivt eller passivt og om det
er betalt eller ej. Grundtanken er at man i teorien således godt kan være medlem uden at have et medlemskab - dog vil
man altid have mindst et i vores implementering.

Når medlemskaber udløber skal man aktivt sende et fornyelseanmodning, som fornyer og laver en betalingsanmodning. Dette
vil lave et nyt medlemskab på medlemmet af samme type som tidligere som er ubetalt.

Medlemmer burde ikke skulle slettes da de er bundet på resultater, som vi ønsker at beholder selvom medlemmet ikke
ønsker at være med i klubben længere. Vi har derfor tilføjet en anonymiserings metode for medlemmer der ønsker at blive udmeldt.

### Betaling

I forhold til betalingsflowet benytter vi pt den fil som vi opretter når vi laver fornyelser af medlemskab. Tanken er
her at betaling foregår eksternt og at det eksterne system så sender et svar tilbage til os med godkendte betalinger.
Hvorefter vi så sætter medlemskabet til betalt.

### Træning og Stævne

Vi har valgt at lave en enkelt klasse til dette, da begge indeholder samme information. Denne hedder SwimEvent. Så
flowet for begge er at oprette et SwimEvent og derefter tilføje resultater til dem. Dette skyldes at vi vuderede at
træninger ligesom stævner også foregår på bestemte datoer og tidspunkter.

### Aldersgrænse

Alder skal være mellem 1 og 150, dette er gjort for ikke tilføje skøre datoer, hvor medlememr kan være fx 1000 år gamle.

### Abstract classes og interface

Vi har ikke benyttes os af selvimplemterede interface, vi har impleteret Serializable og Comparable. Vi har ikke fundet
et sted hvor vi syntes vi have behov for et hjemmelavet interface, da abstact klasser løste vores problem.
