package myPackage;

public class RuleWillEndAtEmptyHouse implements IRule {
    @Override
    public boolean apply(Object objOne, Object objTwo, IPrinter printer) {
        House house = (House) objOne;
        int seedCount = (int) objTwo;
        if (house.seedCount() == 0 & seedCount < 2) {
            return true;
        } else {
            return false;
        }
    }
}
