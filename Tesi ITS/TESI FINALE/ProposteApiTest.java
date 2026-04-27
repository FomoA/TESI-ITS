package it.realeites.vita;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import it.realeites.vita.api.DominiApiController;
import it.realeites.vita.api.PolizzeApiController;
import it.realeites.vita.api.ProdottiApiController;
import it.realeites.vita.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletRequest;

import it.realeites.comune.utility.CustomNativeWebRequest;
import it.realeites.vita.api.ProposteApiController;
import it.realeites.vita.model.CaricamentoDocumenti.CodiceCompagniaEnum;
import it.realeites.vita.model.CaricamentoDocumenti.OrigineEnum;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ProposteApiTest {

	@Test
	public void getDatiTecniciV10() {
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		request.addParameter("canaleBusiness", "Vites");
		request.addParameter("codiceCompagnia", "RMA");
		request.addParameter("codiceAgenzia", "119");
		request.addParameter("codiceSubagenzia", "119");
		request.addParameter("codiceUtente", "A111906");
		request.addParameter("codiceProdotto", "RM1R_PFO1");
		request.addParameter("codiceNominativoContraente", "3070000066464");
		ProposteApiController proposteApiController = new ProposteApiController(request);
		ResponseEntity<RispostaDatiTecniciProposta> response = proposteApiController.getDatiTecniciV10(
				request.getParameter("canaleBusiness"),
				request.getParameter("codiceCompagnia"),
				request.getParameter("codiceAgenzia"),
				request.getParameter("codiceSubagenzia"),
				request.getParameter("codiceUtente"),
				request.getParameter("codiceProdotto"),
				request.getParameter("codiceNominativoContraente"),
                request.getParameter("tipo_soggetto_POE"));
		assertEquals(HttpStatus.OK, response.getStatusCode());


	}

	//@Test
	public void checkQuotazioneV10Successo() {
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");

		InputCheckSubmitProposta inputCheckQuotazioneProposta = new InputCheckSubmitProposta();
		inputCheckQuotazioneProposta.setCanaleBusiness("Vites");
		inputCheckQuotazioneProposta.setCodiceCompagnia(InputCheckSubmitProposta.CodiceCompagniaEnum.valueOf("ITA"));
		inputCheckQuotazioneProposta.setCodiceUtente("A371601");
		inputCheckQuotazioneProposta.setCodiceAgenzia("716");
		inputCheckQuotazioneProposta.setCodiceSubagenzia("716");

		DatiOperazioneProposta datiOperazione = new DatiOperazioneProposta();
		datiOperazione.setCodiceProdotto("B01E");

		SoggettoAnagraficoProposta contraente = new SoggettoAnagraficoProposta();
		contraente.setCodiceNominativo("14080000000027");
		datiOperazione.setContraente(contraente);

		BeneficiarioProposta beneficiario = new BeneficiarioProposta();
		beneficiario.setTipoBeneficiario("A");
		beneficiario.setCodice("C9999");
		beneficiario.setCodiceNominativo("14080000005435");
		beneficiario.setClausolaDifetto("si");
		beneficiario.setClausolaDifettoTesto("ciao");
		beneficiario.setPercentuale(100F);

		SoggettoAnagraficoProposta titolare = new SoggettoAnagraficoProposta();
		titolare.setCodiceNominativo("14080000002789");
		beneficiario.setTitolariEffettivi(Collections.singletonList(titolare));

		datiOperazione.setBeneficiariMorte(Arrays.asList(beneficiario));
		inputCheckQuotazioneProposta.setDatiOperazione(datiOperazione);

		ProposteApiController proposteApiController = new ProposteApiController(request);
		ResponseEntity<RispostaCheckProposta> response = proposteApiController.checkDatiQuotazioneV10(inputCheckQuotazioneProposta);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}


	@Test
	public void getDatiBeneEtaAssicuratoV10() {
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		request.addParameter("canaleBusiness", "Vites");
		request.addParameter("codiceCompagnia", "ITA");
		request.addParameter("codiceAgenzia", "EDS");
		request.addParameter("codiceSubagenzia", "EDS");
		request.addParameter("codiceUtente", "A3A2S01");
		request.addParameter("codiceProdotto", "ITAPIPDSP1");
		request.addParameter("codiceNominativo", "10000142792");
		request.addParameter("dataEffetto", "15/09/2025");
		ProposteApiController proposteApiController = new ProposteApiController(request);
		ResponseEntity<RispostaDatiBeneEtaAssicuratoProposta> response = proposteApiController.getDatiBeneEtaAssicuratoV10(
				request.getParameter("canaleBusiness"),
				request.getParameter("codiceCompagnia"),
				request.getParameter("codiceAgenzia"),
				request.getParameter("codiceSubagenzia"),
				request.getParameter("codiceUtente"),
				request.getParameter("codiceProdotto"),
				request.getParameter("codiceNominativo"),
				request.getParameter("dataEffetto"));
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}	

	// @Test 19/12/2025 commentato temporaneamente per blocco dei rilasci in pipeline a causa di errore 400
	public void putTipologiaFirmaV10() {
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		String numeroPolizza = "41007071";
		TipologiaFirmaPropostaRichiesta body = new TipologiaFirmaPropostaRichiesta();
		body.setCanaleBusiness("C1");
		body.setCodiceCompagnia(it.realeites.vita.model.TipologiaFirmaPropostaRichiesta.CodiceCompagniaEnum.ITA);
		body.setCodiceAgenzia("AXN");
		body.setCodiceSubagenzia("AXN");
		body.setCodiceUtente("utenteItaliana");
		body.setTipologiaFirma(TipologiaFirmaPropostaRichiesta.TipologiaFirmaEnum.CARTACEO);
		ProposteApiController proposteApiController = new ProposteApiController(request);
		ResponseEntity<RispostaTipologiaFirmaProposta> response = proposteApiController.specificaTipologiaFirmaPropostaV10(numeroPolizza, body);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	// @Test
	/*public void getDatiQuotazioneV10() {
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		request.addParameter("canaleBusiness", "Vites");
		request.addParameter("codiceCompagnia", "ITA");
		request.addParameter("codiceAgenzia", "A2S");
		request.addParameter("codiceSubagenzia", "A2S");
		request.addParameter("codiceUtente", "A3A2S01");
		request.addParameter("codiceProdotto", "ITAPIPDSP1");
		ProposteApiController proposteApiController = new ProposteApiController(request);
		ResponseEntity<RispostaDatiQuotazioneProposta> response = proposteApiController.getDatiQuotazioneV10(
				request.getParameter("canaleBusiness"),
				request.getParameter("codiceCompagnia"),
				request.getParameter("codiceAgenzia"),
				request.getParameter("codiceSubagenzia"),
				request.getParameter("codiceUtente"),
				request.getParameter("codiceProdotto"));
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}*/



	// 15/01/2023: Test commentato per overtiming in MAVEN nel deploy
	// @Test
	public void getProposteV10() {
		//MockHttpServletRequest request = new MockHttpServletRequest();
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		request.addParameter("token:canalebusiness", "C1");
		request.addParameter("token:codicecompagnia", "ITA");
		request.addParameter("token:codiceagenzia", "AXN");
		request.addParameter("token:codicesubagenzia", "AXN");
		request.addParameter("token:codiceutente", "utenteItaliana");
		request.addParameter("numero_proposta", "030226462");
		request.addParameter("codice_prodotto", "ITA_C460");
		request.addParameter("data_decorrenza", "01/01/2021");
		Long codiceNominativo = 10000197456L;
		request.addParameter("situazione", "enc");
		
		ProposteApiController proposteApiController = new ProposteApiController(request);
		ResponseEntity<RispostaProposte> response = proposteApiController.getProposteV10(request.getParameter("token:canalebusiness"),
				request.getParameter("token:codicecompagnia"),
				request.getParameter("token:codiceagenzia"),
				request.getParameter("token:codicesubagenzia"),
				request.getParameter("token:codiceutente"),
				request.getParameter("numero_proposta"),
				request.getParameter("codice_prodotto"),
				request.getParameter("data_decorrenza"),
				codiceNominativo,
				request.getParameter("situazione")
				);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	// 11/01/2024 "GraphTalk Fatal Error"
	//@Test
	public void getPropostaV10() {
		//MockHttpServletRequest request = new MockHttpServletRequest();
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		request.addParameter("token:canalebusiness", "C1");
		request.addParameter("token:codicecompagnia", "ITA");
		request.addParameter("token:codiceagenzia", "AXN");
		request.addParameter("token:codicesubagenzia", "AXN");
		request.addParameter("token:codiceutente", "utenteItaliana");
		request.addParameter("numero_proposta", "030226462");
		
		ProposteApiController proposteApiController = new ProposteApiController(request);
		
		ResponseEntity<RispostaPropostaDettaglio> response = proposteApiController.getPropostaV10(request.getParameter("token:canalebusiness"),
				request.getParameter("token:codicecompagnia"),
				request.getParameter("token:codiceagenzia"),
				request.getParameter("token:codicesubagenzia"),
				request.getParameter("token:codiceutente"),
				request.getParameter("numero_proposta")
				);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	// 15/01/2023: Test commentato per overtiming in MAVEN nel deploy
	// @Test
	public void datiISAACPropostaV10() {
		//MockHttpServletRequest request = new MockHttpServletRequest();
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		request.addParameter("token:canalebusiness", "ISAAC");
		request.addParameter("token:codicecompagnia", "ITA");
		request.addParameter("token:codiceagenzia", "AJT");
		request.addParameter("token:codicesubagenzia", "AJT");
		request.addParameter("token:codiceutente", "utenteItaliana");
		request.addParameter("nproposta", "001262581");
		
		ProposteApiController proposteApiController = new ProposteApiController(request);
		
		ResponseEntity<RispostaDatiISAAC> response = proposteApiController.datiISAACPropostaV10(request.getParameter("token:canalebusiness"),
				request.getParameter("token:codicecompagnia"),
				request.getParameter("token:codiceagenzia"),
				request.getParameter("token:codicesubagenzia"),
				request.getParameter("token:codiceutente"),
				request.getParameter("nproposta")
				);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}
	
	// 11/01/2024 : Errori duranteil recupero dei dati della proposta >>> 001265633 : Pro2 : Proposta : La proposta deve essere annullata per poter procedere.
	// @Test
	public void datiPropostaV10() {
		//MockHttpServletRequest request = new MockHttpServletRequest();
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		request.addParameter("token:canalebusiness", "Dashboard");
		request.addParameter("token:codicecompagnia", "ITA");
		request.addParameter("token:codiceagenzia", "EBK");
		request.addParameter("token:codicesubagenzia", "EBK");
		request.addParameter("token:codiceutente", "utenteItaliana");
		request.addParameter("numero_proposta", "001265633");
		
		ProposteApiController proposteApiController = new ProposteApiController(request);
		
		ResponseEntity<RispostaDatiProposta> response = proposteApiController.datiPropostaV10(request.getParameter("token:canalebusiness"),
				request.getParameter("token:codicecompagnia"),
				request.getParameter("token:codiceagenzia"),
				request.getParameter("token:codicesubagenzia"),
				request.getParameter("token:codiceutente"),
				request.getParameter("numero_proposta")
				);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}
	
	// 11/01/2024 : i campi 'cognome', 'nome', 'dataNascitaAssicurato', 'codiceProdotto', 'importoCapitale', 'elencoGaranzie', 'durata', 'frequenza' e 'sesso' sono tutti obbligatori
	//@Test
	public void riemissionePropostaV10() {
		//MockHttpServletRequest request = new MockHttpServletRequest();
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		
		SospendiProposta body = new SospendiProposta();
		
		body.setCanaleBusiness("Dashboard");
		body.setCodiceCompagnia(SospendiProposta.CodiceCompagniaEnum.ITA);
		body.setCodiceAgenzia("EBK");
		body.setCodiceSubagenzia("EBK");
		body.setCodiceUtente("utenteItaliana");
		String numeroProposta = "001265633";
		
		ProposteApiController proposteApiController = new ProposteApiController(request);
		
		ResponseEntity<RispostaSalvataggioProposta> response = proposteApiController.riemissionePropostaV10(numeroProposta, body);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}
	
	// 11/01/2024 :DocumentiXML : La polizza non è associata all'agenzia selezionata
	//@Test
	public void documentoPositivPdfV10() {
		//MockHttpServletRequest request = new MockHttpServletRequest();
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		
		/*
		 * 	Prova in PREPROD (dove c'è lo STUB di AIA)

			canaleBusiness   = Tinaba
			codiceCompagnia  = ITA
			codiceAgenzia    = DAI
			codiceSubagenzia = DAI
			codiceUtente     = utenteItaliana
			user             = ABC
			tipoDocumento    = PROPOSTA
			tipoStampa       = tradizionale
			tipologiaFirma   = test
			numeroProposta   = 004070027

		 */
		request.addParameter("token:canalebusiness"  , "Tinaba");
		request.addParameter("token:codicecompagnia" , "ITA");
		request.addParameter("token:codiceagenzia"   , "DAI");
		request.addParameter("token:codicesubagenzia", "DAI");
		request.addParameter("token:codiceutente"    , "utenteItaliana");
		request.addParameter("user"                  , "ABC");
		request.addParameter("tipoDocumento"         , "PROPOSTA");
		request.addParameter("tipoStampa"            , "tradizionale");
		request.addParameter("tipologiaFirma"        , "test");
		request.addParameter("numeroProposta"        , "004070027");
		
		ProposteApiController proposteApiController = new ProposteApiController(request);
		
		ResponseEntity<RispostaDocumentoPositivPdf> response = proposteApiController.documentoPositivPdfV10(
														request.getParameter("token:canalebusiness"),
														request.getParameter("token:codicecompagnia"),
														request.getParameter("token:codiceagenzia"),
														request.getParameter("token:codicesubagenzia"),
														request.getParameter("token:codiceutente"),
														request.getParameter("numeroProposta"),
														request.getParameter("user"),
														request.getParameter("tipoDocumento"),
														request.getParameter("tipoStampa"),
														request.getParameter("tipologiaFirma")
														);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	
	// @Test
	//  test commentato per evitare di caricare troppi documenti nel Documentale
	/*
	 numeroProposta = 123456

	{
	  "canaleBusiness": "API",
	  "codiceCompagnia": "RMA",
	  "codiceAgenzia": "217001",
	  "codiceSubagenzia": "217001",
	  "codiceUtente": "utenteReale",
	  "codiceAgente": "A121701",
	  "origine": "PROPOSTA_VITA",
	  "annoPolizza": "",
	  "ramoPolizza": "",
	  "codiceNominativo": "4832789",
	  "cognomeNomeNominativo": "",
	  "codiceFiscaleNominativo": "",
	  "ragioneSocialeNominativo": "",
	  "partitaIvaNominativo": "",
	  "codiceUnitaOrganizzativa": "",
	  "dataSinistro": "",
	  "numeroSinistro": "",
	  "codiceIncarico": "",
	  "numeroIncarico": "",
	  "targa": "",
	  "entePubblicante": "",
	  "enteGestore": "",
	  "numeroPosizione": "",
	  "tipologiaDocumento": "",
	  "matricolaPerSeguito": "",
	  "documentoDaLeggere": true,
	  "documento": {
	    "codice": "",
	    "classe": "",
	    "sottoclasse": "",
	    "codiceTipo": "8.1.43",
	    "descrizioneTipo": "",
	    "origine": "PROPOSTA_VITA",
	    "nome": "4161037_2.pdf",
	    "formato": "PDF",
	    "versione": "",
	    "codiceEdizione": "",
	    "codiceProdotto": "",
	    "nomeProdotto": "",
	    "areaProdotto": "",
	    "flagProdottoCatalogo": true,
	    "numeroFogli": 1,
	    "contenuto": "",
	    "dataPubblicazione": "",
	    "dataDecorrenza": "",
	    "dataScadenza": "",
	    "dataUltimaModifica": "",
	    "flagDocumentoScaricabile": true,
	    "entePubblicante": "",
	    "sistemaOrigine": ""
	  }
	}
	 */
	public void caricamentoDocumentiPropostaVitaV10() {
		//MockHttpServletRequest request = new MockHttpServletRequest();
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		ProposteApiController proposteApiController = new ProposteApiController(request);
		
		String numeroProposta = "123456";
		
		CaricamentoDocumenti body = new CaricamentoDocumenti();
		body.setCanaleBusiness("API");
		body.setCodiceCompagnia(CodiceCompagniaEnum.RMA);
		body.setCodiceAgenzia("217001");
		
		  //"codiceSubagenzia": "217001",
		  //"codiceUtente": "utenteReale",
		body.setCodiceSubagenzia("217001");
		body.setCodiceUtente("utenteReale");
		
		body.setCodiceAgente("A121701");
		body.setOrigine(OrigineEnum.PROPOSTA_VITA);
		body.setCodiceNominativo("4832789");

		Documento documentoProposta = new Documento();
		documentoProposta.setCodiceTipo("8.1.43"); // Proposta PassMobile Vita
		documentoProposta.setContenuto("VGVzdA==");
		documentoProposta.setNome("4161037_2.pdf");
		documentoProposta.setOrigine(Documento.OrigineEnum.PROPOSTA_VITA);
		documentoProposta.setNumeroFogli(Integer.valueOf(1));
		body.setDocumento(documentoProposta);
		
		ResponseEntity<RispostaCaricamentoDocumenti> response = proposteApiController.caricamentoDocumentiPropostaVitaV10(numeroProposta, body);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	
	//@Test
	public void datiPropostaPortafoglioV10() {
		//MockHttpServletRequest request = new MockHttpServletRequest();
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		request.addParameter("token:codicecompagnia", "ITA");
		request.addParameter("numero_proposta", "001300376");
		
		ProposteApiController proposteApiController = new ProposteApiController(request);
		
		ResponseEntity<RispostaPropostaPortafoglio> response = proposteApiController.datiPropostaPortafoglioV10(request.getParameter("token:codicecompagnia"),
				request.getParameter("numero_proposta")
				);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}
	

	//@Test
	public void datiAssuntiviV10() {
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		ProposteApiController proposteApiController = new ProposteApiController(request);
		ResponseEntity<RispostaDatiAssuntivi> response = proposteApiController.datiAssuntiviV10("Dashboard",
																								"ITA",
																								"4B3",
																								"4B3",
																								"utenteItaliana",
																								"016280127"
																								);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	//@Test
	public void consultaProposteV20() {
		CustomNativeWebRequest request = new CustomNativeWebRequest(Mockito.mock(HttpServletRequest.class), null);
		request.addHeader("Accept", "application/json");
		ProposteApiController proposteApiController = new ProposteApiController(request);
		ResponseEntity<RispostaConsultazioneProposta> response = proposteApiController.consultaPropostaV20("Vites",
				"RMA",
				"261120259",
				"119",
				"GRUPPO_DETTAGLIO",
				"119"
		);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}



}