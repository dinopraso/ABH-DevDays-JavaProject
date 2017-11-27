package models.helpers;

import java.util.UUID;

public class UserHabitsBean
{
    private UUID userId;
    private UUID city;
    private Integer userPriceRange;
    private Double userRatingRange;

    public UUID getCity()
    {
	return city;
    }

    public void setCity(UUID city)
    {
	this.city = city;
    }

    public UUID getUserId()
    {
	return userId;
    }

    public void setUserId(UUID userId)
    {
	this.userId = userId;
    }

    public Integer getUserPriceRange()
    {
	return userPriceRange;
    }

    public void setUserPriceRange(Integer userPriceRange)
    {
	this.userPriceRange = userPriceRange;
    }

    public Double getUserRatingRange()
    {
	return userRatingRange;
    }

    public void setUserRatingRange(Double userRatingRange)
    {
	this.userRatingRange = userRatingRange;
    }

    public UserHabitsBean()
    {
    }
}
