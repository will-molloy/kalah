package kalah;
public class Store
{
	private int score = 0 ;
	void SetClearStore ()
	{
		this.score = 0 ;
	}
	void AddSeeds( int seeds )
	{
		this.score += seeds ;
	}
	int GetScore ( )
	{
		return this.score ;
	}
}
