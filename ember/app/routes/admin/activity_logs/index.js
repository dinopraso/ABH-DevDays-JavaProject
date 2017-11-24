import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model() {
    return Ember.RSVP.hash({
      activity_logs: this.get('ajax').request('/admin/getAllActivityLogs'),
      logUser: this.get('ajax').request('/getCurrentUser', {
          xhrFields: {
            withCredentials: true,
          },
        }),
    });
  },
});