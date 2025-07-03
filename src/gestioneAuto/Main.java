package gestioneAuto;

import java.io.File;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String filePath = "auto.dat";
		String filePath1 = "utente.dat";
		String filePath2 = "ruolo.dat";
		String filePath3 = "permesso.dat";
		String filePath4 = "gruppo.dat";
		GestioneAuto gestioneAuto = new GestioneAuto(filePath);
		GestioneUtente gestioneUtente = new GestioneUtente(filePath1);
		GestioneRuolo gestioneRuolo = new GestioneRuolo(filePath2);
		GestionePermesso gestionePermesso = new GestionePermesso(filePath3);
		GestioneGruppo gestioneGruppo = new GestioneGruppo(filePath4);
		Scanner scanner = new Scanner(System.in);
		
		
		String sceltaGestione = "0";
		Utente logIn=null;
		boolean accesso=false;
		boolean gestione=false;
		
		if(gestioneUtente.listaUtente.isEmpty() || !gestioneUtente.listaUtente.get(0).getRuolo().equals("admin")) {
			bootstrap(gestioneUtente,gestioneRuolo,gestionePermesso,gestioneGruppo);
			accesso=true;
		}else {
			accesso=true;
		}
		
		while(accesso) {
			System.out.println("1--- ACCEDI ---");
			System.out.println("2--- REGISTRATI ---");
			System.out.print("Scelta: ");
			String sceltaAccesso=scanner.nextLine();
			switch (sceltaAccesso) {
			case "1":
				Utente accedi=accedi(gestioneUtente,scanner);
				if(accedi!=null) {
					logIn=accedi;
					accesso=false;
					gestione=true;
				}else {
					System.out.println("DATI ERRATI");
				}
				break;
			case "2":
				creaUtente(gestioneUtente, scanner);
				break; 
			}

		}




		while (gestione) {
			String cf=logIn.getCodiceFiscale();
			switch(sceltaGestione) {            	
			case "0":
				System.out.println("SCEGLI GESTIONE");
				List<String> menu=gestioneUtente.getMenu(cf, gestioneRuolo, gestioneGruppo, gestioneUtente);//lista gruppi utente
				int i=1;
				for(String g:menu) {
					System.out.println("---"+i+"."+" "+g+" ---");
					i+=1;
				}
				System.out.print("Scelta: ");
				int scelta = scanner.nextInt();
				sceltaGestione = menu.get(scelta-1);
			}

			switch(sceltaGestione) {
			case "gestioneAuto":
				System.out.println("\n--- GESTIONE AUTO ---");
				List<String> sottoMenu=gestioneUtente.getSottoMenu(cf, sceltaGestione, gestioneRuolo, gestioneGruppo, gestioneUtente);
				int i=1;
				for(String p:sottoMenu) {
					System.out.println("---"+i+"."+" "+p+" ---");
					i++;
				}
				System.out.println("0. Esci");
				System.out.print("Scelta: ");
				int scelta = scanner.nextInt();
				scanner.nextLine();
				String sceltaAuto =null;
				if(scelta==0) {
					sceltaAuto="0";
				}else if(scelta>sottoMenu.size()) {
					System.out.print("Scelta non disponibile");
					break;
				}else {
					sceltaAuto=sottoMenu.get(scelta-1);
				}
				try {
					switch (sceltaAuto) {
					case "aggiungiAuto":
						aggiungiAuto(gestioneAuto, scanner);                        
						break;
					case "eliminaAuto":
						rimuoviAuto(gestioneAuto, scanner);                        
						break;
					case "modificaAuto":
						modificaAuto(gestioneAuto, scanner);                      
						break;
					case "4":
						cercaAutoPerTarga(gestioneAuto, scanner);
						break;
					case "visualizzaAuto":
						gestioneAuto.stampaTutteLeAuto();
						break;
					case "6":
						filtroMarca(gestioneAuto, scanner);
						break;
					case "7":
						gestioneAuto.ordinaPerPrezzoMax();
						System.out.println("Auto ordinate per prezzo.");
						break;
					case "8":
						double chilometraggioMedio = gestioneAuto.calcolaChilometraggioMedio();
						System.out.println("Chilometraggio medio: " + chilometraggioMedio);
						break;
					case "9":
						Auto autoPiuVecchia = gestioneAuto.trovaAutoPiuVecchia();
						if (autoPiuVecchia != null) {
							System.out.println("Auto più vecchia: " + autoPiuVecchia);
						} else {
							System.out.println("Nessuna auto presente.");
						}
						break;
					case "10":
						autoOltreKm(gestioneAuto,scanner);
						break;
					case "11":
						associaAuto(gestioneAuto,gestioneUtente,scanner);
						break;
					case "0":
						System.out.println("Uscita.");
						sceltaGestione="0";
						break;
					default:
						System.out.println("Scelta non valida.");
					}
				} catch (Exception e) {
					System.out.println("Errore: " + e.getMessage());
				}
			}

			switch(sceltaGestione) {

			case "gestioneUtente":
				System.out.println("\n--- GESTIONE UTENTE ---");
				List<String> sottoMenu=gestioneUtente.getSottoMenu(cf, sceltaGestione, gestioneRuolo, gestioneGruppo, gestioneUtente);
				int i=1;
				for(String p:sottoMenu) {
					System.out.println("---"+i+"."+" "+p+" ---");
					i++;
				}
				System.out.println("0. Esci");
				System.out.print("Scelta: ");
				int scelta = scanner.nextInt();
				scanner.nextLine();
				String sceltaUtente =null;
				if(scelta==0) {
					sceltaUtente="0";
				}else if(scelta>sottoMenu.size()) {
					System.out.print("Scelta non disponibile");
					break;
				}else {
					sceltaUtente=sottoMenu.get(scelta-1);
				}
				try {
					switch (sceltaUtente) {
					case "creaUtente":
						creaUtente(gestioneUtente, scanner);
						break;
					case "modificaUtente":
						modificaUtente(gestioneUtente, scanner);
						break;
					case "eliminaUtente":
						eliminaUtente(gestioneUtente, scanner,gestioneAuto);
						break;
					case "visualizzaUtenti":
						gestioneUtente.visualizzaUtenti();
						break;
					case "cercaUtentePerCodiceFiscale":
						cercaUtentePerCodiceFiscale(gestioneUtente, scanner);
						break;
					case"associaUtenteRuolo":
						associaUtenteRuolo(gestioneUtente,scanner,gestioneRuolo);
						break;
					case "0":
						System.out.println("Uscita.");
						sceltaGestione="0";
						break;
					default:
						System.out.println("Scelta non valida.");
					}
				} catch (Exception e) {
					System.out.println("Errore: " + e.getMessage());
				}

			}

			switch(sceltaGestione) {
			case("gestioneRuolo"):
				System.out.println("\n--- GESTIONE RUOLO ---");
			List<String> sottoMenu=gestioneUtente.getSottoMenu(cf, sceltaGestione, gestioneRuolo, gestioneGruppo, gestioneUtente);
			int i=1;
			for(String p:sottoMenu) {
				System.out.println("---"+i+"."+" "+p+" ---");
				i++;
			}
			System.out.println("0. Esci");
			System.out.print("Scelta: ");
			int scelta = scanner.nextInt();
			scanner.nextLine();
			String sceltaRuolo =null;
			if(scelta==0) {
				sceltaRuolo="0";
			}else if(scelta>sottoMenu.size()) {
				System.out.print("Scelta non disponibile");
				break;
			}else {
				sceltaRuolo=sottoMenu.get(scelta-1);
			}
			try {
				switch (sceltaRuolo) {
				case "aggiungiRuolo":
					creaRuolo(gestioneRuolo, scanner);
					break;
				case "modificaRuolo":
					modificaRuolo(gestioneRuolo, scanner);
					break;
				case "eliminaRuolo":                        
					eliminaRuolo(gestioneRuolo, scanner,gestioneUtente);
					break;
				case "visualizzaRuoli":
					gestioneRuolo.visualizzaRuoli();
					break;
				case "cercaRuoloPerNome":
					cercaRuoloPerNome(gestioneRuolo, scanner);
					break;
				case "0":
					System.out.println("Uscita.");
					sceltaGestione="0";
					break;
				default:
					System.out.println("Scelta non valida.");
				}
			} catch (Exception e) {
				System.out.println("Errore: " + e.getMessage());
			}

			}

			switch(sceltaGestione) {
			case("gestionePermesso"):
				System.out.println("\n--- GESTIONE PERMESSO ---");
			List<String> sottoMenu=gestioneUtente.getSottoMenu(cf, sceltaGestione, gestioneRuolo, gestioneGruppo, gestioneUtente);
			int i=1;
			for(String p:sottoMenu) {
				System.out.println("---"+i+"."+" "+p+" ---");
				i++;
			}
			System.out.println("0. Esci");
			System.out.print("Scelta: ");
			int scelta = scanner.nextInt();
			scanner.nextLine();
			String sceltaPermesso =null;
			if(scelta==0) {
				sceltaPermesso="0";
			}else if(scelta>sottoMenu.size()) {
				System.out.print("Scelta non disponibile");
				break;
			}else {
				sceltaPermesso=sottoMenu.get(scelta-1);
			}
			try {
				switch (sceltaPermesso) {
				case "aggiungiPermesso":
					creaPermesso(gestionePermesso, scanner);
					break;
				case "modificaPermesso":
					modificaPermesso(gestionePermesso, scanner);
					break;
				case "eliminaPermesso":                        
					eliminaPermesso(gestionePermesso, scanner,gestioneRuolo,gestioneGruppo);
					break;
				case "visualizzaPermessi":
					gestionePermesso.visualizzaPermessi();
					break;
				case "cercaPermessoPerNome":
					cercaPermessoPerNome(gestionePermesso, scanner);
					break;
				case "associaPermessoRuolo":
					associaPermessoRuolo(gestioneRuolo, gestionePermesso,scanner);
					break;
				case "dissociaPermessoRuolo":
					dissociaPermessoRuolo(gestioneRuolo, gestionePermesso,scanner);
					break;
				case "associaPermessoGruppo":
					associaPermessoGruppo(gestionePermesso,gestioneGruppo,scanner);
					break;
				case "dissociaPermessoGruppo":
					dissociaPermessoGruppo(gestionePermesso,gestioneGruppo,scanner);
					break;
				case "0":
					System.out.println("Uscita.");
					sceltaGestione="0";
					break;
				default:
					System.out.println("Scelta non valida.");
				}
			} catch (Exception e) {
				System.out.println("Errore: " + e.getMessage());
			}
			}
			switch(sceltaGestione) {
			case("gestioneGruppo"):
				System.out.println("\n--- GESTIONE GRUPPI ---");
			List<String> sottoMenu=gestioneUtente.getSottoMenu(cf, sceltaGestione, gestioneRuolo, gestioneGruppo, gestioneUtente);
			int i=1;
			for(String p:sottoMenu) {
				System.out.println("---"+i+"."+" "+p+" ---");
				i++;
			}
			System.out.println("0. Esci");
			System.out.print("Scelta: ");
			int scelta = scanner.nextInt();
			scanner.nextLine();
			String sceltaGruppo =null;
			if(scelta==0) {
				sceltaGruppo="0";
			}else if(scelta>sottoMenu.size()) {
				System.out.print("Scelta non disponibile");
				break;
			}else {
				sceltaGruppo=sottoMenu.get(scelta-1);
			}
			try {
				switch (sceltaGruppo) {
				case "aggiungiGruppo":
					creaGruppo(gestioneGruppo, scanner);
					break;
				case "modificaGruppo":
					modificaGruppo(gestioneGruppo, scanner);
					break;
				case "eliminaGruppo":                        
					eliminaGruppo(gestioneGruppo, scanner,gestioneRuolo);
					break;
				case "visualizzaGruppi":
					gestioneGruppo.visualizzaGruppo();
					break;
				case "cercaGruppoPerNome":
					cercaGruppoPerNome(gestioneGruppo, scanner);
					break;

				case "0":
					System.out.println("Uscita.");
					sceltaGestione="0";
					break;
				default:
					System.out.println("Scelta non valida.");
				}
			} catch (Exception e) {
				System.out.println("Errore: " + e.getMessage());
			}
			}

		}//FINE WHILE
	}//FINE MAIN



	private static void associaUtenteRuolo(GestioneUtente gestioneUtente, Scanner scanner,
			GestioneRuolo gestioneRuolo) {
		System.out.print("Codice fiscale: ");
		String codiceFiscale = scanner.nextLine();
		System.out.print("Nome ruolo: ");
		String ruolo = scanner.nextLine();  
		
		gestioneUtente.associaUtenteRuolo(gestioneRuolo,codiceFiscale,ruolo);
		
	}



	private static void bootstrap(GestioneUtente gestioneUtente, GestioneRuolo gestioneRuolo, GestionePermesso gestionePermesso, GestioneGruppo gestioneGruppo) {
		gestioneUtente.creaUtente("admin", "admin", 0, "admin", "admin", "admin");
		gestioneUtente.creaUtente("gestore", "gestore", 0, "gestore", "gestore", "gestore");
		
		gestioneRuolo.creaRuolo("admin");
		gestioneRuolo.creaRuolo("gestore");
		gestioneRuolo.creaRuolo("user");
		
		gestionePermesso.aggiungiPermesso("creaUtente");
		gestionePermesso.aggiungiPermesso("modificaUtente");
		gestionePermesso.aggiungiPermesso("eliminaUtente");
		gestionePermesso.aggiungiPermesso("visualizzaUtenti");
		gestionePermesso.aggiungiPermesso("cercaUtentePerCodiceFiscale");
	    gestionePermesso.aggiungiPermesso("associaUtenteRuolo");
		
		gestionePermesso.aggiungiPermesso("aggiungiRuolo");
		gestionePermesso.aggiungiPermesso("modificaRuolo");
		gestionePermesso.aggiungiPermesso("eliminaRuolo");
		gestionePermesso.aggiungiPermesso("visualizzaRuoli");
		gestionePermesso.aggiungiPermesso("cercaRuoloPerNome");
		
		gestionePermesso.aggiungiPermesso("aggiungiPermesso");
		gestionePermesso.aggiungiPermesso("modificaPermesso");
		gestionePermesso.aggiungiPermesso("eliminaPermesso");
		gestionePermesso.aggiungiPermesso("visualizzaPermessi");
		gestionePermesso.aggiungiPermesso("cercaPermessoPerNome");
		gestionePermesso.aggiungiPermesso("associaPermessoRuolo");
		gestionePermesso.aggiungiPermesso("dissociaPermessoRuolo");
		gestionePermesso.aggiungiPermesso("associaPermessoGruppo");
		gestionePermesso.aggiungiPermesso("dissociaPermessoGruppo");
		
		gestionePermesso.aggiungiPermesso("aggiungiGruppo");
		gestionePermesso.aggiungiPermesso("modificaGruppo");
		gestionePermesso.aggiungiPermesso("eliminaGruppo");
		gestionePermesso.aggiungiPermesso("visualizzaGruppi");
		gestionePermesso.aggiungiPermesso("cercaGruppoPerNome");
	
		gestioneGruppo.aggiungiGruppo("gestioneUtente");
		gestioneGruppo.aggiungiGruppo("gestioneRuolo");
		gestioneGruppo.aggiungiGruppo("gestioneGruppo");
		gestioneGruppo.aggiungiGruppo("gestionePermesso");
		
		gestioneUtente.associaUtenteRuolo(gestioneRuolo, "admin", "admin");
		gestioneUtente.associaUtenteRuolo(gestioneRuolo, "gestore", "gestore");
		
		
		for(Permesso p:gestionePermesso.getListaPermessi()) {
			gestionePermesso.associaPermessoRuolo("admin", p.getNome(), gestioneRuolo);
		}
		
		gestionePermesso.associaPermessoRuolo("gestore", "creaUtente", gestioneRuolo);
		gestionePermesso.associaPermessoRuolo("gestore", "modificaUtente", gestioneRuolo);
		gestionePermesso.associaPermessoRuolo("gestore", "eliminaUtente", gestioneRuolo);
		gestionePermesso.associaPermessoRuolo("gestore", "visualizzaUtenti", gestioneRuolo);
		gestionePermesso.associaPermessoRuolo("gestore", "cercaUtentePerCodiceFiscale", gestioneRuolo);
		
		gestionePermesso.associaPermessoRuolo("gestore", "visualizzaPermessi", gestioneRuolo);
		gestionePermesso.associaPermessoRuolo("gestore", "cercaPermessoPerNome", gestioneRuolo);
		gestionePermesso.associaPermessoRuolo("gestore", "associaPermessoRuolo", gestioneRuolo);
		gestionePermesso.associaPermessoRuolo("gestore", "associaPermessoGruppo", gestioneRuolo);
		
		gestionePermesso.associaPermessoRuolo("gestore", "aggiungiGruppo", gestioneRuolo);
		gestionePermesso.associaPermessoRuolo("gestore", "visualizzaGruppi", gestioneRuolo);
		gestionePermesso.associaPermessoRuolo("gestore", "cercaGruppoPerNome", gestioneRuolo);
				
		gestionePermesso.associaPermessoGruppo("gestioneUtente", "creaUtente", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneUtente", "modificaUtente", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneUtente", "eliminaUtente", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneUtente", "visualizzaUtenti", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneUtente", "cercaUtentePerCodiceFiscale", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneUtente", "associaUtenteRuolo", gestioneGruppo);

		gestionePermesso.associaPermessoGruppo("gestioneRuolo", "aggiungiRuolo", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneRuolo", "modificaRuolo", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneRuolo", "eliminaRuolo", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneRuolo", "visualizzaRuoli", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneRuolo", "cercaRuoloPerNome", gestioneGruppo);
		
		gestionePermesso.associaPermessoGruppo("gestioneGruppo", "aggiungiGruppo", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneGruppo", "modificaGruppo", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneGruppo", "eliminaGruppo", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneGruppo", "visualizzaGruppi", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestioneGruppo", "cercaGruppoPerNome", gestioneGruppo);
		
		gestionePermesso.associaPermessoGruppo("gestionePermesso", "aggiungiPermesso", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestionePermesso", "modificaPermesso", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestionePermesso", "eliminaPermesso", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestionePermesso", "visualizzaPermessi", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestionePermesso", "cercaPermessoPerNome", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestionePermesso", "associaPermessoRuolo", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestionePermesso", "dissociaPermessoRuolo", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestionePermesso", "associaPermessoGruppo", gestioneGruppo);
		gestionePermesso.associaPermessoGruppo("gestionePermesso", "dissociaPermessoGruppo", gestioneGruppo);
		
		System.out.println("primo start fatto");
	}



	private static Utente accedi(GestioneUtente gestioneUtente, Scanner scanner) {
		System.out.print("Username: ");
		String username = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();        
		return gestioneUtente.accedi(username, password);
	}


	private static void aggiungiAuto(GestioneAuto gestioneAuto, Scanner scanner) {
		try {
			System.out.print("Targa: ");
			String targa = scanner.nextLine();
			System.out.print("Marca: ");
			String marca = scanner.nextLine();
			System.out.print("Modello: ");
			String modello = scanner.nextLine();
			System.out.print("Anno di immatricolazione: ");
			int anno = scanner.nextInt();
			scanner.nextLine();
			System.out.print("Chilometraggio: ");
			double km = scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Prezzo: ");
			double prezzo = scanner.nextDouble();
			scanner.nextLine();

			gestioneAuto.aggiungiAuto(targa, marca, modello, anno, km, prezzo);

			System.out.println("Auto aggiunta.");
		} catch (InputMismatchException e) {
			System.out.println("Errore: Input non valido. Assicurati di inserire il tipo di dato corretto.");
			scanner.nextLine();
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}

	private static void rimuoviAuto(GestioneAuto gestioneAuto, Scanner scanner) {
		System.out.print("Targa dell'auto da rimuovere: ");
		String targa = scanner.nextLine();
		scanner.nextLine();
		try {
			gestioneAuto.rimuoviAuto(targa);
			System.out.println("Auto rimossa.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}

	private static void modificaAuto(GestioneAuto gestioneAuto, Scanner scanner) {
		System.out.print("Targa dell'auto da modificare: ");
		String targa = scanner.nextLine();
		try {
			System.out.print("Nuovo marca: ");
			String nuovaMarca=scanner.nextLine();
			System.out.print("Nuovo modello: ");
			String nuovaModello=scanner.nextLine();
			System.out.print("Nuovo anno di immatricolazione: ");
			int nuovoAnnoImmatricolazione =scanner.nextInt();
			System.out.print("Nuovo prezzo: ");
			double nuovoPrezzo = scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Nuovo chilometraggio: ");
			int nuovoChilometraggio = scanner.nextInt();
			scanner.nextLine();

			gestioneAuto.modificaAuto(targa, nuovaMarca,nuovaModello, nuovoAnnoImmatricolazione,nuovoPrezzo, nuovoChilometraggio);
			System.out.println("Auto modificata.");
		} catch (InputMismatchException e) {
			System.out.println("Errore: Input non valido. Assicurati di inserire un numero.");
			scanner.nextLine();
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}

	private static void cercaAutoPerTarga(GestioneAuto gestioneAuto, Scanner scanner) {
		System.out.print("Targa dell'auto da cercare: ");
		String targa = scanner.nextLine();
		scanner.nextLine();
		Auto auto = gestioneAuto.cercaAutoPerTarga(targa);
		if (auto != null) {
			System.out.println("Auto trovata: " + auto);
		} else {
			System.out.println("Auto non trovata.");
		}
	}

	private static void filtroMarca(GestioneAuto gestioneAuto, Scanner scanner) {
		System.out.print("Marca da filtrare: ");
		String marca = scanner.nextLine();
		List<Auto> autoFiltrate = gestioneAuto.filtroMarca(marca);
		if (autoFiltrate.isEmpty()) {
			System.out.println("Nessuna auto trovata per questa marca.");
		} else {
			autoFiltrate.forEach(System.out::println);
		}
	}

	private static void autoOltreKm(GestioneAuto gestioneAuto, Scanner scanner) {
		System.out.print("Chilometraggio: ");
		double km = scanner.nextDouble();
		List<Auto> trovata=gestioneAuto.trovaAutoSopraKm(km);
		if ( trovata != null ) {
			System.out.println("Le auto oltre la soglia sono: " + trovata);
		} else {
			System.out.println("Nessuna auto supera la soglia.");
		}
	}

	private static void associaAuto(GestioneAuto gestioneAuto,GestioneUtente gestioneUtente, Scanner scanner) {
		System.out.print("Targa dell'auto da associare: ");
		String targa = scanner.nextLine();
		System.out.print("Codice fiscale da associare: ");
		String codiceFiscale = scanner.nextLine();

		gestioneAuto.associaAutoUtente(targa, codiceFiscale, gestioneUtente);

		System.out.println("Auto e utente associati.");
	}


	private static void creaUtente(GestioneUtente gestioneUtente, Scanner scanner) {
		try {
			System.out.print("Nome: ");
			String nome = scanner.nextLine();
			System.out.print("Cognome: ");
			String cognome = scanner.nextLine();
			System.out.print("Età: ");
			int età = scanner.nextInt();
			scanner.nextLine();
			System.out.print("Codice fiscale: ");
			String codiceFiscale = scanner.nextLine();
			System.out.print("Username: ");
			String username = scanner.nextLine();
			System.out.print("Password: ");
			String password = scanner.nextLine();

			gestioneUtente.creaUtente(nome, cognome, età, codiceFiscale,username,password);

			System.out.println("Utente aggiunto.");
		} catch (InputMismatchException e) {
			System.out.println("Errore: Input non valido. Assicurati di inserire il tipo di dato corretto.");
			scanner.nextLine();
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}


	private static void eliminaUtente(GestioneUtente gestioneUtente, Scanner scanner, GestioneAuto gestioneAuto) {
		System.out.print("Codice fiscale dell'utente da rimuovere: ");
		String codiceFiscale = scanner.nextLine();
		try {
			gestioneUtente.eliminaUtente(codiceFiscale, gestioneAuto);
			System.out.println("Utente rimosso.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}

	private static void modificaUtente(GestioneUtente gestioneUtente, Scanner scanner) {
		System.out.print("Codice fiscale dell'utente da modificare: ");
		String codiceFiscale = scanner.nextLine();
		try {
			System.out.print("Nuovo nome: ");
			String nuovoNome=scanner.nextLine();
			System.out.print("Nuovo cognome: ");
			String nuovoCognome=scanner.nextLine();
			System.out.print("Nuova età: ");
			int nuovaEtà =scanner.nextInt();
			scanner.nextLine();


			gestioneUtente.modificaUtente(codiceFiscale, nuovoNome,nuovoCognome, nuovaEtà);
			System.out.println("Utente modificato.");
		} catch (InputMismatchException e) {
			System.out.println("Errore: Input non valido. Assicurati di inserire un numero.");
			scanner.nextLine();
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}

	private static void cercaUtentePerCodiceFiscale(GestioneUtente gestioneUtente, Scanner scanner) {
		System.out.print("Codice fiscale dell'utente da cercare: ");
		String codiceFiscale = scanner.nextLine();
		scanner.nextLine();
		Utente utente = gestioneUtente.cercaUtentePerCodiceFiscale(codiceFiscale);
		if (utente != null) {
			System.out.println("Utente trovato: " + utente);
		} else {
			System.out.println("Utente non trovato.");
		}

	}



	private static void creaRuolo(GestioneRuolo gestioneRuolo, Scanner scanner) {
		try {
			System.out.print("Nome: ");
			String nome = scanner.nextLine();            
			gestioneRuolo.creaRuolo(nome);
			System.out.println("Ruolo aggiunto.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}

	}

	private static void cercaRuoloPerNome(GestioneRuolo gestioneRuolo, Scanner scanner) {
		System.out.print("Nome del ruolo da cercare: ");
		String nome = scanner.nextLine();
		Ruolo ruolo = gestioneRuolo.cercaRuoloPerNome(nome);
		if (ruolo != null) {
			System.out.println("Utente trovato: " + ruolo);
		} else {
			System.out.println("Utente non trovato.");
		}

	}

	private static void modificaRuolo(GestioneRuolo gestioneRuolo, Scanner scanner) {
		System.out.print("Nome del ruolo da modificare: ");
		String nome = scanner.nextLine();
		try {
			System.out.print("Nuovo nome: ");
			String nuovoNome=scanner.nextLine();

			gestioneRuolo.modificaRuolo(nome, nuovoNome);
			System.out.println("Ruolo modificato.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}

	}

	private static void eliminaRuolo(GestioneRuolo gestioneRuolo, Scanner scanner, GestioneUtente gestioneUtente) {
		System.out.print("Ruolo da rimuovere: ");
		String nome = scanner.nextLine();
		try {
			gestioneRuolo.eliminaRuolo(nome, gestioneUtente);
			System.out.println("Ruolo rimosso.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}

	}


	private static void associaPermessoRuolo(GestioneRuolo gestioneRuolo, GestionePermesso gestionePermesso,
			Scanner scanner) {
		System.out.print("Nome del ruolo da associare: ");
		String nomeRuolo = scanner.nextLine(); 
		System.out.print("Nome del permesso da associare: ");
		String nomePermesso = scanner.nextLine(); 

		gestionePermesso.associaPermessoRuolo(nomeRuolo, nomePermesso, gestioneRuolo);
		System.out.println("Ruolo e permesso associati.");
	}



	private static void dissociaPermessoRuolo(GestioneRuolo gestioneRuolo, GestionePermesso gestionePermesso,
			Scanner scanner) {
		System.out.print("Nome del ruolo da dissociare: ");
		String nomeRuolo = scanner.nextLine(); 
		System.out.print("Nome del permesso da dissociare: ");
		String nomePermesso = scanner.nextLine();

		gestionePermesso.dissociaPermessoRuolo(nomeRuolo, nomePermesso, gestioneRuolo);
		System.out.println("Ruolo e permesso dissociati.");
	}


	private static void creaPermesso(GestionePermesso gestionePermesso, Scanner scanner) {
		try {
			System.out.print("Nome: ");
			String nome = scanner.nextLine();            
			gestionePermesso.aggiungiPermesso(nome);
			System.out.println("Permesso aggiunto.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}

	}


	private static void modificaPermesso(GestionePermesso gestionePermesso, Scanner scanner) {
		System.out.print("Nome del permesso da modificare: ");
		String nome = scanner.nextLine();
		try {
			System.out.print("Nuovo nome: ");
			String nuovoNome=scanner.nextLine();

			gestionePermesso.modificaPermesso(nome, nuovoNome);
			System.out.println("Ruolo modificato.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}

	}


	private static void eliminaPermesso(GestionePermesso gestionePermesso, Scanner scanner, GestioneRuolo gestioneRuolo,GestioneGruppo gestioneGruppo) {
		System.out.print("Permesso da rimuovere: ");
		String nome = scanner.nextLine();
		try {
			gestionePermesso.eliminaPermesso(nome, gestioneRuolo,gestioneGruppo);
			System.out.println("Permesso rimosso.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}


	private static void cercaPermessoPerNome(GestionePermesso gestionePermesso, Scanner scanner) {
		System.out.print("Nome del permesso da cercare: ");
		String nome = scanner.nextLine();
		Permesso permesso = gestionePermesso.cercaPermessoPerNome(nome);
		if (permesso != null) {
			System.out.println("Utente trovato: " + permesso);
		} else {
			System.out.println("Utente non trovato.");
		}

	}



	private static void creaGruppo(GestioneGruppo gestioneGruppo, Scanner scanner) {
		try {
			System.out.print("Nome: ");
			String nome = scanner.nextLine();            
			gestioneGruppo.aggiungiGruppo(nome);
			System.out.println("Gruppo aggiunto.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}

	}


	private static void modificaGruppo(GestioneGruppo gestioneGruppo, Scanner scanner) {
		System.out.print("Nome del gruppo da modificare: ");
		String nome = scanner.nextLine();
		try {
			System.out.print("Nuovo nome: ");
			String nuovoNome=scanner.nextLine();
			gestioneGruppo.modificaGruppo(nome, nuovoNome);
			System.out.println("Gruppo modificato.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}

	}

	private static void eliminaGruppo(GestioneGruppo gestioneGruppo, Scanner scanner, GestioneRuolo gestioneRuolo) {
		System.out.print("Gruppo da rimuovere: ");
		String nome = scanner.nextLine();
		try {
			gestioneGruppo.eliminaGruppo(nome);
			System.out.println("Gruppo rimosso.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}

	}


	private static void cercaGruppoPerNome(GestioneGruppo gestioneGruppo, Scanner scanner) {
		System.out.print("Nome del gruppo da cercare: ");
		String nome = scanner.nextLine();
		Gruppo gruppo = gestioneGruppo.cercaGruppoPerNome(nome);
		if (gruppo != null) {
			System.out.println("Gruppo trovato: " + gruppo);
		} else {
			System.out.println("Gruppo non trovato.");
		}

	}

	private static void associaPermessoGruppo(GestionePermesso gestionePermesso,GestioneGruppo gestioneGruppo, 
			Scanner scanner) {
		System.out.print("Nome del gruppo da associare: ");
		String nomeGrupo = scanner.nextLine();
		System.out.print("Nome del permesso da associare: ");
		String nomePermesso = scanner.nextLine();

		gestionePermesso.associaPermessoGruppo(nomeGrupo, nomePermesso, gestioneGruppo);
		System.out.println("Gruppo e permesso associati.");
	}


	private static void dissociaPermessoGruppo(GestionePermesso gestionePermesso,GestioneGruppo gestioneGruppo, 
			Scanner scanner) {
		System.out.print("Nome del gruppo da dissociare: ");
		String nomeGrupo = scanner.nextLine();
		System.out.print("Nome del permesso da dissociare: ");
		String nomePermesso = scanner.nextLine();

		gestionePermesso.dissociaPermessoGruppo(nomeGrupo, nomePermesso, gestioneGruppo);
		System.out.println("Gruppo e permesso dissociati.");
	}


}
