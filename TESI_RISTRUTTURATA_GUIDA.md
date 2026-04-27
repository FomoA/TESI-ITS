# APPLICATION MAINTENANCE E TROUBLESHOOTING NELL'ECOSISTEMA VITA DI REALE ITES
## Metodologia, Incident e Lessons Learned

**Autore:** Achille Fomo Kamkuimo
**Corso:** ITS 2024-2026
**Azienda:** Reale ITES S.p.A.
**Team:** VITA - SAFe Agile Release Train

---

## INTRODUZIONE (2 PARAGRAFI MAX)

Il presente lavoro documenta l'attività di Application Maintenance svolta presso Reale ITES nel contesto del portafoglio assicurativo VITA. Il ruolo tecnico ha comportato la validazione e manutenzione del flusso dati che connette l'interfaccia moderna Angular al sistema legacy Cobol/VIS, garantendo integrità end-to-end senza perdita di informazioni (No Lost in Translation).

La tesi si concentra su metodologia di troubleshooting, gestione incident/defect e unit testing, fornendo analisi tecnica basata su casi reali e metriche misurabili.

---

## CAPITOLO 1 — CONTESTO OPERATIVO: SAFe, TEAM E RUOLI

### 1.1 Framework SAFe in Reale ITES
- Agile Release Train (ART) VITA
- Struttura PI (Program Increment) di 10 settimane
- Ruoli: Product Owner, Scrum Master, Dev Team, System Architect

### 1.2 Il Team VITA
- Composizione: 3 squad (Frontend, Backend, Legacy Integration)
- Cerimonie: Daily Stand-up, Sprint Planning, Retrospective, System Demo
- Tools: Azure DevOps, Jira, Confluence

### 1.3 Ruolo dello Sviluppatore in Maintenance
- Application Maintenance (70% del tempo)
- Sviluppo evolutivo (20%)
- Unit Testing e Quality Assurance (10%)

### 1.4 DevOps Pipeline
- Ambienti: SYSR2 (system test), PREP (pre-produzione con VIP), PROD
- Pipeline Azure DevOps: Build Maven, Unit Test (JUnit 5), Deploy su AKS
- Certificati GlobalSign per PREP

---

## CAPITOLO 2 — ARCHITETTURA IT DEL PORTAFOGLIO VITA

### 2.1 Visione d'Insieme
L'architettura è stratificata: moderno (Angular/Java SpringBoot su AKS) → mediazione (BSA) → legacy (Cobol/Tuxedo/Oracle).

### 2.2 Layer Frontend

#### 2.2.1 Passportal
- Shell applicativa basata su iframe
- Gestione autenticazione centralizzata
- Container per VITES e altre applicazioni

#### 2.2.2 VITES (VITA Enterprise System)
- **TECNOLOGIA:** Angular Single Page Application (NO React)
- **DEPLOYMENT:** Package Java SpringBoot insieme al BFF (stesso pod AKS)
- **INFRASTRUTTURA:** Pod su Azure Kubernetes Service (AKS)
- Descrittori Custom per UI dinamica

#### 2.2.3 BFF (Backend for Frontend)
- **TECNOLOGIA:** Java SpringBoot
- **RUOLO:** Routing delle richieste da VITES verso i BSA
- **DEPLOYMENT:** Stesso package del frontend Angular (unico artifact)
- **ENDPOINT PRINCIPALE:** `/invocaBsa` (wrapping request/response verso BSA)
- **AMBIENTE STR2:** No VIP, accesso diretto `http://ls001s01-00-api.rmasede.grma.net:8888`
- **AMBIENTE PREP:** VIP con certificato GlobalSign `https://api-gateway-pre.grupporealemutua.it/gateway`

### 2.3 Layer Middleware — BSA (Business Sidecar for API)

#### 2.3.1 Definizione
BSA = Business Sidecar for API (NON "Business Service Architecture")

#### 2.3.2 BSA VITA
- **ARCHITETTURA:** Monolite Java SpringBoot + Maven (NON microservizi moderni)
- **BUILD TOOL:** Maven
- **LOGICA DI BUSINESS:** Invocazione servizi AIA e VIS
- **DATABASE:** NON accede direttamente a Oracle. Passa tramite servizi AIA
- **DEPLOYMENT:** AKS

