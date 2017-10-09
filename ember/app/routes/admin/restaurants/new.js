import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model() {
    return Ember.RSVP.hash({
      restaurant: {},
      cities: this.get('ajax').request('/getAllCities'),
      cuisines: this.get('ajax').request('/getAllCuisines'),
    });
  },

  afterModel(model) {
    model.restaurant.city = model.cities[0];
    model.restaurant.priceRange = 1;
    model.restaurant.cuisines = [];
    model.restaurant.tables = [];
  },
});
