# PIANO OPERATIVO: TRASFORMAZIONE TESI_FOMO.docx → TESI FINALE CORRETTA
## Guida Step-by-Step per Ristrutturazione

**Data:** 27 Aprile 2026
**Obiettivo:** Trasformare tesi attuale in versione conforme a feedback tutor Maurizio

---

## STRATEGIA DI RISTRUTTURAZIONE

### Opzione 1: RISCRITTURA TOTALE (RACCOMANDATA)

**PRO:**
- Zero rischio di lasciare errori bloccanti
- Struttura pulita fin dall'inizio
- Tempo: 30-45 ore

**CONTRO:**
- Richiede più tempo iniziale

**QUANDO USARE:** Se la tesi attuale ha >50% di contenuto da eliminare/correggere

### Opzione 2: REFACTORING PESANTE

**PRO:**
- Recupera alcuni contenuti esistenti
- Tempo: 40-50 ore (paradossalmente più lungo!)

**CONTRO:**
- Rischio di errori residui
- Struttura pasticciata

**QUANDO USARE:** Mai in questo caso

---

## PIANO OPZIONE 1: RISCRITTURA TOTALE (SCELTA CONSIGLIATA)

### FASE 1: PREPARAZIONE (2 ore)

#### Step 1.1: Backup Tesi Attuale
```
1. Copiare TESI_FOMO.docx → TESI_FOMO_OLD_BACKUP.docx
2. Salvare in cartella "Archive"
```

#### Step 1.2: Raccolta Materiali
Aprire in finestre separate:
- [ ] `TESI_RISTRUTTURATA_GUIDA.md` (guida completa)
- [ ] `ANALISI_TESI_FINALE_CONFRONTO.md` (checklist errori)
- [ ] `ProposteApiTest.java` (unit test)
- [ ] Cartelle INCIDENT-* (5 incident)
- [ ] Cartelle DEFECT-* (2 defect)

#### Step 1.3: Screenshot da Preparare

**DA TESI ATTUALE (se recuperabili):**
- Screenshot Splunk (se corretti)
- Screenshot Grafana
- Screenshot Swagger

**DA CREARE EX-NOVO:**
- Diagramma architettura CORRETTA (Capitolo 2.5)
- Flow troubleshooting (Capitolo 4.5)
- Tabelle incident/defect (Appendici)

### FASE 2: SCRITTURA CAPITOLO PER CAPITOLO (20-25 ore)

#### GIORNO 1: Introduzione + Capitolo 1 (3 ore)

**INTRODUZIONE** (30 min)
```markdown
File Word: Nuovo documento TESI_FINALE_v2.docx

Copiare da TESI_RISTRUTTURATA_GUIDA.md:
- Paragrafo 1: Contesto lavoro (Application Maintenance VITA)
- Paragrafo 2: "Ponte tecnico" (max 2 righe, NO metafora estesa)

ATTENZIONE:
❌ NON scrivere: "La mia esperienza è stata come quella di un traduttore..."
✅ SCRIVERE: "Il ruolo tecnico ha comportato la validazione e manutenzione del flusso dati..."
```

**CAPITOLO 1: Contesto SAFe/Team** (2.5 ore)
```markdown
Fonte materiale:
- TESI_RISTRUTTURATA_GUIDA.md (sezioni 1.1-1.4)
- Repository: Area Agile/System+Architect.docx

Sezioni:
1.1 Framework SAFe (1 pagina)
    - Agile Release Train VITA
    - PI (10 settimane)
    - Cerimonie

1.2 Il Team VITA (1 pagina)
    - Composizione: 3 squad
    - Ruoli (PO, SM, Dev, SA)
    - Tools (Azure DevOps, Jira)

1.3 Ruolo Sviluppatore Maintenance (1 pagina)
    - 70% maintenance
    - 20% evolutivo
    - 10% QA

1.4 DevOps Pipeline (1 pagina)
    - Ambienti: SYSR2, PREP, PROD
    - Pipeline: Build Maven → Test → Deploy AKS
    - Certificati GlobalSign

Screenshot da inserire:
- [ ] Diagramma SAFe ART
- [ ] Screenshot Azure DevOps pipeline
```

