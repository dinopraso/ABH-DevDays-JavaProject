import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Route.extend({
  ajax: service('ajax'),

  queryParams: {
    restaurantName: {
      refreshModel: true,
    },
    numberOfPeople: {
      refreshModel: true,
    },
    date: {
      refreshModel: true,
    },
    time: {
      refreshModel: true,
    },
    currentPage: {
      refreshModel: true,
    },
  },

  model(params) {
    return Ember.RSVP.hash({
      params: params,
      restaurants: this.get('ajax').request('/getAllRestaurants?pageNumber=' + params.currentPage + '&pageSize=9&nameFilter=' + params.restaurantName),
      user: this.get('ajax').request('/getCurrentUser', {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });
  },
});
