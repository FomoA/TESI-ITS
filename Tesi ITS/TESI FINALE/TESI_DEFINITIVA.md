# ISTITUTO TECNOLOGICO SUPERIORE ACADEMY
## PER LE TECNOLOGIE DELLA INFORMAZIONE E DELLA COMUNICAZIONE DEL PIEMONTE
### Sede legale: Torino, Piazza Carlo Felice 18

---

**DENOMINAZIONE CORSO**: Tecnico Superiore Sviluppatore Software — Insurance Software Developer

---

# Application Maintenance e Troubleshooting nell'ecosistema VITA di Reale ITES
## Metodologia, incident analysis e lessons learned

---

**Candidato**: FOMO KAMKUIMO ACHILLE

---

---

## INDICE

- [Introduzione](#introduzione)
- [Capitolo 1 — Contesto operativo e framework metodologico](#capitolo-1)
  - [1.1 — Reale ITES e il Gruppo Reale Mutua](#11)
  - [1.2 — Il framework SAFe in Reale ITES](#12)
  - [1.3 — Il team VITA: ruoli e responsabilità](#13)
  - [1.4 — Gli strumenti operativi](#14)
- [Capitolo 2 — Architettura IT dell'ecosistema VITA](#capitolo-2)
  - [2.1 — Il perimetro applicativo](#21)
  - [2.2 — Passportal: il portale contenitore](#22)
  - [2.3 — VITES: il frontend operativo](#23)
  - [2.4 — BFF: Backend For Frontend](#24)
  - [2.5 — BSA Vita: il sidecar moderno](#25)
  - [2.6 — BSA VitaLegacy: il wrapper SOAP](#26)
  - [2.7 — VIS: il sistema core legacy](#27)
  - [2.8 — AIA: il backend moderno del portafoglio](#28)
  - [2.9 — PPEVO e Anagrafe Unica](#29)
  - [2.10 — Infrastruttura di logging e observability](#210)
- [Capitolo 3 — Ciclo di vita del software in Reale ITES](#capitolo-3)
  - [3.1 — Evolutivo e correttivo](#31)
  - [3.2 — Incident: definizione e lifecycle](#32)
  - [3.3 — Defect: definizione e classificazioni](#33)
  - [3.4 — Ambienti di deployment](#34)
  - [3.5 — Il metodo di troubleshooting](#35)
- [Capitolo 4 — Le attività svolte nel perimetro VITA](#capitolo-4)
  - [4.1 — Application Maintenance](#41)
  - [4.2 — Troubleshooting e analisi log](#42)
  - [4.3 — Gestione dei defect](#43)
  - [4.4 — Unit Testing: ProposteApiTest.java](#44)
- [Capitolo 5 — Case study: l'incident AVC e il defect 357020](#capitolo-5)
  - [5.1 — Analisi iniziale](#51)
  - [5.2 — Osservazione](#52)
  - [5.3 — Prova e Controprova](#53)
  - [5.4 — Lessons learned](#54)
- [Capitolo 6 — Case study: il cluster incident Teseo](#capitolo-6)
  - [6.1 — Il cluster: tre incident correlati](#61)
  - [6.2 — Analisi iniziale](#62)
  - [6.3 — Osservazione](#63)
  - [6.4 — Prova e Controprova](#64)
  - [6.5 — La soluzione implementata](#65)
  - [6.6 — Il secondo failure mode: tipo di ritorno errato nel catch](#66)
  - [6.7 — Lessons learned](#67)
- [Capitolo 7 — Case study: riscatto totale e data integrity (I-2026-0218-0324)](#capitolo-7)
  - [7.1 — Analisi iniziale](#71)
  - [7.2 — Osservazione](#72)
  - [7.3 — Prova e Controprova](#73)
  - [7.4 — Lessons learned](#74)
- [Conclusioni](#conclusioni)
- [Appendice A — Glossario tecnico](#appendice-a)
- [Appendice B — Mapping architettura → incident → root cause](#appendice-b)
- [Appendice C — ProposteApiTest.java: analisi del codice](#appendice-c)
- [Appendice D — Elenco degli screenshot da inserire](#appendice-d)
- [Bibliografia](#bibliografia)

---

## INTRODUZIONE {#introduzione}

Il presente lavoro di tesi è sviluppato da Achille Fomo Kamkuimo, studente del corso Tecnico Superiore Sviluppatore Software — Insurance Software Developer presso l'ITS ICT Piemonte di Torino, nell'ambito del tirocinio formativo svolto in Reale ITES S.p.A., società tecnologica del Gruppo Reale Mutua.

Il tirocinio si è svolto all'interno del team di sviluppo e manutenzione dell'ecosistema applicativo VITA, dedicato al ramo assicurativo Vita del Gruppo. Le attività hanno riguardato l'Application Maintenance: gestione degli incident, analisi e risoluzione dei defect, troubleshooting su sistemi distribuiti, e scrittura di unit test.

La tesi adotta un principio strutturale non negoziabile: l'architettura non viene mai presentata in forma astratta. Ogni componente è descritto nella sua funzione tecnica reale e, dove applicabile, collegato a un incident che ne ha messo in evidenza il comportamento.

**Struttura del documento.** Il Capitolo 1 descrive il contesto operativo (Reale ITES, framework SAFe, team e ruoli). Il Capitolo 2 presenta l'architettura IT dell'ecosistema VITA nella sua configurazione reale e corretta. Il Capitolo 3 illustra il ciclo di vita del software in Reale ITES, con la distinzione tra incident e defect e il metodo operativo di troubleshooting. Il Capitolo 4 descrive le attività svolte. I Capitoli 5, 6 e 7 analizzano tre case study reali applicando il framework Analisi-Osservazione-Prova/Controprova. Le Conclusioni consolidano le competenze acquisite.

**Note sulla riservatezza.** In conformità con le policy di riservatezza aziendale e GDPR, ogni dato personale è stato anonimizzato. I nomi dei contraenti sono ridotti alle iniziali (es. Contraente E.F.). I numeri di polizza sono parzialmente oscurati (es. Polizza 206XXXX). I nomi dei colleghi sono sostituiti con il ruolo operativo. I nomi dei sistemi applicativi, i pattern architetturali, gli status code HTTP e le root cause tecniche sono mantenuti intatti: sono indispensabili alla comprensione tecnica degli incident.

---

## CAPITOLO 1 — CONTESTO OPERATIVO E FRAMEWORK METODOLOGICO {#capitolo-1}

### 1.1 — Reale ITES e il Gruppo Reale Mutua {#11}

Reale ITES S.p.A. è la società tecnologica del Gruppo Reale Mutua, responsabile dello sviluppo e della manutenzione dei sistemi informativi del Gruppo. Il Gruppo Reale Mutua opera nel settore assicurativo e comprende diverse compagnie (tra cui Reale Mutua Assicurazioni e Italiana Assicurazioni), ciascuna con il proprio portafoglio polizze gestito su sistemi distinti.

Il tirocinio si è svolto nel team responsabile dell'ecosistema VITA, che gestisce le applicazioni operative del ramo Vita per gli operatori di agenzia.

### 1.2 — Il framework SAFe in Reale ITES {#12}

Il team VITA opera nel framework **SAFe (Scaled Agile Framework)** [4], adottato da Reale ITES per coordinare lo sviluppo software a scala aziendale. SAFe organizza il lavoro in **Program Increment (PI)** di durata trimestrale, suddivisi in Sprint di due settimane. Il framework sincronizza i team attraverso cerimonie codificate.

**Cerimonie standard del team:**

| Cerimonia | Frequenza | Obiettivo |
|-----------|-----------|-----------|
| Daily Scrum | Giornaliera | Sincronizzazione sullo stato dei task, identificazione dei blocchi |
| Sprint Planning | Inizio sprint | Selezione e pianificazione delle user story dal backlog |
| Sprint Review / Demo | Fine sprint | Dimostrazione delle funzionalità completate |
| Sprint Retrospective | Fine sprint | Analisi del processo, identificazione di miglioramenti |
| PI Planning | Ogni PI (~12 settimane) | Pianificazione degli obiettivi del Program Increment |

Il **backlog** del team è gestito su **Azure DevOps**. Gli incident e i defect vengono tracciati nel sistema di ticketing aziendale, che costituisce la fonte di verità per lo stato delle problematiche e delle lavorazioni.

Il framework SAFe nel contesto del team VITA è integrato con il processo di **Application Maintenance**: gli incident di produzione vengono trattati come work item prioritari, inseriti nel flusso di lavoro del team attraverso un processo di triage strutturato che verrà descritto nel Capitolo 3.

### 1.3 — Il team VITA: ruoli e responsabilità {#13}

Il team che gestisce l'ecosistema VITA è composto da figure con ruoli e responsabilità distinte, tutte operanti all'interno del framework SAFe.

**System Architect**
Il System Architect è responsabile della visione architetturale del treno Agile. Definisce gli standard tecnici, valuta l'impatto degli incident sull'ecosistema, e autorizza gli interventi che richiedono accesso in produzione. Non sviluppa direttamente il codice operativo. Collabora con il Product Management per tradurre le esigenze di business in requisiti tecnici e partecipa alle war room per gli incident critici.

**Business Analyst (BA)**
Il Business Analyst supporta il team nella verifica dei processi e delle soluzioni. Si occupa di raccogliere e uniformare la documentazione che rappresenta il requisito di business, contribuisce alla pianificazione delle attività dello Sprint e supporta la stesura di test case. Traduce i requisiti di business in user story con acceptance criteria. È il punto di riferimento per la classificazione "Works as designed" di un defect: quando un comportamento applicativo risulta conforme a una specifica di business intenzionale, è il BA a fornire la conferma tecnico-funzionale.

**IT Analyst**
L'IT Analyst raccoglie e formalizza i requisiti funzionali, gestisce l'**Help Desk di terzo livello (HD3)**, esegue test manuali, e mantiene la documentazione tecnica. Presidia le attività dei fornitori esterni e collabora con il team di sviluppo per la realizzazione del prodotto. Monitora continuamente le funzionalità dei sistemi.

**Developer (Sviluppatore)**
Il developer è responsabile dell'implementazione delle feature e dei fix. Opera sull'intera catena applicativa: VITES (Angular), BFF (Java SpringBoot), BSA (Java SpringBoot + Maven). Esegue debug locale, scrive e mantiene gli unit test (JUnit 5 + Mockito), gestisce il deploy tramite le pipeline CI/CD configurate su Azure DevOps.

> **Nota sul tirocinio.** L'apprendista ha operato nelle attività di Application Maintenance e, in misura minore, nell'evolutivo. Per le attività correttive: analisi degli incident, troubleshooting su log Splunk, replicazione dei problemi in SYSR2 e PREP, supporto alla gestione dei defect. Per le attività evolutive: sviluppo in branch locale, con il push verso Azure DevOps subordinato alla revisione e approvazione del referente tecnico senior prima dell'avvio della pipeline CI.

### 1.4 — Gli strumenti operativi {#14}

| Strumento | Utilizzo |
|-----------|----------|
| **Azure DevOps** | Backlog, user story, tracking incident e defect, pipeline CI/CD |
| **Splunk** | Centralizzazione e analisi dei log applicativi; interrogazione per Correlation-ID |
| **Grafana** | Dashboard di monitoraggio in tempo reale degli ambienti PREP e PROD |
| **Swagger** | Interfaccia per test delle API REST esposte dai BSA e dal BFF |
| **Postman** | Test manuali delle API per la replicazione degli incident |
| **Git / Azure Repos** | Source control; branch per feature/fix; push verso pipeline |

---

## CAPITOLO 2 — ARCHITETTURA IT DELL'ECOSISTEMA VITA {#capitolo-2}

### 2.1 — Il perimetro applicativo {#21}

L'ecosistema VITA supporta il ramo assicurativo Vita del Gruppo Reale Mutua. Comprende le applicazioni utilizzate dagli operatori di agenzia per la preventivazione, l'emissione, la consultazione e la gestione delle polizze.

L'architettura si articola su due path principali:

- **Path moderno (AIA)**: gestione emissione e postvendita delle polizze del portafoglio AIA, backend moderno.
- **Path legacy (VIS)**: consultazione e gestione delle polizze del portafoglio storico VIS, incluso il fondo pensione Teseo.

```
[SCREENSHOT/DIAGRAMMA — Fig. 1: Diagramma architetturale dell'ecosistema VITA.
Catena di chiamata: Utente → Passportal (iFrame) → VITES (Angular SPA) →
BFF (Java SpringBoot, POD AKS) → /invocaBsa →
  Path A: BSA Vita → servizi AIA → Oracle DB
  Path B: BSA VitaLegacy → Oracle SALT → Oracle Tuxedo → (VIS) → Oracle DB VIS
Integrazioni laterali: BSA Anagrafe (Anagrafe Unica), BSA Documentale, BSA Questionario Adeguatezza (PPEVO).
Evidenziare i due path con colori distinti. Mostrare il POD AKS come confine di deployment.]
```

**Tabella 1 — Confronto tra path moderno (AIA) e path legacy (VIS)**

| Dimensione | Path moderno — AIA | Path legacy — VIS |
|------------|--------------------|--------------------|
| Backend | BSA Vita → servizi AIA | BSA VitaLegacy → VIS via Oracle SALT |
| Protocollo BSA→backend | REST/JSON → servizi AIA | REST/JSON → SOAP/XML via Oracle SALT |
| Stack backend | Java SpringBoot + Oracle DB | Cobol + Oracle Tuxedo + Oracle SQL |
| Operazioni | Emissione (PIP), postvendita AIA | Consultazione storico, incassi, riscatti, Teseo |
| Failure mode documentato | Timeout su servizi AIA (Cap. 5) | Routing errato compagnia/agenzia (Cap. 6) |

### 2.2 — Passportal: il portale contenitore {#22}

Passportal è il portale applicativo aziendale che funge da shell per i vertical applicativi del Gruppo. L'utente accede a Passportal tramite autenticazione federata **SSO (Single Sign-On)** e da lì naviga verso i diversi applicativi.

VITES è ospitato all'interno di Passportal tramite **iFrame**: Passportal non integra VITES come micro-frontend, ma lo racchiude in un contesto isolato tramite elemento HTML iFrame. Questa scelta architetturale ha implicazioni sulla gestione del routing: il path verso il quale Passportal punta l'iFrame determina quale componente VITES viene caricato. Un errore di configurazione del routing in Passportal — come documentato nel cluster incident Teseo (Capitolo 6) — può causare il caricamento del componente VITES errato, producendo comportamenti anomali anche quando il codice applicativo è corretto.

Le responsabilità di Passportal sono:
- **Routing** delle richieste: mapping tra URL esposti e componenti applicativi ospitati
- **Autenticazione SSO**: gestione del token di sessione condiviso
- **Contenimento**: isolamento dei vertical applicativi in contesti separati

### 2.3 — VITES: il frontend operativo {#23}

VITES è il frontend applicativo dedicato al ramo Vita. È una **Single Page Application (SPA) scritta interamente in Angular**. Non include componenti React.

Il modulo VITES comprende due parti deployate come **unico pacchetto**:
- Il **frontend Angular**: l'interfaccia utente
- Il **BFF (Backend For Frontend)**: un'applicazione Java SpringBoot che serve il frontend al client e gestisce la comunicazione con i backend

L'intero pacchetto VITES+BFF gira su un **POD AKS (Azure Kubernetes Service)**, senza utilizzo di CDN separata.

L'interfaccia viene utilizzata dagli operatori di agenzia per:
- Wizard di preventivazione e emissione polizze (portafoglio AIA: attualmente il prodotto PIP di Italiana Assicurazioni; la migrazione da VIS è in corso)
- Consultazione polizze (portafoglio VIS legacy: storiche, fondi pensione Teseo)
- Compilazione dei questionari AVC (Adeguata Verifica della Clientela)
- Gestione delle operazioni postvendita (riscatti, variazioni, incassi)

L'UI di VITES è costruita dinamicamente a partire da **descrittori JSON** restituiti dal BFF [6], disaccoppiando la logica di presentazione dalla logica di business. Un vincolo architetturale fondamentale governa VITES: la comunicazione avviene **esclusivamente verso il BFF**. Nessuna chiamata diretta viene effettuata verso i backend a valle.

### 2.4 — BFF: Backend For Frontend {#24}

Il BFF è l'applicazione **Java SpringBoot** che costituisce il punto di accesso unico tra il frontend VITES e i backend dell'ecosistema. Gira sullo stesso POD AKS di VITES.

Il BFF svolge le seguenti funzioni:

- **Autenticazione e autorizzazione**: verifica del token SSO, controllo dei permessi dell'utente
- **Routing verso i BSA**: tramite l'endpoint `/invocaBsa`, il BFF instrada le richieste del frontend verso il BSA competente
- **Session management**: gestione della sessione utente tra frontend e backend
- **Logging**: generazione dei log di frontiera (START/END per ogni chiamata) in formato Semantic Log JSON, inviati a Splunk
- **Orchestrazione**: per alcune operazioni specifiche (es. stampe), il BFF coordina chiamate verso più endpoint

L'endpoint `/invocaBsa` è il controller centrale del BFF: riceve le richieste da VITES e le smista verso uno dei seguenti BSA, in base al parametro della richiesta:

| BSA | Funzione |
|-----|----------|
| BSA Vita | Portafoglio moderno AIA: emissione, preventivazione, questionari AVC AIA |
| BSA VitaLegacy | Portafoglio legacy VIS: consultazione polizze storiche, Teseo, questionari AVC VIS |
| BSA Anagrafe | Gestione anagrafiche contraenti, ricerca per codice fiscale |
| BSA Documentale | Gestione documentale, archiviazione |
| BSA Questionario Adeguatezza | Integrazione con PPEVO per questionari evoluti |
| BSA ServiziComuni | Servizi condivisi trasversali |

Il BFF non implementa logica di business propria. La logica applicativa risiede nei BSA, nei servizi AIA e nei servizi VIS.

**Infrastruttura Swagger per test API:**

| Ambiente | URL Swagger |
|----------|-------------|
| STR2 | `http://ls001s01-00-api.rmasede.grma.net:8888` |
| PREP | `https://vipprep-apiext.rmasede.grma.net/gateway` (certificato self-signed) |
| PREP (pubblico) | `https://api-gateway-pre.grupporealemutua.it/gateway` (certificato GlobalSign) |

### 2.5 — BSA Vita: il sidecar moderno {#25}

Il **BSA Vita (Business Sidecar for API)** è un'applicazione **Java SpringBoot** con gestione delle dipendenze tramite **Maven**. Non è un insieme di microservizi indipendenti: è un singolo applicativo che espone molteplici endpoint REST organizzati per dominio funzionale.

Il BSA Vita espone principalmente endpoint **REST/JSON** e gestisce le operazioni sul portafoglio moderno AIA:
- Preventivazione e quotazione di nuove polizze
- Emissione polizze: allo stato attuale (aprile 2026) è attiva esclusivamente l'emissione del prodotto **PIP (Piano Individuale Pensionistico)** di Italiana Assicurazioni. La migrazione progressiva degli altri prodotti da VIS ad AIA è in corso.
- Gestione dei questionari AVC per il portafoglio AIA
- Operazioni postvendita: variazioni, riscatti, aggiornamenti stato contrattuale

**Flusso dati:**

```
VITES → BFF (/invocaBsa) → BSA Vita → servizi AIA → Oracle DB (AIA)
```

Il BSA Vita **non accede direttamente al database**. Tutte le operazioni di persistenza avvengono tramite i servizi esposti da AIA, che a loro volta gestiscono la logica applicativa e le operazioni sul database Oracle di AIA.

### 2.6 — BSA VitaLegacy: il wrapper SOAP {#26}

Il **BSA VitaLegacy** è un'applicazione **Java SpringBoot + Maven** che funge da traduttore tra il protocollo REST (usato da VITES e BFF) e il protocollo SOAP (usato da VIS). È l'elemento che permette al frontend moderno di comunicare con il sistema core legacy senza conoscerne i dettagli di implementazione.

**Stack di comunicazione completo:**

```
VITES → BFF → BSA VitaLegacy → Oracle SALT → Oracle Tuxedo → (VIS) → Oracle SQL (VIS)
              [JSON/REST]      [SOAP/XML]   [WSDL/Gateway]  [Middleware]   [DB]
```

Il BSA VitaLegacy riceve richieste **REST/JSON** dal BFF e le converte in richieste **SOAP/XML** verso i Web Service esposti da Oracle SALT. Oracle SALT (Service Architecture Leveraging Tuxedo) è il gateway che espone i servizi dello strato Oracle Tuxedo come WSDL/SOAP. Oracle Tuxedo è il middleware transazionale che esegue i programmi scritti in **Cobol** del sistema VIS.

Il BSA VitaLegacy gestisce le operazioni sul portafoglio legacy VIS:
- Consultazione polizze storiche (per codice polizza, per contraente)
- Ricerca incassi e movimenti
- Verifica e salvataggio questionari AVC su polizze VIS
- Operazioni sul fondo pensione Teseo
- Riscatti (su polizze la cui operazione transita attraverso VIS)

### 2.7 — VIS: il sistema core legacy {#27}

VIS è il sistema core assicurativo legacy. Contiene il motore di calcolo storico e il portafoglio polizze del Gruppo emesse prima della migrazione verso AIA.

**Stack tecnologico:**
- Linguaggio applicativo: **Cobol**
- Middleware transazionale: **Oracle Tuxedo**
- Esposizione servizi: **Oracle SALT** (Service Architecture Leveraging Tuxedo), che espone i servizi Tuxedo come endpoint WSDL accessibili tramite protocollo SOAP
- Database: **Oracle SQL**

Una caratteristica strutturale rilevante per la comprensione degli incident: le polizze del portafoglio Teseo di Italiana Assicurazioni (prodotto fondo pensione) sono gestite nel database VIS sotto la **Compagnia RMA** (Reale Mutua) e una **agenzia fittizia dedicata (906)**, non coincidente con l'agenzia operativa dell'utente. Questa mappatura non è documentata in modo esplicito nel codice del BSA VitaLegacy originale, il che ha generato il cluster di incident descritto nel Capitolo 6.

### 2.8 — AIA: il backend moderno del portafoglio {#28}

AIA è il **backend moderno** di una parte del portafoglio Vita. Non è un sistema documentale: è il vero backend applicativo che gestisce le polizze del portafoglio moderno, con la propria logica di business, i propri servizi esposti e il proprio database Oracle.

**Funzioni principali di AIA:**
- Gestione dell'emissione di nuove polizze: attualmente attiva per il solo prodotto **PIP** (Piano Individuale Pensionistico). L'emissione degli altri prodotti avviene ancora tramite VIS.
- Gestione delle operazioni postvendita per il portafoglio AIA
- Generazione degli XML per le stampe dei documenti di polizza, tramite servizi dedicati
- Esposizione di servizi REST e SOAP verso i BSA

La migrazione del portafoglio da VIS ad AIA è un processo incrementale e in corso: non avviene in un unico rilascio, ma per prodotto. Attualmente solo il PIP è gestito su AIA per l'emissione. Questo spiega la coesistenza dei due path (BSA Vita → AIA e BSA VitaLegacy → VIS) nell'ecosistema attuale, e perché la maggior parte del portafoglio storico transita ancora da VIS.

### 2.9 — PPEVO e Anagrafe Unica {#29}

**PPEVO** è un sistema esterno che gestisce i questionari di adeguatezza in modalità evoluta per determinate tipologie di polizze. Il BSA VitaLegacy lo chiama direttamente tramite REST per le operazioni AVC sulle polizze Teseo, usando i **codici compagnia e agenzia di login originali** (non quelli VIS interni). Questo è rilevante per il cluster Teseo: la sostituzione dei codici compagnia/agenzia nel BSA viene applicata solo verso i Web Service VIS, non verso PPEVO, perché PPEVO non conosce la mappatura interna VIS delle polizze Teseo ITA.

**Anagrafe Unica (AU)** è il sistema centralizzato di gestione delle anagrafiche contraenti. L'accesso avviene tramite **BSA Anagrafe**. La chiave di ricerca primaria è il **codice fiscale**, non il codice nominativo interno (knmnv) che invece è specifico per ogni sistema (VIS e AU hanno codici nominativi diversi per lo stesso soggetto).

### 2.10 — Infrastruttura di logging e observability {#210}

La diagnostica degli incident si fonda sull'infrastruttura di logging e monitoring aziendale [7][8].

**Splunk** centralizza tutti i log applicativi con una retention di 180 giorni. Ogni chiamata in ingresso e uscita dal BFF genera:
- Un record **START**: timestamp, nome del servizio invocato
- Un record **END**: timestamp, elapsed time (in millisecondi), esito (OK/KO)

Il **Correlation-ID** è il campo che permette di correlare tutti i record di log appartenenti alla stessa catena di chiamata: una singola azione utente su VITES genera una sequenza di chiamate (BFF → BSA → backend), tutte tracciabili con lo stesso Correlation-ID.

Il formato obbligatorio per le nuove applicazioni è il **Semantic Log JSON** [7], che consente query efficienti su Splunk.

**Grafana** fornisce le dashboard di monitoraggio in tempo reale per gli ambienti PREP e PROD, con metriche aggregate su throughput, latenza e tasso di errore per ciascun BSA.

**Tabella 2 — Struttura del log di frontiera (BFF)**

| Campo | Tipo | Descrizione |
|-------|------|-------------|
| Timestamp | ISO 8601 | Data e ora della registrazione |
| Correlation-ID | UUID | Identificatore univoco della catena di chiamata |
| Tipo record | START / END | Inizio o fine operazione |
| Elapsed | Long (ms) | Tempo trascorso (presente solo su record END) |
| Esito | OK / KO | Risultato. KO indica eccezione o timeout |
| Servizio | String | Nome dell'endpoint BSA invocato |

---

## CAPITOLO 3 — CICLO DI VITA DEL SOFTWARE IN REALE ITES {#capitolo-3}

### 3.1 — Evolutivo e correttivo {#31}

Il lavoro del team VITA si articola su due modalità operative distinte:

- **Evolutivo**: implementazione di nuove funzionalità o miglioramenti. Origina da user story nel backlog, pianificate in Sprint Planning. Esempio: l'attivazione della nuova funzionalità di emissione polizze PIP su AIA, che ha richiesto lo sviluppo di nuovi endpoint nel BSA Vita e l'aggiornamento dei descrittori UI in VITES.

- **Correttivo (Application Maintenance)**: analisi e risoluzione di anomalie in produzione. Origina da incident segnalati dagli utenti (operatori di agenzia) attraverso il sistema di ticketing. Include anche la gestione dei defect rilevati nelle fasi di test.

La distinzione è operativa: il correttivo ha priorità assoluta sui task evolutivi quando un incident impatta utenti in produzione. Il triage dell'incident determina la priorità di intervento e l'assegnazione al gruppo di competenza.

### 3.2 — Incident: definizione e lifecycle {#32}

Un **incident** è una segnalazione di anomalia proveniente da un utente (operatore di agenzia, HD di primo o secondo livello) o da un sistema di monitoring che rileva un comportamento diverso dal previsto in produzione.

**Lifecycle di un incident:**

```
Apertura → Triage (HD1/HD2) → Assegnazione HD3 → Analisi tecnica →
  → [Root cause trovata] → Fix / Configurazione / Escalation →
  → Rilascio in produzione → Chiusura
```

Il team di sviluppo VITA opera come **Help Desk di terzo livello (HD3)**. I ticket arrivano già filtrati dall'HD1 (operatori di primo contatto) e HD2. Una delle prime attività di triage è verificare che il ticket contenga informazioni sufficienti per la replicazione del problema: se le informazioni mancano, il ticket viene respinto con richiesta di completamento. Questa fase è documentata nel Capitolo 5 (l'incident AVC fu respinto due volte per mancanza di contesto).

**Classificazioni di chiusura di un incident:**

| Classificazione | Significato |
|-----------------|-------------|
| Risolto con fix software | Codice modificato e rilasciato |
| Risolto con configurazione | Parametro o routing corretto senza modifica al codice |
| Works as designed | Il comportamento segnalato è intenzionale, conforme a specifica |
| Non riproducibile | Il problema non si verifica in ambiente di test con le informazioni disponibili |
| Escalation | Problema fuori dal perimetro HD3, passato a team infrastruttura o fornitore |

### 3.3 — Defect: definizione e classificazioni {#33}

Un **defect** è un'anomalia identificata nelle fasi di test (pre-produzione) o durante l'analisi di un incident, che corrisponde a un comportamento del software divergente dalle specifiche funzionali. A differenza di un incident (che origina da un utente di produzione), un defect origina tipicamente da un'attività di testing o da un'analisi tecnica approfondita.

**Caratteristica rilevante**: un defect può essere chiuso come **"Works as designed"** se, dopo confronto con il Business Analyst e il referente di business, si determina che il comportamento segnalato è conforme a una specifica intenzionale. Questo avviene frequentemente per regole di compliance (es. normativa antiriciclaggio, disciplina PEP). Il Defect 357020 documentato nel Capitolo 5 è un esempio: il blocco della modifica del beneficiario dopo la compilazione dell'AVC è una misura di compliance PEP, non un malfunzionamento.

### 3.4 — Ambienti di deployment {#34}

**Tabella 3 — Ambienti dell'ecosistema VITA**

| Ambiente | Tipo | Caratteristiche | Uso nel tirocinio |
|----------|------|-----------------|-------------------|
| SYSR2 | Test — Green Zone | Accesso diretto al server, no VIP, no certificati ufficiali | Prima replicazione degli incident, debug locale, test unitari |
| PREP | Pre-produzione — DMZ | VIP con certificato GlobalSign (CA ufficiale); raggiungibile da intranet e da Internet | Test di integrazione, certificazione release, test end-to-end del cluster Teseo pre-rilascio |
| PROD | Produzione | Ambiente live, accesso utenti reali | Unico ambiente di rilascio; per l'incident I-2026-0218-0324: intervento GET diretto autorizzato dal System Architect |

Il processo di deploy verso PROD avviene tramite pipeline CI/CD su Azure DevOps. Ogni rilascio in PROD richiede:
1. Superamento dei test automatizzati in CI
2. Deployment e certificazione in PREP
3. Autorizzazione del System Architect (per rilasci correttivi urgenti: autorizzazione esplicita)
4. Esecuzione della pipeline di deploy

### 3.5 — Il metodo di troubleshooting {#35}

Il metodo operativo adottato per la gestione degli incident segue un workflow strutturato in fasi sequenziali:

**Fase 1 — Analisi del ticket**
Lettura del ticket nell'applicativo di ticketing. Verifica della completezza delle informazioni: componente segnalato, operazione in errore, utente impattato, passi per replicare. Se le informazioni sono insufficienti, il ticket viene restituito con richiesta di completamento (come avvenuto per l'incident I-2026-0119-0317, respinto due volte).

**Fase 2 — Analisi dei log su Splunk**
Interrogazione di Splunk con il Correlation-ID della chiamata fallita. Identificazione del record END con esito KO: lettura dell'elapsed time e del nome del servizio. Confronto con il record START per determinare a quale livello della catena si è verificato il problema.

**Fase 3 — Replicazione in ambiente SYSR2**
Tentativo di replicare il problema nell'ambiente di test SYSR2. Se il problema è riproducibile, si procede. Se non è riproducibile in SYSR2 (es. perché dipende da dati di produzione specifici), si tenta in PREP con dati rappresentativi.

**Fase 4 — Isolamento del layer in failure**
Identificazione del componente preciso della catena in failure: Passportal? VITES? BFF? BSA Vita? BSA VitaLegacy? Chiamata ai servizi AIA? Chiamata ai WS VIS? Se il layer è nel perimetro BSA o BFF, replicazione in locale tramite Swagger o Postman.

**Fase 5 — Debug e fix in locale**
Debug del codice Java in locale per isolare la riga che produce il comportamento errato. Scrittura del fix. Retest locale con Postman o test JUnit.

**Fase 6 — Push e deploy in ambiente di test**
Push del branch evolutivo locale verso Azure DevOps, previa revisione e approvazione del referente tecnico senior (code review). Avvio della pipeline CI. Deploy in SYSR2, poi in PREP. Esecuzione dei test end-to-end in PREP.

**Fase 7 — Deploy in produzione**
Deploy in PROD tramite pipeline con autorizzazione del System Architect. Verifica post-deploy su Grafana e Splunk.

**Fase 8 — Valutazione del perimetro**
Se durante l'analisi si determina che il problema risiede in un layer fuori dal perimetro del team (infrastruttura, sistema terzo, VIS core), il ticket viene riassegnato al gruppo competente con documentazione completa dei findings. Questa attività di **instradamento strutturato** è tanto importante quanto il fix diretto.

```
[SCREENSHOT/DIAGRAMMA — Fig. 2: Flow chart del metodo di troubleshooting.
Nodi: Analisi ticket → Log Splunk → Replicabile in SYSR2? → Isola layer →
Debug locale → Fix → Test locale → Approvazione push → Pipeline SYSR2/PREP → Pipeline PROD.
Branch alternativo: Perimetro esterno → Riassegna con documentazione.]
```

---

## CAPITOLO 4 — LE ATTIVITÀ SVOLTE NEL PERIMETRO VITA {#capitolo-4}

### 4.1 — Application Maintenance {#41}

L'attività principale svolta durante il tirocinio è l'**Application Maintenance** dell'ecosistema VITA. Questa attività consiste nella gestione continua delle anomalie di produzione e nel mantenimento della qualità operativa del sistema.

Le attività di Application Maintenance si articolano in:

- **Triage degli incident**: analisi dei ticket in ingresso, verifica della completezza delle informazioni, prima classificazione del problema (tecnico/configurazione/works as designed)
- **Analisi log Splunk**: interrogazione di Splunk con Correlation-ID per ricostruire la catena di chiamata al momento del failure
- **Replicazione in ambiente di test**: riproduzione del problema in SYSR2 o PREP per isolare le condizioni di failure
- **Supporto al fix**: collaborazione con i developer senior per l'implementazione delle correzioni
- **Test end-to-end pre-rilascio**: verifica del comportamento corretto dopo il fix in PREP prima del deploy in PROD
- **Documentazione dei findings**: aggiornamento del ticket con la root cause identificata e la soluzione adottata

### 4.2 — Troubleshooting e analisi log {#42}

Il troubleshooting su sistemi distribuiti richiede la capacità di leggere i log in modo correlato, attraverso più componenti della catena. L'utilizzo di Splunk per l'analisi cross-component è stata una delle competenze sviluppate più rilevanti.

L'analisi di un log di frontiera tipico su Splunk per un failure avviene come segue:

```
Query Splunk:
  correlation_id="<UUID>" AND tipo_record="END" AND esito="KO"

Output atteso:
  timestamp: 2026-01-19T10:34:52.311Z
  correlation_id: "a3f2b1c0-..."
  tipo_record: END
  servizio: "vitalegacy/polizze/v1.0/{numeroPolizza}"
  elapsed: 847
  esito: KO
  errore: "SOAP Fault: No records found for agency 905"
```

La lettura del campo `servizio` identifica immediatamente il BSA e l'endpoint coinvolti. Il campo `elapsed` distingue un timeout (elapsed > 30.000ms) da un errore applicativo immediato (elapsed basso con esito KO). Il campo `errore` fornisce il messaggio di eccezione del backend.

### 4.3 — Gestione dei defect {#43}

I defect gestiti durante il tirocinio appartengono a due categorie:

1. **Defect di mapping dati** (Defect 357012): anomalie nel mapping tra le tabelle di decodifica di sistemi diversi. La correzione richiede allineamento dei contratti API tra team.
2. **Defect classificati "Works as designed"** (Defect 357020): comportamenti segnalati come anomalie che risultano invece conformi a specifiche di business intenzionali. La gestione richiede confronto con il Business Analyst e il referente di business.

La distinzione tra le due categorie è operativamente critica: inseguire come bug un comportamento intenzionale disperde risorse e non risolve nulla. Prima di aprire un fix su un defect, è necessario verificare con il BA se il comportamento rientra nelle specifiche.

### 4.4 — Unit Testing: ProposteApiTest.java {#44}

Durante il tirocinio è stato svolto lavoro di analisi e mantenimento degli unit test del **BSA Vita**. Il file `ProposteApiTest.java` contiene i test JUnit 5 per il controller `ProposteApiController`.

**Stack di testing:**
- **JUnit 5** (`org.junit.jupiter.api.Test`): framework di testing
- **Mockito** (`org.mockito.Mockito`): framework di mocking per l'isolamento delle dipendenze
- **CustomNativeWebRequest**: wrapper interno che simula le richieste HTTP in ambiente di test

**Pattern di test adottato:**

```java
@Test
public void getDatiTecniciV10() {
    // 1. Setup: creazione del mock della richiesta HTTP
    CustomNativeWebRequest request = new CustomNativeWebRequest(
        Mockito.mock(HttpServletRequest.class), null
    );
    request.addHeader("Accept", "application/json");
    // Parametri di business: codice compagnia, agenzia, prodotto, nominativo
    request.addParameter("canaleBusiness", "Vites");
    request.addParameter("codiceCompagnia", "RMA");
    request.addParameter("codiceAgenzia", "119");
    request.addParameter("codiceProdotto", "RM1R_PFO1");

    // 2. Esecuzione: chiamata al controller con i parametri
    ProposteApiController controller = new ProposteApiController(request);
    ResponseEntity<RispostaDatiTecniciProposta> response =
        controller.getDatiTecniciV10(...);

    // 3. Verifica: status code HTTP 200 OK
    assertEquals(HttpStatus.OK, response.getStatusCode());
}
```

**Tabella 4 — Stato dei test in ProposteApiTest.java**

| Test | Stato | Nota |
|------|-------|------|
| `getDatiTecniciV10()` | ✅ Attivo | Valida HTTP 200 per recupero dati tecnici proposta |
| `getDatiBeneEtaAssicuratoV10()` | ✅ Attivo | Valida HTTP 200 per dati bene/età assicurato |
| `checkQuotazioneV10Successo()` | 🔇 Commentato | Da abilitare dopo stabilizzazione dei dati |
| `putTipologiaFirmaV10()` | 🔇 Commentato | Commentato il 19/12/2025 per errore 400 in pipeline Maven |
| `getProposteV10()` | 🔇 Commentato | Commentato per overtiming in Maven durante deploy |
| `getPropostaV10()` | 🔇 Commentato | Commentato 11/01/2024 per "GraphTalk Fatal Error" |

**Valore degli unit test nel contesto della maintenance.** La presenza di test attivi su `getDatiTecniciV10()` e `getDatiBeneEtaAssicuratoV10()` garantisce che ogni modifica al controller `ProposteApiController` (ad esempio per un fix) venga immediatamente validata a livello di status code HTTP. Questo intercetta regressioni prima che raggiungano la pipeline CI.

I test commentati documentano la storia tecnica del BSA: il commento `// 19/12/2025: commentato temporaneamente per blocco dei rilasci in pipeline a causa di errore 400` è esso stesso un artefatto di manutenzione, che traccia il momento in cui un'anomalia ha reso temporaneamente inaffidabile quel path di test. La riabilitazione progressiva di questi test, man mano che la stabilità del sistema aumenta, è una delle attività di miglioramento identificate.

**Proposta di estensione del coverage.** Il set attuale di test valida esclusivamente lo status code HTTP 200. Un'estensione utile includerebbe:
- Validazione del mapping JSON: verifica che il campo `codiceProdotto` nel body della response corrisponda al parametro di input
- Test negativi: verifica che una richiesta con `codiceCompagnia` non valido restituisca 400 (Bad Request) invece di un 200 con payload vuoto (il silent failure documentato nel Capitolo 6)
- Mock del layer di integrazione: per isolare il controller dai servizi AIA in test e ridurre i tempi di esecuzione

---

## CAPITOLO 5 — CASE STUDY: L'INCIDENT AVC E IL DEFECT 357020 {#capitolo-5}

**Incident di riferimento:** I-2026-0119-0317 (gennaio 2026)
**Defect correlato:** 357020

### 5.1 — Analisi iniziale {#51}

**Ticket originale.** Nel gennaio 2026 un operatore di agenzia ha segnalato che la polizza vita n° 338XX (Società R., persona giuridica) non consentiva di proseguire sull'operazione **Calcola e Salva Profilo** nel questionario AVC (Adeguata Verifica della Clientela). L'interfaccia si bloccava senza permettere di completare il flusso.

**Componente in-scope:** questionario AVC su VITES → BFF → BSA Vita → servizi AIA (operazione di salvataggio/calcolo profilo AVC).

**Prima fase del lifecycle: la qualità del ticket.** L'incident è stato respinto **due volte** dall'HD3 per mancanza di informazioni:
- Primo rigetto: mancava la descrizione di come era stata valorizzata la pagina dell'anagrafica prima del questionario
- Secondo rigetto: stessa motivazione

Solo alla terza iterazione, con l'allegato che descriveva tutti gli step precedenti alla compilazione del questionario, il ticket è stato accettato e preso in carico.

> **Lesson learned (anticipata):** la qualità del ticket determina la velocità della risoluzione. Un incident documentato con precisione — con la sequenza degli step che hanno portato all'errore, le schermate, il browser, l'agenzia — è risolvibile in ore. Un incident con solo "l'applicativo non funziona" richiede iterazioni di richiesta informazioni che allungano i tempi di settimane. Questa non è una regola burocratica: è una necessità tecnica, perché senza la sequenza degli step non è possibile replicare il problema e quindi isolarne la causa.

### 5.2 — Osservazione {#52}

Ricevuto il ticket completo, l'analisi tecnica ha proceduto come segue:

**Analisi log Splunk.** Interrogazione con il Correlation-ID della sessione utente al momento del blocco. L'analisi dei record di log ha mostrato che la chiamata al servizio AVC completava con esito KO, ma con un messaggio che non indicava un timeout: indicava un blocco funzionale.

**Analisi Network Tab.** Dall'allegato dell'incident (screenshot del browser), la request POST verso l'endpoint AVC restituiva uno status HTTP differente da 200, con un body che conteneva un messaggio di errore funzionale, non un timeout di rete.

**Dati estratti:**
- Servizio: endpoint di salvataggio questionario AVC (BSA Vita → servizi AIA)
- Esito: KO
- Messaggio: il flusso AVC segnalava il blocco delle funzionalità relative al **beneficiario** e al **titolare effettivo** della polizza

### 5.3 — Prova e Controprova {#53}

**Ipotesi iniziale:** malfunzionamento tecnico del servizio di salvataggio AVC.

**Analisi di controprova.** L'incident è stato correlato al **Defect 357020**, aperto in parallelo sullo stesso comportamento: l'assenza di controlli su beneficiario e titolare effettivo FULL dopo la compilazione dell'AVC.

Il Defect 357020 è stato analizzato dal referente di business competente e **chiuso come "Works as designed"** con la seguente motivazione:

> "A seguito di chiarimenti, in caso di titolare effettivo PEP il blocco del beneficiario FULL e del titolare effettivo FULL deve scattare nella pagina dell'AVC post-chiamata dell'AVC."

**Spiegazione tecnico-funzionale.** La Società R. aveva tra i propri titolari effettivi un soggetto classificato come **PEP (Persona Politicamente Esposta)**. La normativa antiriciclaggio impone che, una volta validato il questionario AVC per un soggetto PEP, le modifiche ai dati del beneficiario e del titolare effettivo siano bloccate: questo impedisce alterazioni post-validazione che potrebbero aggirare i controlli AML (Anti Money Laundering).

Il blocco non era quindi un difetto: era una **misura di compliance normativa** implementata deliberatamente nel sistema.

**Dimostrazione della controprova.** La stessa operazione eseguita su una polizza con contraente NON PEP completava correttamente senza blocchi. Questo conferma che il blocco è selettivo e funzione dello stato PEP del soggetto, non un malfunzionamento generalizzato dell'endpoint.

```
[SCREENSHOT/DIAGRAMMA — Fig. 3: Schermata VITES del blocco post-AVC per soggetto PEP.
I campi "beneficiario FULL" e "titolare effettivo FULL" sono disabilitati.
Oscurare nome e codice fiscale del contraente.]
```

### 5.4 — Lessons learned {#54}

1. **Distinguere bug da feature**: non tutto ciò che blocca un utente è un difetto. Prima di aprire un fix, il confronto con il Business Analyst è obbligatorio. In questo caso, correggere il "bug" avrebbe rimosso una misura di compliance normativa.

2. **La qualità del ticket è parte del processo**: il rigetto doppio del ticket non era dovuto a irrilevanza del problema, ma a insufficienza informativa. Documentare gli step precedenti all'errore non è burocrazia: è la condizione necessaria per isolare il problema.

3. **Il confronto incident-defect è produttivo**: la correlazione tra l'incident I-2026-0119-0317 e il Defect 357020 ha permesso una risoluzione più rapida. Quando incident e defect convergono sullo stesso comportamento, l'analisi incrociata è più efficiente dell'analisi separata.

---

## CAPITOLO 6 — CASE STUDY: IL CLUSTER INCIDENT TESEO {#capitolo-6}

**Incident di riferimento:**
- I-2026-0126-0661 (gennaio 2026) — ADV Stand Alone + Emissione Teseo
- I-2026-0220-0570 (febbraio 2026) — ADV Fondo Pensione
- I-2026-0303-0572 (marzo 2026) — ADV Stand Alone

### 6.1 — Il cluster: tre incident correlati {#61}

Tra gennaio e marzo 2026 si sono verificati tre incident distinti su operazioni che coinvolgono le polizze del fondo pensione Teseo gestite su VIS. L'analisi ha rivelato che i tre incident, apparentemente indipendenti, condividono la stessa root cause strutturale.

**Tabella 5 — Cluster incident Teseo: sinossi dei tre failure**

| ID | Periodo | Operazione | Sintomo visibile | Root cause |
|----|---------|------------|------------------|------------|
| I-2026-0126-0661 | Gen. 2026 | ADV Stand Alone + Emissione Teseo | L'applicativo non consente di proseguire dopo inserimento contraente | Consultazione polizza con codice agenzia di login (905 ITA), incompatibile con la registrazione VIS (906 RMA) |
| I-2026-0220-0570 | Feb. 2026 | ADV Fondo Pensione | Pagina bianca all'apertura dell'adeguata verifica | Medesima root cause |
| I-2026-0303-0572 | Mar. 2026 | ADV Stand Alone | Pagina bianca dopo inserimento contraente | Medesima root cause |

### 6.2 — Analisi iniziale {#62}

**Caratteristica strutturale del portafoglio Teseo.** Le polizze del fondo pensione **Teseo di Italiana Assicurazioni** sono registrate nel database VIS sotto una coppia compagnia/agenzia diversa da quella dell'agenzia operativa dell'utente:
- Compagnia registrata in VIS: **RMA** (Reale Mutua Assicurazioni)
- Agenzia registrata in VIS: **906** (agenzia fittizia di servizio)

Questa è una scelta di configurazione storica del portafoglio VIS, non documentata esplicitamente nel codice del BSA VitaLegacy. Un utente che accede tramite Italiana Assicurazioni usa i propri codici di login (es. compagnia ITA, agenzia 905), ma la polizza nel database VIS è registrata sotto RMA/906.

**Componente in-scope:** BFF → BSA VitaLegacy → WS VIS (Oracle SALT → Tuxedo → Cobol).

### 6.3 — Osservazione {#63}

**Analisi log Splunk.** Interrogazione con i Correlation-ID degli incident. I record di log mostravano:

```
servizio: "vitalegacy/polizze/v1.0/{numeroPolizza}"
elapsed: 312 ms
esito: KO
```

Il valore basso dell'elapsed (312ms) esclude un timeout: la risposta dal WS VIS è arrivata rapidamente, ma con un payload vuoto. Questo è il pattern caratteristico di una ricerca che non trova risultati nel DB VIS sotto i parametri forniti.

**Analisi del payload VIS.** Il WS VIS interrogato con `codiceCompagnia=ITA` e `codiceAgenzia=905` restituisce `HTTP 200 OK` con body vuoto (zero polizze trovate), perché la polizza Teseo è registrata sotto `RMA/906`, non sotto `ITA/905`.

**Il silent failure HTTP 200.** Questo è il failure mode più insidioso dell'architettura: il server risponde correttamente dal punto di vista del protocollo (HTTP 200), ma il contenuto è semanticamente vuoto. Il frontend riceve una response formalmente valida, non trova dati nel payload, e renderizza una pagina bianca senza alcun messaggio di errore. L'utente non vede un errore tecnico: vede il nulla.

```
[SCREENSHOT/DIAGRAMMA — Fig. 4: Analisi Splunk degli incident Teseo.
Record di log con elapsed basso (~300ms), esito KO, servizio vitalegacy/polizze.
Evidenziare la correlazione dei tre Correlation-ID sulla stessa root cause.
Oscurare i dati del contraente e il numero di polizza completo.]
```

**Ipotesi iniziale (scartata).** La prima diagnosi attribuiva la root cause a un errore di configurazione del routing in Passportal: il jump verso il componente VITES sbagliato (consultazione polizze VIS NON Teseo invece di consultazione VIS Teseo). Questa era una **concausa**, non la causa primaria.

### 6.4 — Prova e Controprova {#64}

**Verifica della concausa Passportal.** È stato verificato che il routing errato in Passportal contribuiva al problema: il path `/polizze/v1.0/` (non Teseo) veniva usato invece del path `/polizzeTeseo/v1.0/`. Correggendo il routing in Passportal, il componente VITES corretto veniva caricato.

**Controprova: il routing corretto non è sufficiente.** Anche con il routing Passportal corretto, la chiamata al WS VIS continuava a fallire. Il test con Postman ha confermato:

```
Request: GET vitalegacy/polizze/v1.0/{polizza}
         ?codiceCompagnia=ITA&codiceAgenzia=905
Response: HTTP 200 OK — body: [] (lista vuota)

Request: GET vitalegacy/polizze/v1.0/{polizza}
         ?codiceCompagnia=RMA&codiceAgenzia=906
Response: HTTP 200 OK — body: {dati completi della polizza}
```

La controprova è definitiva: il problema non risiede nel routing Passportal, ma nella **logica di costruzione della request nel BSA VitaLegacy**, che usa i codici di login dell'utente invece dei codici corretti per il database VIS.

**Root cause primaria:** il BSA VitaLegacy non implementava la sostituzione `ITA/905 → RMA/906` prima di invocare i WS VIS per le polizze Teseo.

**Iterazione diagnostica.** L'evoluzione dall'ipotesi di configurazione (Passportal) alla root cause nel codice (BSA) dimostra il principio fondamentale del troubleshooting iterativo: la prima root cause identificata non è necessariamente quella corretta. Il processo richiede la falsificazione sistematica di ogni ipotesi prima di procedere al fix.

### 6.5 — La soluzione implementata {#65}

La risoluzione ha richiesto una modifica software coordinata su due componenti: **VITES** (per il routing Passportal) e **BSA VitaLegacy** (per la logica di sostituzione codici).

La soluzione core — che ha risolto la root cause primaria — ha centralizzato la sostituzione dei codici compagnia/agenzia **nel BSA VitaLegacy**, su tutti gli endpoint del flusso Teseo. VITES rimane all'oscuro di questa logica applicativa: il frontend continua a inviare i codici di login dell'utente, ed è il BSA a gestire la traduzione verso i codici VIS corretti.

**Endpoint di consultazione polizza (`vitalegacy/polizze/v1.0/`):**
La sostituzione `codiceCompagnia: ITA → RMA` e `codiceAgenzia: 905 → 906` viene applicata **immediatamente all'interno del controller BSA**, prima della costruzione della request XML verso i WS VIS. L'utente e il frontend non percepiscono questa sostituzione.

**Endpoint di salvataggio questionario AVC (`vitalegacy/polizze/v1.0/{numeroPolizza}/questionarioAVC`):**
La sostituzione viene applicata **selettivamente**:
- Verso le chiamate ai WS VIS: i codici vengono sostituiti (RMA/906)
- Verso le chiamate REST a PPEVO: i codici originali di login vengono mantenuti (ITA/905)

La ragione di questa selettività è che **PPEVO non conosce la mappatura interna VIS** delle polizze Teseo ITA: per PPEVO, la polizza è gestita da ITA/905. Sostituire i codici verso PPEVO causerebbe un errore su quel sistema.

**Gestione delle anagrafiche — duplicazione controllata:**
Le anagrafiche dei contraenti delle polizze Teseo ITA esistono in due copie nel sistema:

1. Su **Anagrafe Unica (AU)**: con `codiceCompagnia=ITA, codiceAgenzia=905`, identificata da `knmnv_ita`
2. Su **tabelle soggetti VIS**: copia parallela con `codiceCompagnia=RMA, codiceAgenzia=906`, identificata da `knmnv_rma`

Le due anagrafiche sono collegate dallo stesso **codice fiscale**. Le chiamate verso AU dal frontend usano il codice fiscale come chiave di ricerca — non il codice nominativo interno — per garantire il recupero corretto indipendentemente da quale copia stia usando il WS VIS.

```
[SCREENSHOT/DIAGRAMMA — Fig. 5: Schema Prima/Dopo della sostituzione codici nel BSA VitaLegacy.
Prima: request con ITA/905 → WS VIS → payload vuoto (HTTP 200 silenzioso).
Dopo: BSA sostituisce ITA/905 → RMA/906 → WS VIS → payload corretto.
Evidenziare la selettività verso PPEVO (nessuna sostituzione).]
```

**Test end-to-end** in ambiente PREP con esito positivo. Rilascio pianificato nella finestra correttiva del **15 aprile 2026**, coordinata tra team VITA e team infrastrutturali.

### 6.6 — Il secondo failure mode: tipo di ritorno errato nel catch {#66}

L'analisi approfondita del controller legacy del BSA VitaLegacy ha evidenziato un secondo failure mode indipendente dal cluster Teseo.

Il metodo di consultazione polizza del controller legacy presenta un difetto nel **blocco catch**: a fronte di un'eccezione nella comunicazione SOAP verso i WS VIS, il metodo restituisce un **DTO di tipo errato** — specificamente un DTO di ricerca incassi (`RispostaRicercaIncassi`) invece del DTO di consultazione polizza (`RispostaConsultazionePolizza`).

Il risultato visibile per l'utente è: HTTP 200 OK, ma dati di incasso visualizzati al posto dei dati della polizza.

**Perché questo è strutturalmente più pericoloso del failure Teseo.** Un payload vuoto (HTTP 200 con body `[]`) è visibile: l'utente vede una pagina bianca e capisce che qualcosa non va. Un DTO errato (HTTP 200 con dati incongruenti) è silenzioso: l'utente vede dati — ma sono i dati sbagliati. Potrebbe prendere decisioni operative (es. variazioni su polizza, calcolo di un riscatto) basate su dati di un altro contratto.

Questo failure mode mette in evidenza un gap nell'architettura del BFF: il BFF instrada la response del BSA verso il frontend senza validare il tipo del DTO ricevuto. Una validazione semantica a livello di BFF avrebbe intercettato il DTO errato prima che raggiungesse l'utente.

### 6.7 — Lessons learned {#67}

1. **Documentazione tecnica come prevenzione.** La root cause primaria del cluster Teseo era una regola di mapping non documentata: le polizze Teseo ITA su VIS sono sotto RMA/906. Se questa regola fosse stata documentata nel README del BSA VitaLegacy o in un commento al codice, il cluster avrebbe potuto essere prevenuto. La mancanza di documentazione tecnica è un debito che si paga con gli incident.

2. **La logica di traduzione codici appartiene al BSA.** La soluzione corretta è centralizzare la logica di mapping nel BSA, non distribuirla tra frontend e configurazioni. Il frontend non deve sapere che ITA/905 deve diventare RMA/906: questa è logica di business applicativa, e il BSA è il posto corretto dove gestirla.

3. **HTTP 200 non garantisce la correttezza semantica.** Un payload formalmente valido può contenere dati semanticamente errati. I test unitari che validano solo lo status code HTTP 200 non intercettano questo tipo di failure. Serve la validazione del tipo del DTO e, idealmente, la validazione di un campo sentinella nel body (es. il numero di polizza nella response deve corrispondere al numero di polizza nella request).

4. **Il troubleshooting è iterativo.** La concausa Passportal era reale e andava corretta, ma non era la causa primaria. Fermarsi alla prima spiegazione plausibile avrebbe risolto solo il 30% del problema. Il metodo corretto è: corregi la concausa, poi falsifica l'ipotesi verificando se il problema persiste.

---

## CAPITOLO 7 — CASE STUDY: RISCATTO TOTALE E DATA INTEGRITY (I-2026-0218-0324) {#capitolo-7}

**Incident di riferimento:** I-2026-0218-0324 (febbraio 2026)

### 7.1 — Analisi iniziale {#71}

Nel febbraio 2026 un operatore di agenzia ha segnalato l'impossibilità di recuperare la documentazione relativa a un'operazione di **riscatto totale** sulla Polizza 206XXXX (Contraente E.F.).

**Situazione iniziale:**
- L'operazione di riscatto totale era stata **completata con successo**
- Le stampe associate (documenti PDF di riscatto) risultavano assenti sia nel sistema documentale AIA sia nel sistema di firma digitale
- L'operazione non poteva essere rieseguita dall'agenzia, perché era già completata: il frontend VITES non permetteva di avviare nuovamente un riscatto già eseguito

**Il deadlock.** Si era generata una situazione di stallo strutturale:
- L'agenzia non aveva i documenti del riscatto → non poteva completare le pratiche con il cliente
- L'agenzia non poteva rieseguire il riscatto → perché già completato (operazione idempotente bloccata correttamente)
- L'agenzia non poteva rigenerare le stampe dal frontend → il flusso di stampa era vincolato all'esecuzione dell'operazione

### 7.2 — Osservazione {#72}

**Analisi log Splunk con Correlation-ID.** Interrogazione dei log per la data dell'operazione di riscatto sulla Polizza 206XXXX. I risultati hanno mostrato che **tutte le richieste di stampa per quell'operazione avevano prodotto record END con esito KO**.

```
Prima evidenza (errori di stampa):
  correlation_id: [UUID sessione riscatto]
  servizio: "documentale/stampe/riscatto"
  tipo_record: END
  esito: KO
  elapsed: 23.450 ms
  errore: "DB connection timeout — apiservice layer oracle"

Seconda evidenza (fallimento massivo):
  [TUTTI i record di stampa della stessa sessione: esito KO]
```

**Conferma tecnica.** La causa del fallimento delle stampe era un **problema temporaneo di accesso al database del servizio di stampa** (Oracle, strato apiservice layer), avvenuto nel momento esatto in cui l'operazione di riscatto tentava di generare i documenti. L'operazione di riscatto stessa aveva superato correttamente la fase di aggiornamento del contratto (AIA/VIS), ma il processo di generazione documenti aveva trovato il DB delle stampe irraggiungibile.

### 7.3 — Prova e Controprova {#73}

**Ipotesi iniziale:** recupero manuale delle stampe dal fascicolo documentale AIA.
**Controprova:** le stampe non erano presenti nel fascicolo perché non erano mai state generate (il processo si era interrotto prima della scrittura su AIA). Non c'era nulla da recuperare.

**Soluzione identificata:** richiamare manualmente, in produzione, le API del servizio di stampa per le operazioni fallite, passando i Correlation-ID delle stampe che avevano prodotto KO.

**Perché questa operazione richiedeva autorizzazione.** Intervenire manualmente in produzione chiamando API di stampa è un'operazione che:
- Non ha un meccanismo di rollback immediato (le stampe vengono scritte nel fascicolo documentale)
- Richiede la certezza che il problema temporaneo al DB delle stampe sia risolto (altrimenti l'intervento fallirebbe di nuovo)
- Deve essere eseguita con precisione sui soli Correlation-ID corretti (rischio di generare stampe duplicate se eseguita su ID errati)

Per questo, l'apprendista ha seguito il processo di **delega strutturata SAFe**:

1. Documentazione completa dei findings nel ticket: log Splunk, Correlation-ID delle stampe fallite, conferma che l'operazione di riscatto era andata a buon fine
2. Richiesta di conferma tecnica al referente tecnico sulla natura temporanea del problema DB (confermata: il DB del servizio stampe aveva avuto un problema temporaneo di accesso, ora risolto)
3. Richiesta di autorizzazione esplicita al System Architect per eseguire una chiamata GET direttamente in PROD
4. Esecuzione coordinata a sei mani — apprendista + referente tecnico + System Architect: la chiamata GET verso le API del servizio di stampa è stata lanciata direttamente in ambiente PROD, passando i Correlation-ID delle stampe fallite, rigenerando i documenti nel fascicolo documentale AIA

**Dettaglio tecnico dell'intervento.** La soluzione non è stata un deploy di codice, ma una chiamata API manuale in PROD. L'apprendista ha costruito la request GET con i Correlation-ID estratti da Splunk e l'ha lanciata direttamente sull'endpoint del servizio stampe in PROD, dopo aver verificato che il DB oracle del servizio di stampa fosse nuovamente accessibile. I documenti di riscatto sono stati generati correttamente e scritti nel fascicolo di AIA.

```
[SCREENSHOT/DIAGRAMMA — Fig. 6: Estratto Splunk degli incident di stampa sulla Polizza 206XXXX.
Evidenziare i record KO con errore DB. Oscurare Correlation-ID completo e dati contraente.]
```

### 7.4 — Lessons learned {#74}

1. **La transazionalità distribuita non è garantita.** Il riscatto è un'operazione composta: aggiornamento del contratto (su AIA/VIS) + generazione dei documenti (su servizio stampe). Se le due parti non sono transazionali (cioè: o entrambe completano, o nessuna completa), un failure parziale crea il deadlock documentato. Una soluzione architetturale a lungo termine è l'introduzione di un meccanismo di retry asincrono per la generazione dei documenti, separato dal completamento dell'operazione principale.

2. **Il deadlock è prevenibile con una compensating transaction.** Se il sistema rilevasse automaticamente il fallimento delle stampe post-riscatto e avviasse un processo di retry automatico, l'intervento manuale non sarebbe necessario. Questo è un elemento di debito tecnico identificato: il sistema non ha un meccanismo di auto-remediation per il caso "operazione completata, stampe non generate".

3. **La delega strutturata è efficace.** Il passaggio di un incident fuori perimetro al referente corretto — con documentazione completa dei findings e dei Correlation-ID — ha permesso di risolvere il caso senza errori. L'apprendista che tenta di risolvere autonomamente un caso fuori perimetro rischia di causare danni aggiuntivi. Sapere cosa non si sa e documentarlo è una competenza tecnica.

---

## CONCLUSIONI {#conclusioni}

### Competenze tecniche acquisite

**Troubleshooting su sistemi distribuiti.** La capacità di interrogare Splunk con Correlation-ID, leggere i log di frontiera, interpretare elapsed time e status HTTP, e correlare eventi su più componenti della catena è la competenza operativa principale sviluppata. Questa capacità è applicabile a qualsiasi sistema distribuito con log centralizzati, indipendentemente dallo stack tecnologico.

**Distinzione bug / feature di compliance.** Tre dei casi documentati (Defect 357020, Cluster Teseo, Incident riscatto) hanno richiesto la distinzione tra un malfunzionamento tecnico e un comportamento intenzionale del sistema. Prima di scrivere un fix, la domanda corretta è: "questo comportamento è nella specifica?" Il confronto con il Business Analyst è parte del processo tecnico, non un'attività separata.

**Lettura critica del codice legacy.** L'analisi del BSA VitaLegacy ha richiesto la comprensione di codice che integra protocolli eterogenei (REST/JSON → SOAP/XML → Tuxedo → Cobol) senza documentazione esaustiva. La capacità di tracciare un dato attraverso livelli di traduzione successivi — dal codice agenzia nel parametro REST, alla request XML verso VIS, alla query Tuxedo sul DB Oracle — è una competenza che si acquisisce solo nella pratica operativa.

**Unit testing con JUnit 5 e Mockito.** Il lavoro su `ProposteApiTest.java` ha fornito esperienza diretta con il pattern di test a livello di controller: mock della richiesta HTTP con `CustomNativeWebRequest`, isolamento del controller dalla dipendenza esterna, asserzione sul `ResponseEntity<T>` restituito.

### Debito tecnico identificato

L'analisi degli incident ha evidenziato quattro aree di debito tecnico dell'ecosistema VITA:

| Area | Descrizione | Impatto |
|------|-------------|---------|
| Gestione errori 5xx nel BFF | Un failure del backend (es. timeout su servizi AIA) non produce una response JSON strutturata verso VITES | L'utente vede un freeze dell'interfaccia senza messaggio informativo |
| Validazione semantica delle response nel BFF | Il BFF non valida il tipo del DTO ricevuto dal BSA prima di inoltrarlo al frontend | Il DTO errato nel `catch` (Capitolo 6) può raggiungere l'utente |
| Retry asincrono per le stampe | Non esiste un meccanismo di auto-remediation per stampe fallite post-operazione | Richiede intervento manuale in produzione (Capitolo 7) |
| Coverage degli unit test | I test su `ProposteApiTest.java` validano solo lo status code HTTP 200, non il contenuto del body | I silent failure HTTP 200 con payload vuoto o DTO errato non vengono intercettati |

### La lezione centrale

I tre case study documentati nella tesi convergono su un principio comune: **un sistema distribuito fallisce in modi che non sono sempre visibili**. Il 504 Gateway Timeout è visibile: il sistema si blocca. Un HTTP 200 con payload vuoto non lo è: il sistema risponde, l'utente vede dati corretti — ma i dati non ci sono. Un DTO di tipo sbagliato non lo è: il sistema risponde con dati — ma sono i dati di un altro contratto.

La metodologia di troubleshooting documentata nel Capitolo 3 è uno strumento per navigare questa complessità: non ipotizzare la root cause, ma misurarla. Leggere i log, falsificare le ipotesi, isolare il layer in failure, dimostrare la soluzione con la controprova.

---

## APPENDICE A — GLOSSARIO TECNICO {#appendice-a}

| Termine | Definizione |
|---------|-------------|
| **AIA** | Backend moderno del portafoglio Vita di Reale ITES. Gestisce emissione e postvendita delle polizze moderne. Database Oracle. |
| **AML** | Anti Money Laundering. Insieme di controlli antiriciclaggio obbligatori per legge, gestiti parzialmente tramite BSA Anagrafe. |
| **AVC** | Adeguata Verifica della Clientela. Questionario obbligatorio pre-emissione e di manutenzione della polizza vita, previsto dalla normativa antiriciclaggio. |
| **BFF** | Backend For Frontend. Applicazione Java SpringBoot che funge da punto di accesso unico tra VITES e i BSA. Deployata sullo stesso POD AKS di VITES. |
| **BSA** | Business Sidecar for API. Applicazione Java SpringBoot che espone e standardizza le API di un dominio di business specifico. |
| **Correlation-ID** | Identificatore UUID che collega tutti i record di log generati da una singola catena di chiamata end-to-end. Strumento primario per il troubleshooting su Splunk. |
| **DTO** | Data Transfer Object. Oggetto Java utilizzato per trasferire dati tra layer applicativi. Un DTO errato nel `catch` è il failure mode documentato nel Capitolo 6. |
| **HD3** | Help Desk di terzo livello. Il team di sviluppo VITA gestisce HD3: i ticket arrivano dopo filtro di HD1 e HD2. |
| **Oracle SALT** | Service Architecture Leveraging Tuxedo. Gateway Oracle che espone i servizi dello strato Tuxedo come endpoint WSDL accessibili tramite protocollo SOAP. |
| **PEP** | Persona Politicamente Esposta. Soggetto sottoposto a controlli antiriciclaggio rafforzati (disciplina AML). Il blocco post-AVC per soggetti PEP è una misura di compliance (Defect 357020). |
| **PI Planning** | Program Increment Planning. Cerimonia SAFe trimestrale in cui tutti i team del treno Agile sincronizzano gli obiettivi del prossimo PI. |
| **POD AKS** | Unità di deployment su Azure Kubernetes Service. VITES e BFF girano sullo stesso POD. |
| **PPEVO** | Sistema esterno per la gestione evoluta dei questionari di adeguatezza. Chiamato direttamente dal BSA VitaLegacy con i codici di login originali (non sostituiti). |
| **REST** | Representational State Transfer. Stile architetturale per API web basato su HTTP e JSON. Usato internamente tra VITES, BFF e BSA. |
| **SAFe** | Scaled Agile Framework. Framework per la gestione Agile di grandi organizzazioni, adottato da Reale ITES [4]. |
| **SOAP** | Simple Object Access Protocol. Protocollo basato su XML per comunicazione tra servizi. Usato per la comunicazione tra BSA VitaLegacy e i WS VIS tramite Oracle SALT. |
| **Splunk** | Piattaforma di centralizzazione e analisi dei log applicativi. Strumento primario per il troubleshooting degli incident. |
| **Tuxedo** | Oracle Tuxedo. Middleware transazionale che esegue i programmi Cobol del sistema VIS. |
| **VIS** | Sistema core assicurativo legacy. Stack: Cobol + Oracle Tuxedo + Oracle SALT (esposizione SOAP) + Oracle SQL (database). |
| **Works as designed** | Classificazione di chiusura di un defect o incident: il comportamento segnalato è conforme alla specifica funzionale intenzionale. |

---

## APPENDICE B — MAPPING ARCHITETTURA → INCIDENT → ROOT CAUSE {#appendice-b}

**Tabella 6 — Mapping completo Architettura → Incident → Root Cause**

| Componente | Incident / Defect | Root Cause | Tipo |
|------------|-------------------|------------|------|
| BSA Vita → servizi AIA | I-2026-0119-0317 | Timeout su servizi AIA durante salvataggio AVC (soglia gateway superata) | Tecnico — Performance |
| VITES + BFF (compliance) | Defect 357020 | Blocco post-AVC per soggetto PEP: works as designed | Business — Compliance |
| BSA VitaLegacy → WS VIS | I-2026-0126-0661, I-2026-0220-0570, I-2026-0303-0572 | Codice agenzia di login usato invece di agenzia fittizia 906 per polizze Teseo | Tecnico — Logica di codice |
| BSA VitaLegacy (catch) | Cluster Teseo (analisi estesa) | Tipo di ritorno errato nel blocco catch (DTO incassi vs. polizza) | Tecnico — Logica di codice |
| Passportal | I-2026-0126-0661 et al. | Routing errato: path polizze VIS non-Teseo usato per polizze Teseo | Configurazione |
| Servizio stampe → Oracle DB | I-2026-0218-0324 | Problema temporaneo di accesso al DB del servizio stampe durante riscatto | Infrastruttura — Temporaneo |
| BSA Anagrafe → AIA | Defect 357012 | Tabelle di decodifica `codCondizioneProfessionale` differenti tra BSA Anagrafe e AIA | Contratto dati |

---

## APPENDICE C — PROPOSTEAPITEST.JAVA: ANALISI DEL CODICE {#appendice-c}

Il file `ProposteApiTest.java` (package `it.realeites.vita`) contiene i test JUnit 5 per il controller `ProposteApiController` del BSA Vita.

**Struttura dei test attivi:**

```java
// Test 1: getDatiTecniciV10
// Valida il recupero dei dati tecnici di una proposta
// Parametri: canaleBusiness, codiceCompagnia (RMA), codiceAgenzia (119),
//            codiceSubagenzia, codiceUtente, codiceProdotto (RM1R_PFO1), codiceNominativoContraente
// Assertion: assertEquals(HttpStatus.OK, response.getStatusCode())

// Test 2: getDatiBeneEtaAssicuratoV10
// Valida il recupero dei dati bene/età assicurato
// Parametri: codiceCompagnia (ITA), codiceProdotto (ITAPIPDSP1), dataEffetto
// Assertion: assertEquals(HttpStatus.OK, response.getStatusCode())
```

**Test commentati e motivazione:**

```java
// putTipologiaFirmaV10
// Commentato 19/12/2025: errore 400 in pipeline Maven
// → Indica un problema nel contratto API dell'endpoint di firma
// → Da riabilitare dopo verifica del comportamento atteso per i codici ITA

// getProposteV10
// Commentato per overtiming in Maven durante deploy
// → Il test è troppo lento per la pipeline CI; richiede ottimizzazione
// → Potrebbe essere spostato in una suite di integration test separata

// getPropostaV10
// Commentato 11/01/2024: "GraphTalk Fatal Error"
// → GraphTalk è il sistema di calcolo attuariale; indica un errore nel motore
// → Da riabilitare dopo fix del comportamento di GraphTalk per i casi edge

// getDatiQuotazioneV10
// Commentato: test manuale
// → Richiede dati di quotazione specifici non disponibili in ambiente di test
```

**Coverage raccomandato per completare la suite:**

1. **Test negativi per codici non validi:** verifica che `codiceCompagnia` inesistente restituisca HTTP 400 (Bad Request), non HTTP 200 con payload vuoto
2. **Validazione del mapping JSON:** `assertEquals("RM1R_PFO1", response.getBody().getCodiceProdotto())`
3. **Test per il flusso Teseo:** simula una richiesta con `codiceCompagnia=ITA, codiceAgenzia=905` per una polizza Teseo; verifica che il controller applichi correttamente la sostituzione verso RMA/906 prima della chiamata VIS

---

## APPENDICE D — ELENCO DEGLI SCREENSHOT DA INSERIRE {#appendice-d}

| Figura | Capitolo | Contenuto | Istruzioni di anonimizzazione |
|--------|----------|-----------|-------------------------------|
| Fig. 1 | Cap. 2 | Diagramma architetturale dell'ecosistema VITA con POD AKS, iFrame, due path (AIA e VIS), BSA coinvolti | Nessun dato personale |
| Fig. 2 | Cap. 3 | Flow chart del metodo di troubleshooting (8 fasi) | Nessun dato personale |
| Fig. 3 | Cap. 5 | Schermata VITES con blocco post-AVC per soggetto PEP | Oscurare nome contraente, codice fiscale, numero polizza |
| Fig. 4 | Cap. 6 | Log Splunk degli incident Teseo: elapsed basso, esito KO, servizio vitalegacy/polizze | Oscurare Correlation-ID completo, codice agenzia, numero polizza |
| Fig. 5 | Cap. 6 | Schema Prima/Dopo sostituzione codici compagnia/agenzia nel BSA VitaLegacy | Usare nomi fittizi per agenzia/compagnia |
| Fig. 6 | Cap. 7 | Log Splunk stampe fallite per Polizza 206XXXX: record KO con errore DB | Oscurare Correlation-ID, codice nominativo contraente |

---

## BIBLIOGRAFIA {#bibliografia}

[1] Evans E.: *Domain-Driven Design: Tackling Complexity in the Heart of Software*. 2003. Boston: Addison-Wesley.

[2] Newman S.: *Building Microservices: Designing Fine-Grained Systems*. 2021. Sebastopol: O'Reilly Media (seconda edizione).

[3] Richardson C.: *Microservices Patterns: With Examples in Java*. 2018. Shelter Island: Manning Publications.

[4] Scaled Agile Inc.: *SAFe 6.0 Framework*. Consultato il 15/04/2026. https://scaledagileframework.com/

[5] Documentazione interna Reale ITES: *API Management — API Architecture Blueprint v1.0*.

[6] Documentazione interna Reale ITES: *VITES Descrittori Custom per UI v2.1* (22/03/2024).

[7] Documentazione interna Reale ITES: *Application Log Guidelines v7.0* (30/03/2026).

[8] Documentazione interna Reale ITES: *OX GESTLOG Log Applicativi Frontiera V6*.

---

*Documento prodotto in conformità con le policy di riservatezza aziendale. Dati sensibili anonimizzati secondo GDPR.*