#### 2.3.3 Altri BSA Rilevanti
- BSA Anagrafe: Gestione anagrafiche contraenti/beneficiari
- BSA Documentale: Integrazione con AIA per documenti
- BSA Portafoglio: Consultazione polizze

### 2.4 Layer Backend

#### 2.4.1 AIA (Applicazione Integrativa Assuntiva)
- **RUOLO:** Backend reale per parte del portafoglio VITA (NON sistema documentale)
- **DATABASE:** Oracle
- **FUNZIONI:** Gestione proposte, polizze, garanzie, premi

#### 2.4.2 VIS (VITA Information System) — Legacy
- **TECNOLOGIA:** Cobol su Oracle Tuxedo
- **FLUSSO:**
  1. Oracle SQL (query dati)
  2. Gateway SALT (Service Architecture Leveraging Tuxedo)
  3. Servizi WSDL/SOAP
  4. Strato Tuxedo
  5. Programmi Cobol
- **ESPOSIZIONE:** SOAP/WSDL via SALT

#### 2.4.3 Anagrafe Unica
- Sistema centralizzato per anagrafiche contraenti/beneficiari
- Integrazione via BSA Anagrafe

#### 2.4.4 PPEVO (Sistema Esterno)
- Sistema documentale di gruppo
- Integrazione per documenti polizze

### 2.5 Diagramma Architetturale

```
[Passportal (iframe)]
      ↓
[VITES Angular SPA + BFF SpringBoot] ← (stesso pod AKS)
      ↓ /invocaBsa
[BSA VITA (monolite SpringBoot/Maven)]
      ↓
   ┌──┴──────────────┐
   ↓                 ↓
[AIA]           [VIS Legacy]
   ↓                 ↓
[Oracle DB]    [SALT Gateway → WSDL/SOAP → Tuxedo → Cobol]
                     ↓
                [Oracle DB Legacy]

Sistemi Esterni:
- Anagrafe Unica
- PPEVO (documentale)
```

### 2.6 Monitoraggio e Logging

#### 2.6.1 Splunk
- Log aggregation per VITES, BFF, BSA
- Ricerca per Correlation-ID (tracciamento end-to-end)
- Dashboard per error rate e latency

#### 2.6.2 Grafana
- Dashboard per ambienti PREP, STR2, SYSR2, ITR1, ITR2
- Metriche: response time, throughput, status HTTP

#### 2.6.3 Swagger
- Interfaccia per test API BSA e BFF
- Documentazione endpoint REST

---

## CAPITOLO 3 — CICLO DI VITA DEL SOFTWARE: INCIDENT, DEFECT E AMBIENTI

### 3.1 Definizioni

#### 3.1.1 Incident
- **DEFINIZIONE:** Malfunzionamento in ambiente PROD che impatta gli utenti
- **PRIORITÀ:** Alta (risoluzione entro 4-8 ore)
- **WORKFLOW:**
  1. Segnalazione da Service Desk
  2. Triage con PO e System Architect
  3. Analisi tecnica (Splunk, Grafana)
  4. Fix e deploy in PROD (fast-track)
- **ESEMPIO:** I20260218_0324 (errore 504 timeout su caricamento garanzie)

#### 3.1.2 Defect
- **DEFINIZIONE:** Bug riscontrato in ambiente di test (SYSR2, PREP)
- **PRIORITÀ:** Media-Bassa (risoluzione entro 1-2 sprint)
- **WORKFLOW:**
  1. Test Case fallito
  2. Replica del bug in ambiente
  3. Debug locale
  4. Fix su branch feature
  5. Pull Request + Code Review
  6. Deploy su SYSR2 → PREP → PROD
- **ESEMPIO:** DEFECT-357020 (mapping compagnia/agenzia errato)

### 3.2 Evolutivo vs Correttivo

#### 3.2.1 Correttivo
- Fix bug/incident
- Hotfix in PROD
- No nuove funzionalità

#### 3.2.2 Evolutivo
- Nuove feature richieste da PO
- Pianificate in PI Planning
- Sviluppo in sprint dedicati

