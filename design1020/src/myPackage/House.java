package myPackage;
public class House extends Pit {
	private final int identifierNumber;
	public House(int houseNumber){
		super();
		identifierNumber=houseNumber;
	}
	public boolean hasSeed(){
		return this.seedBank.size()>0;
	}
	public Seed removeAndGetASeed() {
		return this.seedBank.remove(0);
	}
	public int getIdentifierNumber() {
		return identifierNumber;
	}
	public void setSeedCount(int count){
		removeAllSeeds();
		for (int i = 0; i < count; i++) {
			addSeed(new Seed());
		}
	}
	public boolean isEmpty(){
		return seedBank.size()<1;
	}
	@Override
	protected int getInitialNumberOfSeeds() {
		return Variables.initialSeedsInAHouse;
	}
}
