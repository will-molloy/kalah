package myPackage;
public class SeedOverFlowAndHouseNumber {
	private int overFlow;
	private int houseNumber;
	public SeedOverFlowAndHouseNumber(int overFlowArg,int houseNumberArg) {
		setOverFlow(overFlowArg);
		setHouseNumber(houseNumberArg);
	}
	public boolean endsInEmpty(){
		if (overFlow<0 & houseNumber>0) {
			return true;
		}
		return false;
	}
	public boolean oneMoreMove(){
		if (overFlow<0 & houseNumber<0) {
			return true;
		}
		return false;
	}
	public int getHouseNumber() {
		return houseNumber;
	}
	private void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
	private void setOverFlow(int overFlowArg) {
		this.overFlow=overFlowArg;
	}
	public int getOverFlow() {
		return overFlow;
	}
}