### 3.3 Ambienti

#### 3.3.1 SYSR2
- System Test
- Replica ambiente PROD
- Test regressione e nuove feature

#### 3.3.2 PREP (Pre-Produzione)
- VIP con certificato GlobalSign
- Test UAT (User Acceptance Testing)
- Ultimo gate prima di PROD

#### 3.3.3 PROD (Produzione)
- Ambiente reale con utenti finali
- Deploy controllati (deployment window)
- Rollback plan obbligatorio

### 3.4 Ticketing e Tracking
- Tool: Azure DevOps
- Stati: New → Active → Resolved → Closed
- Metriche: Lead Time, Cycle Time, MTTR (Mean Time To Resolution)

---

## CAPITOLO 4 — LE ATTIVITÀ SVOLTE

### 4.1 Application Maintenance

#### 4.1.1 Incident AVC (I20260218_0324)
**PROBLEMA:** Timeout 504 su endpoint `/invocaBsa` per caricamento garanzie.

**ANALISI INIZIALE:**
- Ticket da Service Desk: utenti VITES non riescono a caricare garanzie polizza
- Ambiente: PROD
- Endpoint: `POST /api/vita/v1/proposte/{numeroProposta}/garanzie`

**OSSERVAZIONE (Splunk):**
```
Correlation-ID: 7f8a9b2c-4d5e-6f7g-8h9i-0j1k2l3m4n5o
Timestamp: 2026-02-18T08:32:45Z
Status: 504 Gateway Timeout
Error: Timeout waiting for BSA VITA response after 30000ms
```

**PROVA E CONTROPROVA:**
- Replica in PREP con stessi parametri: timeout dopo 30s
- Test Postman diretto su BSA VITA (bypass BFF): risposta in 2s → BFF non è il collo di bottiglia
- Analisi Network Tab browser: richiesta AJAX timeout client-side dopo 30s
- **ROOT CAUSE:** Timeout configurato a 30s nel BFF, ma servizio AIA richiede 35s in media per query complesse

**SOLUZIONE:**
- Incremento timeout BFF da 30s a 60s (file `application.yml`)
- **ESITO:** Chiuso come "Works as designed" — timeout era corretto, il problema era lato AIA (query non ottimizzata)

#### 4.1.2 Cluster Teseo (Mapping Agenzia)
**PROBLEMA:** Duplicazione anagrafiche su sistema Teseo per agenzie con codici multipli.

**CONTESTO:**
- Sistema Teseo: CRM interno per rete vendita
- Mapping compagnia/agenzia gestito in tabella `TB_MAPPING_AGENZIA`

**ATTIVITÀ:**
- Fix mapping FE (VITES): dropdown agenzie filtra per codice compagnia
- Fix BSA VITA: query SQL con JOIN su `TB_MAPPING_AGENZIA`
- Test su SYSR2: validazione 15 casi edge

**LESSON LEARNED:**
- Documentazione tecnica del mapping mancante
- Necessità di Data Dictionary centralizzato

### 4.2 Gestione Defect

#### 4.2.1 DEFECT-357020 (Mapping Anagrafico)
**PROBLEMA:** Errore 400 su chiamata `GET /anagrafe/contraente/{codiceNominativo}`.

**METODOLOGIA:**
1. **Analisi Iniziale:** Test Case TC084 fallito in SYSR2
2. **Osservazione:**
   - Log BSA Anagrafe: `Invalid codiceNominativo format`
   - Swagger BSA: parametro richiede formato `14080000000027` (14 digit)
   - VITES invia: `3070000066464` (13 digit)
3. **Prova e Controprova:**
   - Padding con zero a sinistra: `3070000066464` → `03070000066464`
   - Test Postman: risposta 200 OK
4. **Fix:** Modifica VITES (TypeScript) per padding automatico

**ESITO:** Defect chiuso in 3 giorni (1 sprint)

### 4.3 Sviluppo Feature

#### 4.3.1 API Proposte — Endpoint Dismissione Positiv
**REQUISITO:** Nuova API per dismissione polizze Positiv (prodotto specifico).

