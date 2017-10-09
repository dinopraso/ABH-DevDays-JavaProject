import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model() {
    return Ember.RSVP.hash({
      users: this.get('ajax').request('/admin/getAllUsers', {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });
  },
});
