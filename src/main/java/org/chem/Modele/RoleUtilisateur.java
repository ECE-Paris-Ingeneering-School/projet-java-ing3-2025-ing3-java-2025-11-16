package org.chem.Modele;

public enum RoleUtilisateur {
    ADMIN("A", "/images/admin.png"),
    PATIENT("P", "/images/patient.png"),
    SPECIALISTE("S", "/images/specialiste.png");

    private final String code;
    private final String imagePath;

    RoleUtilisateur(String code, String imagePath) {
        this.code = code;
        this.imagePath = imagePath;
    }

    public String getCode() {
        return code;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return name() + " (" + code + ") -> " + imagePath;
    }

    public static RoleUtilisateur fromCode(String code) {
        for (RoleUtilisateur role : RoleUtilisateur.values()) {
            if (role.code.equalsIgnoreCase(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Code inconnu : " + code);
    }
}
