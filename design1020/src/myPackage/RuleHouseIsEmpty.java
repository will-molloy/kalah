package myPackage;

public class RuleHouseIsEmpty implements IRule {
    @Override
    public boolean apply(Object objOne, Object objTwo, IPrinter printer) {
        Player currentPlayer = (Player) objOne;
        int houseNumber = (int) objTwo;
        if (currentPlayer.isHouseEmpty(houseNumber)) {
            printer.printHouseIsEmpty();
            return true;
        }
        return false;
    }
}
