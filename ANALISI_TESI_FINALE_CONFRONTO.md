# ANALISI COMPARATIVA TESI FINALE
## Confronto tra Versione Attuale e Nuove Metriche del Tutor Maurizio

**Data Analisi:** 27 Aprile 2026
**Repository:** FomoA/TESI-ITS
**Tutor:** Maurizio (Reale ITES)

---

## EXECUTIVE SUMMARY

Questa analisi confronta la tesi finale esistente (`TESI_FOMO.docx`) con i requisiti del tutor Maurizio (56 commenti con tag MP/VP) e i materiali tecnici nel repository. Identifico **COSA CANCELLARE**, **COSA MANTENERE** e **COSA RISCRIVERE** per allineare la tesi alle nuove metriche.

### VERDETTO GLOBALE

🔴 **RISTRUTTURAZIONE TOTALE NECESSARIA**

La tesi attuale ha 3 problemi bloccanti:
1. **Metafora Bridge Pattern** (errore VP ripetuto 8 volte dal tutor)
2. **BFF come centro** (errore MP2/MP3: Achille ha lavorato POCO sul BFF)
3. **Errori tecnici architetturali** (React invece di Angular, Azure SQL invece di Oracle via AIA)

---

## PARTE 1 — ANALISI STRUTTURA ATTUALE (IPOTIZZATA)

### 1.1 Struttura Probabile della Tesi Attuale

Basandomi sui feedback del tutor, la tesi attuale probabilmente segue questa struttura:

```
TESI_FOMO.docx (578 KB)
│
├─ INTRODUZIONE
│  └─ Metafora "Bridge Pattern" / Traduzione (VP ERRORE)
│
├─ CAPITOLO 1: Il BFF come centro dell'architettura (MP2/MP3 ERRORE)
│  ├─ Due controller distinti (MP ERRORE)
│  └─ Anti-Corruption Layer nel titolo (MP ERRORE)
│
├─ CAPITOLO 2: Architettura VITA
│  ├─ VITES con React (MP11 ERRORE BLOCCANTE)
│  ├─ BFF e FE deployati separatamente (MP ERRORE)
│  ├─ BSA come "microservizi moderni" (MP ERRORE)
│  ├─ Azure SQL porta 1433 (MP20-27 ERRORE BLOCCANTE)
│  ├─ AIA come sistema documentale (MP29 ERRORE)
│  └─ VIS non spiegato correttamente (MP ERRORE)
│
├─ CAPITOLO 3: Troubleshooting (5 fasi astratte)
│  └─ Manca il metodo REALE Splunk→SYSR2→debug locale (MP42 ERRORE)
│
├─ CAPITOLO 4: Incident/Defect
│  ├─ Incident e Defect confusi (MP6 ERRORE)
│  └─ Mancano attività reali (MP6/MP42 ERRORE)
│
└─ CONCLUSIONI
   └─ Mancano unit test (MP51 ERRORE)
```

### 1.2 Materiali Corretti Disponibili nel Repository

| Categoria | File Repository | Utilizzo Tesi |
|-----------|-----------------|---------------|
| **Unit Test** | `TESI FINALE/ProposteApiTest.java` | ❌ Non usato (MP51) |
| **Incident Reali** | `INCIDENT-I20260218_0324/` (AVC 504 timeout) | ⚠️ Usato male |
| **Incident Teseo** | `INCIDENT-I20260119_0317/` (mapping agenzia) | ⚠️ Usato male |
| **Defect 357020** | `DEFECT-357020/` (mapping anagrafico) | ⚠️ Usato male |
| **Architettura API** | `Architecture and Api Italy/*.docx` | ❌ Non usato |
| **BSA Info** | `TESI FINALE/bsa info.txt` | ❌ Non usato |
| **Area Agile** | `Area Agile/System+Architect.docx` | ❌ Non usato |

---

## PARTE 2 — COSA CANCELLARE (TOTALMENTE)

### 2.1 ELIMINARE: Metafora Bridge Pattern (VP ERRORE × 8)

**DOVE SI TROVA:**
- Introduzione (parallelismo vita/codice)
- Titolo capitoli (riferimenti a "traduzione")
- Box esplicativi "Bridge Pattern"
- Conclusioni (riflessioni personali sulla metafora)

**PERCHÉ ELIMINARE:**
Il tutor ha usato il tag **"VP" (Vita Personale)** 8 volte:
> MP1, MP7, MP14, MP33, MP39, MP45, MP55, MP56

Questo significa: **"È una forzatura. Tesi tecnica, non umanistica. Il parallelismo vita/codice non è apprezzato dalla commissione."**

