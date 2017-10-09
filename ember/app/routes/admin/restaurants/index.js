import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  queryParams: {
    currentPage: {
      refreshModel: true,
    },
  },
  model(params) {
    return Ember.RSVP.hash({
      restaurants: this.get('ajax').request('/getAllRestaurants?pageSize=18&pageNumber=' + params.currentPage),
    });
  },
});