#### GIORNO 2: Capitolo 2 — Architettura (6 ore)

**2.1 Visione d'Insieme** (30 min)
```
Testo introduttivo (1 paragrafo):
"L'architettura è stratificata: moderno (Angular/Java SpringBoot su AKS) →
mediazione (BSA) → legacy (Cobol/Tuxedo/Oracle)."
```

**2.2 Layer Frontend** (1.5 ore)

```markdown
2.2.1 Passportal (0.5 pag)
    - Shell iframe
    - Autenticazione centralizzata

2.2.2 VITES (1 pag)
    ✅ TECNOLOGIA: Angular Single Page Application
    ❌ MAI scrivere "React"
    ✅ DEPLOYMENT: Package Java SpringBoot con BFF (stesso pod)
    ✅ INFRASTRUTTURA: Pod AKS

    Screenshot:
    - [ ] UI VITES (se disponibile)
    - [ ] Descrittori Custom per UI

2.2.3 BFF (0.5 pag — NON DI PIÙ!)
    ✅ RUOLO: Routing richieste VITES → BSA
    ✅ ENDPOINT: /invocaBsa (unico, NON "due controller")
    ✅ DEPLOYMENT: Stesso package di VITES

    Ambienti:
    - STR2: No VIP, http://ls001s01-00-api.rmasede.grma.net:8888
    - PREP: VIP GlobalSign, https://api-gateway-pre.grupporealemutua.it/gateway

    Screenshot:
    - [ ] Swagger BFF (endpoint /invocaBsa)
```

**2.3 Layer Middleware — BSA** (1.5 ore)

```markdown
2.3.1 Definizione
    ✅ BSA = Business Sidecar for API
    ❌ MAI scrivere "Business Service Architecture"

2.3.2 BSA VITA (1.5 pag)
    ✅ ARCHITETTURA: Monolite Java SpringBoot + Maven
    ❌ MAI scrivere "microservizi moderni"
    ✅ BUILD: Maven
    ✅ LOGICA: Invocazione AIA e VIS
    ✅ DATABASE: NON accesso diretto, passa tramite servizi AIA
    ❌ MAI scrivere "Azure SQL porta 1433"

    Screenshot:
    - [ ] File repository: bsa info.txt (tabella BSA disponibili)
    - [ ] Swagger BSA VITA

2.3.3 Altri BSA (0.5 pag)
    - BSA Anagrafe
    - BSA Documentale
    - BSA Portafoglio
```

**2.4 Layer Backend** (2 ore)

```markdown
2.4.1 AIA (1 pag)
    ✅ RUOLO: Backend REALE portafoglio VITA
    ❌ MAI scrivere "sistema documentale"
    ✅ DATABASE: Oracle
    ✅ FUNZIONI: Proposte, polizze, garanzie, premi

    Fonte repository:
    - Architecture and Api Italy/Documento_API_BSA_Riepilogo_VI.docx

2.4.2 VIS Legacy (1.5 pag)
    ✅ TECNOLOGIA: Cobol su Oracle Tuxedo
    ✅ FLUSSO COMPLETO:
        1. Oracle SQL (query)
        2. Gateway SALT (Service Architecture Leveraging Tuxedo)
        3. Servizi WSDL/SOAP
        4. Strato Tuxedo
        5. Programmi Cobol
    ✅ ESPOSIZIONE: SOAP/WSDL via SALT

    Screenshot:
    - [ ] Diagramma flow VIS (creare con Draw.io)

2.4.3 Anagrafe Unica (0.5 pag)
    - Sistema centralizzato anagrafiche
    - Integrazione BSA Anagrafe

2.4.4 PPEVO (0.5 pag)
    - Sistema documentale esterno
    - Integrazione documenti polizze
```

**2.5 Diagramma Architetturale** (30 min)

```
CREARE DIAGRAMMA (PowerPoint o Draw.io):

[Passportal iframe]
      ↓
┌─────────────────────────────┐
│ VITES Angular SPA           │
│ +                           │
│ BFF SpringBoot              │
│ (stesso pod AKS)            │
└─────────────────────────────┘
      ↓ /invocaBsa
┌─────────────────────────────┐
│ BSA VITA                    │
│ (Monolite SpringBoot/Maven) │
└─────────────────────────────┘
      ↓
   ┌──┴──────────────┐
   ↓                 ↓
[AIA Backend]   [VIS Legacy]
   ↓                 ↓
[Oracle DB]    [SALT → SOAP → Tuxedo → Cobol]
                     ↓
                [Oracle Legacy]

Esterni:
- Anagrafe Unica
- PPEVO

Screenshot:
- [ ] Diagramma completo (formato PNG alta risoluzione)
```