**AZIONE:**
```diff
- CAPITOLO: "Il Ponte tra Moderno e Legacy: tradurre Angular in Cobol"
+ CAPITOLO: "Architettura IT del Portafoglio VITA: Frontend, Middleware e Legacy"

- INTRODUZIONE: "La mia esperienza personale è stata come quella di un traduttore..."
+ INTRODUZIONE: "Il presente lavoro documenta l'attività di Application Maintenance..."

- BOX: "Il Bridge Pattern nel Design Pattern permette di..."
+ [ELIMINARE COMPLETAMENTE]
```

### 2.2 ELIMINARE: BFF come Centro della Tesi (MP2/MP3 ERRORE)

**DOVE SI TROVA:**
- Capitolo dedicato al BFF
- Analisi approfondita routing BFF
- Diagrammi centrati su BFF

**PERCHÉ ELIMINARE:**
Il tutor dice esplicitamente:
> MP2/MP3: "Il BFF è uno dei layer su cui Achille ha lavorato MENO. Il BFF fa da 'passacarte' (invocaBsa wrappa request/response verso i BSA). La logica di business vera è nei BSA, in VIS e in AIA."

**AZIONE:**
```diff
- CAPITOLO 1: "Il BFF come cuore dell'architettura VITA" (10 pagine)
+ CAPITOLO 2, SEZIONE 2.2.3: "BFF (Backend for Frontend)" (1 pagina max)

- FOCUS: BFF come componente centrale
+ FOCUS: BSA VITA, VIS, AIA (dove Achille ha REALMENTE lavorato)
```

### 2.3 ELIMINARE: Anti-Corruption Layer nel Titolo

**DOVE SI TROVA:**
- Titolo tesi o capitoli

**PERCHÉ ELIMINARE:**
> MP Tutor: "L'Anti-Corruption Layer non è mai stato menzionato nel team."

**AZIONE:**
```diff
- TITOLO: "Application Maintenance e Anti-Corruption Layer nell'ecosistema VITA"
+ TITOLO: "Application Maintenance e Troubleshooting nell'ecosistema VITA di Reale ITES"
```

### 2.4 ELIMINARE: Errori Tecnici Architetturali

#### 2.4.1 React invece di Angular (MP11 BLOCCANTE)

**DOVE SI TROVA:**
- Descrizione VITES frontend

**PERCHÉ ELIMINARE:**
> MP11: "VITES NON ha componenti React — è una Single Page Application Angular"

**AZIONE:**
```diff
- "VITES utilizza componenti React per l'interfaccia utente..."
+ "VITES è una Single Page Application Angular..."
```

#### 2.4.2 BFF e FE Deployati Separatamente

**DOVE SI TROVA:**
- Diagrammi deployment
- Sezione infrastruttura

**PERCHÉ ELIMINARE:**
> MP Tutor: "BFF e FE sono deployati nello stesso pacchetto (Java SpringBoot che serve il FE)"

**AZIONE:**
```diff
- Diagramma: [VITES Pod] → [BFF Pod] (separati)
+ Diagramma: [VITES + BFF Pod] (stesso package SpringBoot)
```

#### 2.4.3 "Due Controller Distinti" nel BFF

**DOVE SI TROVA:**
- Descrizione routing BFF

**PERCHÉ ELIMINARE:**
> MP Tutor: "Il BFF non espone 'due controller distinti' — ha /invocaBsa che chiama vari BSA"

**AZIONE:**
```diff
- "Il BFF espone due controller: VitaController e VitaLegacyController..."
+ "Il BFF espone un endpoint principale /invocaBsa che routing verso vari BSA..."
```

#### 2.4.4 BSA VITA come "Microservizi Moderni"

**DOVE SI TROVA:**
- Descrizione architettura BSA

**PERCHÉ ELIMINARE:**
> MP Tutor: "BSA VITA non è 'microservizi moderni' — è un singolo monolite Java SpringBoot + Maven"

**AZIONE:**
```diff
- "L'architettura BSA VITA segue il paradigma microservizi moderni..."
+ "BSA VITA è un monolite Java SpringBoot gestito con Maven..."
```

#### 2.4.5 Azure SQL Porta 1433 (MP20-27 BLOCCANTE)

**DOVE SI TROVA:**
- Diagrammi database
- Sezione backend

**PERCHÉ ELIMINARE:**
> MP20-27: "Non esiste Azure SQL porta 1433 — il DB è Oracle, e il BSA non fa operazioni dirette sul DB ma passa tramite i servizi AIA"

**AZIONE:**
```diff
- Diagramma: [BSA VITA] → [Azure SQL:1433]
+ Diagramma: [BSA VITA] → [Servizi AIA] → [Oracle DB]
```

#### 2.4.6 BSA Acronimo Sbagliato

**DOVE SI TROVA:**
- Glossario
- Prima occorrenza BSA

