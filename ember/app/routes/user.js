import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Route.extend({
  ajax: service('ajax'),

  model() {
    return Ember.RSVP.hash({
      user: this.get('ajax').request('/getCurrentUser', {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });
  },

  afterModel(model) {
    if (!model.user.isLoggedIn) {
      this.transitionTo('index');
    }

    if (model.user.object.isAdmin) {
      this.transitionTo('admin');
    }
  },
});
