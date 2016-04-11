package com.porodem.cartrigearta;

/**
 * Created by porod on 09.04.2016.
 */
public class Cartridge {

    String cID;
    String cModel;
    String cMark;
    String cProblem;
    String cFix;
    String cCost;
    String cUser;
    String cDate;
    String cStatus;

    public void Cartridge(String caID, String caModel, String caMark, String caProblem, String caFix,
                          String caCost, String caUser, String caDate, String caStatus) {
        cID = caID;
        cModel = caModel;
        cMark = caMark;
        cProblem = caProblem;
        cFix = caFix;
        cCost = caCost;
        cUser = caUser;
        cDate = caDate;
        cStatus = caStatus;

    }
}