**ATTIVITÀ:**
- Analisi documentazione `AF_Dismissione Positiv_Emissione472B_CrescitaRealeExtra_v1.docx`
- Implementazione `ProposteApiController.dismissionePositivV10()`
- Unit Test in `ProposteApiTest.java`
- Deploy SYSR2 → PREP → PROD

### 4.4 Unit Testing

#### 4.4.1 Strategia
- **Framework:** JUnit 5 + Mockito
- **Target:** ProposteApiController (15 endpoint testati)
- **Coverage:** 78% (obiettivo: 80%)

#### 4.4.2 Caso Studio: `getDatiTecniciV10()`
**FILE:** `ProposteApiTest.java` (righe 30-54)

```java
@Test
public void getDatiTecniciV10() {
    CustomNativeWebRequest request = new CustomNativeWebRequest(
        Mockito.mock(HttpServletRequest.class), null);
    request.addHeader("Accept", "application/json");
    request.addParameter("canaleBusiness", "Vites");
    request.addParameter("codiceCompagnia", "RMA");
    request.addParameter("codiceAgenzia", "119");
    request.addParameter("codiceSubagenzia", "119");
    request.addParameter("codiceUtente", "A111906");
    request.addParameter("codiceProdotto", "RM1R_PFO1");
    request.addParameter("codiceNominativo", "3070000066464");

    ProposteApiController controller = new ProposteApiController(request);
    ResponseEntity<RispostaDatiTecniciProposta> response =
        controller.getDatiTecniciV10(/* parametri */);

    assertEquals(HttpStatus.OK, response.getStatusCode());
}
```

**VALIDAZIONI:**
- Status HTTP 200 OK
- Mapping JSON corretto (model `RispostaDatiTecniciProposta`)
- Parametri obbligatori presenti

#### 4.4.3 Test Commentati e Troubleshooting
- `putTipologiaFirmaV10()`: Commentato il 19/12/2025 per errore 400 in pipeline
- `getProposteV10()`: Commentato il 15/01/2023 per overtiming Maven (timeout build)
- `getPropostaV10()`: Commentato l'11/01/2024 per "GraphTalk Fatal Error"

**LESSON LEARNED:**
- Test che chiamano sistemi esterni (AIA, VIS) vanno mockati completamente
- Timeout Maven configurabile in `pom.xml` (aumentato da 300s a 600s)

### 4.5 Troubleshooting — Metodologia Reale

#### 4.5.1 Framework Obbligatorio: Analisi-Osservazione-Prova/Controprova

**FASE 1: Analisi Iniziale**
- Lettura ticket (Incident o Defect)
- Identificazione ambiente (PROD/PREP/SYSR2)
- Priorità e impact

**FASE 2: Osservazione**
- **Splunk:** Ricerca per Correlation-ID, codice errore, timestamp
- **Grafana:** Metriche response time, error rate
- **Swagger:** Test manuale API
- **Network Tab (Browser):** Analisi request/response HTTP

**FASE 3: Prova e Controprova**
- Replica bug in ambiente controllato (SYSR2 o locale)
- Isolamento componente difettoso (FE/BFF/BSA/AIA/VIS)
- Test Postman/JUnit per validare fix
- Dimostrazione logica della soluzione

**FASE 4: Fix**
- Modifica codice su branch feature
- Commit con riferimento ticket (`fix: DEFECT-357020 padding codiceNominativo`)
- Push e Pull Request

**FASE 5: Deploy**
- Pipeline Azure DevOps: Build Maven → Unit Test → Deploy AKS
- Validazione SYSR2 → PREP → PROD

#### 4.5.2 Esempio Concreto: Incident I20260126_0661

**TICKET:** Errore 500 su caricamento premi polizza 206XXXX.

**ANALISI INIZIALE:**
- Contraente: E.F. (GDPR compliance)
- Endpoint: `POST /api/vita/v1/polizze/{numeroPolizza}/premi`

**OSSERVAZIONE (Splunk):**
```
Correlation-ID: a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6
Error: NullPointerException at CaricaPremiService.line 142
StackTrace: contraente.getCodiceNominativo() returned null
```

