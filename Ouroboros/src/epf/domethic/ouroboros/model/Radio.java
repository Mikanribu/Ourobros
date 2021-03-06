package epf.domethic.ouroboros.model;

public class Radio {

	private int id_patient;
	private String titre;
	private String nomRadio;
	private String cause;
	private String date;
	private String medecin;
	private String description;
	private String interpretation;

	public Radio(int id_patient, String titre, String nomRadio, String cause, String date,
			String medecin, String description, String interpretation) {
		super();
		this.id_patient = id_patient;
		this.titre = titre;
		this.nomRadio = nomRadio;
		this.cause = cause;
		this.date = date;
		this.medecin = medecin;
		this.description = description;
		this.interpretation = interpretation;
	}

	public int getId_patient() {
		return id_patient;
	}

	public void setId_patient(int id_patient) {
		this.id_patient = id_patient;
	}

	public Radio() {
		super();

	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getNomRadio() {
		return nomRadio;
	}

	public void setNomRadio(String nomRadio) {
		this.nomRadio = nomRadio;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMedecin() {
		return medecin;
	}

	public void setMedecin(String medecin) {
		this.medecin = medecin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

}
