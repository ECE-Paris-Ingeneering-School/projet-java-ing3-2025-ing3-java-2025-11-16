package org.chem.Modele;

public enum TypeUtilisateur {
    ADMIN("A", "/images/admin.png", "Admin" ),
    PATIENT("P", "/images/patient.png", "Patient"),
    SPECIALISTE("S", "/images/specialiste.png", "Spécialiste");

    private final String code;
    private final String imagePath;
    private final String label;

    TypeUtilisateur(String code, String imagePath, String label) {
        this.code = code;
        this.imagePath = imagePath;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return name() + " (" + code + ") -> " + imagePath;
    }

    public static TypeUtilisateur fromCode(String code) {
        for (TypeUtilisateur role : TypeUtilisateur.values()) {
            if (role.code.equalsIgnoreCase(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Code inconnu : " + code);
    }
}