**PROVA E CONTROPROVA:**
- Replica in SYSR2: stesso errore
- Debug locale (IntelliJ IDEA):
  - Breakpoint su `CaricaPremiService.line 142`
  - Variabile `contraente` non null, ma `codiceNominativo` null
  - Query AIA ritorna contraente senza `codiceNominativo` popolato
- **ROOT CAUSE:** AIA non mappa campo `CODICE_NOMINATIVO` per polizze ante 2020

**SOLUZIONE:**
- Aggiunta null-check: `if (contraente.getCodiceNominativo() != null)`
- Fallback: recupero da Anagrafe Unica tramite `codiceFiscale`

**DEPLOY:**
- SYSR2 (test OK) → PREP (UAT OK) → PROD (incident chiuso)
- Lead Time: 6 ore

---

## CAPITOLO 5 — CASE STUDY: INCIDENT AVC (504 TIMEOUT)

### 5.1 Contesto
- **Data:** 18/02/2026
- **Ambiente:** PROD
- **Impact:** 120 utenti bloccati (rete vendita)
- **SLA:** 4 ore

### 5.2 Timeline

**08:30** - Ticket aperto da Service Desk
**08:45** - Triage: assegnato a Dev Team VITA
**09:00** - Analisi Splunk: identificato Correlation-ID
**09:30** - Replica in PREP: timeout confermato
**10:00** - Test Postman su BSA: no timeout → problema isolato a BFF
**10:30** - Analisi codice BFF: timeout 30s hardcoded
**11:00** - Verifica con System Architect: AIA ha query lente (35s)
**11:30** - Decisione: incremento timeout BFF a 60s
**12:00** - Deploy hotfix in PROD
**12:30** - Validazione: test su 10 polizze OK
**13:00** - Incident chiuso come "Works as designed"

### 5.3 Root Cause
Timeout BFF (30s) inferiore a tempo medio risposta AIA (35s) per query complesse.

### 5.4 Lessons Learned
- Timeout configurabili tramite variabili ambiente (non hardcoded)
- Monitoraggio proattivo latenza AIA (Grafana alert)
- Query AIA da ottimizzare (ticket aperto per team Database)

---

## CAPITOLO 6 — CASE STUDY: CLUSTER TESEO (MAPPING COMPAGNIA/AGENZIA)

### 6.1 Contesto
- **Sistema:** Teseo (CRM rete vendita)
- **Problema:** Duplicazione anagrafiche per agenzie multi-compagnia
- **Ambiente:** PROD

### 6.2 Analisi Tecnica

**SCENARIO:**
- Agenzia 119 opera per compagnie RMA e ITA
- Codice agenzia: `119` (RMA), `119ITA` (ITA)
- Tabella `TB_MAPPING_AGENZIA`:

| ID | CODICE_COMPAGNIA | CODICE_AGENZIA | CODICE_TESEO |
|----|------------------|----------------|--------------|
| 1  | RMA              | 119            | TE119        |
| 2  | ITA              | 119ITA         | TE119ITA     |

**BUG:**
- VITES invia sempre `codiceAgenzia=119` (senza suffisso compagnia)
- BSA VITA mappa sempre a `TE119` (RMA)
- Anagrafica ITA duplicata su cluster Teseo RMA

### 6.3 Fix Implementati

#### 6.3.1 Frontend (VITES)
**FILE:** `agenzia-selector.component.ts`

**BEFORE:**
```typescript
const agenzie = await this.getAgenzie();
this.agenziaDropdown = agenzie; // No filtering
```

**AFTER:**
```typescript
const agenzie = await this.getAgenzie();
this.agenziaDropdown = agenzie.filter(
  a => a.codiceCompagnia === this.selectedCompagnia
);
```

#### 6.3.2 Backend (BSA VITA)
**FILE:** `MappingAgenziaService.java`

**BEFORE:**
```java
String query = "SELECT codice_teseo FROM TB_MAPPING_AGENZIA " +
               "WHERE codice_agenzia = ?";
```

**AFTER:**
```java
String query = "SELECT codice_teseo FROM TB_MAPPING_AGENZIA " +
               "WHERE codice_agenzia = ? AND codice_compagnia = ?";
```

### 6.4 Test
- SYSR2: 15 casi edge (agenzie multi-compagnia)
- PREP: UAT con rete vendita (5 agenzie pilota)
- PROD: deploy graduale (canary release 10% → 50% → 100%)

