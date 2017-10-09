import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  actions: {
    submitCuisine() {
      let isNew = typeof this.get('model.cuisine.id') === 'undefined';
      let method = isNew ? 'post' : 'put';
      let request = '/admin/' + (isNew ? 'createCuisine' : 'editCuisine');

      this.get('ajax')[method](request, {
        xhrFields: {
          withCredentials: true,
        },
        data: this.get('model.cuisine'),
      })
      .then(() => this.transitionToRoute('admin.cuisines'));
    },
  },
});