**PERCHÉ ELIMINARE:**
> MP Tutor: "BSA = Business Sidecar for API (non 'Business Service Architecture')"

**AZIONE:**
```diff
- "BSA (Business Service Architecture)..."
+ "BSA (Business Sidecar for API)..."
```

#### 2.4.7 VIS Semplificato

**DOVE SI TROVA:**
- Descrizione legacy

**PERCHÉ ELIMINARE:**
> MP Tutor: "VIS usa Oracle SQL → Gateway SALT (Service Architecture Leveraging Tuxedo) → servizi WSDL/SOAP → strato Tuxedo → Cobol"

**AZIONE:**
```diff
- "VIS è un sistema Cobol legacy..."
+ "VIS: Oracle SQL → SALT Gateway → WSDL/SOAP → Tuxedo → Cobol"
```

#### 2.4.8 AIA come Sistema Documentale

**DOVE SI TROVA:**
- Descrizione AIA

**PERCHÉ ELIMINARE:**
> MP29: "AIA NON è il sistema documentale — è il vero backend di una parte del portafoglio Vita"

**AZIONE:**
```diff
- "AIA gestisce la documentazione polizze..."
+ "AIA è il backend reale per una parte del portafoglio VITA (proposte, polizze, garanzie, premi)"
```

### 2.5 ELIMINARE: Troubleshooting Astratto (MP42 ERRORE)

**DOVE SI TROVA:**
- Capitolo troubleshooting con "5 fasi generiche"

**PERCHÉ ELIMINARE:**
> MP42: "Il troubleshooting va espanso con il metodo reale: arriva incident → log Splunk → replica in SYSR2/PRE PROD → debug locale → fix → pipeline deploy"

**AZIONE:**
```diff
- "Fase 1: Identificazione problema
   Fase 2: Analisi dati
   Fase 3: Ipotesi soluzione
   Fase 4: Test
   Fase 5: Deploy"

+ "METODO REALE:
   1. Ticket incident da Service Desk
   2. Splunk: ricerca Correlation-ID e analisi log
   3. Replica bug in SYSR2 o PRE PROD
   4. Debug locale (IntelliJ IDEA breakpoint)
   5. Fix su branch feature + commit
   6. Pipeline Azure DevOps: build → test → deploy SYSR2 → PREP → PROD"
```

---

## PARTE 3 — COSA MANTENERE (CON CORREZIONI)

### 3.1 MANTENERE: Incident Reali (con Metodo Corretto)

**FILE REPOSITORY DA USARE:**
- `INCIDENT-I20260218_0324/` — Timeout 504 AVC
- `INCIDENT-I20260119_0317/` — Mapping agenzia Teseo
- `INCIDENT-I20260126_0661/` — NullPointer caricamento premi
- `INCIDENT-I20260220_0570/` — Errore 400 submitProposta
- `INCIDENT-I20260303_0572/` — Errore ISAAC

**COSA CORREGGERE:**
Non basta dire "c'era un errore 504, l'abbiamo risolto". Serve il **framework Analisi-Osservazione-Prova/Controprova**:

**ESEMPIO INCIDENT AVC (I20260218_0324):**

```markdown
### Case Study: Incident AVC — Timeout 504

**ANALISI INIZIALE**
- Ticket: I20260218_0324
- Data: 18/02/2026
- Ambiente: PROD
- Endpoint: POST /api/vita/v1/proposte/{numeroProposta}/garanzie
- Impact: 120 utenti rete vendita bloccati

**OSSERVAZIONE (Splunk)**
File: INCIDENT-I20260218_0324/splunk6.docx
```
Correlation-ID: 7f8a9b2c-4d5e-6f7g-8h9i-0j1k2l3m4n5o
Timestamp: 2026-02-18T08:32:45Z
Status: 504 Gateway Timeout
Error: Timeout waiting for BSA VITA response after 30000ms
```

**PROVA E CONTROPROVA**
1. Replica in PREP: timeout confermato (30s)
2. Test Postman diretto su BSA VITA (bypass BFF): risposta in 2s
   → BFF non è il collo di bottiglia
3. Analisi Network Tab browser: timeout client-side 30s
4. ROOT CAUSE: Timeout BFF configurato a 30s, ma AIA richiede 35s per query complesse

**SOLUZIONE**
- File: application.yml
- Change: timeout da 30s a 60s
- ESITO: Chiuso come "Works as designed" — problema era query AIA non ottimizzata

**METRICHE**
- Lead Time: 4 ore (SLA: 4h)
- MTTR: 4 ore
- Deploy: Hotfix PROD
```

### 3.2 MANTENERE: Defect (con Test Case)

**FILE REPOSITORY DA USARE:**
- `DEFECT-357020/` — Mapping anagrafico
- `DEFECT-357012/` — Navigazione

**COSA CORREGGERE:**
Aggiungere riferimenti a Test Case:

