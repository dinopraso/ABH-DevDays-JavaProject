import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model(params) {
    return Ember.RSVP.hash({
      location: this.get('ajax').request('/getCity/' + params.location_id),
    });
  },
});