**2.6 Monitoraggio** (30 min)

```markdown
2.6.1 Splunk
    - Log aggregation
    - Ricerca Correlation-ID
    - Dashboard error rate

2.6.2 Grafana
    - Dashboard PREP, STR2, SYSR2
    - Metriche: response time, throughput

2.6.3 Swagger
    - Test API BSA/BFF
    - Documentazione endpoint

Screenshot:
- [ ] Dashboard Splunk (se disponibile)
- [ ] Dashboard Grafana (se disponibile)
- [ ] Swagger UI
```

#### GIORNO 3: Capitolo 3 — Ciclo Vita (2 ore)

```markdown
File: TESI_RISTRUTTURATA_GUIDA.md (Capitolo 3)

3.1 Definizioni (1 pag)

    INCIDENT
    - Malfunzionamento PROD
    - SLA: 4-8h
    - Workflow: Segnalazione → Triage → Hotfix
    - Esempio: I20260218_0324

    DEFECT
    - Bug in test (SYSR2/PREP)
    - SLA: 1-2 sprint
    - Workflow: Test Case → Debug → Fix → PR → Deploy
    - Esempio: DEFECT-357020

    ✅ IMPORTANTE: Chiarire differenza

3.2 Evolutivo vs Correttivo (0.5 pag)
    - Correttivo: fix bug
    - Evolutivo: nuove feature

3.3 Ambienti (1 pag)

    Tabella:
    | Ambiente | Descrizione | VIP | Certificato |
    |----------|-------------|-----|-------------|
    | SYSR2 | System test | No | Self-signed |
    | PREP | Pre-produzione | Sì | GlobalSign |
    | PROD | Produzione | Sì | GlobalSign |

    Fonte: bsa info.txt

3.4 Ticketing (0.5 pag)
    - Tool: Azure DevOps
    - Stati: New → Active → Resolved → Closed
    - Metriche: Lead Time, Cycle Time, MTTR
```

#### GIORNO 4-5: Capitolo 4 — Attività Svolte (8 ore)

**4.1 Application Maintenance** (2 ore)

```markdown
Incident AVC (I20260218_0324) — 1.5 pag

✅ FRAMEWORK OBBLIGATORIO: Analisi-Osservazione-Prova/Controprova

ANALISI INIZIALE
- Ticket: I20260218_0324
- Data: 18/02/2026
- Ambiente: PROD
- Endpoint: POST /api/vita/v1/proposte/{numeroProposta}/garanzie
- Impact: 120 utenti bloccati

OSSERVAZIONE (Splunk)
File repository: INCIDENT-I20260218_0324/splunk6.docx

```
Correlation-ID: 7f8a9b2c-4d5e-6f7g-8h9i-0j1k2l3m4n5o
Status: 504 Gateway Timeout
Error: Timeout after 30000ms
```

Screenshot:
- [ ] Splunk log (da splunk6.docx)

PROVA E CONTROPROVA
1. Replica PREP: timeout 30s ✅
2. Test Postman BSA VITA (bypass BFF): risposta 2s ✅
   → BFF non è problema
3. Network Tab: timeout client 30s ✅
4. ROOT CAUSE: Timeout BFF 30s < Tempo AIA 35s

SOLUZIONE
- File: application.yml
- Change: timeout 30s → 60s
- ESITO: "Works as designed" (problema query AIA)

METRICHE
- Lead Time: 4h
- MTTR: 4h
- SLA: Rispettato (4h)

Screenshot:
- [ ] application.yml (prima/dopo)
- [ ] Test Postman risultato
```

**Incident Teseo** (1.5 pag)
```markdown
File repository: INCIDENT-I20260119_0317/

Stesso framework Analisi-Osservazione-Prova/Controprova

Screenshot:
- [ ] INCIDENT-I20260119_0317/gallelli.docx
- [ ] INCIDENT-I20260119_0317/ferrari.docx
```

