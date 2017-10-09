import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  actions: {
    logout() {
      this.get('ajax').post('/logout', {
        xhrFields: {
          withCredentials: true,
        },
      })
      .then(() => this.transitionToRoute('index'));
    },
  },
});
