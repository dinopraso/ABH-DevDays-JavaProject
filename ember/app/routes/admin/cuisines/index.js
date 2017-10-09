import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model() {
    return Ember.RSVP.hash({
      cuisines: this.get('ajax').request('/getAllCuisines'),
    });
  },
});