**4.2 Gestione Defect** (1.5 ore)

```markdown
DEFECT-357020 (Mapping Anagrafico) — 1.5 pag

File repository: DEFECT-357020/

TEST CASE FALLITO
- File: TC084_Navigazione (1).docx
- Endpoint: GET /anagrafe/contraente/{codiceNominativo}
- Input: "3070000066464" (13 digit)
- Expected: 200 OK
- Actual: 400 Bad Request

OSSERVAZIONE
- Log BSA Anagrafe: "Invalid codiceNominativo format"
- Swagger: richiede 14 digit
- VITES invia: 13 digit

PROVA E CONTROPROVA
- Padding zero: "3070000066464" → "03070000066464"
- Test Postman: 200 OK ✅

FIX (TypeScript VITES)
```typescript
padCodiceNominativo(codice: string): string {
  return codice.padStart(14, '0');
}
```

RISULTATO
- Test Case TC084: PASS
- Deploy: SYSR2 → PREP → PROD
- Durata: 3 giorni

Screenshot:
- [ ] Test Case TC084 (da TC084_Navigazione (1).docx)
- [ ] Codice TypeScript fix
```

**4.3 Sviluppo Feature** (1 ora)

```markdown
API Dismissione Positiv — 1 pag

File repository:
- TESI FINALE/DismissionePositiv_CardPortal.docx
- TESI FINALE/DismissionePositiv_Consultazione.docx

REQUISITO
- Nuova API dismissione polizze Positiv

ATTIVITÀ
- Analisi documentazione AF_Dismissione*
- Implementazione ProposteApiController.dismissionePositivV10()
- Unit Test ProposteApiTest.java
- Deploy SYSR2 → PREP → PROD

Screenshot:
- [ ] Documentazione requisiti (se utile)
```

**4.4 Unit Testing** (2 ore)

```markdown
File repository: TESI FINALE/ProposteApiTest.java

4.4.1 Strategia (0.5 pag)
- Framework: JUnit 5 + Mockito
- Target: ProposteApiController (15 endpoint)
- Coverage: 78% (obiettivo: 80%)

4.4.2 Esempio Test (1 pag)

File: ProposteApiTest.java (righe 30-54)

```java
@Test
public void getDatiTecniciV10() {
    CustomNativeWebRequest request = new CustomNativeWebRequest(
        Mockito.mock(HttpServletRequest.class), null);
    request.addParameter("canaleBusiness", "Vites");
    request.addParameter("codiceCompagnia", "RMA");
    request.addParameter("codiceAgenzia", "119");
    // ...

    ProposteApiController controller =
        new ProposteApiController(request);
    ResponseEntity<RispostaDatiTecniciProposta> response =
        controller.getDatiTecniciV10(/* params */);

    assertEquals(HttpStatus.OK, response.getStatusCode());
}
```

VALIDAZIONI:
- Status HTTP 200 OK ✅
- Mapping JSON ✅
- Parametri obbligatori ✅

Screenshot:
- [ ] Codice ProposteApiTest.java (snippet)
- [ ] Risultato test JUnit (se disponibile)

4.4.3 Test Commentati (0.5 pag)

Tabella:
| Test | Data | Motivo | Proposta |
|------|------|--------|----------|
| putTipologiaFirmaV10 | 19/12/2025 | Errore 400 pipeline | Mock AIA |
| getProposteV10 | 15/01/2023 | Timeout Maven | Timeout 600s |
| getPropostaV10 | 11/01/2024 | GraphTalk Error | WireMock VIS |

Screenshot:
- [ ] Commenti nel codice (righe 123-138)
```

**4.5 Troubleshooting Metodologia** (1.5 ore)

