package com.antazri.model.utils;

/**
 * L'énumération LoanStatus permet de créer une norme quant aux noms utilisés pour définir le statut d'un prêt
 * ({@link com.antazri.model.bean.Loan})
 */
public enum LoanStatus {

    ONGOING (AppMessage.getAppMessages().getString("loan.status.ongoing")),
    ENDED (AppMessage.getAppMessages().getString("loan.status.ended")),
    EXTENDED (AppMessage.getAppMessages().getString("loan.status.extended")),
    DELAYED (AppMessage.getAppMessages().getString("loan.status.delayed"));

    private String name;

    LoanStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