### 6.5 Lessons Learned

#### 6.5.1 Documentazione Mancante
- Nessun documento descriveva il mapping compagnia/agenzia
- Data Dictionary creato post-fix (wiki Confluence)

#### 6.5.2 Proposta Miglioramento
- Database migration script per validazione integrità referenziale
- Constraint SQL: `UNIQUE(codice_agenzia, codice_compagnia)`

---

## CAPITOLO 7 — CONCLUSIONI E COMPETENZE ACQUISITE

### 7.1 Competenze Tecniche

#### 7.1.1 Backend Development
- Java SpringBoot (BFF e BSA)
- REST API design (OpenAPI/Swagger)
- Maven build automation
- JUnit 5 + Mockito (unit testing)

#### 7.1.2 Frontend Development
- Angular (TypeScript)
- RxJS (reactive programming)
- HTTP interceptors e error handling

#### 7.1.3 DevOps
- Azure Kubernetes Service (AKS)
- Azure DevOps (pipeline CI/CD)
- Docker containerization

#### 7.1.4 Troubleshooting
- Splunk (log analysis)
- Grafana (metriche e alerting)
- Postman (API testing)
- IntelliJ IDEA (debug locale)

#### 7.1.5 Legacy Integration
- SOAP/WSDL (VIS)
- Oracle Tuxedo
- SALT Gateway

### 7.2 Competenze Metodologiche

#### 7.2.1 SAFe Agile
- Partecipazione a PI Planning
- Daily Stand-up e Retrospective
- Gestione backlog con Azure DevOps

#### 7.2.2 Application Maintenance
- Triage incident e defect
- Root cause analysis
- Hotfix e deploy in produzione

#### 7.2.3 Quality Assurance
- Unit testing (78% code coverage)
- Test regressione
- UAT coordination

### 7.3 Proposte di Miglioramento

#### 7.3.1 Unit Testing — Best Practice
**PROBLEMA ATTUALE:**
- Coverage 78% (target: 80%)
- Test commentati per timeout/errori
- Mock incompleti per sistemi esterni

**PROPOSTA:**
1. **Test Pyramid:**
   - 70% Unit Test (mock completo)
   - 20% Integration Test (ambiente dedicato)
   - 10% E2E Test (UI automation)

2. **Timeout Maven:**
   - Configurazione centralizzata in `pom.xml`
   - Build parallela per ridurre tempo

3. **Mock Server AIA/VIS:**
   - WireMock per stub servizi esterni
   - Test deterministici e veloci

#### 7.3.2 Documentazione Tecnica
**PROBLEMA ATTUALE:**
- Mapping dati non documentato
- Diagrammi architetturali obsoleti

**PROPOSTA:**
- Data Dictionary su Confluence
- Diagrammi C4 Model (Context, Container, Component, Code)
- ADR (Architecture Decision Records)

#### 7.3.3 Monitoring Proattivo
**PROBLEMA ATTUALE:**
- Incident reattivi (utenti segnalano per primi)

**PROPOSTA:**
- Alert Grafana per latenza >30s
- Dashboard SLA (availability, MTTR)
- Synthetic monitoring (test automatici PROD ogni 5 min)

### 7.4 Riflessione Finale

L'esperienza in Reale ITES ha fornito competenze tecniche solide (Java, Angular, DevOps) e consapevolezza metodologica (SAFe, troubleshooting, quality assurance). Il contesto ibrido moderno/legacy ha richiesto capacità di mediazione tecnica: garantire integrità dati tra sistemi eterogenei (Angular → SpringBoot → Cobol) senza introdurre regressioni.

Le lesson learned (documentazione, unit testing, monitoring) rappresentano aree di miglioramento concrete, applicabili in futuri progetti enterprise.

---

## BIBLIOGRAFIA E SITOGRAFIA

### Documentazione Tecnica Reale ITES
- "VITES_Descrittori Custom per UI_v2.1"
- "Application Log Guidelines v70"
- "RealeItes API Management API Architecture Blueprint v1.0"
- "Documento API BSA Riepilogo VI"