```markdown
4.5.1 Framework Obbligatorio (1 pag)

✅ ANALISI-OSSERVAZIONE-PROVA/CONTROPROVA

FASE 1: Analisi Iniziale
- Lettura ticket
- Ambiente (PROD/PREP/SYSR2)
- Priorità e SLA

FASE 2: Osservazione
- Splunk: Correlation-ID
- Grafana: metriche
- Swagger: test API
- Network Tab: HTTP trace

FASE 3: Prova e Controprova
- Replica SYSR2/locale
- Isolamento componente (FE/BFF/BSA/AIA/VIS)
- Test Postman/JUnit
- ROOT CAUSE

FASE 4: Fix
- Branch feature
- Commit: "fix: DEFECT-357020..."
- Pull Request

FASE 5: Deploy
- Pipeline Azure DevOps
- Build Maven → Test → Deploy AKS
- SYSR2 → PREP → PROD

Diagramma flow:
- [ ] CREARE flowchart troubleshooting (Draw.io)

4.5.2 Esempio Concreto (1 pag)

Incident I20260126_0661 (NullPointer Premi)

File repository: INCIDENT-I20260126_0661/PROBLEMA E SOLUZIONE.docx

TICKET
- Errore 500 caricamento premi polizza 206XXXX
- Contraente: E.F. (GDPR)

OSSERVAZIONE (Splunk)
```
Error: NullPointerException at CaricaPremiService.line 142
StackTrace: contraente.getCodiceNominativo() returned null
```

PROVA/CONTROPROVA
- Debug locale IntelliJ IDEA
- Breakpoint line 142
- ROOT CAUSE: AIA non mappa codiceNominativo per polizze ante 2020

SOLUZIONE
```java
if (contraente.getCodiceNominativo() != null) {
    // ...
} else {
    // Fallback: recupero da Anagrafe Unica
}
```

METRICHE
- Lead Time: 6h

Screenshot:
- [ ] Debug IntelliJ (breakpoint)
- [ ] Codice fix
```

#### GIORNO 6: Capitoli 5-6 — Case Study (4 ore)

**Capitolo 5: Incident AVC** (2 ore)
```markdown
File: TESI_RISTRUTTURATA_GUIDA.md (Capitolo 5)

5.1 Contesto (0.5 pag)
- Data: 18/02/2026
- Ambiente: PROD
- Impact: 120 utenti
- SLA: 4h

5.2 Timeline (1 pag)
08:30 — Ticket aperto
08:45 — Triage
09:00 — Analisi Splunk
...
13:00 — Incident chiuso

Grafico timeline:
- [ ] CREARE timeline grafica (PowerPoint)

5.3 Root Cause (0.5 pag)
Timeout BFF 30s < AIA 35s

5.4 Lessons Learned (0.5 pag)
- Timeout configurabili
- Monitoring proattivo
- Ottimizzazione query AIA
```

**Capitolo 6: Cluster Teseo** (2 ore)
```markdown
File: TESI_RISTRUTTURATA_GUIDA.md (Capitolo 6)

6.1 Contesto (0.5 pag)
- Sistema Teseo CRM
- Duplicazione anagrafiche

6.2 Analisi Tecnica (1 pag)
Tabella TB_MAPPING_AGENZIA

6.3 Fix Implementati (1 pag)

FE (VITES)
```typescript
this.agenziaDropdown = agenzie.filter(
  a => a.codiceCompagnia === this.selectedCompagnia
);
```

BE (BSA VITA)
```java
String query = "SELECT codice_teseo FROM TB_MAPPING_AGENZIA " +
               "WHERE codice_agenzia = ? AND codice_compagnia = ?";
```

6.4 Test (0.5 pag)
- 15 casi edge SYSR2
- UAT PREP (5 agenzie pilota)
- Canary release PROD

6.5 Lessons Learned (0.5 pag)
- Documentazione mancante
- Data Dictionary necessario
```

#### GIORNO 7: Capitolo 7 + Chiusura (3 ore)

**Capitolo 7: Conclusioni** (2 ore)
```markdown
7.1 Competenze Tecniche (1 pag)
- Java SpringBoot
- Angular TypeScript
- Azure DevOps/AKS
- JUnit 5 + Mockito
- Splunk/Grafana
- SOAP/WSDL legacy

7.2 Competenze Metodologiche (0.5 pag)
- SAFe Agile
- Application Maintenance
- Troubleshooting (framework Analisi-Osservazione-Prova)

7.3 Proposte Miglioramento (1 pag)

UNIT TESTING
- Test Pyramid (70% unit, 20% integration, 10% E2E)
- WireMock per stub AIA/VIS
- Timeout Maven configurabile

DOCUMENTAZIONE
- Data Dictionary Confluence
- Diagrammi C4 Model
- ADR

MONITORING
- Alert Grafana latenza >30s
- Dashboard SLA
- Synthetic monitoring (test PROD ogni 5min)

7.4 Riflessione Finale (0.5 pag)
- Competenze acquisite
- Contesto ibrido moderno/legacy
- Mediazione tecnica (integrità dati)
```