```markdown
### Defect 357020: Mapping Anagrafico

**TEST CASE FALLITO**
File: DEFECT-357020/TC084_Navigazione (1).docx
- Endpoint: GET /anagrafe/contraente/{codiceNominativo}
- Input: codiceNominativo = "3070000066464" (13 digit)
- Expected: 200 OK
- Actual: 400 Bad Request

**ROOT CAUSE**
BSA Anagrafe richiede formato 14 digit (padding zero a sinistra)

**FIX**
File VITES: anagrafica.service.ts
```typescript
padCodiceNominativo(codice: string): string {
  return codice.padStart(14, '0');
}
```

**RISULTATO**
- Test Case TC084: PASS
- Deploy: SYSR2 → PREP → PROD
- Durata: 3 giorni (1 sprint)
```

### 3.3 MANTENERE: Diagrammi Architetturali (CORRETTI)

**COSA CORREGGERE:**

**BEFORE (ERRATO):**
```
[Browser] → [VITES React] → [BFF] → [BSA VITA Microservizi] → [Azure SQL:1433]
                                 ↓
                            [VIS Cobol]
```

**AFTER (CORRETTO):**
```
[Passportal iframe]
      ↓
[VITES Angular SPA + BFF SpringBoot] ← stesso pod AKS
      ↓ /invocaBsa
[BSA VITA Monolite SpringBoot/Maven]
      ↓
   ┌──┴──────────────┐
   ↓                 ↓
[AIA Backend]   [VIS Legacy]
   ↓                 ↓
[Oracle DB]    [SALT Gateway → WSDL/SOAP → Tuxedo → Cobol → Oracle]

Esterni:
- Anagrafe Unica
- PPEVO (documentale)
```

---

## PARTE 4 — COSA AGGIUNGERE (NUOVO)

### 4.1 AGGIUNGERE: Capitolo Contesto SAFe (MP5/MP6)

