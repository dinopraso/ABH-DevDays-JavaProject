import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model(params) {
    return Ember.RSVP.hash({
      cuisine: this.get('ajax').request('/getCuisine/' + params.cuisine_id),
    });
  },
});