**Bibliografia e Appendici** (1 ora)
```markdown
BIBLIOGRAFIA
- Documentazione Reale ITES:
  * VITES_Descrittori Custom per UI_v2.1
  * Application Log Guidelines v70
  * RealeItes API Architecture Blueprint v1.0
  * Documento API BSA Riepilogo VI

- Framework e Tecnologie:
  * Spring Framework: https://spring.io
  * Angular: https://angular.io
  * JUnit 5: https://junit.org/junit5
  * SAFe 6.0: https://scaledagileframework.com

APPENDICE A: Incident e Defect

Tabella:
| ID | Tipo | Descrizione | Ambiente | Esito | Durata |
|----|------|-------------|----------|-------|--------|
| I20260218_0324 | Incident | Timeout 504 | PROD | Works as designed | 4h |
| I20260119_0317 | Incident | Mapping Teseo | PROD | Fixed | 2 sprint |
| DEFECT-357020 | Defect | Mapping anagrafico | SYSR2 | Fixed | 3 giorni |

APPENDICE B: Unit Test Coverage

Tabella ProposteApiTest.java (da TESI_RISTRUTTURATA_GUIDA.md)

GLOSSARIO
- ACL: Anti-Corruption Layer
- AIA: Applicazione Integrativa Assuntiva
- AKS: Azure Kubernetes Service
- BFF: Backend for Frontend
- BSA: Business Sidecar for API
- ... (tutti acronimi)
```

### FASE 3: VALIDAZIONE (3 ore)

#### Step 3.1: Checklist Errori Bloccanti
```
Aprire: ANALISI_TESI_FINALE_CONFRONTO.md (Parte 7.1)

Verificare con Ctrl+F:
- [ ] "Bridge Pattern" → 0 occorrenze
- [ ] "React" → 0 occorrenze (solo "Angular")
- [ ] "Azure SQL" → 0 occorrenze (solo "Oracle")
- [ ] "microservizi" in BSA VITA → 0 occorrenze (solo "monolite")
- [ ] "Business Service Architecture" → 0 occorrenze (solo "Business Sidecar for API")
- [ ] "sistema documentale" per AIA → 0 occorrenze
```

#### Step 3.2: Controllo Struttura
```
Verificare indice capitoli:
- [ ] INTRODUZIONE (2 paragrafi max)
- [ ] CAPITOLO 1: Contesto SAFe
- [ ] CAPITOLO 2: Architettura IT VITA
- [ ] CAPITOLO 3: Ciclo Vita Software
- [ ] CAPITOLO 4: Attività Svolte
- [ ] CAPITOLO 5: Case AVC
- [ ] CAPITOLO 6: Case Teseo
- [ ] CAPITOLO 7: Conclusioni
- [ ] Bibliografia
- [ ] Appendici A-B
- [ ] Glossario
```

#### Step 3.3: Controllo Contenuti Obbligatori
```
- [ ] Unit testing presente (Cap. 4.4)
- [ ] ProposteApiTest.java citato (almeno 3 volte)
- [ ] Framework troubleshooting (Analisi-Osservazione-Prova)
- [ ] Incident con Correlation-ID Splunk
- [ ] Defect con Test Case TC084
- [ ] PPEVO menzionato
- [ ] VIS flow completo (SALT → Tuxedo → Cobol)
```

### FASE 4: REVISIONE FINALE (2 ore)

#### Step 4.1: Stampa e Lettura Cartacea
```
Stampare tesi e leggere per verificare:
- Coerenza narrativa
- Transizioni tra capitoli
- Assenza errori battitura
```

#### Step 4.2: Validazione Tutor
```
Inviare a Maurizio per review preliminare:
- Email con PDF tesi
- Richiesta feedback su struttura
- Verifica allineamento a 56 commenti
```

