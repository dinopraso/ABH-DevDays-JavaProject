import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  queryParams: {
    currentPage: {
      refreshModel: true,
    },
    nameFilter: {
      refreshModel: true,
    },
    priceFilter: {
      refreshModel: true,
    },
    ratingFilter: {
      refreshModel: true,
    },
    cuisineFilter: {
      refreshModel: true,
    },
    sortBy: {
      refreshModel: true,
    },
    city: {
      refreshModel: true,
    },
  },
  model(params) {
    return Ember.RSVP.hash({
      response: this.get('ajax').request('/getAllRestaurants?pageNumber=' + params.currentPage + '&pageSize=9&nameFilter=' + params.nameFilter + '&priceFilter=' + params.priceFilter + '&ratingFilter=' + params.ratingFilter + '&sortBy=' + params.sortBy + '&cuisineFilter=' + params.cuisineFilter + '&cityFilter=' + params.city),
      popularLocations: this.get('ajax').request('/getPopularLocations'),
      cuisines: this.get('ajax').request('/getAllCuisinesAsString'),
      user: this.get('ajax').request('/getCurrentUser', {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });

  },
});
