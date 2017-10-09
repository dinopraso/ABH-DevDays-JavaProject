import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model(params) {
    return Ember.RSVP.hash({
      restaurant: this.get('ajax').request('/getRestaurant/' + params.restaurant_id),
    });
  },
});