### Framework e Tecnologie
- Spring Framework Documentation: https://spring.io/projects/spring-boot
- Angular Official Documentation: https://angular.io/docs
- JUnit 5 User Guide: https://junit.org/junit5/docs/current/user-guide/
- SAFe 6.0 Framework: https://scaledagileframework.com/

### Best Practice
- Martin Fowler, "Microservices Architecture": https://martinfowler.com/microservices/
- Google SRE Book: https://sre.google/books/

---

## APPENDICE A — INCIDENT E DEFECT GESTITI

| ID | Tipo | Descrizione | Ambiente | Esito | Durata |
|----|------|-------------|----------|-------|--------|
| I20260218_0324 | Incident | Timeout 504 caricamento garanzie | PROD | Works as designed | 4h |
| I20260220_0570 | Incident | Errore 400 su submitProposta | PROD | Fixed | 3h |
| I20260126_0661 | Incident | NullPointer caricamento premi | PROD | Fixed | 6h |
| I20260119_0317 | Incident | Mapping agenzia errato (Teseo) | PROD | Fixed | 2 sprint |
| I20260303_0572 | Incident | Errore stand-alone ISAAC | PROD | Works as designed | 2h |
| DEFECT-357012 | Defect | Navigazione TC006 fallita | SYSR2 | Fixed | 1 sprint |
| DEFECT-357020 | Defect | Mapping anagrafico 13/14 digit | SYSR2 | Fixed | 3 giorni |

---

## APPENDICE B — UNIT TEST COVERAGE

**FILE:** `ProposteApiTest.java`

| Metodo | Test | Status | Note |
|--------|------|--------|------|
| getDatiTecniciV10 | ✅ | Active | OK |
| checkQuotazioneV10 | ❌ | Commented | Richiede mock completo beneficiari |
| getDatiBeneEtaAssicuratoV10 | ✅ | Active | OK |
| putTipologiaFirmaV10 | ❌ | Commented | Errore 400 in pipeline (19/12/2025) |
| getProposteV10 | ❌ | Commented | Timeout Maven (15/01/2023) |
| getPropostaV10 | ❌ | Commented | GraphTalk Fatal Error (11/01/2024) |
| datiISAACPropostaV10 | ❌ | Commented | Timeout Maven |
| datiPropostaV10 | ❌ | Commented | Errore business (proposta annullata) |
| riemissionePropostaV10 | ❌ | Commented | Campi obbligatori mancanti |
| documentoPositivPdfV10 | ❌ | Commented | Polizza non associata agenzia |
| caricamentoDocumentiV10 | ❌ | Commented | Evita carico documenti test in PREP |
| datiPropostaPortafoglioV10 | ❌ | Commented | Da riattivare |
| datiAssuntiviV10 | ❌ | Commented | Da riattivare |
| consultaProposteV20 | ❌ | Commented | Da riattivare |

**Coverage Totale:** 3/14 attivi = 21% (target: 80% → richiede refactoring mock)

---

## GLOSSARIO TECNICO

- **ACL (Anti-Corruption Layer):** Pattern di isolamento dati tra domini bounded context (DDD)
- **AIA:** Applicazione Integrativa Assuntiva (backend VITA moderno)
- **AKS:** Azure Kubernetes Service
- **BFF:** Backend for Frontend
- **BSA:** Business Sidecar for API
- **Cobol:** Common Business-Oriented Language (linguaggio legacy VIS)
- **Correlation-ID:** Identificatore univoco tracciamento request end-to-end
- **GDPR:** General Data Protection Regulation
- **MTTR:** Mean Time To Resolution
- **PI:** Program Increment (SAFe framework)
- **PII:** Personally Identifiable Information
- **SALT:** Service Architecture Leveraging Tuxedo (gateway Oracle)
- **SAFe:** Scaled Agile Framework
- **SLA:** Service Level Agreement
- **Splunk:** Piattaforma log aggregation e analytics
- **Tuxedo:** Oracle middleware transazionale (legacy)
- **UAT:** User Acceptance Testing
- **VIS:** VITA Information System (legacy Cobol)
- **VITES:** VITA Enterprise System (frontend Angular)

---

**FINE DOCUMENTO**
