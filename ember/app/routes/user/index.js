import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model() {
    return Ember.RSVP.hash({
      userReservations: this.get('ajax').request('/getMyReservations', {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });
  },
});