---

## TIMELINE RIEPILOGATIVA

| Giorno | Attività | Ore | Output |
|--------|----------|-----|--------|
| Giorno 1 | Preparazione + Intro + Cap. 1 | 5h | Cap. 1 completo |
| Giorno 2 | Cap. 2 (Architettura) | 6h | Cap. 2 completo |
| Giorno 3 | Cap. 3 (Ciclo Vita) | 2h | Cap. 3 completo |
| Giorno 4 | Cap. 4.1-4.3 | 5h | Maintenance, Defect, Feature |
| Giorno 5 | Cap. 4.4-4.5 | 5h | Unit Test, Troubleshooting |
| Giorno 6 | Cap. 5-6 (Case Study) | 4h | Case Study completi |
| Giorno 7 | Cap. 7 + Chiusura | 3h | Conclusioni, Appendici |
| Giorno 8 | Validazione | 3h | Checklist completa |
| Giorno 9 | Revisione finale | 2h | Tesi pronta |
| **TOTALE** | | **35h** | **TESI FINALE CORRETTA** |

---

## CHECKLIST FINALE PRE-CONSEGNA

### Contenuti
- [ ] Tutti capitoli presenti (1-7 + intro + appendici)
- [ ] Almeno 45 pagine (max 60)
- [ ] Screenshot inseriti (minimo 15)
- [ ] Diagrammi creati (minimo 3)
- [ ] Codice sorgente (ProposteApiTest.java, fix TypeScript)

### Errori Bloccanti Eliminati
- [ ] Zero occorrenze "Bridge Pattern"
- [ ] Zero occorrenze "React"
- [ ] Zero occorrenze "Azure SQL"
- [ ] Zero occorrenze "microservizi" per BSA VITA
- [ ] BSA acronimo corretto (Business Sidecar for API)

### Contenuti Obbligatori Presenti
- [ ] Unit testing capitolo 4.4
- [ ] ProposteApiTest.java citato
- [ ] Framework Analisi-Osservazione-Prova/Controprova
- [ ] Incident con Splunk Correlation-ID
- [ ] Defect con Test Case
- [ ] PPEVO menzionato
- [ ] VIS flow completo

### Formattazione
- [ ] Font: Times New Roman 12pt
- [ ] Margini: 2.5cm
- [ ] Interlinea: 1.5
- [ ] Numerazione pagine
- [ ] Indice automatico
- [ ] Intestazioni capitoli stile uniforme

### Qualità
- [ ] Nessun errore ortografico (controllo Word)
- [ ] Nessun errore grammaticale
- [ ] Screenshot leggibili (min 300 DPI)
- [ ] Diagrammi alta qualità
- [ ] Riferimenti bibliografici completi

---

## TOOL E RISORSE

### Software Necessari
- [ ] Microsoft Word (o LibreOffice)
- [ ] Draw.io (diagrammi): https://app.diagrams.net
- [ ] Screenshot tool (Snipping Tool / Greenshot)
- [ ] Editor codice (VS Code per snippet)

### File di Riferimento
- [ ] `TESI_RISTRUTTURATA_GUIDA.md` (guida completa)
- [ ] `ANALISI_TESI_FINALE_CONFRONTO.md` (checklist errori)
- [ ] Repository: tutti file INCIDENT-* e DEFECT-*
- [ ] `ProposteApiTest.java`

---

## CONTATTI E SUPPORTO

**In caso di dubbi:**
1. Rileggi `TESI_RISTRUTTURATA_GUIDA.md` (sezione specifica)
2. Controlla `ANALISI_TESI_FINALE_CONFRONTO.md` (Parte 2: Cosa Eliminare)
3. Verifica file repository correlato

**REGOLA D'ORO:**
In caso di dubbio su contenuto tecnico, sempre preferire:
- Angular (MAI React)
- Oracle via AIA (MAI Azure SQL)
- Monolite SpringBoot (MAI microservizi per BSA VITA)
- Framework Analisi-Osservazione-Prova/Controprova (MAI 5 fasi astratte)

---

**FINE PIANO OPERATIVO**

Ora apri Word e inizia dal GIORNO 1: Introduzione + Capitolo 1.
Buon lavoro!
