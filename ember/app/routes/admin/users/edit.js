import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model(params) {
    return Ember.RSVP.hash({
      user: this.get('ajax').request('/admin/getUser/' + params.user_id, {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });
  },
});
