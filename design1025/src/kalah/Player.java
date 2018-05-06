package kalah;
public class Player
{
	private String playerName ;
	private House houses ;
	private Store store ;
	private boolean isMyTurn ;
	private int initalHouseSeeds ;
	private int numOfHouses ;
	Player ( String playerName , House houses ,int initialHouseSeeds ,  Store  store)
	{
		this.playerName = playerName ;
		this.houses = houses ;
		this.numOfHouses = houses.GetHouseLength() ;
		this.store = store ;
		this.initalHouseSeeds = initialHouseSeeds ;
		InitHouses () ;
	}
	int GetNumOfHouses()
	{
		return this.numOfHouses ;
	}
	void InitHouses ()
	{
		for ( int i = 1 ; i < this.houses.GetHouseLength() ; i ++ )
		{
			this.houses.setHouse(i, this.initalHouseSeeds);
		}
	}
	boolean IsStop ()
	{
		boolean stop ;
		stop = true ;
		for ( int i = 1 ; i < this.houses.GetHouseLength() ; i ++ )
		{
			if ( this.houses.GetSeedsOfHouse(i) != 0 )
			{
				stop = false ;
				break ;
			}
		}
		return stop ;
	}
	int GetLastIndex ( int lastIndex , int restSeeds)
	{
		while ( restSeeds > 1 )
		{
			restSeeds -- ;
			if (  lastIndex == this.houses.GetHouseLength()  && restSeeds > 0 )
			{
				break ;
			}
			lastIndex++ ;
		}
		return  lastIndex == this.houses.GetHouseLength() && restSeeds > 0 ?  -1 :  lastIndex ;
	}
	int SowSeeds ( int houseIndex  , int restSeeds , boolean addToStore)
	{
		int nextHouseIndex ;
		int numOfSeeds ;
		if ( houseIndex > 0 )
		{
			numOfSeeds = this.houses.GetSeedsOfHouse(houseIndex) ;
			this.houses.setHouse(houseIndex, 0);
			nextHouseIndex = houseIndex + 1 ;
		}
		else
		{
			numOfSeeds = restSeeds ;
			nextHouseIndex = 1 ;
		}
		while ( numOfSeeds > 0 )
		{
			if ( nextHouseIndex < this.houses.GetHouseLength()  )
			{
				this.houses.IncrementOfHouse(nextHouseIndex);
			}
			else if ( nextHouseIndex == this.houses.GetHouseLength()  && addToStore )
			{
				AddSeedsToStore(1) ;
			}
			else
			{
				break ;
			}
			nextHouseIndex++ ;
			numOfSeeds-- ;
		}
		return (nextHouseIndex == this.houses.GetHouseLength()+1 && numOfSeeds == 0) ? -1 : numOfSeeds ;
	}
	int GetSeedsOfHouse ( int index )
	{
		return this.houses.GetSeedsOfHouse(index) ;
	}
	String GetPlayerName ()
	{
		return this.playerName ;
	}
	void SetClearOfHouse ( int index )
	{
		this.houses.ClearOfHouse(index);
	}
	void AddSeedsToStore ( int seeds )
	{
		this.store.AddSeeds(seeds);
	}
	void SetMyTurn ( boolean myTurn )
	{
		this.isMyTurn = myTurn ;
	}
	boolean GetMyTurn ()
	{
		return this.isMyTurn ;
	}
	int GetScore ()
	{
		return this.store.GetScore() ;
	}
}