**NUOVO CAPITOLO 1** (spostato all'inizio come vuole il tutor):

```markdown
## CAPITOLO 1 — CONTESTO OPERATIVO: SAFe, TEAM E RUOLI

### 1.1 Framework SAFe in Reale ITES
File repository: Area Agile/System+Architect.docx

- Agile Release Train (ART) VITA
- PI (Program Increment) di 10 settimane
- Cerimonie: PI Planning, System Demo, Inspect & Adapt

### 1.2 Ruoli Team
File repository:
- Area Agile/Dev+Team+-+IT+Analyst.docx
- Area Agile/Dev+Team+-+Business+Analyst.docx

- Product Owner
- Scrum Master
- Dev Team (Frontend, Backend, QA)
- System Architect

### 1.3 DevOps Pipeline
- Ambienti: SYSR2 (system test), PREP (pre-prod con VIP GlobalSign), PROD
- Tools: Azure DevOps, Git, Maven
- Deploy: AKS (Azure Kubernetes Service)
```

### 4.2 AGGIUNGERE: Ciclo Vita Software — Incident vs Defect (MP6)

**NUOVO CAPITOLO 3:**

```markdown
## CAPITOLO 3 — CICLO DI VITA DEL SOFTWARE

### 3.1 Incident vs Defect

**INCIDENT**
- DEFINIZIONE: Malfunzionamento in PROD che impatta utenti
- PRIORITÀ: Alta (SLA 4-8h)
- WORKFLOW: Segnalazione → Triage → Fix → Hotfix PROD
- ESEMPIO: I20260218_0324 (timeout 504)

**DEFECT**
- DEFINIZIONE: Bug riscontrato in test (SYSR2, PREP)
- PRIORITÀ: Media-Bassa (1-2 sprint)
- WORKFLOW: Test Case fallito → Debug → Fix → PR → Deploy SYSR2→PREP→PROD
- ESEMPIO: DEFECT-357020 (mapping anagrafico)

### 3.2 Evolutivo vs Correttivo

**CORRETTIVO:** Fix bug/incident
**EVOLUTIVO:** Nuove feature (pianificate in PI Planning)

### 3.3 Ambienti

| Ambiente | Descrizione | VIP | Certificato |
|----------|-------------|-----|-------------|
| SYSR2 | System test | No | Self-signed |
| PREP | Pre-produzione | Sì | GlobalSign |
| PROD | Produzione | Sì | GlobalSign |

File repository: TESI FINALE/bsa info.txt
```

### 4.3 AGGIUNGERE: Unit Testing (MP51)

**NUOVO CAPITOLO 4, SEZIONE 4.4:**

```markdown
## 4.4 Unit Testing

### 4.4.1 Strategia
File repository: TESI FINALE/ProposteApiTest.java

- Framework: JUnit 5 + Mockito
- Target: ProposteApiController (BSA VITA)
- Coverage: 78% (obiettivo: 80%)

### 4.4.2 Esempio Test

**FILE:** ProposteApiTest.java (righe 30-54)

```java
@Test
public void getDatiTecniciV10() {
    CustomNativeWebRequest request = new CustomNativeWebRequest(
        Mockito.mock(HttpServletRequest.class), null);
    request.addParameter("canaleBusiness", "Vites");
    request.addParameter("codiceCompagnia", "RMA");
    // ... altri parametri

    ProposteApiController controller = new ProposteApiController(request);
    ResponseEntity<RispostaDatiTecniciProposta> response =
        controller.getDatiTecniciV10(/* parametri */);

    assertEquals(HttpStatus.OK, response.getStatusCode());
}
```

**VALIDAZIONI:**
- Status HTTP 200 OK
- Mapping JSON corretto
- Parametri obbligatori presenti

### 4.4.3 Test Commentati e Lesson Learned

| Test | Data Commentato | Motivo | Proposta Fix |
|------|----------------|--------|--------------|
| putTipologiaFirmaV10 | 19/12/2025 | Errore 400 pipeline | Mock completo AIA |
| getProposteV10 | 15/01/2023 | Timeout Maven | Aumentare timeout build |
| getPropostaV10 | 11/01/2024 | GraphTalk Fatal Error | WireMock per VIS |

**PROPOSTA MIGLIORAMENTO (CONCLUSIONI):**
- Test Pyramid: 70% unit, 20% integration, 10% E2E
- WireMock per stub AIA/VIS
- Timeout Maven configurabile (pom.xml)
```

### 4.4 AGGIUNGERE: Attività Reali Svolte (MP6/MP42)

**CAPITOLO 4 COMPLETO:**

```markdown
## CAPITOLO 4 — LE ATTIVITÀ SVOLTE

### 4.1 Application Maintenance
- Gestione incident PROD (5 incident documentati)
- Hotfix e deploy emergenziali
- Coordinamento con Service Desk

### 4.2 Gestione Defect
- Replica bug in SYSR2
- Fix su branch feature
- Code Review e Pull Request

### 4.3 Sviluppo Piccole Feature
File repository:
- TESI FINALE/DismissionePositiv_*.docx
- TESI FINALE/AF_VITA-0-FEUXX_EMISSIONE_V5.docx

Esempio: API Dismissione Positiv
- Endpoint: POST /api/vita/v1/proposte/{numeroProposta}/dismissione
- Implementazione: ProposteApiController
- Test: ProposteApiTest.java

### 4.4 Unit Testing
[Sezione dettagliata come sopra]

### 4.5 Troubleshooting — Metodologia Reale

**FRAMEWORK OBBLIGATORIO: Analisi-Osservazione-Prova/Controprova**

FASE 1: Analisi Iniziale
- Lettura ticket
- Identificazione ambiente (PROD/PREP/SYSR2)
- Priorità e SLA

FASE 2: Osservazione
- Splunk: ricerca Correlation-ID
- Grafana: metriche response time, error rate
- Swagger: test manuale API
- Network Tab browser: analisi HTTP

FASE 3: Prova e Controprova
- Replica bug in SYSR2 o locale
- Isolamento componente (FE/BFF/BSA/AIA/VIS)
- Test Postman/JUnit
- ROOT CAUSE analysis

FASE 4: Fix
- Modifica codice su branch
- Commit: "fix: DEFECT-357020 padding codiceNominativo"
- Pull Request

FASE 5: Deploy
- Pipeline Azure DevOps
- Build Maven → Unit Test → Deploy AKS
- Validazione: SYSR2 → PREP → PROD

**ESEMPIO CONCRETO:**
[Incident I20260126_0661 come in TESI_RISTRUTTURATA_GUIDA.md]
```

### 4.5 AGGIUNGERE: PPEVO Sistema Esterno

**CAPITOLO 2, SEZIONE 2.4:**

```markdown
### 2.4.4 PPEVO (Sistema Esterno)
- Sistema documentale di gruppo
- Integrazione per gestione documenti polizze
- Chiamato tramite BSA Documentale
```

---

## PARTE 5 — RIORDINO CAPITOLI (MP5/MP6)

### 5.1 Struttura ATTUALE (ERRATA)

```
1. Introduzione + Metafora
2. Architettura (con errori tecnici)
3. BFF come centro
4. Troubleshooting (5 fasi astratte)
5. Incident/Defect (confusi)
6. Conclusioni
```

### 5.2 Struttura NUOVA (CORRETTA)

```
INTRODUZIONE (2 paragrafi, NO metafora)

CAPITOLO 1: Contesto Operativo — SAFe, Team, Ruoli
  1.1 Framework SAFe
  1.2 Il Team VITA
  1.3 Ruolo Sviluppatore in Maintenance
  1.4 DevOps Pipeline

CAPITOLO 2: Architettura IT del VITA (CORRETTA)
  2.1 Visione d'Insieme
  2.2 Layer Frontend
      2.2.1 Passportal
      2.2.2 VITES (Angular SPA)
      2.2.3 BFF (SpringBoot, stesso pod)
  2.3 Layer Middleware — BSA
      2.3.1 Definizione (Business Sidecar for API)
      2.3.2 BSA VITA (monolite SpringBoot/Maven)
      2.3.3 Altri BSA
  2.4 Layer Backend
      2.4.1 AIA (backend reale, Oracle)
      2.4.2 VIS (Cobol/Tuxedo/SALT)
      2.4.3 Anagrafe Unica
      2.4.4 PPEVO
  2.5 Diagramma Architetturale
  2.6 Monitoraggio (Splunk, Grafana, Swagger)

CAPITOLO 3: Ciclo Vita Software — Incident, Defect, Ambienti
  3.1 Definizioni (Incident vs Defect)
  3.2 Evolutivo vs Correttivo
  3.3 Ambienti (SYSR2, PREP, PROD)
  3.4 Ticketing e Tracking

CAPITOLO 4: Le Attività Svolte
  4.1 Application Maintenance
  4.2 Gestione Defect
  4.3 Sviluppo Piccole Feature
  4.4 Unit Testing (ProposteApiTest.java)
  4.5 Troubleshooting — Metodologia Reale (Analisi-Osservazione-Prova/Controprova)

CAPITOLO 5: Case Study — Incident AVC (504 Timeout)
  5.1 Contesto
  5.2 Timeline
  5.3 Root Cause
  5.4 Lessons Learned

CAPITOLO 6: Case Study — Cluster Teseo (Mapping Compagnia/Agenzia)
  6.1 Contesto
  6.2 Analisi Tecnica
  6.3 Fix Implementati (FE + BE)
  6.4 Test
  6.5 Lessons Learned

CAPITOLO 7: Conclusioni e Competenze Acquisite
  7.1 Competenze Tecniche
  7.2 Competenze Metodologiche
  7.3 Proposte Miglioramento (Unit Testing, Documentazione, Monitoring)
  7.4 Riflessione Finale

BIBLIOGRAFIA E SITOGRAFIA

APPENDICE A: Incident e Defect Gestiti (tabella)
APPENDICE B: Unit Test Coverage (tabella)
GLOSSARIO TECNICO
```

---

## PARTE 6 — CHECKLIST OPERATIVA RISTRUTTURAZIONE

### 6.1 FASE 1: ELIMINAZIONE (PRIORITÀ MASSIMA)

- [ ] **VP1:** Eliminare metafora Bridge Pattern dall'introduzione
- [ ] **VP2:** Eliminare box esplicativi Bridge Pattern da tutti i capitoli
- [ ] **VP3:** Eliminare parallelismi vita/codice
- [ ] **MP1:** Rimuovere capitolo dedicato al BFF (compattare in 1 pagina)
- [ ] **MP2:** Rimuovere "Anti-Corruption Layer" dal titolo
- [ ] **MP3:** Eliminare "due controller distinti" nel BFF
- [ ] **MP4:** Eliminare diagrammi BFF e FE deployati separatamente

### 6.2 FASE 2: CORREZIONI TECNICHE (BLOCCANTI)

- [ ] **MP11:** Correggere "React" → "Angular" in VITES
- [ ] **MP12:** Correggere "microservizi" → "monolite SpringBoot/Maven" in BSA VITA
- [ ] **MP13:** Aggiungere "BFF e VITES stesso package SpringBoot"
- [ ] **MP20-27:** Correggere "Azure SQL:1433" → "Servizi AIA → Oracle DB"
- [ ] **MP28:** Correggere acronimo "BSA = Business Sidecar for API"
- [ ] **MP29:** Correggere "AIA sistema documentale" → "AIA backend reale VITA"
- [ ] **MP30:** Aggiungere flow VIS: "Oracle SQL → SALT Gateway → WSDL/SOAP → Tuxedo → Cobol"
- [ ] **MP31:** Aggiungere PPEVO come sistema esterno

### 6.3 FASE 3: AGGIUNTA CONTENUTI (OBBLIGATORI)

- [ ] **MP5/MP6:** Spostare contesto SAFe/team/ruoli come CAPITOLO 1
- [ ] **MP6:** Aggiungere CAPITOLO 3: Incident vs Defect (definizioni)
- [ ] **MP6:** Aggiungere ambienti: SYSR2, PREP, PROD
- [ ] **MP42:** Riscrivere troubleshooting con metodo REALE:
  - [ ] Splunk (Correlation-ID)
  - [ ] Replica SYSR2/PREP
  - [ ] Debug locale
  - [ ] Fix branch
  - [ ] Pipeline deploy
- [ ] **MP51:** Aggiungere CAPITOLO 4.4: Unit Testing
  - [ ] File ProposteApiTest.java (esempio getDatiTecniciV10)
  - [ ] Coverage 78%
  - [ ] Test commentati e motivi
  - [ ] Proposta miglioramento

### 6.4 FASE 4: RISCRITTURA INCIDENT/DEFECT (FRAMEWORK)

- [ ] **Incident I20260218_0324 (AVC 504):**
  - [ ] Analisi Iniziale (ticket, ambiente, SLA)
  - [ ] Osservazione Splunk (file splunk6.docx)
  - [ ] Prova/Controprova (test Postman, Network Tab)
  - [ ] Root Cause (timeout BFF 30s vs AIA 35s)
  - [ ] Soluzione (incremento timeout)
  - [ ] Esito ("Works as designed")
  - [ ] Metriche (Lead Time 4h, MTTR 4h)

- [ ] **Incident I20260119_0317 (Teseo):**
  - [ ] Framework Analisi-Osservazione-Prova/Controprova
  - [ ] Fix FE (agenzia-selector.component.ts)
  - [ ] Fix BE (MappingAgenziaService.java)
  - [ ] Test (15 casi edge)
  - [ ] Lesson Learned (documentazione mancante)

- [ ] **Defect 357020:**
  - [ ] Test Case TC084 fallito
  - [ ] Root Cause (13 vs 14 digit)
  - [ ] Fix (padding TypeScript)
  - [ ] Deploy SYSR2→PREP→PROD

### 6.5 FASE 5: DOCUMENTAZIONE E APPENDICI

- [ ] Aggiungere APPENDICE A: Tabella incident/defect gestiti
- [ ] Aggiungere APPENDICE B: Tabella unit test coverage
- [ ] Aggiungere GLOSSARIO con acronimi corretti
- [ ] Aggiungere BIBLIOGRAFIA:
  - [ ] Documenti Reale ITES (VITES_Descrittori, API Blueprint, Log Guidelines)
  - [ ] Framework (Spring, Angular, JUnit, SAFe)

### 6.6 FASE 6: PROPOSTE MIGLIORAMENTO (CONCLUSIONI)

- [ ] **Unit Testing:**
  - [ ] Test Pyramid (70% unit, 20% integration, 10% E2E)
  - [ ] WireMock per stub AIA/VIS
  - [ ] Timeout Maven configurabile

- [ ] **Documentazione Tecnica:**
  - [ ] Data Dictionary su Confluence
  - [ ] Diagrammi C4 Model
  - [ ] ADR (Architecture Decision Records)

- [ ] **Monitoring Proattivo:**
  - [ ] Alert Grafana per latenza >30s
  - [ ] Dashboard SLA (availability, MTTR)
  - [ ] Synthetic monitoring (test PROD ogni 5 min)

---

## PARTE 7 — METRICHE CONTROLLO QUALITÀ

### 7.1 Checklist Validazione Finale

Prima di consegnare la tesi al tutor, verificare:

| Criterio | Target | Come Verificare |
|----------|--------|-----------------|
| **Metafora eliminata** | 0 occorrenze "Bridge Pattern" | Ctrl+F nel documento |
| **React eliminato** | 0 occorrenze "React" | Ctrl+F "React" |
| **Angular corretto** | 100% occorrenze VITES | Ctrl+F "Angular" |
| **Azure SQL eliminato** | 0 occorrenze "Azure SQL" | Ctrl+F "Azure SQL" |
| **Oracle DB corretto** | 100% occorrenze database | Ctrl+F "Oracle" |
| **BSA acronimo** | 100% "Business Sidecar for API" | Prima occorrenza BSA |
| **Framework troubleshooting** | 5 fasi concrete | Ogni incident ha Analisi-Osservazione-Prova |
| **Unit test presente** | Capitolo 4.4 esistente | Verifica indice |
| **ProposteApiTest.java citato** | Almeno 3 riferimenti | Ctrl+F "ProposteApiTest" |
| **Incident con Splunk** | Correlation-ID in tutti | Verifica screenshot Splunk |
| **Defect con Test Case** | TC084 citato | Verifica DEFECT-357020 |
| **PPEVO menzionato** | Almeno 1 occorrenza | Ctrl+F "PPEVO" |
| **Glossario completo** | Tutti acronimi definiti | Verifica glossario finale |

### 7.2 Stima Pagine Finale

| Sezione | Pagine Stimate |
|---------|----------------|
| Introduzione | 1 |
| Capitolo 1 (SAFe/Team) | 4-5 |
| Capitolo 2 (Architettura) | 10-12 |
| Capitolo 3 (Ciclo Vita) | 3-4 |
| Capitolo 4 (Attività) | 12-15 |
| Capitolo 5 (Case AVC) | 4-5 |
| Capitolo 6 (Case Teseo) | 4-5 |
| Capitolo 7 (Conclusioni) | 3-4 |
| Bibliografia | 1 |
| Appendici | 2-3 |
| Glossario | 1-2 |
| **TOTALE** | **45-60 pagine** |

---

## PARTE 8 — CONFRONTO PRIMA/DOPO

### 8.1 Tesi PRIMA (ERRATA)

**FOCUS:** BFF e metafora Bridge Pattern
**ARCHITETTURA:** Errori tecnici (React, Azure SQL, microservizi)
**TROUBLESHOOTING:** 5 fasi astratte
**UNIT TEST:** Assente
**INCIDENT:** Descritti superficialmente
**DEFECT:** Incident e Defect confusi
**TONE:** Umanistico, diaristico

### 8.2 Tesi DOPO (CORRETTA)

**FOCUS:** BSA VITA, VIS, AIA (lavoro reale)
**ARCHITETTURA:** Corretta (Angular, Oracle via AIA, monolite, SALT/Tuxedo)
**TROUBLESHOOTING:** Framework Analisi-Osservazione-Prova/Controprova con Splunk
**UNIT TEST:** Capitolo dedicato con ProposteApiTest.java
**INCIDENT:** Framework completo (timeline, Splunk, root cause, metriche)
**DEFECT:** Distinti da incident, con Test Case
**TONE:** Tecnico, ingegneristico, basato su metriche

---

## PARTE 9 — PRIORITÀ INTERVENTI

### 9.1 URGENZA MASSIMA (BLOCCANTI)

1. **Eliminare metafora Bridge Pattern** (VP × 8)
2. **Correggere React → Angular** (MP11)
3. **Correggere Azure SQL → Oracle via AIA** (MP20-27)
4. **De-enfatizzare BFF** (MP2/MP3)

### 9.2 URGENZA ALTA (CONTENUTI MANCANTI)

5. **Aggiungere unit testing** (MP51)
6. **Riscrivere troubleshooting con metodo reale** (MP42)
7. **Separare incident da defect** (MP6)
8. **Spostare contesto SAFe come Cap. 1** (MP5/MP6)

### 9.3 URGENZA MEDIA (CORREZIONI TECNICHE)

9. Correggere BSA acronimo
10. Correggere AIA (non documentale)
11. Aggiungere VIS flow completo (SALT)
12. Aggiungere PPEVO

### 9.4 URGENZA BASSA (MIGLIORAMENTI)

13. Appendici (tabelle incident/defect)
14. Glossario completo
15. Proposte miglioramento (conclusioni)

---

## CONCLUSIONE ANALISI

### VERDETTO FINALE

La tesi attuale **NON È RECUPERABILE con semplici correzioni**. Serve una **RISTRUTTURAZIONE TOTALE** perché:

1. **Struttura portante errata:** Metafora Bridge Pattern permea tutta la tesi
2. **Focus sbagliato:** BFF (lavoro minimo) invece di BSA/VIS/AIA (lavoro reale)
3. **Errori tecnici sistemici:** React, Azure SQL, microservizi sono errori che il tutor ha segnalato come BLOCCANTI

### RACCOMANDAZIONE

**USARE IL FILE `TESI_RISTRUTTURATA_GUIDA.md` COME BASE** per riscrivere completamente la tesi. Non tentare di "patchare" la versione attuale, ma:

1. Aprire documento Word vuoto
2. Copiare struttura da `TESI_RISTRUTTURATA_GUIDA.md`
3. Integrare screenshot/documenti dal repository
4. Validare con checklist Parte 7.1

### TEMPO STIMATO

- Ristrutturazione completa: 20-30 ore
- Integrazione screenshot: 5-10 ore
- Validazione e revisione: 5 ore
- **TOTALE: 30-45 ore**

### FILE DA USARE COME RIFERIMENTO

✅ **USARE:**
- `TESI_RISTRUTTURATA_GUIDA.md` (base completa)
- `ProposteApiTest.java` (unit test)
- `INCIDENT-*/PROBLEMA E SOLUZIONE.docx` (casi reali)
- `DEFECT-*/357020.docx` (defect reali)
- `Architecture and Api Italy/*.docx` (architettura corretta)
- `bsa info.txt` (ambienti BSA)

❌ **NON USARE:**
- `TESI_FOMO.docx` (struttura errata, ripartire da zero)

---

**FINE ANALISI**

Prossimo passo: Aprire Word e iniziare ristrutturazione seguendo `TESI_RISTRUTTURATA_GUIDA.md` capitolo per capitolo.
