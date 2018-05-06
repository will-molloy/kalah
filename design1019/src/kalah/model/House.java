package kalah.model;
public class House extends PitBase{
	private House oppositeHouse = null;
	public House(int seeds) {
		super(seeds);
	}
	public House getOppositeHouse() {
		return oppositeHouse;
	}
	public void setOppositeHouse(House opposite) {
		this.oppositeHouse = opposite;
	}
}
