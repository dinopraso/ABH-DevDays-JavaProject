package models.helpers;

import java.util.List;

/**
 * The type Administrator statistics.
 */
public class AdministratorStatistics {
    
    
    	public List<PopularLocation> popularLocations;
        public Long numberOfRestoraunt;
    	public Long numberOfReservation;
    	public Long numberOfCity;
    	public Long numberOfUsers;
    	
    	public List<PopularLocation> getPopularLocations()
	{
	    return popularLocations;
	}



	public AdministratorStatistics setPopularLocations(List<PopularLocation> popularLocations)
	{
	    this.popularLocations = popularLocations;
	    return this;
	}



	public Long getNumberOfRestoraunt()
	{
	    return numberOfRestoraunt;
	}



	public AdministratorStatistics setNumberOfRestoraunt(Long numberOfRestoraunt)
	{
	    this.numberOfRestoraunt = numberOfRestoraunt;
	    return this;
	}



	public Long getNumberOfReservation()
	{
	    return numberOfReservation;
	}



	public AdministratorStatistics setNumberOfReservation(Long numberOfReservation)
	{
	    this.numberOfReservation = numberOfReservation;
	    return this;
	}



	public Long getNumberOfCity()
	{
	    return numberOfCity;
	}



	public AdministratorStatistics setNumberOfCity(Long numberOfCity)
	{
	    this.numberOfCity = numberOfCity;
	    return this;
	}



	public Long getNumberOfUsers()
	{
	    return numberOfUsers;
	}



	public AdministratorStatistics setNumberOfUsers(Long numberOfUsers)
	{
	    this.numberOfUsers = numberOfUsers;
	    return this;
	}



    	
    	private AdministratorStatistics(){}

	public static AdministratorStatistics createStatistics() {
	    return new AdministratorStatistics();
	    
	}

}
